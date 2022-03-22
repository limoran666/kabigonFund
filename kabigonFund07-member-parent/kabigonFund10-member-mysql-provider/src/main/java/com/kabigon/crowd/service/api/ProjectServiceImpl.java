package com.kabigon.crowd.service.api;

import com.kabigon.crowd.entity.po.MemberConfirminfoPO;
import com.kabigon.crowd.entity.po.MemberLaunchInfoPO;
import com.kabigon.crowd.entity.po.ProjectPO;
import com.kabigon.crowd.entity.po.ReturnPO;
import com.kabigon.crowd.entity.vo.*;
import com.kabigon.crowd.mapper.*;
import com.kabigon.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ReturnPOMapper returnPOMapper;


    @Autowired
    private MemberConfirminfoPOMapper memberConfirminfoPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private ProjectItempicPOMapper projectItempicPOMapper;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        //保存ProjectPO
        //创建一个ProjectPO对象
        ProjectPO projectPO = new ProjectPO();

        //复制属性
        BeanUtils.copyProperties(projectVO,projectPO);

        //修复bug把memberId设置到projectPO中
        projectPO.setMemberid(memberId);

        //修复bug 生成创建时间存入
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(format);
        //修复bug status设置
        projectPO.setStatus(0);//表示即将开始

        //保存projectPO
        //为了能或许其自增主键 要去mapper.xml设置
        projectPOMapper.insertSelective(projectPO);
        //从projectPo获取自增主键
        Integer projectId = projectPO.getId();

        //保存项目分类的关联关系信息
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertRelationship(typeIdList,projectId);

        //保存项目标签关联信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationpeship(tagIdList,projectId);

        //保存项目详情图片路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItempicPOMapper.insertPathList(projectId,detailPicturePathList);


        //保存项目发起人信息

        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO,memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);



        //保存项目回报信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<>();
        for (ReturnVO returnvo:returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnvo,returnPO);
            returnPOList.add(returnPO);
        }




        returnPOMapper.inserReturnPOBatch(returnPOList,projectId);


        //保存项目确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirminfoPO memberConfirminfoPO = new MemberConfirminfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO,memberConfirminfoPO);
        memberConfirminfoPO.setMemberid(memberId);
        memberConfirminfoPOMapper.insert(memberConfirminfoPO);

    }

    @Override
    public List<PortalTypeVO> getPortalType() {
        return projectPOMapper.selectPortaiTypeVOList();


    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectId) {
        //查询得到DetailProjectVO对象
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);
        Integer status = detailProjectVO.getStatus();
        switch (status){
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 3:
                detailProjectVO.setStatusText("已关闭");
                break;
            default:
                break;
        }
        //depoyeDate计算lastDay

        String deployDate = detailProjectVO.getDeployDate();
        //获取当前日期
        Date currentDay = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date deployDay = simpleDateFormat.parse(deployDate);
            //获取当前日期的时间戳
            long currentTimeStamp= currentDay.getTime();
            //获取众筹日期的时间戳
            long deployDayTimeStamp = deployDay.getTime();

            //使用总的众筹时间减去已经过去的时间
            long pastDays=(currentTimeStamp-deployDayTimeStamp)/1000/60/60/24;
            //获取总的众筹天数
            Integer totalDays=detailProjectVO.getDay();
            //使用总的众筹天数减去已经过去的天数得到剩余天数
            Integer lastDay=(int)(totalDays-pastDays);

        } catch (ParseException e) {
            e.printStackTrace();

        }
        return null;
    }
}
