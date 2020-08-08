package com.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
public class User implements Serializable {
    
    private Integer uid;
    
    private String phone;
    
    private String username;
    
    private Integer password;
    
    private Boolean vip;
    
    private Boolean sex;
    
    private String birthday;
    
    private String location;
    
    private List<FollowUser> following;
    
    private List<FollowUser> followers;
    
    private List<SongList> songList;
    
    private List<News> newsList;

    public Integer getUid () {
        return uid;
    }

    public void setUid (Integer uid) {
        this.uid = uid;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public Integer getPassword () {
        return password;
    }

    public void setPassword (Integer password) {
        this.password = password;
    }

    public Boolean getVip () {
        return vip;
    }

    public void setVip (Boolean vip) {
        this.vip = vip;
    }

    public Boolean getSex () {
        return sex;
    }

    public void setSex (Boolean sex) {
        this.sex = sex;
    }
    
    public String getBirthday () {
        return birthday;
    }
    
    public void setBirthday (String birthday) {
        this.birthday = birthday;
    }
    
    public String getLocation () {
        return location;
    }

    public void setLocation (String location) {
        this.location = location;
    }
    
    public List<FollowUser> getFollowing () {
        return following;
    }
    
    public void setFollowing (List<FollowUser> following) {
        this.following = following;
    }
    
    public List<FollowUser> getFollowers () {
        return followers;
    }
    
    public void setFollowers (List<FollowUser> followers) {
        this.followers = followers;
    }
    
    public List<SongList> getSongList () {
        return songList;
    }
    
    public void setSongList (List<SongList> songList) {
        this.songList = songList;
    }
    
    public List<News> getNewsList () {
        return newsList;
    }
    
    public void setNewsList (List<News> newsList) {
        this.newsList = newsList;
    }
    
}
