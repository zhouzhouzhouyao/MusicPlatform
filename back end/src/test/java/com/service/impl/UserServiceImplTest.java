package com.service.impl;

import com.domain.*;
import com.service.*;
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
    public void find () {
        System.out.println("=====findAllUser=====");
        findAllUser();
        System.out.println("=====findUserByUid=====");
        findUserByUid();
        System.out.println("=====findUserByName=====");
        findUserByName();
        System.out.println("=====findAllFollow=====");
        findAllFollowing();
        System.out.println("=====findAllFollowers=====");
        findAllFollower();
        System.out.println("=====findAllSongList=====");
        findAllSongList();
        System.out.println("=====findAllSongInList=====");
        findAllSong();
        System.out.println("=====findAllNews=====");
        findAllNews();
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
            System.out.println(user.getNewsList());
        }
    }
    
    @Test
    public void findUserByUid () {
        try {
            User user = userService.findUserByUid(100001);
            System.out.println(user);
            System.out.println(user.getFollowing());
            System.out.println(user.getFollowers());
            System.out.println(user.getSongList());
            for (SongList songList : user.getSongList()) {
                System.out.println(songList.getSongs());
            }
        } catch (Exception e) {
//            System.out.println(e.getClass() + " : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void findUserByName () {
        try {
            List<User> users = userService.findUserByName("h");
            System.out.println(users);
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
            System.out.println(followings);
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
            List<FollowUser> followers = userService.findAllFollower(100001);
            System.out.println(followers);
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
            for (SongList songList : songLists) {
                System.out.println(songList);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findAllSong () {
        try {
            List<Song> songs = userService.findAllSongInList(100001, 2);
            System.out.println(songs);
            for (Song song : songs) {
                System.out.println(song);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findSongBySinger () {
        try {
            List<Song> songs = userService.findSongBySinger("周杰伦");
            System.out.println(songs);
            for (Song song : songs) {
                System.out.println(song);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findTopSongs () {
        try {
            List<Song> songs = userService.findTopSongs();
            for (Song song : songs) {
                System.out.println(song);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findRandomSongs () {
        try {
            List<Song> songs = userService.findRandomSongs();
            for (Song song : songs) {
                System.out.println(song);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void findAllNews () {
        try {
            List<News> News = userService.findAllNews(100002, ShowNewsActionEnum.ALL);
            System.out.println(News);
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
                System.out.println("添加用户成功");
            }
            else {
                System.out.println("添加用户失败");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateUser () {
        try {
            User user = userService.findUserByUid(530500);
//            user.setUsername("test");
//            user.setLocation("上海市");
//            user.setSex(true);
            user.setUsername("hhhhh");
            user.setLocation("武汉市");
            user.setSex(false);
            if (userService.updateUser(user, UpdateUserActionEnum.UPDATE_INFO)) {
                System.out.println("用户信息修改成功");
            }
            if (userService.updateUser(user, UpdateUserActionEnum.UPDATE_VIP)) {
                System.out.println("用户 vip 信息修改成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateUserPhone () {
        try {
            if (userService.updateUserPhone(530500, "12345678902")) {
                System.out.println("用户电话修改成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateUserPassword () {
        try {
            if (userService.updateUserPassword(530500, "password1", "password")) {
                System.out.println("用户密码修改成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void login () {
        try {
            User user;
            if ((user = userService.login("12345678900", "Administrator")) != null) {
                System.out.println("登陆成功");
                System.out.println(user);
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void createSongList () {
        try {
            if (userService.createSongList(100001, "动漫")) {
                System.out.println("歌单添加成功");
            }
            else {
                System.out.println("歌单添加失败");
            }
            if (userService.createSongList(100001, "流行")) {
                System.out.println("歌单添加成功");
            }
            else {
                System.out.println("歌单添加失败");
            }
            if (userService.createSongList(100002, "日韩")) {
                System.out.println("歌单添加成功");
            }
            else {
                System.out.println("歌单添加失败");
            }
            if (userService.createSongList(100003, "欧美")) {
                System.out.println("歌单添加成功");
            }
            else {
                System.out.println("歌单添加失败");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void removeSongList () {
        try {
            if (userService.removeSongList(100001, 3)) {
                System.out.println("歌单删除成功");
            }
            else {
                System.out.println("歌单删除失败");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateSongList () {
        try {
            if (userService.updateSongList(100001, 0, 10000001, SongActionEnum.ADD_SONG)) {
                System.out.println("歌曲添加成功");
            }
            if (userService.updateSongList(100001, 0, 10000002, SongActionEnum.ADD_SONG)) {
                System.out.println("歌曲添加成功");
            }
            if (userService.updateSongList(100001, 2, 10000002, SongActionEnum.ADD_SONG)) {
                System.out.println("歌曲添加成功");
            }
            if (userService.updateSongList(100001, 2, 10000001, SongActionEnum.ADD_SONG)) {
                System.out.println("歌曲添加成功");
            }
            if (userService.updateSongList(100002, 0, 10000000, SongActionEnum.ADD_SONG)) {
                System.out.println("歌曲添加成功");
            }
            if (userService.updateSongList(100002, 1, 10000001, SongActionEnum.ADD_SONG)) {
                System.out.println("歌曲添加成功");
            }
            if (userService.updateSongList(100002, 0, 10000002, SongActionEnum.REMOVE_SONG)) {
                System.out.println("歌曲删除成功");
            }
            if (userService.updateSongList(100003, 0, 10000001, SongActionEnum.ADD_SONG)) {
                System.out.println("歌曲添加成功");
            }
            if (userService.updateSongList(100002, 0, 10000001, null)) {
                System.out.println("歌曲添加成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateSongListName () {
        try {
            if (userService.updateSongListName(100001, 3, "11")) {
                System.out.println("歌单名称修改成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void updateFollow () {
        try {
            if (userService.updateFollow(100001, 530500, FollowActionEnum.ADD_FOLLOW)) {
                System.out.println("关注用户成功");
            }
            if (userService.updateFollow(100002, 100003, FollowActionEnum.ADD_FOLLOW)) {
                System.out.println("关注用户成功");
            }
            if (userService.updateFollow(100002, 100001, FollowActionEnum.ADD_FOLLOW)) {
                System.out.println("关注用户成功");
            }
            if (userService.updateFollow(100003, 100001, FollowActionEnum.ADD_FOLLOW)) {
                System.out.println("关注用户成功");
            }
            if (userService.updateFollow(100001, 100002, FollowActionEnum.REMOVE_FOLLOW)) {
                System.out.println("取关用户成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void addNews () {
        try {
            if (userService.addNews(100002, "hhhhhhh")) {
                System.out.println("动态发布成功");
            }
            if (userService.addNews(100002, "rrrrrrrr")) {
                System.out.println("动态发布成功");
            }
            if (userService.addNews(100001, "iiiiiiii")) {
                System.out.println("动态发布成功");
            }
            if (userService.addNews(100003, "testtest")) {
                System.out.println("动态发布成功");
            }
            if (userService.addNews(100003, "testtest4")) {
                System.out.println("动态发布成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
    @Test
    public void deleteNews () {
        try {
            if (userService.deleteNews(100001, 3)) {
                System.out.println("动态删除成功");
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }
    
//    @Test
    public void deleteUser () {
        for (User user : userService.findAllUser()) {
            userService.deleteUser(user.getUid());
        }
//        addAdministrator();
    }
    
}