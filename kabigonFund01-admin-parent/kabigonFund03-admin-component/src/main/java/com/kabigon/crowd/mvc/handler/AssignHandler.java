package com.kabigon.crowd.mvc.handler;

import com.kabigon.crowd.service.api.AdminService;
import com.kabigon.crowd.service.api.RoleService;
import com.kabigon.crowd.util.ResultEntity;
import com.kabigon.entity.Auth;
import com.kabigon.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AssignHandler {

    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap){
        // 1.查询已分配角色
        List<Role> assignedRolist=roleService.getAssignedRole(adminId);
        // 2.查询未分配角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        modelMap.addAttribute("assignedRoleList",assignedRolist);
        modelMap.addAttribute("unAssignedRoleList",unAssignedRoleList);
        return "assign-role";

    }

    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value ="roleIdList",required = false) List<Integer> roleIdList){

        adminService.saveAdminRoleRationship(adminId,roleIdList);


    return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;

    }


}
