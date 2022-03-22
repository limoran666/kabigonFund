package com.kabigon.crowd.mvc.config;

import com.kabigon.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class SecurityAdmin extends User{
    public static  final long serialVersionUID=1L;
    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        super(originalAdmin.getLoginAcct(),originalAdmin.getUserPswd(),authorities);//父类构造器去做的密码检查

        this.originalAdmin=originalAdmin;

        this.originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin(){
        return originalAdmin;
    }
}
