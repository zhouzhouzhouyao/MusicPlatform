package com.dao;

import com.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

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
    @Select("select * from User")
    List<User> findAll ();
    
    /**
     * 根据 uid 查询用户并返回
     * @param uid 需要查询的目标 id
     * @return 根据 uid 在数据库中查找的结果，如果该 uid 对应的用户不存在则返回 null
     */
    @Select("select * from User where uid = #{uid}")
    User findUserByUid (Integer uid);
    
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
    int getUserPasswordByUid (Integer uid);
    
    /**
     * 根据用户电话号码获得当前用户密码的哈希值
     * @param phone 需要查询密码的用户的电话号码
     * @return 用户的密码的哈希值
     */
    @Select("select password from User where phone = #{phone}")
    int getUserPasswordByPhone (String phone);
    
    /**
     * 保存用户信息，将用户的全部信息保存到 User 表中，将用户的 uid 与电话号码额外存到 UserPhone 表中
     * @param user 需要保存的用户对象
     */
    @Insert("insert into User(uid, phone, username, password, vip, sex, birthday, location) " +
                    "values(#{uid}, #{phone}, #{username}, #{password}, #{vip}, #{sex}, #{birthday}, #{location});" +
                    "insert into UserPhone(uid, phone) values(#{uid}, #{phone})")
    void saveUser (User user);
    
    /**
     * 修改 uid 对应的用户的 username 用户名、sex 性别、location 地区、birthday 生日的信息
     * @param user 需要修改的用户对象
     */
    @Update("update User set username = #{username}, sex = #{sex}, location = #{location}, birthday = #{birthday} where uid = #{uid}")
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
    
    @Update("create table if not exists `${listName}` (songID int primary key)")
    void createSongList (@Param("listName") String listName);
    
    @Update("drop table if exists `${listName}`")
    void deleteSongList (@Param("listName") String listName);
    
}
