package com.kabigon.crowd.mapper;

import com.kabigon.crowd.entity.po.ProjectTagPO;
import com.kabigon.crowd.entity.po.ProjectTagPOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectTagPOMapper {
    int countByExample(ProjectTagPOExample example);

    int deleteByExample(ProjectTagPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectTagPO record);

    int insertSelective(ProjectTagPO record);

    List<ProjectTagPO> selectByExample(ProjectTagPOExample example);

    ProjectTagPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectTagPO record, @Param("example") ProjectTagPOExample example);

    int updateByExample(@Param("record") ProjectTagPO record, @Param("example") ProjectTagPOExample example);

    int updateByPrimaryKeySelective(ProjectTagPO record);

    int updateByPrimaryKey(ProjectTagPO record);
}