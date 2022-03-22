package com.kabigon.test;

import com.kabigon.crowd.mapper.AdminMapper;

import com.kabigon.crowd.service.api.AdminService;
import com.kabigon.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//在类上标记必要的注解，Spring整合junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void test1() {
    /*    Admin admin=new Admin(null,"jerry","123456","sda","asdas@qq.com",null);
        int insert = adminMapper.insert(admin);
        System.out.println(insert);

        //如果在实际开发中，所有想查看数值的地方都使用sysout方式打印，会给项目上线运行带来问题！
//sysout本质上是一个IO操作，通常IO的操作是比较消耗性能的。如果项目中sysout很多，那么对性能的影响就比较大了
//即使上线前专门花时间删除代码中的sysout，也很可能有遗漏，而且非常麻烦。
//而如果使用日志系统，那么通过日志级别就可以批量的控制信息的打印。

        */
        System.out.println(adminMapper);
    }

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection + "");

    }
    @Test
    public void test3(){
        //获取Logger对象 这里传入的class就是当前打印日志的类
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);

        //根据不同日志级别打印日志
        logger.debug("hello i am a bug level");
        logger.debug("hello i am a bug level");

        logger.info("indo level!");
        logger.info("indo level!");

        logger.warn("war!");
        logger.warn("war!");
        logger.warn("war!");

        logger.error("error!");
    }
    @Test
    public void testTx(){
        Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "asda@qq.com", null);
        adminService.saveAdmin(admin);

    }
}
