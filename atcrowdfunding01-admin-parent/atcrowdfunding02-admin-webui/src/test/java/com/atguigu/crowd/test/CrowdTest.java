package com.atguigu.crowd.test;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.AdminService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testInsertRole() {
        for(int i = 0; i < 148; i++) {
            roleMapper.insert(new Role(null, "role"+i));
        }
    }

    @Test
    public void testSaveAdminMulti() {
        for (int i = 0; i < 256; i++) {
            adminMapper.insert(new Admin(null, "loginAcct" + i, "userPswd" + i, "user-" + i, "user" + i + "@test.com", null));
        }
    }

    @Test
    public void testTX() {
        Admin admin = new Admin(null, "admin002", "123123", "Lisi", "ls@test.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testLog() {
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        // 2.根据不同日志级别打印日志
        logger.debug("Hello I am Debug level!!!");
        logger.debug("Hello I am Debug level!!!");
        logger.info("Info level!!!");
        logger.info("Info level!!!");
        logger.warn("Warn level!!!");
        logger.warn("Warn level!!!");
        logger.error("Error level!!!");
        logger.error("Error level!!!");
    }

    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "admin001", "123123", "zhangsan", "zs@test.com", null);
        adminMapper.insert(admin);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("--------------------connection = " + connection);
    }

}
