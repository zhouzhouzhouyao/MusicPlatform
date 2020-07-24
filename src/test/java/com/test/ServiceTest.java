package com.test;

import com.domain.User;
import com.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
public class ServiceTest {
    
    private ApplicationContext applicationContext;
    private IUserService userService;
    
    @Before
    public void init () {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (IUserService) applicationContext.getBean("userService");
    }
    
    @Test
    public void testSerFindAll () {
        List<User> users = userService.findAll();
        for(User user : users) {
            System.out.println(user);
        }
    }
    
    @Test
    public void testFindAccountById () {
        User user = userService.findUserByUid(100006);
        System.out.println(user);
    }
    
    @Test
    public void testFindAccountByName () {
        List<User> users = userService.findUserByName("h");
        for(User user : users) {
            System.out.println(user);
        }
    }
    
    @Test
    public void testCache () {
        User user1 = userService.findUserByUid(100001);
        System.out.println(user1);
        User user2 = userService.findUserByUid(100001);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }
    
    @Test
    public void testSaveUser() {
        try {
            User user = new User();
//            user.setUid(100000);
            user.setPhone("12345679444");
            user.setUsername("test");
            user.setSex(true);
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if(userService.saveUser(user, "testpa")) {
                System.out.println("保存用户成功");
            }
        }catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        
    }
    
    @Test
    public void checkUser () {
        System.out.println(userService.checkUserIsExistByUid(100001));
        System.out.println(userService.checkUserIsExistByUid(100000));
        System.out.println(userService.checkUserIsExistByPhone("12345678909"));
        System.out.println(userService.checkUserIsExistByPhone("12345678900"));
    }
    
    @Test
    public void testUpdateUserInfo () {
        try {
            User user = userService.findUserByUid(100007);
            user.setBirthday("2234-9-900");
            if(userService.updateUserInfo(user)) {
                System.out.println("用户信息修改成功");
            }
            if(userService.updateVip(100006)) {
                System.out.println("用户 vip 信息修改成功");
            }
            
        }catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
    
    @Test
    public void testUpdate () {
        try {
            if(userService.updateUserPhone(100006, "12345678900")) {
                System.out.println("用户电话修改成功");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    @Test
    public void testUpdatePassword () {
        try {
            if(userService.updateUserPassword(100006,"testtest", "23333llll")) {
                System.out.println("用户密码修改成功");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void testLogin () {
        try {
            if(userService.login("12345678900", "Administrator1")) {
                System.out.println("登陆成功");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void test () {
//        userService.createSongList(100001, 1);
        userService.deleteSongList(100001, 1);
    }
    
}
