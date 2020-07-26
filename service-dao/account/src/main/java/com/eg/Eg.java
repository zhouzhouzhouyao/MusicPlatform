package com.eg;

import com.domain.User;
import com.service.IUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
            flag = userService.saveUser(user, "testPassword");
        }catch (Exception e) {
            flag = false;
            message = e.getMessage();
        }
        if(flag) {
            System.out.println("注册成功");
            System.out.println(user);
        }
        else {
            System.out.println("注册失败");
            System.out.println(message);
        }
        //多例对象
        IUserService userService1 = (IUserService) applicationContext.getBean("userService");
        IUserService userService2 = (IUserService) applicationContext.getBean("userService");
        System.out.println(userService1 == userService2);//false
    }
    
}
