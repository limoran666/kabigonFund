package com.kabigon.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.kabigon.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);
    List<Admin> getAll();

    Admin getAdminByLoginAct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRationship(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String userName);
}
