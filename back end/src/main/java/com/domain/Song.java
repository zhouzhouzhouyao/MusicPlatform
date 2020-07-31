package com.domain;

import java.io.Serializable;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class Song implements Serializable {
    
    private Integer songId;
    
    private String name;
    
    private String singer;
    
    private String url;
    
    private Boolean vip;
    
    public Integer getSongId () {
        return songId;
    }
    
    public void setSongId (Integer songId) {
        this.songId = songId;
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getSinger () {
        return singer;
    }
    
    public void setSinger (String singer) {
        this.singer = singer;
    }
    
    public String getUrl () {
        return url;
    }
    
    public void setUrl (String url) {
        this.url = url;
    }
    
    public Boolean getVip () {
        return vip;
    }
    
    public void setVip (Boolean vip) {
        this.vip = vip;
    }
    
    @Override
    public String toString () {
        return "Song{" + "songId=" + songId + ", name='" + name + '\'' + ", singer='" + singer + '\'' + ", vip=" + vip + '}';
    }
    
}
