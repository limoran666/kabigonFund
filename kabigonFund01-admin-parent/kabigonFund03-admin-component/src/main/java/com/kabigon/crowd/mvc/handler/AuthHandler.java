package com.kabigon.crowd.mvc.handler;


import com.kabigon.crowd.service.api.AuthService;
import com.kabigon.crowd.service.api.RoleService;
import com.kabigon.crowd.util.ResultEntity;
import com.kabigon.entity.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthHandler {

    @Autowired
    private AuthService authService;



    @ResponseBody
    @RequestMapping("/assgin/get/all/auth.json")
    public ResultEntity<List<Auth>> getWholeTreeView(){
        List<Auth> authList=authService.getAll();

        return ResultEntity.successWithData(authList);


    }
    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAlreadyAssigned(@RequestParam("roleId") Integer roleId){
        List<Integer> authIdList=authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }
    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship(@RequestBody Map<String,List<Integer>> map){
        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successWithoutData();

    }
}
