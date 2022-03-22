package com.kabigon.crowd.mapper;


import java.util.List;

import com.kabigon.crowd.entity.po.MemberConfirminfoPO;
import com.kabigon.crowd.entity.po.MemberConfirminfoPOExample;
import org.apache.ibatis.annotations.Param;

public interface MemberConfirminfoPOMapper {
    int countByExample(MemberConfirminfoPOExample example);

    int deleteByExample(MemberConfirminfoPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberConfirminfoPO record);

    int insertSelective(MemberConfirminfoPO record);

    List<MemberConfirminfoPO> selectByExample(MemberConfirminfoPOExample example);

    MemberConfirminfoPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberConfirminfoPO record, @Param("example") MemberConfirminfoPOExample example);

    int updateByExample(@Param("record") MemberConfirminfoPO record, @Param("example") MemberConfirminfoPOExample example);

    int updateByPrimaryKeySelective(MemberConfirminfoPO record);

    int updateByPrimaryKey(MemberConfirminfoPO record);
}