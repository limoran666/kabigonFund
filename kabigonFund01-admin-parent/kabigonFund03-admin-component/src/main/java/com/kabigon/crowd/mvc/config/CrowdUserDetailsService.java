package com.kabigon.crowd.mvc.config;

import com.kabigon.crowd.service.api.AdminService;
import com.kabigon.crowd.service.api.AuthService;
import com.kabigon.crowd.service.api.RoleService;
import com.kabigon.entity.Admin;
import com.kabigon.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class CrowdUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Admin admin=adminService.getAdminByLoginAcct(userName);
        //获取adminId
        Integer adminId = admin.getId();
        //根据adminId查询角色信息
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        //根据adminId查询权限信息
        List<String> authNameList = authService.getAssignedAuthNameByAdminId(adminId);

        //创建集合对象用来存储GrantedAuthority
        List<GrantedAuthority> authorities=new ArrayList<>();

        //存入角色信息
        for (Role role:assignedRoleList){
            String roleName="ROLE_"+role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        //遍历authNameList存入权限信息
        for (String authName:authNameList){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }
        //8.封装SecurityAdmin对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
        return securityAdmin;

    }
}
