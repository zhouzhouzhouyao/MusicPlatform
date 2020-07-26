package com.service.impl;

import com.domain.FollowUser;
import com.domain.News;
import com.domain.SongList;
import com.domain.User;
import com.service.FollowAction;
import com.service.IUserService;
import com.service.SongAction;
import com.service.UpdateUserAction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/26.
 */
public class UserServiceImplTest {
    
    private IUserService userService;
    
    @Before
    public void setUp () {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (IUserService) applicationContext.getBean("userService");
    }
    
    @Test
    public void addAdministrator () {
        userService.addAdministrator();
    }
    
    @Test
    public void findAllUser () {
        List<User> users = userService.findAllUser();
        for (User user : users) {
            System.out.println(user);
            System.out.println(user.getFollowing());
            System.out.println(user.getFollowers());
            System.out.println(user.getSongList());
            for (SongList songList : user.getSongList()) {
                System.out.println(songList.getSongs());
            }
        }
    }
    
    @Test
    public void findUserByUid () {
        try {
            User user = userService.findUserByUid(100001);
            System.out.println(user);
            System.out.println(user.getFollowing());
            System.out.println(user.getSongList());
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findUserByName () {
        try {
            List<User> users = userService.findUserByName("1");
            for (User user : users) {
                System.out.println(user);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findAllFollowing () {
        try {
            List<FollowUser> followings = userService.findAllFollowing(100002);
            for (FollowUser following : followings) {
                System.out.println(following);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findAllFollower () {
        try {
            List<FollowUser> followers = userService.findAllFollower(398457);
            for (FollowUser follower : followers) {
                System.out.println(follower);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findAllSongList () {
        try {
            List<SongList> songLists = userService.findAllSongList(100001);
            System.out.println(songLists);
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findAllSong () {
        try {
            System.out.println(userService.findAllSong(100001, 1));
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findAllNews () {
        try {
            List<News> News = userService.findAllNews(100002);
            for (News news : News) {
                System.out.println(news);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void addUser () {
        try {
            User user = new User();
            user.setPhone("12345679446");
            user.setUsername("test");
            user.setSex(true);
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if (userService.addUser(user, "password")) {
                System.out.println("保存用户成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateUser () {
        try {
            User user = userService.findUserByUid(398457);
//            user.setUsername("test");
//            user.setLocation("上海市");
//            user.setSex(true);
            user.setUsername("hhhhh");
            user.setLocation("武汉市");
            user.setSex(false);
            if (userService.updateUser(user, UpdateUserAction.updateUserInfo)) {
                System.out.println("用户信息修改成功");
            }
            if (userService.updateUser(user, UpdateUserAction.updateUserVip)) {
                System.out.println("用户 vip 信息修改成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateUserPhone () {
        try {
            if (userService.updateUserPhone(100006, "12345678900")) {
                System.out.println("用户电话修改成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateUserPassword () {
        try {
            if (userService.updateUserPassword(100006, "testtest", "23333llll")) {
                System.out.println("用户密码修改成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void login () {
        try {
            if (userService.login("12345678900", "Administrator")) {
                System.out.println("登陆成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void createSongList () {
        try {
            userService.createSongList(100001, "动漫");
            userService.createSongList(100001, "流行");
            userService.createSongList(100002, "日韩");
            userService.createSongList(100003, "欧美");
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void removeSongList () {
        try {
            userService.removeSongList(100001, 2);
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateSongList () {
        try {
            userService.updateSongList(100001, 0, 10000001, SongAction.addSong);
            userService.updateSongList(100001, 0, 10000002, SongAction.addSong);
            userService.updateSongList(100001, 1, 10000002, SongAction.addSong);
            userService.updateSongList(100001, 2, 10000001, SongAction.addSong);
            userService.updateSongList(100002, 0, 10000000, SongAction.addSong);
            userService.updateSongList(100002, 1, 10000001, SongAction.addSong);
            userService.updateSongList(100002, 0, 10000002, SongAction.removeSong);
            userService.updateSongList(100002, 0, 10000001, null);
            userService.updateSongList(100003, 0, 10000001, SongAction.addSong);
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateSongListName () {
        try {
            userService.updateSongListName(100001, 4, "11");
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateFollow () {
        try {
            userService.updateFollow(100001, 398457, FollowAction.removeFollow);
            userService.updateFollow(100002, 398457, FollowAction.addFollow);
            userService.updateFollow(100003, 398457, FollowAction.addFollow);
            userService.updateFollow(100002, 100003, FollowAction.addFollow);
            userService.updateFollow(100002, 100001, FollowAction.addFollow);
            userService.updateFollow(100003, 100001, FollowAction.addFollow);
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void addNews () {
        try {
            userService.addNews(100001, "hhhhhhh");
            userService.addNews(100001, "rrrrrrrr");
            userService.addNews(100001, "iiiiiiii");
            userService.addNews(100003, "testtest");
            userService.addNews(100003, "testtest4");
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void deleteNews () {
        try {
            userService.deleteNews(100001, 3);
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void deleteUser () {
        for (User user : userService.findAllUser()) {
            userService.deleteUser(user.getUid());
        }
        addAdministrator();
    }
    
}