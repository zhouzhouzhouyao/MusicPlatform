package com.domain;

import java.io.Serializable;

/**
 * @author Liu
 * Created on 2020/7/24.
 */
public class FollowUser implements Serializable {
    
    private Integer uid;
    
    private String username;
    
    private Boolean vip;
    
    private Boolean sex;
    
    private String birthday;
    
    private String location;
    
    public Integer getUid () {
        return uid;
    }
    
    public void setUid (Integer uid) {
        this.uid = uid;
    }
    
    public String getUsername () {
        return username;
    }
    
    public void setUsername (String username) {
        this.username = username;
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
    
}
