package com.kabigon.crowd.handler;

import com.kabigon.crowd.api.MySQLRemoteService;
import com.kabigon.crowd.constant.CrowdConstant;
import com.kabigon.crowd.entity.vo.PortalTypeVO;
import com.kabigon.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PortalHandler {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/")
    public String showPortalPage(Model model){
        //显示首页要显示的project
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectData();

        String result = resultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result)){
        //获取查询结果数据
            List<PortalTypeVO> list = resultEntity.getData();

            model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_TYPE_LIST,list);
        }


        return "portal";
    }

}
