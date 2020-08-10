package com.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class News implements Serializable {
    
    private Integer newsId;
    
    private Integer uid;
    
    private String username;
    
    private String info;
    
    private Date time;
    
    public Integer getNewsId () {
        return newsId;
    }
    
    public void setNewsId (Integer newsId) {
        this.newsId = newsId;
    }
    
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
    
    public String getInfo () {
        return info;
    }
    
    public void setInfo (String info) {
        this.info = info;
    }
    
    public Date getTime () {
        return time;
    }
    
    public void setTime (Date time) {
        this.time = time;
    }
    
}
