package com.kabigon.crowd.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kabigon.crowd.constant.CrowdConstant;
import com.kabigon.crowd.exception.LoginAcctAlreadyInUseException;
import com.kabigon.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.kabigon.crowd.exception.LoginFailedException;
import com.kabigon.crowd.mapper.AdminMapper;
import com.kabigon.crowd.mapper.RoleMapper;
import com.kabigon.crowd.service.api.AdminService;
import com.kabigon.crowd.util.CrowdUtil;
import com.kabigon.entity.Admin;
import com.kabigon.entity.AdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void saveAdmin(Admin admin) {



        //密码加密
        String userPswd = admin.getUserPswd();
        //userPswd = CrowdUtil.MD5(userPswd);
        //交给Security
        userPswd = bCryptPasswordEncoder.encode(userPswd);
        admin.setUserPswd(userPswd);

        //2.生成一个创建时间
        Date date=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=simpleDateFormat.format(date);
        admin.setCreateTime(createTime);

        try {
            //执行保存
            adminMapper.insert(admin);
        } catch (Exception exception) {
            if (exception instanceof DuplicateKeyException){
                throw  new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }

    }

    @Override
    public List<Admin> getAll() {
        List<Admin> admins = adminMapper.selectByExample(new AdminExample());
        return admins;
    }

    @Override
    public Admin getAdminByLoginAct(String loginAcct, String userPswd) {
        // 1.根据登录账号查询Admin对象
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        // 2.判断Admin对象是否为null
        // 3.如果Admin对象为null则抛出异常
        if (admins==null||admins.size()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size()>1){

            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_UNIQUE);
        }

        Admin admin = admins.get(0);
        if (admin==null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        //4.如果Admin对象不为null则将数据库密码从Admin对象中取出
        String userPswdDB = admin.getUserPswd();

/*        //5.将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.MD5(userPswd);
        //6.对密码进行比较
        //7.如果比较结果是不一致则抛出异常
       if (!Objects.equals(userPswdDB,userPswdForm)){
           throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
       }*/
        // 8.如果一致则返回Admin对象
        if (Objects.equals(userPswdDB,userPswd)){
            return admin;
        }

      return null;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1.调用pageHelper的静态方法开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        // 2.执行查询
        List<Admin> adminList = adminMapper.selectAdminListByKeyword(keyword);

        // 3.封装到PageInfo对象中
        return new PageInfo<>(adminList);

    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        return admin;
    }

    @Override
    public void update(Admin admin) {
        try {
            //表示有选择的更新 对于null值的不更新
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception exception) {
          if (exception instanceof DuplicateKeyException){
              throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
          }
        }

    }

    @Override
    public void saveAdminRoleRationship(Integer adminId, List<Integer> roleIdList) {


        adminMapper.deleteOldRelationship(adminId);

        if (roleIdList!=null&&roleIdList.size()>0){
            adminMapper.insertNewRelationship(adminId,roleIdList);

        }

    }

    @Override
    public Admin getAdminByLoginAcct(String userName) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(userName);
        List<Admin> list=adminMapper.selectByExample(example);
        Admin admin = list.get(0);
        return admin;

    }
}
