package com.kabigon.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.kabigon.crowd.constant.CrowdConstant;
import com.kabigon.crowd.service.api.AdminService;
import com.kabigon.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd, HttpSession session){
        //调用service执行登录检查
        Admin admin=adminService.getAdminByLoginAct(loginAcct,userPswd);

        //将登录成功返回的admin对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("admin/do/logout.html")
    public String doLogout(HttpSession session){
        //强制Session失效
        session.invalidate();

        return "redirect:/admin/to/login/page.html";
    }

    /*@RequestParam(value = "**",defaultValue ="" 默认**的值 */
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(@RequestParam(value = "keyword",defaultValue ="") String keyword,
                              @RequestParam(value = "pageNum",defaultValue ="1") Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue ="5") Integer pageSize,
                              ModelMap modelMap){
        /*调用service方法获取PageInfo对象*/
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        //将pageinfo存入map
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);

        return "admin-page";

     }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
    ){
        //执行删除
        adminService.remove(adminId);

        //页面跳转：回到分页页面
        //方案一 直接转发 会无法显示分页数据
        //return "admin-page";

        //方案二 转发到/admin/get/page.html 会把删除的再做一遍 浪费性能
        //return "forward:/admin/get/page.html";


        //重定向到 同时保持原来页面和查询关键词 我们附加pageNum 和keyword
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;

    }

    @PreAuthorize("hasAnyAuthority('user:save')")
    @RequestMapping("/admin/save.html")
    public String save(Admin admin){
        adminService.saveAdmin(admin);

        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;//让page直接跳转到最后一页

    }
    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId,ModelMap modelMap){

        Admin admin=adminService.getAdminById(adminId);

        modelMap.addAttribute("admin",admin);

        return "admin-edit";


    }

    @RequestMapping("/admin/update.html")
    public String update(Admin admin,@RequestParam("pageNum") Integer pageNum,@RequestParam("keyword") String keyword){

        adminService.update(admin);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;

    }

}
