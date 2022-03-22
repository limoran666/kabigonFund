package com.kabigon.crowd.handler;

import com.kabigon.crowd.constant.CrowdConstant;
import com.kabigon.crowd.entity.po.MemberPO;
import com.kabigon.crowd.service.api.MemberService;
import com.kabigon.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberProviderHandler {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMember(@RequestBody  MemberPO memberPO){//springboot传来的是json的格式 但是springmvc那套是封装的对象
        try{
            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){

            if (e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            return ResultEntity.failed(e.getMessage());
        }



    }

    @RequestMapping("/get/memberpo/by/login/acct/remote")
   public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){
        try {
            //调用本地service完成查询
            MemberPO memberPO=memberService.getMemberPOByLoginAcct(loginacct);
            //如果没有抛异常就返回成功的结构
            return ResultEntity.successWithData(memberPO);
        } catch (Exception exception) {

            return ResultEntity.failed(exception.getMessage());
        }
    }
}
