package com.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class SongList implements Serializable {
    
    private Integer uid;
    
    private Integer listIndex;
    
    private String listName;
    
    private String fullName;
    
    private List<Song> songs;
    
    public Integer getUid () {
        return uid;
    }
    
    public void setUid (Integer uid) {
        this.uid = uid;
    }
    
    public Integer getListIndex () {
        return listIndex;
    }
    
    public void setListIndex (Integer listIndex) {
        this.listIndex = listIndex;
    }
    
    public String getListName () {
        return listName;
    }
    
    public String getFullName () {
        return fullName;
    }
    
    public void setFullName (String fullName) {
        this.fullName = fullName;
    }
    
    public List<Song> getSongs () {
        return songs;
    }
    
    public void setSongs (List<Song> songs) {
        this.songs = songs;
    }
    
}
