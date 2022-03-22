package com.kabigon.crowd.service.api;

import com.kabigon.crowd.entity.po.MemberPO;
import com.kabigon.crowd.entity.po.MemberPOExample;
import com.kabigon.crowd.mapper.MemberPOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {

        //创建一个Example对象
        MemberPOExample memberPOExample = new MemberPOExample();
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();
        //封装查询条件
        criteria.andLoginacctEqualTo(loginacct);
        List<MemberPO> memberPOList = memberPOMapper.selectByExample(memberPOExample);

        if (memberPOList==null||memberPOList.size()==0){
            return null;
        }
        MemberPO memberPO = memberPOList.get(0);
        return memberPO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
    @Override
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);//有值就行 没有就为算了

    }
}
