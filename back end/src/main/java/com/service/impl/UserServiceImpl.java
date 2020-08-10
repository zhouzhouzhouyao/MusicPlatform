package com.service.impl;

import com.dao.IUserDao;
import com.domain.*;
import com.enums.FollowActionEnum;
import com.enums.ShowNewsActionEnum;
import com.exception.*;
import com.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 检查 uid 对应的用户是否不存在
     *
     * @param uid 要检查的 uid
     * @return 布尔值，为 true 则 uid 对应用户不存在，为 false 则对应用户存在
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private boolean checkUserIsNotExistByUid (Integer uid) throws SystemErrorException {
        User user;
        try {
            user = userDao.findUserById(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return user == null;
    }
    
    /**
     * 检查电话号对应的用户是否存在
     *
     * @param phone 要检查的电话号码
     * @return 布尔值，为 true 则该号码对应用户存在，为 false 则对应用户不存在
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private boolean checkUserIsExistByPhone (String phone) throws SystemErrorException {
        User user;
        try {
            user = userDao.findUserByPhone(phone);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return user != null;
    }
    
    /**
     * 检查 uid 对应的用户是否正在关注 fUid 对应的用户
     *
     * @param uid  粉丝用户 uid
     * @param fUid 被关注用户 uid
     * @return 布尔值，为 true 则未关注，为 false 则正在关注
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private boolean checkIsFollowing (Integer uid, Integer fUid) throws SystemErrorException {
        List<Integer> ids;
        try {
            ids = userDao.findAllFollowingUid(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return ids != null && ids.contains(fUid);
    }
    
    /**
     * 检查歌曲是否在歌单中
     *
     * @param fullName 歌单表全名
     * @param url      歌曲 url
     * @return 布尔值，为 true 则存在，为 false 则不存在
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private boolean checkSongIsExistInList (String fullName, String url) throws SystemErrorException {
        List<String> urls;
        try {
            urls = userDao.findAllSongUrl(fullName);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return urls != null && urls.contains(url);
    }
    
    /**
     * 检查动态 id 是否存在
     *
     * @param uid    用户 uid
     * @param newsId 动态的 id
     * @return 布尔值，为 true 则存在，为 false 则不存在
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private boolean checkNewsIdIsExist (Integer uid, Integer newsId) throws SystemErrorException {
        List<Integer> ids;
        try {
            ids = userDao.findAllNewsId(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return ids != null && ids.contains(newsId);
    }
    
    /**
     * 检查用户 uid 是否合法或者是否存在
     *
     * @param uid 需要检查的 uid
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private void checkUidValid (Integer uid) throws UidErrorException, NullUserException, SystemErrorException {
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
     * @throws UserInfoNotValidException 若用户用户名为 null 或者为空字符串，抛出该异常；
     *                                   若用户的生日对象字符串不满足 yyyy-MM-dd 的格式，抛出该异常
     */
    private void checkInfoValid (User user) throws UserPhoneNotValidException, UserInfoNotValidException {
        if (user.getUsername() == null || user.getUsername().length() == 0) {
            throw new UserInfoNotValidException("用户名不可为空");
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
    
    /**
     * 检查歌单是否存在或者歌单 id 是否合法
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @throws UidErrorException      若 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若歌单索引不合法，抛出该异常；
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    private void checkSongListValid (Integer uid, Integer listIndex) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException {
        checkUidValid(uid);
        List<SongList> songLists = userDao.findAllSongList(uid);
        if (listIndex == null || listIndex < 0 || listIndex >= songLists.size()) {
            throw new SongListErrorException("歌单索引错误");
        }
    }
    
    /**
     * 检查歌曲信息是否合法
     *
     * @param name   歌曲名
     * @param singer 歌曲的歌手
     * @param url    歌曲的 url
     * @return 若歌曲信息合法，返回歌曲对象
     * @throws SongErrorException   若歌曲名为空或者歌曲 url 为空，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private Song checkSongValid (String name, String singer, String url) throws SongErrorException, SystemErrorException {
        if (name == null || name.length() == 0) {
            throw new SongErrorException("歌曲名为空");
        }
        if (url == null || url.length() == 0) {
            throw new SongErrorException("歌曲 url 为空");
        }
        return new Song(name, singer, url);
    }
    
    /**
     * 根据用户 uid 获得用户密码哈希值
     *
     * @param uid 需要获取密码哈希值的用户 uid
     * @return uid 对应用户的密码哈希值
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private Integer findUserPasswordByUid (Integer uid) throws SystemErrorException {
        Integer password;
        try {
            password = userDao.findUserPasswordByUid(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return password;
    }
    
    /**
     * 根据用户电话号码获得用户密码哈希值
     *
     * @param phone 需要获取密码哈希值的用户电话号码
     * @return phone 对应的用户的密码哈希值
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private Integer findUserPasswordByPhone (String phone) throws SystemErrorException {
        Integer password;
        try {
            password = userDao.findUserPasswordByPhone(phone);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return password;
    }
    
    /**
     * 查找用户当前歌单 id 后缀到达了多少号
     *
     * @param uid 用户 uid
     * @return 当前最大的歌单 id 后缀
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private int findUserListCount (Integer uid) throws SystemErrorException {
        int listCount;
        try {
            listCount = userDao.findUserListCount(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return listCount;
    }
    
    /**
     * 查找歌曲的索引
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @return 歌曲应该被赋予的索引值
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private int findSongIndex (Integer uid, Integer listIndex) throws SystemErrorException {
        int songIndex;
        try {
            String fullName = findListFullName(uid, listIndex);
            List<String> urls = userDao.findAllSongUrl(fullName);
            songIndex = urls.size();
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return songIndex;
    }
    
    /**
     * 查找歌单表的全名
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @return 歌单表的全名
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    private String findListFullName (Integer uid, Integer listIndex) throws SystemErrorException {
        String fullName;
        try {
            fullName = userDao.findSongListFullName(uid, listIndex);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return fullName;
    }
    
    @Override
    public User login (String account, String password) throws AccountNotValidException, UserPasswordNotValidException, UidErrorException, NullUserException, UserPhoneNotValidException, SystemErrorException {
        int rPassword;
        User user;
        if (account == null) {
            throw new AccountNotValidException("账号不可为空");
        }
        if (password == null) {
            throw new UserPasswordNotValidException("密码不可为空");
        }
        if (account.length() == 6) {
            Integer uid;
            try {
                uid = Integer.valueOf(account);
            } catch (Exception e) {
                throw new UidErrorException("uid 错误");
            }
            checkUidValid(uid);
            try {
                user = userDao.findUserByUid(uid);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            rPassword = findUserPasswordByUid(uid);
        }
        else if (account.length() == 11) {
            try {
                checkPhoneValid(account);
            } catch (Exception e) {
                throw new UserPhoneNotValidException("电话号码错误");
            }
            if (!checkUserIsExistByPhone(account)) {
                throw new NullUserException("用户不存在");
            }
            else {
                try {
                    user = userDao.findUserByPhone(account);
                } catch (Exception exception) {
                    throw new SystemErrorException("系统异常");
                }
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
            return user;
        }
    }
    
    @Override
    public User findUserByUid (Integer uid) throws UidErrorException, SystemErrorException, NullUserException {
        if (uid == null || uid <= validUidLow || uid >= validUidHigh) {
            throw new UidErrorException("uid 错误");
        }
        User user;
        try {
            user = userDao.findUserById(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        if (user == null) {
            throw new NullUserException("找不到该用户");
        }
        else {
            return user;
        }
    }
    
    @Override
    public List<User> findUserByName (String username) throws SystemErrorException {
        if (username == null || username.length() == 0) {
            throw new NullUserException("用户名为空");
        }
        String name = "%" + username + "%";
        List<User> res;
        try {
            res = userDao.findUserByUsername(name);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return res;
    }
    
    @Override
    public List<FollowUser> findAllFollowing (Integer uid) throws UidErrorException, NullUserException, SystemErrorException {
        checkUidValid(uid);
        List<FollowUser> following;
        try {
            following = userDao.findAllFollowing(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return following;
    }
    
    @Override
    public List<FollowUser> findAllFollower (Integer uid) throws UidErrorException, NullUserException, SystemErrorException {
        checkUidValid(uid);
        List<FollowUser> followers;
        try {
            followers = userDao.findAllFollower(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return followers;
    }
    
    @Override
    public List<SongList> findAllSongList (Integer uid) throws UidErrorException, NullUserException, SystemErrorException {
        checkUidValid(uid);
        List<SongList> songLists;
        try {
            songLists = userDao.findAllSongList(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return songLists;
    }
    
    @Override
    public List<Song> findAllSongInList (Integer uid, Integer listIndex) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException {
        checkSongListValid(uid, listIndex);
        String fullName = uid + "_Songs_" + listIndex;
        List<Song> songs;
        try {
            songs = userDao.findAllSongInList(fullName);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return songs;
    }
    
    @Override
    public String findSongUrl (Integer uid, Integer listIndex, Integer songIndex) throws SongListErrorException, SongErrorException, SystemErrorException {
        checkUidValid(uid);
        if (listIndex < 0) {
            throw new SongListErrorException("歌单索引错误");
        }
        if (songIndex < 0) {
            throw new SongErrorException("歌曲索引错误");
        }
        List<SongList> songLists;
        try {
            songLists = userDao.findAllSongList(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        if (listIndex >= songLists.size()) {
            throw new SongListErrorException("歌单索引错误");
        }
        SongList songList = songLists.get(listIndex);
        List<Song> songs = songList.getSongs();
        if (songIndex >= songs.size()) {
            throw new SongErrorException("歌曲索引错误");
        }
        return songs.get(songIndex).getUrl();
    }
    
    @Override
    public List<News> findAllNews (Integer uid, ShowNewsActionEnum action) throws UidErrorException, NullUserException, SystemErrorException, UnknownActionException {
        checkUidValid(uid);
        List<News> news;
        List<News> self;
        try {
            self = userDao.findAllNews(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        if (ShowNewsActionEnum.SELF.equals(action)) {
            news = self;
        }
        else if (ShowNewsActionEnum.ALL.equals(action)) {
            List<Integer> ids;
            try {
                ids = userDao.findAllFollowingUid(uid);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            news = new ArrayList<>(ids.size() * 10);
            news.addAll(self);
            for (Integer id : ids) {
                List<News> newsList;
                try {
                    newsList = userDao.findAllNews(id);
                } catch (Exception exception) {
                    throw new SystemErrorException("系统异常");
                }
                news.addAll(newsList);
            }
        }
        else {
            throw new UnknownActionException("未知操作");
        }
        return news;
    }
    
    @Override
    public boolean addUser (User user, String password) throws NullUserException, UidErrorException, UserPhoneNotValidException, UserPasswordNotValidException, UserInfoNotValidException, SystemErrorException {
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
        checkPhoneValid(user.getPhone());
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
        } catch (Exception e) {
            try {
                userDao.deleteUser(user.getUid());
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return false;
        }
        return true;
    }
    
    @Override
    public boolean updateUser (User user) throws NullUserException, UidErrorException, UserPhoneNotValidException, UserInfoNotValidException, SystemErrorException {
        if (user == null) {
            throw new NullUserException("用户不存在");
        }
        int uid = user.getUid();
        checkUidValid(uid);
        checkInfoValid(user);
        try {
            userDao.updateUserInfo(user);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        List<Integer> ids;
        try {
            ids = userDao.findAllFollowersUid(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        for (Integer id : ids) {
            try {
                userDao.updateFollowersListInfo(id, user);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
        }
        return true;
    }
    
    @Override
    public boolean updateUserPhone (Integer uid, String phone) throws UidErrorException, NullUserException, UserPhoneNotValidException, SystemErrorException {
        checkUidValid(uid);
        checkPhoneValid(phone);
        boolean isExist = checkUserIsExistByPhone(phone);
        if (isExist) {
            throw new UserPhoneNotValidException("该号码已被注册");
        }
        else {
            try {
                userDao.updateUserPhone(uid, phone);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return true;
        }
    }
    
    @Override
    public boolean updateUserPassword (Integer uid, String oldPassword, String newPassword) throws UidErrorException, NullUserException, UserPasswordNotValidException, SystemErrorException {
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
            try {
                userDao.updateUserPassword(uid, newPasswordVal);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return true;
        }
    }
    
    @Override
    public boolean updateFollow (Integer uid, Integer fUid, FollowActionEnum action) throws UidErrorException, NullUserException, SystemErrorException, FollowUserException, UnknownActionException {
        checkUidValid(uid);
        checkUidValid(fUid);
        if (uid.equals(fUid)) {
            throw new UidErrorException("不可以关注自己");
        }
        if (checkUserIsNotExistByUid(uid) || checkUserIsNotExistByUid(fUid)) {
            throw new NullUserException("找不到用户");
        }
        if (FollowActionEnum.ADD_FOLLOW.equals(action)) {
            if (checkIsFollowing(uid, fUid)) {
                throw new FollowUserException("不能重复关注同一用户");
            }
            else {
                try {
                    userDao.addFollow(uid, fUid);
                } catch (Exception exception) {
                    throw new SystemErrorException("系统异常");
                }
            }
        }
        else if (FollowActionEnum.REMOVE_FOLLOW.equals(action)) {
            if (!checkIsFollowing(uid, fUid)) {
                throw new FollowUserException("还没有关注该用户");
            }
            else {
                try {
                    userDao.removeFollow(uid, fUid);
                } catch (Exception exception) {
                    throw new SystemErrorException("系统异常");
                }
            }
        }
        else {
            throw new UnknownActionException("未知操作");
        }
        return true;
    }
    
    @Override
    public boolean createSongList (Integer uid, String listName) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException {
        checkUidValid(uid);
        int listId = findUserListCount(uid);
        if (listName == null || listName.length() == 0) {
            throw new SongListErrorException("歌单名不可为空");
        }
        try {
            userDao.createSongList(uid, listId, listName);
        } catch (Exception e) {
            try {
                userDao.removeSongList(uid + "_Songs_" + listId);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return false;
        }
        return true;
    }
    
    @Override
    public boolean removeSongList (Integer uid, Integer listIndex) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException {
        checkSongListValid(uid, listIndex);
        if (listIndex == 0) {
            throw new SongListErrorException("当前歌单不支持删除");
        }
        SongList songList;
        List<Song> songs;
        String fullName;
        try {
            songList = userDao.findSongList(uid, listIndex);
            fullName = songList.getFullName();
            songs = userDao.findAllSongInList(fullName);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        try {
            userDao.removeSongList(fullName);
        } catch (Exception e) {
            try {
                userDao.createSongList(uid, listIndex, songList.getListName());
                for (Song song : songs) {
                    userDao.addSong(fullName, song);
                }
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return false;
        }
        return true;
    }
    
    @Override
    public boolean addSong (Integer uid, Integer listIndex, String name, String singer, String url) throws UidErrorException, NullUserException, SongListErrorException, SongErrorException, SystemErrorException, UnknownActionException {
        checkSongListValid(uid, listIndex);
        String fullName = findListFullName(uid, listIndex);
        if (checkSongIsExistInList(fullName, url)) {
            throw new SongListErrorException("不可向一个歌单中重复添加一首歌");
        }
        else {
            Song song = checkSongValid(name, singer, url);
            song.setSongIndex(findSongIndex(uid, listIndex));
            try {
                userDao.addSong(fullName, song);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
        }
        return true;
    }
    
    @Override
    public boolean removeSong (Integer uid, Integer listIndex, Integer songIndex) throws UidErrorException, NullUserException, SongListErrorException, SongErrorException, SystemErrorException {
        checkSongListValid(uid, listIndex);
        String fullName = findListFullName(uid, listIndex);
        SongList songList = userDao.findSongList(uid, listIndex);
        List<Song> songs = songList.getSongs();
        if (songIndex == null || songIndex < 0 || songIndex >= songs.size()) {
            throw new SongErrorException("歌曲索引错误");
        }
        try {
            userDao.removeSong(fullName, songIndex);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return true;
    }
    
    @Override
    public boolean updateSongListName (Integer uid, Integer listIndex, String listName) throws SongListErrorException, UidErrorException, NullUserException, SystemErrorException {
        if (Integer.valueOf(0).equals(listIndex)) {
            throw new SongListErrorException("当前歌单不支持修改名称");
        }
        if (listName == null || listName.length() == 0) {
            throw new SongListErrorException("歌单名不可为空");
        }
        checkSongListValid(uid, listIndex);
        try {
            userDao.updateSongListName(uid, listIndex, listName);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return true;
    }
    
    @Override
    public boolean addNews (Integer uid, String info) throws UidErrorException, NullUserException, NewsErrorException, SystemErrorException {
        checkUidValid(uid);
        if (info == null || info.length() == 0) {
            throw new NewsErrorException("动态内容不可为空");
        }
        try {
            userDao.addNews(uid, info, new Date());
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return true;
    }
    
    @Override
    public boolean deleteNews (Integer uid, Integer newsId) throws UidErrorException, NullUserException, NewsErrorException, SystemErrorException {
        checkUidValid(uid);
        if (newsId == null || newsId <= 0) {
            throw new NewsErrorException("动态 id 错误");
        }
        if (!checkNewsIdIsExist(uid, newsId)) {
            throw new NewsErrorException("动态不存在");
        }
        else {
            try {
                userDao.deleteNews(uid, newsId);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return true;
        }
    }
    
}
