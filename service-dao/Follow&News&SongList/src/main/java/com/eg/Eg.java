package com.eg;

import com.domain.User;
import com.exception.*;
import com.service.IUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/22.
 */
public class Eg {
    
    public static void main (String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserService userService = (IUserService) applicationContext.getBean("userService");
        User user = new User();
        user.setUsername("test");
        user.setPhone("12345677777");
        boolean flag;
        String message = null;
        try {
            flag = userService.addUser(user, "testPassword");
        } catch (NullUserException | UidErrorException | UserPasswordNotValidException | UserPhoneNotValidException | UserInfoNotValidException e) {
            //仅供参考，实际调用需分开处理不同异常
            flag = false;
            message = e.getClass() + " : " + e.getMessage();
        } catch (Exception e) {
            flag = false;
            message = "Unknown Exception";
        }
        if(flag) {
            System.out.println("注册成功");
            System.out.println(user);
        }
        else {
            System.out.println("注册失败");
            System.out.println(message);
        }
        
        
        //每个事务独立开启一个 service，get 一个新的 service 对象
        //service1
        IUserService userService1 = (IUserService) applicationContext.getBean("userService");
        List<User> users = userService1.findAllUser();
        for (User user1 : users) {
            System.out.println(user1);
            System.out.println(user1.getFollowing());
            System.out.println(user1.getFollowers());
            System.out.println(user1.getSongList());
        }
        
        //service2
        IUserService userService2 = (IUserService) applicationContext.getBean("userService");
        boolean flag1;
        String message1 = null;
        try {
            flag1 = userService2.login("12345678900", "Administrator");
        } catch (AccountNotValidException | UserPasswordNotValidException | UidErrorException | UserPhoneNotValidException e) {
            flag1 = false;
            message1 = e.getClass() + " : " + e.getMessage();
        }
        if (flag1) {
            System.out.println("登陆成功");
        }
        else {
            System.out.println("登陆失败");
            System.out.println(message1);
        }
        
        
        System.out.println(userService1 == userService2);//false,service 为多例对象
    }
    
}
