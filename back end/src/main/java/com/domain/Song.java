package com.domain;

import java.io.Serializable;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class Song implements Serializable {
    
    private Integer songIndex;
    
    private String name;
    
    private String singer;
    
    private String url;
    
    public Song () {
    
    }
    
    public Song (String name, String singer, String url) {
        this.name = name;
        this.singer = singer;
        this.url = url;
    }
    
    public Integer getSongIndex () {
        return songIndex;
    }
    
    public void setSongIndex (Integer songIndex) {
        this.songIndex = songIndex;
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
    
}
