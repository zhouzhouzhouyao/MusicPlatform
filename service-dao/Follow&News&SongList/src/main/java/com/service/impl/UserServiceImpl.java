package com.service.impl;

import com.dao.IUserDao;
import com.domain.*;
import com.exception.*;
import com.service.FollowAction;
import com.service.IUserService;
import com.service.SongAction;
import com.service.UpdateUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
@Service("userService")
@Scope("prototype")
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
public class UserServiceImpl implements IUserService {
    
    private IUserDao userDao;
    
    private final int validUidLow = 100000;
    
    private final int validUidHigh = 1000000;
    
    @Autowired
    public void setUserDao (IUserDao userDao) {
        this.userDao = userDao;
    }
    
    /**
     * 检查 uid 对应的用户是否不存在存在
     *
     * @param uid 要检查的 uid
     * @return 布尔值，为 true 则 uid 对应用户不存在，为 false 则对应用户存在
     */
    private boolean checkUserIsNotExistByUid (Integer uid) {
        return userDao.checkUserIsExistByUid(uid) == null;
    }
    
    /**
     * 检查电话号对应的用户是否存在
     *
     * @param phone 要检查的电话号码
     * @return 布尔值，为 true 则该号码对应用户存在，为 false 则对应用户不存在
     */
    private boolean checkUserIsExistByPhone (String phone) {
        return userDao.checkUserIsExistByPhone(phone) != null;
    }
    
    private boolean checkIsFollowing (Integer uid, Integer fUid) {
        List<Integer> ids = userDao.findAllFollowingUid(uid);
        return ids != null && ids.contains(fUid);
    }
    
    private boolean checkSongListIsExist (Integer uid, Integer listId) {
        List<Integer> ids = userDao.findAllSongListId(uid);
        return ids != null && ids.contains(listId);
    }
    
    private boolean checkSongIsExist (Integer songId) {
        return userDao.findSong(songId) != null;
    }
    
    private boolean checkSongIsExistInList (Integer uid, Integer listId, Integer songId) {
        List<Integer> ids = userDao.findAllSongId(uid, listId);
        return ids != null && ids.contains(songId);
    }
    
    private boolean checkNewsIdIsExist (Integer uid, Integer newsId) {
        List<Integer> ids = userDao.findAllNewsId(uid);
        return ids != null && ids.contains(newsId);
    }
    
    /**
     * 检查用户 uid 是否合法或者是否存在
     *
     * @param uid 需要检查的 uid
     * @throws UidErrorException 若 uid 不合法，抛出该异常
     * @throws NullUserException 若 uid 对应的用户不存在，抛出该异常
     */
    private void checkUidValid (Integer uid) throws UidErrorException, NullUserException {
        if (uid == null || uid <= validUidLow || uid >= validUidHigh) {
            throw new UidErrorException("uid 错误");
        }
        if (checkUserIsNotExistByUid(uid)) {
            throw new NullUserException("用户不存在");
        }
    }
    
    /**
     * 检查用户的电话号码、用户名、生日的数据是否合法，若不合法，抛出异常
     *
     * @param user 需要检查的用户对象
     * @throws UserPhoneNotValidException 若用户的电话属性不合法，抛出该异常
     * @throws UserInfoNotValidException  若用户用户名为 null 或者为空字符串，抛出该异常；
     *                                    若用户的生日对象字符串不满足 yyyy-MM-dd 的格式，抛出该异常
     */
    private void checkInfoValid (User user) throws UserPhoneNotValidException, UserInfoNotValidException {
        checkPhoneValid(user.getPhone());
        if (user.getUsername() == null || user.getUsername().length() == 0) {
            throw new UserInfoNotValidException("用户名不可为空");
        }
        if (user.getVip() == null) {
            user.setVip(false);
        }
        if (user.getBirthday() != null) {
            try {
                String birth = user.getBirthday();
                if (birth.length() != 10) {
                    throw new Exception();
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(birth);
                user.setBirthday(format.format(date));
            } catch (Exception e) {
                throw new UserInfoNotValidException("生日数据异常");
            }
        }
    }
    
    /**
     * 检查用户的电话是否合法，若不合法，抛出异常
     *
     * @param phone 需要检查的电话字符串
     * @throws UserPhoneNotValidException 若传入的电话字符串对象为 null，抛出该异常；
     *                                    若传入的电话字符串对象长度不为 11，抛出该异常；
     *                                    若传入的电话字符串对象解析为数字对象后不为 11 位或者无法解析为数字对象，抛出该异常
     */
    private void checkPhoneValid (String phone) throws UserPhoneNotValidException {
        if (phone == null) {
            throw new UserPhoneNotValidException("电话不可为空");
        }
        if (phone.length() != 11) {
            throw new UserPhoneNotValidException("号码不合法");
        }
        else {
            try {
                long phoneNum = Long.parseLong(phone);
                if (phoneNum <= 10000000000L || phoneNum >= 20000000000L) {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new UserPhoneNotValidException("号码不合法");
            }
        }
    }
    
    /**
     * 检查密码是否符合要求
     *
     * @param password 需要检查的密码字符串
     * @throws UserPasswordNotValidException 若传入的字符串为 null 或者密码长度为 0，抛出该异常；
     *                                       若传入的字符串长度小于 8，抛出该异常
     */
    private void checkPasswordValid (String password) throws UserPasswordNotValidException {
        if (password == null || password.length() == 0) {
            throw new UserPasswordNotValidException("密码不可为空");
        }
        if (password.length() < 8) {
            throw new UserPasswordNotValidException("密码长度过短，至少需要设置 8 位的密码");
        }
    }
    
    private void checkSongListValid (Integer uid, Integer listId) throws UidErrorException, NullUserException, SongListErrorException {
        checkUidValid(uid);
        if (listId == null || listId < 0) {
            throw new SongListErrorException("歌单 id 错误");
        }
        if (!checkSongListIsExist(uid, listId)) {
            throw new SongListErrorException("歌单不存在");
        }
    }
    
    private void checkSongIdValid (Integer songId) throws SongIdErrorException {
        int validSongIdLow = 10000000;
        int validSongIdHigh = 100000000;
        if (songId == null || songId <= validSongIdLow || songId >= validSongIdHigh) {
            throw new SongIdErrorException("歌曲 id 错误");
        }
        if (!checkSongIsExist(songId)) {
            throw new SongIdErrorException("歌曲不存在");
        }
    }
    
    /**
     * 根据用户 uid 获得用户密码哈希值
     *
     * @param uid 需要获取密码哈希值的用户 uid
     * @return uid 对应用户的密码哈希值
     */
    private int findUserPasswordByUid (Integer uid) {
        return userDao.findUserPasswordByUid(uid);
    }
    
    /**
     * 根据用户电话号码获得用户密码哈希值
     *
     * @param phone 需要获取密码哈希值的用户电话号码
     * @return phone 对应的用户的密码哈希值
     */
    private int findUserPasswordByPhone (String phone) {
        return userDao.findUserPasswordByPhone(phone);
    }
    
    private int findUserListCount (Integer uid) {
        return userDao.findUserListCount(uid);
    }
    
    public void addAdministrator () throws NullUserException, UidErrorException, UserPasswordNotValidException, UserPhoneNotValidException, UserInfoNotValidException {
        for (User user : userDao.addAdministrator()) {
            userDao.addUser(user);
        }
    }
    
    @Override
    public List<User> findAllUser () {
        return userDao.findAllUser();
    }
    
    @Override
    public User findUserByUid (Integer uid) throws NullUserException {
        User user = userDao.findUserByUid(uid);
        if (user == null) {
            throw new NullUserException("找不到该用户");
        }
        else {
            return user;
        }
    }
    
    @Override
    public List<User> findUserByName (String username) {
        return userDao.findUserByUsername("%" + username + "%");
    }
    
    @Override
    public List<FollowUser> findAllFollowing (Integer uid) {
        return userDao.findAllFollowing(uid);
    }
    
    @Override
    public List<FollowUser> findAllFollower (Integer uid) {
        return userDao.findAllFollower(uid);
    }
    
    @Override
    public List<SongList> findAllSongList (Integer uid) {
        return userDao.findAllSongList(uid);
    }
    
    @Override
    public List<Song> findAllSong (Integer uid, Integer listId) {
        return userDao.findAllSong(uid + "_Songs_" + listId);
    }
    
    @Override
    public List<News> findAllNews (Integer uid) {
        List<FollowUser> followUsers = userDao.findAllFollowing(uid);
        List<News> news = new ArrayList<>(followUsers.size() * 10);
        for (FollowUser followUser : followUsers) {
            news.addAll(userDao.findAllNews(followUser.getUid()));
        }
        return news;
    }
    
    @Override
    public boolean addUser (User user, String password) throws NullUserException, UidErrorException, UserPasswordNotValidException, UserPhoneNotValidException, UserInfoNotValidException {
        if (user == null) {
            throw new NullUserException("用户为空");
        }
        if (user.getUid() != null) {
            throw new UidErrorException("用户 uid 异常");
        }
        if (checkUserIsExistByPhone(user.getPhone())) {
            throw new UserPhoneNotValidException("该号码已被注册");
        }
        checkPasswordValid(password);
        checkInfoValid(user);
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        while (true) {
            int uid = random.nextInt(999999);
            if (uid > validUidLow && checkUserIsNotExistByUid(uid)) {
                user.setUid(uid);
                break;
            }
        }
        user.setPassword(password.hashCode());
        try {
            userDao.addUser(user);
            return true;
        } catch (Exception e) {
            userDao.deleteUser(user.getUid());
            return false;
        }
    }
    
    @Override
    public boolean updateUser (User user, UpdateUserAction action) throws NullUserException, UidErrorException, UserPhoneNotValidException, UserInfoNotValidException {
        if (user == null) {
            throw new NullUserException("用户不存在");
        }
        int uid = user.getUid();
        checkUidValid(uid);
        if (UpdateUserAction.updateUserInfo.equals(action)) {
            checkInfoValid(user);
            userDao.updateUserInfo(user);
            List<Integer> ids = userDao.findAllFollowersUid(uid);
            for (Integer id : ids) {
                userDao.updateFollowersListInfo(id, user);
            }
            return true;
        }
        else if (UpdateUserAction.updateUserVip.equals(action)) {
            userDao.updateVip(uid);
            List<Integer> ids = userDao.findAllFollowersUid(uid);
            for (Integer id : ids) {
                userDao.updateFollowersListVip(id, uid);
            }
            return true;
        }
        else {
            throw new UnknownActionException("未知操作");
        }
    }
    
    @Override
    public boolean updateUserPhone (Integer uid, String phone) throws UidErrorException, NullUserException, UserPhoneNotValidException {
        checkUidValid(uid);
        checkPhoneValid(phone);
        boolean isExist = checkUserIsExistByPhone(phone);
        if (isExist) {
            throw new UserPhoneNotValidException("该号码已被注册");
        }
        else {
            userDao.updateUserPhone(uid, phone);
            return true;
        }
    }
    
    @Override
    public boolean updateUserPassword (Integer uid, String oldPassword, String newPassword) throws UidErrorException, NullUserException, UserPasswordNotValidException {
        if (oldPassword == null) {
            throw new UserPasswordNotValidException("旧密码为空");
        }
        checkUidValid(uid);
        checkPasswordValid(newPassword);
        int oldPasswordVal = oldPassword.hashCode();
        int passwordVal = findUserPasswordByUid(uid);
        if (oldPasswordVal != passwordVal) {
            throw new UserPasswordNotValidException("旧密码输入错误");
        }
        int newPasswordVal = newPassword.hashCode();
        if (newPasswordVal == oldPasswordVal) {
            throw new UserPasswordNotValidException("新密码不可以与当前密码相同");
        }
        else {
            userDao.updateUserPassword(uid, newPasswordVal);
            return true;
        }
    }
    
    @Override
    public boolean login (String account, String password) throws AccountNotValidException, UserPasswordNotValidException, UidErrorException, UserPhoneNotValidException {
        int rPassword;
        if (account == null) {
            throw new AccountNotValidException("账号不可为空");
        }
        if (password == null) {
            throw new UserPasswordNotValidException("密码不可为空");
        }
        if (account.length() == 6) {
            int uid;
            try {
                uid = Integer.parseInt(account);
                if (uid <= validUidLow || uid >= validUidHigh) {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new UidErrorException("uid 错误");
            }
            rPassword = findUserPasswordByUid(uid);
        }
        else if (account.length() == 11) {
            try {
                checkPhoneValid(account);
            } catch (Exception e) {
                throw new UserPhoneNotValidException("电话号码错误");
            }
            rPassword = findUserPasswordByPhone(account);
        }
        else {
            throw new AccountNotValidException("账号错误");
        }
        int passwordVal = password.hashCode();
        if (rPassword != passwordVal) {
            throw new UserPasswordNotValidException("密码错误");
        }
        else {
            return true;
        }
    }
    
    @Override
    public boolean createSongList (Integer uid, String listName) throws UidErrorException, NullUserException, SongListErrorException {
        checkUidValid(uid);
        int listId = findUserListCount(uid);
        if (listName == null || listName.length() == 0) {
            throw new SongListErrorException("歌单名不可为空");
        }
        try {
            userDao.createSongList(uid, listId, listName);
            return true;
        } catch (Exception e) {
            userDao.removeSongList(uid, listId);
            return false;
        }
    }
    
    @Override
    public boolean removeSongList (Integer uid, Integer listId) throws UidErrorException, NullUserException, SongListErrorException {
        checkSongListValid(uid, listId);
        SongList songList = userDao.findSongList(uid, listId);
        List<Song> songs = userDao.findAllSong(songList.getFullName());
        try {
            userDao.removeSongList(uid, listId);
            return true;
        } catch (Exception e) {
            userDao.createSongList(uid, listId, songList.getListName());
            for (Song song : songs) {
                userDao.addSong(uid, listId, song.getSongId());
            }
            return false;
        }
    }
    
    @Override
    public boolean updateSongList (Integer uid, Integer listId, Integer songId, SongAction action) throws UidErrorException, NullUserException, SongListErrorException, SongIdErrorException, UnknownActionException {
        checkSongListValid(uid, listId);
        checkSongIdValid(songId);
        if (SongAction.addSong.equals(action)) {
            if (checkSongIsExistInList(uid, listId, songId)) {
                throw new SongListErrorException("不可向一个歌单中重复添加一首歌");
            }
            else {
                userDao.addSong(uid, listId, songId);
                return true;
            }
        }
        else if (SongAction.removeSong.equals(action)) {
            if (!checkSongIsExistInList(uid, listId, songId)) {
                throw new SongListErrorException("当前歌单中没有这首歌");
            }
            else {
                userDao.removeSong(uid, listId, songId);
                return true;
            }
        }
        else {
            throw new UnknownActionException("未知操作");
        }
    }
    
    @Override
    public boolean updateSongListName (Integer uid, Integer listId, String listName) {
        if (Integer.valueOf(0).equals(listId)) {
            throw new SongListErrorException("当前歌单不支持修改名称");
        }
        if (listName == null || listName.length() == 0) {
            throw new SongListErrorException("歌单名不可为空");
        }
        checkSongListValid(uid, listId);
        userDao.updateSongList(uid, listId, listName);
        return true;
    }
    
    @Override
    public boolean updateFollow (Integer uid, Integer fUid, FollowAction action) throws UidErrorException, NullUserException, FollowUserException, UnknownActionException {
        checkUidValid(uid);
        checkUidValid(fUid);
        if (uid.equals(fUid)) {
            throw new UidErrorException("不可以关注自己");
        }
        if (checkUserIsNotExistByUid(uid) || checkUserIsNotExistByUid(fUid)) {
            throw new NullUserException("找不到用户");
        }
        if (FollowAction.addFollow.equals(action)) {
            if (checkIsFollowing(uid, fUid)) {
                throw new FollowUserException("不能重复关注同一用户");
            }
            else {
                userDao.addFollow(uid, fUid);
                return true;
            }
        }
        else if (FollowAction.removeFollow.equals(action)) {
            if (!checkIsFollowing(uid, fUid)) {
                throw new FollowUserException("还没有关注该用户");
            }
            else {
                userDao.removeFollow(uid, fUid);
                return true;
            }
        }
        else {
            throw new UnknownActionException("未知操作");
        }
    }
    
    @Override
    public boolean addNews (Integer uid, String info) {
        checkUidValid(uid);
        if (info == null || info.length() == 0) {
            throw new NewsErrorException("动态内容不可为空");
        }
        userDao.addNews(uid, info, new Date());
        return true;
    }
    
    @Override
    public boolean deleteNews (Integer uid, Integer newsId) {
        checkUidValid(uid);
        if (newsId == null || newsId <= 0) {
            throw new NewsErrorException("动态 id 错误");
        }
        if (!checkNewsIdIsExist(uid, newsId)) {
            throw new NewsErrorException("动态不存在");
        }
        else {
            userDao.deleteNews(uid, newsId);
            return true;
        }
    }
    
    @Override
    public void deleteUser (Integer uid) {
        userDao.deleteUser(uid);
    }
    
}
