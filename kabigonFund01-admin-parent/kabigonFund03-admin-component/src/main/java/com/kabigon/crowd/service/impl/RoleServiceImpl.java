package com.kabigon.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kabigon.crowd.mapper.RoleMapper;
import com.kabigon.crowd.service.api.RoleService;
import com.kabigon.entity.Role;
import com.kabigon.entity.RoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

 @Autowired
 private RoleMapper roleMapper;


 @Override
 public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
  PageHelper.startPage(pageNum,pageSize);
  List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);

  return new PageInfo<>(roleList);
 }

 @Override
 public void updateRole(Role role) {
  roleMapper.updateByPrimaryKey(role);
 }

 @Override
 public void saveRole(Role role) {
  roleMapper.insert(role);
 }

 @Override
 public void removeRole(List<Integer> roleIdList) {
  RoleExample roleExample = new RoleExample();
  RoleExample.Criteria criteria = roleExample.createCriteria();
  criteria.andIdIn(roleIdList);
  roleMapper.deleteByExample(roleExample);
 }

 @Override
 public List<Role> getAssignedRole(Integer adminId) {
   return roleMapper.selectAssignedRole(adminId);
 }

 @Override
 public List<Role> getUnAssignedRole(Integer adminId) {
  return roleMapper.selectUnAssignedRole(adminId);
 }
}
