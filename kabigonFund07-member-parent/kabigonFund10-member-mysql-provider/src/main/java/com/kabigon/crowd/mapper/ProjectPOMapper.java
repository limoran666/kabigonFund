package com.kabigon.crowd.mapper;


import java.util.List;

import com.kabigon.crowd.entity.po.ProjectPO;
import com.kabigon.crowd.entity.po.ProjectPOExample;
import com.kabigon.crowd.entity.vo.DetailProjectVO;
import com.kabigon.crowd.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;

public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    void insertRelationship(@Param("typeIdList") List<Integer> typeIdList,@Param("projectId") Integer projectId);

    void insertTagRelationpeship(@Param("tagIdList") List<Integer> tagIdList,@Param("projectId") Integer projectId);
    List<PortalTypeVO> selectPortaiTypeVOList();
    void selectPortalProjectVOList(Integer id);

    DetailProjectVO selectDetailProjectVO(Integer projectId);
}