package com.kabigon.crowd.mapper;


import java.util.List;

import com.kabigon.crowd.entity.po.ProjectItempicPO;
import com.kabigon.crowd.entity.po.ProjectItempicPOExample;
import org.apache.ibatis.annotations.Param;

public interface ProjectItempicPOMapper {
    int countByExample(ProjectItempicPOExample example);

    int deleteByExample(ProjectItempicPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectItempicPO record);

    int insertSelective(ProjectItempicPO record);

    List<ProjectItempicPO> selectByExample(ProjectItempicPOExample example);

    ProjectItempicPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectItempicPO record, @Param("example") ProjectItempicPOExample example);

    int updateByExample(@Param("record") ProjectItempicPO record, @Param("example") ProjectItempicPOExample example);

    int updateByPrimaryKeySelective(ProjectItempicPO record);

    int updateByPrimaryKey(ProjectItempicPO record);

    void insertPathList(@Param("projectId")Integer projectId,@Param("detailPicturePathList") List<String> detailPicturePathList);
}