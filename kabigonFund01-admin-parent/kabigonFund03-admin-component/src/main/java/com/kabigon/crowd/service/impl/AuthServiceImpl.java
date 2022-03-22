package com.kabigon.crowd.service.impl;

import com.kabigon.crowd.mapper.AuthMapper;
import com.kabigon.crowd.service.api.AuthService;
import com.kabigon.entity.Auth;
import com.kabigon.entity.AuthExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;
    @Override
    public List<Auth> getAll() {
        List<Auth> authList = authMapper.selectByExample(new AuthExample());
        return authList;
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
       return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        List<Integer> roleList = map.get("roleId");
        Integer roleId = roleList.get(0);
        authMapper.deleteOldRelationship(roleId);
        List<Integer> authIdList = map.get("authIdArray");
        if(authIdList != null && authIdList.size() > 0) {
            authMapper.insertNewRelationship(roleId,authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
