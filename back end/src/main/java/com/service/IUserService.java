package com.service;

import com.domain.*;
import com.enums.FollowActionEnum;
import com.enums.ShowNewsActionEnum;
import com.enums.SongActionEnum;
import com.enums.UpdateUserActionEnum;
import com.exception.*;

import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
public interface IUserService {
    
    /**
     * 登陆检查，传入用户账号与密码
     * 若账号为 6 位字符串则视为 uid 登陆，若为 11 位字符串则视为电话号码登陆
     *
     * @param account  用户账号，为 uid 或者电话号码字符串
     * @param password 用户输入的密码
     * @return 若用户账户密码对应无误且无异常发生则返回登陆的用户对象
     * @throws AccountNotValidException      若输入的账户为 null 或者长度不是 6 位的 uid 的长度或者不是 11 位的电话号码长度，抛出该异常
     * @throws UserPasswordNotValidException 若用户输入的密码为 null 或者密码哈希值与数据库中保存的密码哈希值不一致，抛出该异常
     * @throws UidErrorException             若传入的为 6 位的账号，视为 uid 登陆，检查 uid 后如果不合法，抛出该异常
     * @throws NullUserException             若账户不存在，抛出该异常
     * @throws UserPhoneNotValidException    若传入的为 11 位的账号，视为电话号码登陆，检查电话号码后如果不合法，抛出该异常
     * @throws SystemErrorException          查询数据库的过程中可能出现的异常
     */
    User login (String account, String password)
            throws AccountNotValidException, UserPasswordNotValidException, UidErrorException, NullUserException, UserPhoneNotValidException, SystemErrorException;
    
    /**
     * 根据 uid 查询用户
     *
     * @param uid 需要查询的 uid
     * @return 根据 uid 查询到的用户
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     */
    User findUserByUid (Integer uid) throws UidErrorException, SystemErrorException, NullUserException;
    
    /**
     * 根据用户名模糊查询用户
     *
     * @param username 需要查询的用户名
     * @return 根据模糊查询得到的对象集合
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<User> findUserByName (String username) throws SystemErrorException;
    
    /**
     * 查询 uid 对应的用户的关注列表
     *
     * @param uid 需要查询关注列表的用户 uid
     * @return 用户的关注列表
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若传入的 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<FollowUser> findAllFollowing (Integer uid) throws UidErrorException, NullUserException, SystemErrorException;
    
    /**
     * 查询 uid 对应的用户的粉丝列表
     *
     * @param uid 需要查询粉丝列表的用户 uid
     * @return 用户的粉丝列表
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<FollowUser> findAllFollower (Integer uid) throws UidErrorException, NullUserException, SystemErrorException;
    
    /**
     * 查询 uid 对应的用户的歌单列表
     *
     * @param uid 需要查询歌单列表的用户 uid
     * @return 用户的歌单列表
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<SongList> findAllSongList (Integer uid) throws UidErrorException, NullUserException, SystemErrorException;
    
    /**
     * 查询用户某个歌单中的全部歌曲
     *
     * @param uid    用户 uid
     * @param listId 用户的歌单 id
     * @return 存放 uid 对应用户的 listId 的歌单的全部歌曲
     * @throws UidErrorException      若 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若 listId 不合法，抛出该异常
     *                                若 uid 对应的用户不存在该歌单，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    List<Song> findAllSongInList (Integer uid, Integer listId)
            throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException;
    
    /**
     * 查找歌手的全部歌曲
     *
     * @param singer 歌手名称
     * @return 歌手的全部歌曲列表
     * @throws SongListErrorException 如果歌手名为空，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    List<Song> findSongBySinger (String singer) throws SongListErrorException, SystemErrorException;
    
    /**
     * 根据关键字查找歌曲
     *
     * @param keyword 歌曲关键字
     * @return 歌曲列表
     * @throws SongErrorException 若 name 为 null 或为空字符串，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<Song> findSongByName (String keyword) throws SongErrorException, SystemErrorException;
    
    /**
     * 查找 top10 热度的歌曲
     *
     * @return 搜索热度前十的歌曲列表
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<Song> findTopSongs () throws SystemErrorException;
    
    /**
     * 随机推荐 20 首歌
     *
     * @return 包含 20 首歌曲的列表
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<Song> findRandomSongs () throws SystemErrorException;
    
    /**
     * 根据 ShowNewsActionEnum 的值判断是仅查看自己的动态还是查看所有关注的人的动态
     *
     * @param uid    用户 uid
     * @param action 枚举类的值，合法值为 ALL 或者 SELF
     * @return 动态列表
     * @throws UidErrorException      若 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     * @throws UnknownActionException 若 action 参数的值不合法，抛出该异常
     */
    List<News> findAllNews (Integer uid, ShowNewsActionEnum action)
            throws UidErrorException, NullUserException, SystemErrorException, UnknownActionException;
    
    /**
     * 保存用户信息
     * 为传入的用户对象生成一个唯一存在的 uid，将 uid 赋给该用户对象后将该对象存入数据库中，其中密码存放的为用户输入密码的哈希值
     *
     * @param user     要保存的新用户的对象
     * @param password 要保存的新用户的密码
     * @return 若保存用户未遇到异常则返回 true，若保存失败则返回 false
     * @throws NullUserException             若传入的 User 对象为 null，抛出该异常
     * @throws UidErrorException             若传入的 User 对象已拥有 uid，抛出该异常
     * @throws UserPhoneNotValidException    若传入的 User 对象的电话号码已被注册，抛出该异常
     * @throws UserPasswordNotValidException 若传入的密码不合法，抛出该异常
     * @throws UserInfoNotValidException     若传入的 User 对象的信息存在不合法信息，抛出该异常
     * @throws SystemErrorException          查询数据库的过程中可能出现的异常
     */
    boolean addUser (User user, String password)
            throws NullUserException, UidErrorException, UserPhoneNotValidException, UserPasswordNotValidException, UserInfoNotValidException, SystemErrorException;
    
    /**
     * 修改用户的用户名、性别、地区和生日信息或者修改用户 vip 属性
     * 其中 vip 属性是将其修改为当前的非值：true -> false, false -> true
     *
     * @param user   需要修改信息的用户对象
     * @param action 枚举类的值，合法值为 UPDATE_INFO 或者 UPDATE_VIP
     * @return 若更新用户信息未遇到异常则返回 true
     * @throws NullUserException          若传入的用户对象为 null，抛出该异常；
     *                                    若传入的用户对象的 uid 在数据库中对应的对象不存在，抛出该异常
     * @throws UidErrorException          若传入的用户对象的 uid 为 null 或者不是 6 位的 uid，抛出该异常
     * @throws UserPhoneNotValidException 若传入的用户电话不合法，抛出该异常
     * @throws UserInfoNotValidException  若传入的用户对象的信息不合法，抛出该异常
     * @throws SystemErrorException       查询数据库的过程中可能出现的异常
     * @throws UnknownActionException     若 action 参数的值不合法，抛出该异常
     */
    boolean updateUser (User user, UpdateUserActionEnum action)
            throws NullUserException, UidErrorException, UserPhoneNotValidException, UserInfoNotValidException, SystemErrorException, UnknownActionException;
    
    /**
     * 修改用户电话号码
     *
     * @param uid   要修改电话号码的用户的 uid
     * @param phone 修改的目标电话号码
     * @return 若更新用户电话未遇到异常则返回 true
     * @throws UidErrorException          若传入的 uid 为 null 或者不是 6 位的 uid，抛出该异常
     * @throws NullUserException          若 uid 对应的用户不存在，抛出该异常
     * @throws UserPhoneNotValidException 若传入的电话不合法，抛出该异常；
     *                                    若修改的目标电话号码已被注册，抛出该异常
     * @throws SystemErrorException       查询数据库的过程中可能出现的异常
     */
    boolean updateUserPhone (Integer uid, String phone)
            throws UidErrorException, NullUserException, UserPhoneNotValidException, SystemErrorException;
    
    /**
     * 修改用户密码
     *
     * @param uid         要修改密码的用户的 uid
     * @param oldPassword 用户输入的旧密码字符串，用以比对旧密码是否正确
     * @param newPassword 用户输入的新密码字符串
     * @return 若更新密码未遇到异常则返回 true
     * @throws UidErrorException             若 uid 为 null 或者不是 6 位的 uid，抛出该异常
     * @throws NullUserException             若 uid 对应的用户不存在，抛出该异常
     * @throws UserPasswordNotValidException 若新密码不合法，抛出该异常；
     *                                       若旧密码的哈希值与数据库中 uid 对应的用户的密码不一致；抛出该异常；
     *                                       若新密码与旧密码相同，抛出该异常
     * @throws SystemErrorException          查询数据库的过程中可能出现的异常
     */
    boolean updateUserPassword (Integer uid, String oldPassword, String newPassword)
            throws UidErrorException, NullUserException, UserPasswordNotValidException, SystemErrorException;
    
    /**
     * 添加关注或取消关注
     *
     * @param uid    关注者 uid
     * @param fUid   被关注着 uid
     * @param action 枚举类的值，合法值为 ADD_FOLLOW 或 REMOVE_FOLLOW
     * @return 若添加关注或取消关注没有出现异常，返回 true
     * @throws UidErrorException      若 uid 不合法，抛出该异常；
     *                                若 uid 等于 fUid，抛出该异常
     * @throws NullUserException      若 uid 或 fUid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     * @throws FollowUserException    若重复关注同一用户，抛出该异常；
     *                                若试图取消关注一个没有关注的用户，抛出该异常
     * @throws UnknownActionException 若 action 参数的值不合法，抛出该异常
     */
    boolean updateFollow (Integer uid, Integer fUid, FollowActionEnum action)
            throws UidErrorException, NullUserException, SystemErrorException, FollowUserException, UnknownActionException;
    
    /**
     * 创建歌单
     *
     * @param uid      需要添加歌单的用户的 uid
     * @param listName 添加的歌单名
     * @return 若创建歌单未出现异常则返回 true，否则返回 false
     * @throws UidErrorException      若用户 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若用户输入的歌单名为空，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    boolean createSongList (Integer uid, String listName)
            throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException;
    
    /**
     * 删除歌单
     *
     * @param uid    需要删除歌单的用户的 uid
     * @param listId 删除的歌单 id
     * @return 若删除歌单未出现异常则返回 true，否则返回 false
     * @throws UidErrorException      若传入的 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若 uid 对应的用户没有 listId 号歌单，抛出该异常；
     *                                若 listId 不合法，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    boolean removeSongList (Integer uid, Integer listId)
            throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException;
    
    /**
     * 更新用户歌单，添加删除歌曲
     *
     * @param uid    需要操作歌单的用户 uid
     * @param listId 需要操作的歌单 id
     * @param songId 需要添加或删除的歌曲 id
     * @param action 枚举类的值，合法值为 ADD_SONG 或者 REMOVE_SONG
     * @return 若添加或删除歌曲没有出现异常，返回 true
     * @throws UidErrorException      若传入的 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若用户向同一个歌单中添加同一首歌曲时，抛出该异常；
     *                                若 listId 不合法，抛出该异常
     *                                若 uid 对应的用户没有 listId 号歌单，抛出该异常
     * @throws SongErrorException   若 songId 不合法，抛出该异常；
     *                                若 songId 对应的歌曲不存在，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     * @throws UnknownActionException 若 action 参数的值不合法，抛出该异常
     */
    boolean updateSongList (Integer uid, Integer listId, Integer songId, SongActionEnum action)
            throws UidErrorException, NullUserException, SongListErrorException, SongErrorException, SystemErrorException, UnknownActionException;
    
    /**
     * 更新歌曲搜索热度
     *
     * @param songId 歌曲 id
     * @return 若更新热度没有出现异常，返回 true
     * @throws SongErrorException 若 songId 不合法，抛出该异常
     *                              若 songId 对应的歌曲不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    boolean updateSongSearchCount (Integer songId) throws SongErrorException, SystemErrorException;
    
    /**
     * 更新用户歌单名称
     *
     * @param uid      用户 uid
     * @param listId   需要更新名称的歌单 id
     * @param listName 新的歌单名称
     * @return 若修改歌单名称未出现异常，返回 true
     * @throws SongListErrorException 若修改的是 0 号默认歌单，抛出该异常；
     *                                若新的歌单名称为空，抛出该异常；
     *                                若 listId 不合法，抛出该异常；
     *                                若 uid 对应的用户没有 listId 号歌单，抛出该异常
     * @throws UidErrorException      若用户 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    boolean updateSongListName (Integer uid, Integer listId, String listName)
            throws SongListErrorException, UidErrorException, NullUserException, SystemErrorException;
    
    /**
     * 发布动态
     *
     * @param uid  发布动态的用户的 uid
     * @param info 动态内容
     * @return 若发布动态没有异常，返回 true
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws NewsErrorException   若动态内容为空，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    boolean addNews (Integer uid, String info)
            throws UidErrorException, NullUserException, NewsErrorException, SystemErrorException;
    
    /**
     * 删除动态
     *
     * @param uid    需要删除动态的用户的 uid
     * @param newsId 需要删除的动态的 id
     * @return 若删除动态没有异常，返回 true
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws NewsErrorException   若 newsId 对应的动态不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    boolean deleteNews (Integer uid, Integer newsId)
            throws UidErrorException, NullUserException, NewsErrorException, SystemErrorException;
    
}
