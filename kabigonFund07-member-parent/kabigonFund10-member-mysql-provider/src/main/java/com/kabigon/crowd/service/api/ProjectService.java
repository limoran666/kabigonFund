package com.kabigon.crowd.service.api;

import com.kabigon.crowd.entity.vo.DetailProjectVO;
import com.kabigon.crowd.entity.vo.PortalTypeVO;
import com.kabigon.crowd.entity.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getPortalType();

    DetailProjectVO getDetailProjectVO(Integer projectId);

}
