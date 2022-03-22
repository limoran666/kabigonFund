package com.kabigon.crowd.mvc.handler;

import com.kabigon.crowd.service.api.AdminService;
import com.kabigon.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/test/ssm.html")
    public String testssm(ModelMap modelMap){
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList",adminList);
        return "target";
    }

    @ResponseBody
    @RequestMapping("/send/array.html")
    public String  testReceArr1(@RequestBody List<Integer> array){

        for (Integer i:array){
            System.out.println("前端传的数据"+i);
        }
        return "success";
    }
}
