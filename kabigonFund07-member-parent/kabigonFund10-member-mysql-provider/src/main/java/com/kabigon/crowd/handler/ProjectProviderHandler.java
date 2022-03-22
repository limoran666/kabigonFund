package com.kabigon.crowd.handler;

import com.kabigon.crowd.entity.vo.DetailProjectVO;
import com.kabigon.crowd.entity.vo.PortalTypeVO;
import com.kabigon.crowd.entity.vo.ProjectVO;
import com.kabigon.crowd.service.api.ProjectService;
import com.kabigon.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectProviderHandler {

    @Autowired
    private ProjectService projectService;



    @RequestMapping("/save/project/vo/remote")
   public ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId){

        try {
            projectService.saveProject(projectVO,memberId);
            return ResultEntity.successWithoutData();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }


    }
    @RequestMapping("/get/portal/type/project/data")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectData(){
        try {
            List<PortalTypeVO> portalType = projectService.getPortalType();
            return ResultEntity.successWithData(portalType);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }


    }

    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjecrtVORemote(@PathVariable("projectId") Integer projectId){
        try {
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        } catch (Exception exception) {
            exception.printStackTrace();
            return  ResultEntity.failed(exception.getMessage());
        }


    }

}
