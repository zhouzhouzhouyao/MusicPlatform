package com.service.impl;

import com.dao.IUserDao;
import com.domain.User;
import com.exception.*;
import com.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
@Service("userService")
@Scope("prototype")
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = TransactionException.class)
public class UserServiceImpl implements IUserService {
    
    private IUserDao userDao;
    
    private final int validUid = 100000;
    
    @Autowired
    public void setUserDao (IUserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public List<User> findAll () {
        System.out.println("\n==========Service 的 findAll 方法执行了==========\n");
        return userDao.findAll();
    }
    
    @Override
    public User findUserByUid (Integer uid) throws NullUserException {
        System.out.println("\n==========Service 的 findUserByUid 方法执行了==========\n");
        User user = userDao.findUserByUid(uid);
        if(user == null) {
            throw new NullUserException("找不到该用户");
        }
        else {
            return user;
        }
    }
    
    @Override
    public List<User> findUserByName (String username) {
        System.out.println("\n==========Service 的 findUserByName 方法执行了==========\n");
        return userDao.findUserByUsername("%" + username + "%");
    }
    
    @Override
    public boolean checkUserIsExistByUid (Integer uid) {
        System.out.println("\n==========Service 的 checkUserIsExistByUid 方法执行了==========\n");
        return userDao.checkUserIsExistByUid(uid) != null;
    }
    
    @Override
    public boolean checkUserIsExistByPhone (String phone) {
        System.out.println("\n==========Service 的 checkUserIsExistByPhone 方法执行了==========\n");
        return userDao.checkUserIsExistByPhone(phone) != null;
    }
    
    @Override
    public void checkUidValid (Integer uid) throws UidErrorException, NullUserException {
        if(uid == null || uid <= validUid) {
            throw new UidErrorException("uid 错误");
        }
        if(!checkUserIsExistByUid(uid)) {
            throw new NullUserException("用户不存在");
        }
    }
    
    @Override
    public void checkInfoValid (User user) throws UserPhoneNotValidException, UserInfoNotValidException {
        checkPhoneValid(user.getPhone());
        if(user.getUsername() == null || user.getUsername().length() == 0) {
            throw new UserInfoNotValidException("用户名不可为空");
        }
        if(user.getVip() == null) {
            user.setVip(false);
        }
        if(user.getBirthday() != null) {
            try {
                String birth = user.getBirthday();
                if(birth.length() != 10) {
                    throw new Exception();
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(birth);
                user.setBirthday(format.format(date));
            }catch (Exception e) {
                throw new UserInfoNotValidException("生日数据异常");
            }
        }
    }
    
    @Override
    public void checkPhoneValid (String phone) throws UserPhoneNotValidException {
        if(phone == null) {
            throw new UserPhoneNotValidException("电话不可为空");
        }
        if(phone.length() != 11) {
            throw new UserPhoneNotValidException("号码不合法");
        }
        else {
            try {
                long phoneNum = Long.parseLong(phone);
                if(phoneNum <= 10000000000L || phoneNum >= 20000000000L) {
                    throw new Exception();
                }
            }catch (Exception e) {
                throw new UserPhoneNotValidException("号码不合法");
            }
        }
    }
    
    @Override
    public void checkPasswordValid (String password) throws UserPasswordNotValidException {
        if(password == null || password.length() == 0) {
            throw new UserPasswordNotValidException("密码不可为空");
        }
        if(password.length() < 8) {
            throw new UserPasswordNotValidException("密码长度过短，至少需要设置 8 位的密码");
        }
    }
    
    @Override
    public int getUserPasswordByUid (Integer uid) {
        return userDao.getUserPasswordByUid(uid);
    }
    
    @Override
    public int getUserPasswordByPhone (String phone) {
        return userDao.getUserPasswordByPhone(phone);
    }
    
    @Override
    public boolean saveUser (User user, String password)
            throws NullUserException, UidErrorException, UserPasswordNotValidException, UserPhoneNotValidException, UserInfoNotValidException {
        System.out.println("\n==========Service 的 saveUser 方法执行了==========\n");
        if(user == null) {
            throw new NullUserException("用户为空");
        }
        if(user.getUid() != null) {
            throw new UidErrorException("用户 uid 异常");
        }
        if(checkUserIsExistByPhone(user.getPhone())) {
            throw new UserPhoneNotValidException("该号码已被注册");
        }
        checkPasswordValid(password);
        checkInfoValid(user);
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        while(true) {
            int uid = random.nextInt(999999);
            if(uid > validUid && !checkUserIsExistByUid(uid)) {
                user.setUid(uid);
                break;
            }
        }
        user.setPassword(password.hashCode());
        userDao.saveUser(user);
        return true;
    }
    
    @Override
    public boolean updateUserInfo (User user) throws NullUserException, UidErrorException, UserPhoneNotValidException, UserInfoNotValidException {
        System.out.println("\n==========Service 的 updateUserInfo 方法执行了==========\n");
        if (user == null) {
            throw new NullUserException("用户不存在");
        }
        checkUidValid(user.getUid());
        checkInfoValid(user);
        userDao.updateUserInfo(user);
        return true;
    }
    
    @Override
    public boolean updateVip (Integer uid) throws UidErrorException, NullUserException {
        System.out.println("\n==========Service 的 updateVip 方法执行了==========\n");
        checkUidValid(uid);
        userDao.updateVip(uid);
        return true;
    }
    
    @Override
    public boolean updateUserPhone (Integer uid, String phone) throws UidErrorException, NullUserException, UserPhoneNotValidException {
        System.out.println("\n==========Service 的 updateUserPhone 方法执行了==========\n");
        checkUidValid(uid);
        checkPhoneValid(phone);
        boolean isExist = checkUserIsExistByPhone(phone);
        if(isExist) {
            throw new UserPhoneNotValidException("该号码已被注册");
        }
        else {
            userDao.updateUserPhone(uid, phone);
            return true;
        }
    }
    
    @Override
    public boolean updateUserPassword (Integer uid, String oldPassword, String newPassword) throws UidErrorException, NullUserException, UserPasswordNotValidException {
        System.out.println("\n==========Service 的 updateUserPassword 方法执行了==========\n");
        if(oldPassword == null) {
            throw new UserPasswordNotValidException("旧密码为空");
        }
        checkUidValid(uid);
        checkPasswordValid(newPassword);
        int oldPasswordVal = oldPassword.hashCode();
        int passwordVal = getUserPasswordByUid(uid);
        if(oldPasswordVal != passwordVal) {
            throw new UserPasswordNotValidException("旧密码输入错误");
        }
        int newPasswordVal = newPassword.hashCode();
        if(newPasswordVal == oldPasswordVal) {
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
        if(account == null) {
            throw new AccountNotValidException("账号不可为空");
        }
        if(password == null) {
            throw new UserPasswordNotValidException("密码不可为空");
        }
        if(account.length() == 6) {
            int uid;
            try {
                uid = Integer.parseInt(account);
                if(uid <= validUid) {
                    throw new Exception();
                }
            }catch (Exception e) {
                throw new UidErrorException("uid 错误");
            }
            rPassword = getUserPasswordByUid(uid);
        }
        else if(account.length() == 11) {
            try {
                checkPhoneValid(account);
            }catch (Exception e) {
                throw new UserPhoneNotValidException("电话号码错误");
            }
            rPassword = getUserPasswordByPhone(account);
        }
        else {
            throw new AccountNotValidException("账号错误");
        }
        int passwordVal = password.hashCode();
        if(rPassword != passwordVal) {
            throw new UserPasswordNotValidException("密码错误");
        }
        else {
            return true;
        }
    }
    
    @Override
    public void createSongList (Integer uid, Integer listId) {
        String name = uid + "_" + listId;
        userDao.createSongList(name);
    }
    
    @Override
    public void deleteSongList (Integer uid, Integer listId) {
        String name = uid + "_" + listId;
        userDao.deleteSongList(name);
    }
    
}
