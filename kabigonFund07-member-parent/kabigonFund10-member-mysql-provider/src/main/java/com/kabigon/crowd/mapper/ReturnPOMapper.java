package com.kabigon.crowd.mapper;


import java.util.List;

import com.kabigon.crowd.entity.po.ReturnPO;
import com.kabigon.crowd.entity.po.ReturnPOExample;
import org.apache.ibatis.annotations.Param;

public interface ReturnPOMapper {
    int countByExample(ReturnPOExample example);

    int deleteByExample(ReturnPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReturnPO record);

    int insertSelective(ReturnPO record);

    List<ReturnPO> selectByExample(ReturnPOExample example);

    ReturnPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReturnPO record, @Param("example") ReturnPOExample example);

    int updateByExample(@Param("record") ReturnPO record, @Param("example") ReturnPOExample example);

    int updateByPrimaryKeySelective(ReturnPO record);

    int updateByPrimaryKey(ReturnPO record);

    void inserReturnPOBatch(@Param("returnPOList") List<ReturnPO> returnPOList,@Param("projectId") Integer projectId);
}