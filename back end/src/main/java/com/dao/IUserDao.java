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
@Repository("userDao")
public interface IUserDao {
    
    /**
     * 根据 uid 查询用户，返回用户除密码外的全部个人信息
     *
     * @param uid 需要查询的目标 id
     * @return 根据 uid 在数据库中查找的结果，如果该 uid 对应的用户不存在则返回 null
     */
    @Select("select uid, phone, username, sex, birthday, location from User where uid = #{uid}")
    User findUserByUid (Integer uid);
    
    /**
     * 根据 uid 查询用户名
     *
     * @param uid 用户 uid
     * @return uid 用户的用户名
     */
    @Select("select username from User where uid = #{uid}")
    String findUsername (Integer uid);
    
    /**
     * 根据 uid 查询用户，仅返回用户公开信息：uid，用户名，性别，生日和地区
     *
     * @param uid 用户 uid
     * @return uid 对应的用户，若不存在则返回 null
     */
    @Select("select uid, username, sex, birthday, location from User where uid = #{id}")
    User findUserById (Integer uid);
    
    /**
     * 根据用户名模糊查询用户
     *
     * @param username 要查询的用户名
     * @return 模糊查询的结果
     */
    @Select("select uid, username, sex, birthday, location from User where username like #{username}")
    List<User> findUserByUsername (String username);
    
    /**
     * 检索 User 表，检查电话号码对应的用户是否存在
     *
     * @param phone 需要检查的用户电话号码
     * @return 若该号码对应的用户存在则返回用户对象，若不存在则返回 null
     */
    @Select("select uid, phone, username, sex, birthday, location from User where phone = #{phone}")
    User findUserByPhone (String phone);
    
    /**
     * 查找用户所有的关注
     *
     * @param uid 用户 uid
     * @return 用户关注列表
     */
    @Select("select * from #{uid}_Following")
    List<FollowUser> findAllFollowing (Integer uid);
    
    /**
     * 查找用户关注的 uid
     *
     * @param uid 用户 uid
     * @return 用户关注列表的 uid 列表
     */
    @Select("select uid from #{uid}_Following")
    List<Integer> findAllFollowingUid (Integer uid);
    
    /**
     * 查找用户所有的粉丝
     *
     * @param uid 用户 uid
     * @return 用户粉丝列表
     */
    @Select("select * from #{uid}_Followers")
    List<FollowUser> findAllFollower (Integer uid);
    
    /**
     * 查找用户粉丝的 uid
     *
     * @param uid 用户 uid
     * @return 用户粉丝的 uid 列表
     */
    @Select("select uid from #{uid}_Followers")
    List<Integer> findAllFollowersUid (Integer uid);
    
    /**
     * 查找用户的全部歌单
     *
     * @param uid 用户 uid
     * @return 用户的歌单列表
     */
    @Results(id = "songMap", value = {
            @Result(column = "fullName", property = "songs", many = @Many(select = "com.dao.IUserDao.findAllSongInList", fetchType = FetchType.EAGER))
    })
    @Select("select * from SongList where uid = #{uid}")
    List<SongList> findAllSongList (Integer uid);
    
    /**
     * 查找用户指定的歌单
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @return 用户的歌单，若不存在则返回 null
     */
    @Select("select * from SongList where uid = #{uid} and listIndex = #{listIndex}")
    SongList findSongList (@Param("uid") Integer uid, @Param("listIndex") Integer listIndex);
    
    /**
     * 查找用户歌单在数据库中对应的表名
     *
     * @param uid       用户 uid
     * @param listIndex 用户歌单索引
     * @return 歌单表全名
     */
    @Select("select fullName from SongList where uid = #{uid} and listIndex = #{listIndex}")
    String findSongListFullName (@Param("uid") Integer uid, @Param("listIndex") Integer listIndex);
    
    /**
     * 查找用户歌单中全部歌曲
     *
     * @param fullName 歌单在数据库中的表名
     * @return 歌单中的全部歌曲列表
     */
    @Select("select * from `${fullName}`")
    List<Song> findAllSongInList (String fullName);
    
    /**
     * 查找用户歌单中全部歌曲的 id
     *
     * @param fullName 歌单表全名
     * @return 用户歌单中的歌曲 id
     */
    @Select("select url from `${fullName}`")
    List<String> findAllSongUrl (String fullName);
    
    /**
     * 查找用户的全部动态
     *
     * @param uid 用户 uid
     * @return uid 对应用户的全部动态
     */
    @Results(id = "usernameMap", value = {
            @Result(column = "uid", property = "username", one = @One(select = "com.dao.IUserDao.findUsername", fetchType = FetchType.EAGER))
    })
    @Select("select * from #{uid}_news")
    List<News> findAllNews (Integer uid);
    
    /**
     * 查找用户的全部动态的 id
     *
     * @param uid 用户 uid
     * @return uid 对应用户的全部动态的 id
     */
    @Select("select newsId from #{uid}_news")
    List<Integer> findAllNewsId (Integer uid);
    
    /**
     * 查找用户的歌单后缀
     *
     * @param uid 用户 uid
     * @return 用户当前歌单表名数字后缀
     */
    @Select("select listCount from User where uid = #{uid}")
    Integer findUserListCount (Integer uid);
    
    /**
     * 根据用户 uid 查询用户密码的哈希值
     *
     * @param uid 需要查询用户密码的 uid
     * @return uid 对应用户的密码的哈希值
     */
    @Select("select password from User where uid = #{uid}")
    Integer findUserPasswordByUid (Integer uid);
    
    /**
     * 根据用户电话号码获得当前用户密码的哈希值
     *
     * @param phone 需要查询密码的用户的电话号码
     * @return 用户的密码的哈希值
     */
    @Select("select password from User where phone = #{phone}")
    Integer findUserPasswordByPhone (String phone);
    
    /**
     * 保存用户信息
     * 将用户的全部信息保存到 User 表中，同时向歌单表中插入该用户默认歌单的信息
     * 动态创建四张与用户相关的表
     *
     * @param user 需要保存的用户对象
     */
    @Update("insert into User(uid, phone, username, password, sex, birthday, location) " +
                    "values(#{uid}, #{phone}, #{username}, #{password}, #{sex}, #{birthday}, #{location});" +
            "insert into SongList values(#{uid}, 0, '我喜欢的音乐', '${uid}_Songs_0');" +
            "create table #{uid}_Following (" +
                    "uid int primary key, " +
                    "username varchar(20) not null, " +
                    "sex tinyint(1) null, " +
                    "birthday date null, " +
                    "location varchar(30) null);" +
            "create table #{uid}_Followers (" +
                    "uid int primary key, " +
                    "username varchar(20) not null, " +
                    "sex tinyint(1) null, " +
                    "birthday date null, " +
                    "location varchar(30) null);" +
            "create table #{uid}_Songs_0 (" +
                    "songIndex int not null, " +
                    "name varchar(30) not null, " +
                    "singer varchar(20)," +
                    "url varchar(500) not null);" +
            "create table #{uid}_news (" +
                    "newsId int auto_increment primary key," +
                    "uid int not null," +
                    "info varchar(200) not null," +
                    "time datetime not null)")
    void addUser (User user);
    
    /**
     * 修改 uid 对应的用户的 username 用户名、sex 性别、location 地区、birthday 生日的信息
     *
     * @param user 需要修改的用户对象
     */
    @Update("update User set username = #{username}, sex = #{sex}, location = #{location}, birthday = #{birthday} where uid = #{uid};")
    void updateUserInfo (User user);
    
    /**
     * 更新 uid 对应的用户的电话号码属性
     *
     * @param uid      需要修改电话属性的用户的 uid
     * @param newPhone 新的电话号码
     */
    @Update("update User set phone = #{newPhone} where uid = #{uid}")
    void updateUserPhone (@Param("uid") Integer uid, @Param("newPhone") String newPhone);
    
    /**
     * 更新 uid 对应的用户的密码
     *
     * @param uid         需要修改密码的用户的 uid
     * @param newPassword 用户的新密码
     */
    @Update("update User set password = #{newPassword} where uid = #{uid}")
    void updateUserPassword (@Param("uid") Integer uid, @Param("newPassword") Integer newPassword);
    
    /**
     * 添加关注
     *
     * @param uid  进行操作的用户 uid
     * @param fUid 关注的用户的 uid
     */
    @Insert("insert into #{uid}_Following select uid, username, sex, birthday, location from User " +
                    "where uid = #{fUid};" +
            "insert into #{fUid}_Followers select uid, username, sex, birthday, location from User where uid = #{uid}")
    void addFollow (@Param("uid") Integer uid, @Param("fUid") Integer fUid);
    
    /**
     * 取消关注
     *
     * @param uid  进行操作的用户 uid
     * @param fUid 被取消关注的用户 uid
     */
    @Delete("delete from #{uid}_Following where uid = #{fUid};" +
            "delete from #{fUid}_Followers where uid = #{uid}")
    void removeFollow (@Param("uid") Integer uid, @Param("fUid") Integer fUid);
    
    /**
     * 更新用户的粉丝表中的用户的关注表中该用户的信息
     *
     * @param fUid 粉丝的 uid
     * @param user 用户信息
     */
    @Update("update #{fUid}_Following " +
                    "set username = #{user.username}, sex = #{user.sex}, birthday = #{user.birthday}, location = #{user.location}" +
                    "where uid = #{user.uid}")
    void updateFollowersListInfo (@Param("fUid") Integer fUid, @Param("user") User user);
    
    /**
     * 创建歌单
     *
     * @param uid      用户 uid
     * @param listId   歌单 id
     * @param listName 歌单名
     */
    @Update("insert into SongList values(#{uid}, #{listId}, #{listName}, '${uid}_Songs_${listId}');" +
            "update User set listCount = listCount + 1 where uid = #{uid};" +
            "create table #{uid}_Songs_${listId} (" +
                    "songIndex int not null," +
                    "name varchar(30) not null," +
                    "singer varchar(20)," +
                    "url varchar(500) not null);")
    void createSongList (@Param("uid") Integer uid, @Param("listId") Integer listId, @Param("listName") String listName);
    
    /**
     * 删除歌单
     *
     * @param fullName 歌单表全名
     */
    @Update("delete from SongList where fullName = #{fullName};" +
            "drop table if exists #{fullName}")
    void removeSongList (String fullName);
    
    /**
     * 添加歌曲到歌单中
     *
     * @param fullName 歌单表的全名
     * @param song     歌曲
     */
    @Insert("insert into `${fullName}` values(#{song.songIndex}, #{song.name}, #{song.singer}, #{song.url})")
    void addSong (@Param("fullName") String fullName, @Param("song") Song song);
    
    /**
     * 将歌曲从歌单中移除
     *
     * @param fullName  歌单表全名
     * @param songIndex 歌曲索引
     */
    @Delete("delete from `${fullName}` where songIndex = #{songIndex}")
    void removeSong (@Param("fullName") String fullName, @Param("songIndex") Integer songIndex);
    
    /**
     * 更新歌单名
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @param listName  新的歌单名
     */
    @Update("update SongList set listName = #{listName} where uid = #{uid} and listIndex = #{listIndex}")
    void updateSongListName (@Param("uid") Integer uid, @Param("listIndex") Integer listIndex, @Param("listName") String listName);
    
    /**
     * 发布动态
     *
     * @param uid  用户 uid
     * @param info 动态内容
     * @param time 发布时间
     */
    @Insert("insert into #{uid}_news(uid, info, time) values(#{uid}, #{info}, #{time})")
    void addNews (@Param("uid") Integer uid, @Param("info") String info, @Param("time") Date time);
    
    /**
     * 删除动态
     *
     * @param uid    用户 uid
     * @param newsId 动态 id
     */
    @Delete("delete from #{uid}_news where newsId = #{newsId}")
    void deleteNews (@Param("uid") Integer uid, @Param("newsId") Integer newsId);
    
    /**
     * 删除用户信息以及用户对应的表，用于事务控制
     *
     * @param uid 用户 uid
     */
    @Delete("delete from User where uid = #{uid};" +
            "delete from SongList where uid = #{uid};" +
            "drop table if exists #{uid}_Following;" +
            "drop table if exists #{uid}_Followers;" +
            "drop table if exists #{uid}_Songs_0;" +
            "drop table if exists #{uid}_news")
    void deleteUser (Integer uid);
    
    @Select("select * from Administrator")
    List<User> addAdministrator ();
    
    @Select("select * from User")
    List<User> findAll ();
    
}
