package com.kabigon.crowd.handler;

import com.kabigon.crowd.api.MySQLRemoteService;
import com.kabigon.crowd.api.RedisRemoteService;
import com.kabigon.crowd.config.ShortMessageProperties;
import com.kabigon.crowd.constant.CrowdConstant;
import com.kabigon.crowd.entity.po.MemberPO;
import com.kabigon.crowd.entity.vo.MemberLoginVO;
import com.kabigon.crowd.entity.vo.MemberVO;
import com.kabigon.crowd.util.CrowdUtil;
import com.kabigon.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {
    @Autowired
    private ShortMessageProperties shortMessageProperties;

    @Autowired
    private RedisRemoteService redisRemoteService;
    @Autowired
    private MySQLRemoteService mySQLRemoteService;


    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, ModelMap modelMap){

     //获取用户输入的手机号
        String phoneNum = memberVO.getPhoneNum();

        //拼接Redis中存储验证码的key
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

        //从Redis读取key对应的value
        ResultEntity<String> redisStringValueByKeyRemote = redisRemoteService.getRedisStringValueByKeyRemote(key);

        //检查查询操作是否有效
        String result = redisStringValueByKeyRemote.getResult();

        if (ResultEntity.FAILED.equals(result)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,redisStringValueByKeyRemote.getMessage());
            return "member-reg";

        }
        //走到这里默认验证码的值已经拿到 result success
        String redisCode = redisStringValueByKeyRemote.getData();
        if (redisCode==null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXIT);
        }

                //走到这里 表示redis能够查询到value则比较表单验证码和Redis验证码
                String formCode = memberVO.getCode();

                if (!Objects.equals(formCode,redisCode)){
                    modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_INVALID);
                    return "member-reg";
                }
                    //走到这里表示验证码一致 则从Redis删除
                ResultEntity<String> removeResultEntity = redisRemoteService.removeRedisKeyRemote(key);

                //执行密码加密
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    String userBeforePswd = memberVO.getUserpswd();
                    String userAfterPswd = bCryptPasswordEncoder.encode(userBeforePswd);
                    memberVO.setUserpswd(userAfterPswd);

                    //执行保存
                    //1.创建空的MemberPO对象
                    MemberPO memberPO = new MemberPO();
                    //2.复制属性
                    BeanUtils.copyProperties(memberVO,memberPO);
                    //3.调用远程方法
                ResultEntity<String> saveMemberResultEntity = mySQLRemoteService.saveMember(memberPO);
                //假如用户名已经存在了
                    if (ResultEntity.FAILED.equals(saveMemberResultEntity.getResult())){

                        modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,saveMemberResultEntity.getMessage());
                        return "member-reg";
                    }
                    //使用重定向避免刷新浏览器导致重新执行注册流程
                    return "redirect:/auth/member/to/login/page";

    }



   @RequestMapping("/auth/member/do/login")
   public String doLogin(@RequestParam("loginacct") String loginacct,@RequestParam("userpswd") String userpswd,ModelMap modelMap,HttpSession session){

        //调用远程接口根据登录账号查询MemberPO对象
       ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
       //返回结果为failed的话
       if (ResultEntity.FAILED.equals(resultEntity.getResult())){

           //把错误信息存到modelmap返回
           modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());


           return "member-login";
       }
       MemberPO memberPO=resultEntity.getData();
       if (memberPO==null){
           modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
           return "member-login";
       }
       //2.比较密码
       String userpswdDB= memberPO.getUserpswd();
       BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //因为盐值是随机的
       boolean matchesResult = bCryptPasswordEncoder.matches(userpswd,userpswdDB);
       if (!matchesResult){
           modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
           return "member-login";
       }

       //3.创建MemberLoginVO存入Session域中
       MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,memberLoginVO);
        return "redirect:http://localhost:81/auth/member/to/center/page";//redirect-->带上session


   }

   @RequestMapping("/auth/member/logout")
   public String logout(HttpSession session){

        session.invalidate();//使得session失效
        return "redirect:/";//再次访问 获得新的sessionid
   }







    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum){

        //1.发送验证码到phoneNum手机
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(phoneNum, shortMessageProperties.getAccessKeyId(), shortMessageProperties.getAccessKeySecret(), shortMessageProperties.getSignName(), shortMessageProperties.getTemplateCode());

        //2.判断短信发送结果

        if (sendMessageResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())){
            //3.如果发送成功，就将验证码存入Redis
            String code = sendMessageResultEntity.getData();

            //拼接redis的key
            String key= CrowdConstant.REDIS_CODE_PREFIX+phoneNum;

            //调用远程的接口存入redis
            ResultEntity<String> saveCodeEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 15, TimeUnit.MINUTES);

            if (ResultEntity.SUCCESS.equals(saveCodeEntity.getResult())){

                return ResultEntity.successWithoutData();
            }else {
                return saveCodeEntity;
            }





    }
        else {
            return sendMessageResultEntity;
        }

        }




}
