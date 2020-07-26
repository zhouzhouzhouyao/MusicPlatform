package com.dao;

import com.domain.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
@CacheNamespace(blocking = true)
@Repository("userDao")
public interface IUserDao {
    
    /**
     * 查询所有用户并返回
     * @return 保存所有用户对象的 List 列表
     */
    @Results(id = "userMap", value = {
            @Result(id = true, column = "uid", property = "uid"),
            @Result(column = "username", property = "username"),
            @Result(column = "vip", property = "vip"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "birthday", property = "birthday"),
            @Result(column = "location", property = "location"),
            @Result(column = "uid", property = "following", many = @Many(select = "com.dao.IUserDao.findAllFollowing", fetchType = FetchType.LAZY)),
            @Result(column = "uid", property = "followers", many = @Many(select = "com.dao.IUserDao.findAllFollower", fetchType = FetchType.LAZY)),
            @Result(column = "uid", property = "songList", many = @Many(select = "com.dao.IUserDao.findAllSongList", fetchType = FetchType.LAZY))
    })
    @Select("select * from User")
    List<User> findAllUser ();
    
    @Select("select * from #{uid}_Following")
    List<FollowUser> findAllFollowing (Integer uid);
    
    @Select("select uid from #{uid}_Following")
    List<Integer> findAllFollowingUid (Integer uid);
    
    @Select("select * from #{uid}_Followers")
    List<FollowUser> findAllFollower (Integer uid);
    
    @Select("select uid from #{uid}_Followers")
    List<Integer> findAllFollowersUid (Integer uid);
    
    @Results(id = "songMap", value = {
            @Result(id = true, column = "uid", property = "uid"),
            @Result(column = "listId", property = "listId"),
            @Result(column = "listName", property = "listName"),
            @Result(column = "fullName", property = "songs", many = @Many(select = "com.dao.IUserDao.findAllSong", fetchType = FetchType.LAZY))
    })
    @Select("select * from SongList where uid = #{uid}")
    List<SongList> findAllSongList (Integer uid);
    
    @Select("select listId from SongList where uid = #{uid}")
    List<Integer> findAllSongListId (Integer uid);
    
    @Select("select * from SongList where uid = #{uid} and listId = #{listId}")
    SongList findSongList (@Param("uid") Integer uid, @Param("listId") Integer listId);
    
    @Select("select * from `${fullName}`")
    List<Song> findAllSong (String fullName);
    
    @Select("select songId from #{uid}_Songs_${listId}")
    List<Integer> findAllSongId (@Param("uid") Integer uid, @Param("listId") Integer listId);
    
    @Select("select * from #{uid}_news")
    List<News> findAllNews (Integer uid);
    
    @Select("select newsId from #{uid}_news")
    List<Integer> findAllNewsId (Integer uid);
    
    /**
     * 根据 uid 查询用户并返回
     * @param uid 需要查询的目标 id
     * @return 根据 uid 在数据库中查找的结果，如果该 uid 对应的用户不存在则返回 null
     */
    @ResultMap("userMap")
    @Select("select * from User where uid = #{uid}")
    User findUserByUid (Integer uid);
    
    @Select("select listCount from User where uid = #{uid}")
    Integer findUserListCount (Integer uid);
    
    @Select("select * from Song where songId = #{songId}")
    Song findSong (Integer songId);
    
    @Select("select * from Administrator")
    List<User> addAdministrator ();
    
    /**
     * 根据用户名模糊查询用户
     * @param username 要查询的用户名
     * @return 模糊查询的结果
     */
    @Select("select * from User where username like #{username}")
    List<User> findUserByUsername (String username);
    
    /**
     * 检索 UserPhone 表，检查 uid 对应的用户是否存在
     * @param uid 需要检查的用户 uid
     * @return 若 uid 对应的用户存在则返回该用户对象，若不存在则返回 null
     */
    @Select("select * from UserPhone where uid = #{uid}")
    User checkUserIsExistByUid (Integer uid);
    
    /**
     * 检索 UserPhone 表，检查电话号码对应的用户是否存在
     * @param phone 需要检查的用户电话号码
     * @return 若该号码对应的用户存在则返回用户对象，若不存在则返回 null
     */
    @Select("select * from UserPhone where phone = #{phone}")
    User checkUserIsExistByPhone (String phone);
    
    /**
     * 根据用户 uid 查询用户密码的哈希值
     * @param uid 需要查询用户密码的 uid
     * @return uid 对应用户的密码的哈希值
     */
    @Select("select password from User where uid = #{uid}")
    int findUserPasswordByUid (Integer uid);
    
    /**
     * 根据用户电话号码获得当前用户密码的哈希值
     * @param phone 需要查询密码的用户的电话号码
     * @return 用户的密码的哈希值
     */
    @Select("select password from User where phone = #{phone}")
    int findUserPasswordByPhone (String phone);
    
    /**
     * 保存用户信息，将用户的全部信息保存到 User 表中，将用户的 uid 与电话号码额外存到 UserPhone 表中
     * @param user 需要保存的用户对象
     */
    @Update("insert into User(uid, phone, username, password, vip, sex, birthday, location) " +
                    "values(#{uid}, #{phone}, #{username}, #{password}, #{vip}, #{sex}, #{birthday}, #{location});" +
            "insert into UserPhone(uid, phone) values(#{uid}, #{phone});" +
            "insert into SongList values(#{uid}, 0, '我喜欢的音乐', '${uid}_Songs_0');" +
            "create table #{uid}_Following (" +
                    "uid int primary key, " +
                    "username varchar(20) not null, " +
                    "vip tinyint(1) default 0 not null, " +
                    "sex tinyint(1) null, " +
                    "birthday date null, " +
                    "location varchar(30) null);" +
            "create table #{uid}_Followers (" +
                    "uid int primary key, " +
                    "username varchar(20) not null, " +
                    "vip tinyint(1) default 0 not null, " +
                    "sex tinyint(1) null, " +
                    "birthday date null, " +
                    "location varchar(30) null);" +
            "create table #{uid}_Songs_0 (" +
                    "songId int primary key, " +
                    "name varchar(30) not null, " +
                    "vip tinyint(1) default 0 not null);" +
            "create table #{uid}_news (" +
                    "newsId int auto_increment primary key," +
                    "uid int not null," +
                    "info varchar(200) not null," +
                    "time datetime not null)")
    void addUser (User user);
    
    /**
     * 修改 uid 对应的用户的 username 用户名、sex 性别、location 地区、birthday 生日的信息
     * @param user 需要修改的用户对象
     */
    @Update("update User set username = #{username}, sex = #{sex}, location = #{location}, birthday = #{birthday} where uid = #{uid};")
    void updateUserInfo (User user);
    
    /**
     * 调整用户的 vip 属性，将其修改为当前 vip 属性的非值，true -> false，false -> true
     * @param uid 需要修改 vip 信息的用户的 uid
     */
    @Update("update User set vip = !vip where uid = #{uid}")
    void updateVip (Integer uid);
    
    /**
     * 更新 uid 对应的用户的电话号码属性，同时修改 User 表与 UserPhone 表
     * @param uid 需要修改电话属性的用户的 uid
     * @param newPhone 新的电话号码
     */
    @Update("update User set phone = #{newPhone} where uid = #{uid};" +
            "update UserPhone set phone = #{newPhone} where uid = #{uid}")
    void updateUserPhone (@Param("uid") Integer uid, @Param("newPhone") String newPhone);
    
    /**
     * 更新 uid 对应的用户的密码
     * @param uid 需要修改密码的用户的 uid
     * @param newPassword 用户的新密码
     */
    @Update("update User set password = #{newPassword} where uid = #{uid}")
    void updateUserPassword (@Param("uid") Integer uid, @Param("newPassword") Integer newPassword);
    
    @Update("insert into SongList values(#{uid}, #{listId}, #{listName}, '${uid}_Songs_${listId}');" +
            "update User set listCount = listCount + 1 where uid = #{uid};" +
            "create table #{uid}_Songs_${listId} (" +
                    "songId int primary key, " +
                    "name varchar(30) not null," +
                    "vip tinyint(1) default 0 not null)")
    void createSongList (@Param("uid") Integer uid, @Param("listId") Integer listId, @Param("listName") String listName);
    
    @Update("delete from SongList where uid = #{uid} and listId = #{listId};" +
            "update User set listCount = listCount - 1 where uid = #{uid};" +
            "drop table if exists #{uid}_Songs_#{listId}")
    void removeSongList (@Param("uid") Integer uid, @Param("listId") Integer listId);
    
    @Update("update SongList set listName = #{listName} where uid = #{uid} and listId = #{listId}")
    void updateSongList (@Param("uid") Integer uid, @Param("listId") Integer listId, @Param("listName") String listName);
    
    @Insert("insert into #{uid}_Songs_#{listId} select songId, name, vip from Song where songId = #{songId}")
    void addSong (@Param("uid") Integer uid, @Param("listId") Integer listId, @Param("songId") Integer songId);
    
    @Delete("delete from #{uid}_Songs_#{listId} where songId = #{songId}")
    void removeSong (@Param("uid") Integer uid, @Param("listId") Integer listId, @Param("songId") Integer songId);
    
    @Insert("insert into #{uid}_Following select uid, username, vip, sex, birthday, location from User where uid = #{fUid};" +
            "insert into #{fUid}_Followers select uid, username, vip, sex, birthday, location from User where uid = #{uid}")
    void addFollow (@Param("uid") Integer uid, @Param("fUid") Integer fUid);
    
    @Delete("delete from #{uid}_Following where uid = #{fUid};" +
            "delete from #{fUid}_Followers where uid = #{uid}")
    void removeFollow (@Param("uid") Integer uid, @Param("fUid") Integer fUid);
    
    @Update("update #{fUid}_Following " +
                    "set username = #{user.username}, vip = #{user.vip}, sex = #{user.sex}, birthday = #{user.birthday}, location = #{user.location}" +
                    "where uid = #{user.uid}")
    void updateFollowersListInfo (@Param("fUid") Integer fUid, @Param("user") User user);
    
    @Update("update #{fUid}_Following set vip = !vip where uid = #{uid}")
    void updateFollowersListVip (@Param("fUid") Integer fUid, @Param("uid") Integer uid);
    
    @Insert("insert into #{uid}_news(uid, info, time) values(#{uid}, #{info}, #{time})")
    void addNews (@Param("uid") Integer uid, @Param("info") String info, @Param("time") Date time);
    
    @Delete("delete from #{uid}_news where newsId = #{newsId}")
    void deleteNews (@Param("uid") Integer uid, @Param("newsId") Integer newsId);
    
    @Delete("delete from User where uid = #{uid};" +
            "delete from UserPhone where uid = #{uid};" +
            "delete from SongList where uid = #{uid};" +
            "drop table if exists #{uid}_Following;" +
            "drop table if exists #{uid}_Followers;" +
            "drop table if exists #{uid}_Songs_0;" +
            "drop table if exists #{uid}_news")
    void deleteUser (Integer uid);
    
}
