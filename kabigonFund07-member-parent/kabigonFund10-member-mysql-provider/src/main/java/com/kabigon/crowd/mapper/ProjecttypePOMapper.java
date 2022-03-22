package com.kabigon.crowd.mapper;


import java.util.List;

import com.kabigon.crowd.entity.po.ProjecttypePO;
import com.kabigon.crowd.entity.po.ProjecttypePOExample;
import org.apache.ibatis.annotations.Param;

public interface ProjecttypePOMapper {
    int countByExample(ProjecttypePOExample example);

    int deleteByExample(ProjecttypePOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjecttypePO record);

    int insertSelective(ProjecttypePO record);

    List<ProjecttypePO> selectByExample(ProjecttypePOExample example);

    ProjecttypePO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjecttypePO record, @Param("example") ProjecttypePOExample example);

    int updateByExample(@Param("record") ProjecttypePO record, @Param("example") ProjecttypePOExample example);

    int updateByPrimaryKeySelective(ProjecttypePO record);

    int updateByPrimaryKey(ProjecttypePO record);
}