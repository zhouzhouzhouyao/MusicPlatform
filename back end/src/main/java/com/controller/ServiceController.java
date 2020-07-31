package com.controller;

import com.domain.*;
import com.exception.*;
import com.service.*;
import com.service.FollowActionEnum;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/27.
 */
@Controller
public class ServiceController {
    
    private IUserService userService;
    
    private JSONObject jsonObject;
    
    @Autowired
    public void setUserService (IUserService userService) {
        this.userService = userService;
    }
    
    private void transfer (Object object, Class<?> clazz) {
        JSONObject subObject = new JSONObject();
        if (clazz.equals(User.class)) {
            User user = (User) object;
            subObject.put("uid", user.getUid());
            subObject.put("phone", user.getPhone());
            subObject.put("username", user.getUsername());
            subObject.put("vip", user.getVip());
            subObject.put("sex", user.getSex());
            subObject.put("birthday", user.getBirthday());
            subObject.put("location", user.getLocation());
            jsonObject.accumulate("User", subObject);
        }
        else if (clazz.equals(FollowUser.class)) {
            FollowUser user = (FollowUser) object;
            subObject.put("uid", user.getUid());
            subObject.put("username", user.getUsername());
            subObject.put("vip", user.getVip());
            subObject.put("sex", user.getSex());
            subObject.put("birthday", user.getBirthday());
            subObject.put("location", user.getLocation());
            jsonObject.accumulate("FollowUser", subObject);
        }
        else if (clazz.equals(SongList.class)) {
            SongList songList = (SongList) object;
            subObject.put("uid", songList.getUid());
            subObject.put("listId", songList.getListId());
            subObject.put("listName", songList.getListName());
            jsonObject.accumulate("SongList", subObject);
        }
        else if (clazz.equals(Song.class)) {
            Song song = (Song) object;
            subObject.put("songId", song.getSongId());
            subObject.put("name", song.getName());
            subObject.put("singer", song.getSinger());
            subObject.put("url", song.getUrl());
            subObject.put("vip", song.getVip());
            jsonObject.accumulate("Song", subObject);
        }
        else if (clazz.equals(News.class)) {
            News news = (News) object;
            subObject.put("newsId", news.getNewsId());
            subObject.put("uid", news.getUid());
            subObject.put("info", news.getInfo());
            subObject.put("time", news.getTime());
            jsonObject.accumulate("News", subObject);
        }
        jsonObject.put("success", true);
    }
    
    private Integer getUid (HttpServletRequest request) throws UidErrorException {
        int uid;
        try {
            uid = Integer.parseInt(request.getParameter("uid"));
        } catch (NumberFormatException e) {
            throw new UidErrorException("uid 错误");
        }
        return uid;
    }
    
    private Integer getListId (HttpServletRequest request) throws SongListErrorException {
        int listId;
        try {
            listId = Integer.parseInt(request.getParameter("listId"));
        } catch (NumberFormatException e) {
            throw new SongListErrorException("歌单 id 错误");
        }
        return listId;
    }
    
    private void txException (TransactionException e) {
        jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("err", e.getClass() + " : " + e.getMessage());
    }
    
    private void exception (Exception e) {
        jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("err", "系统异常：" + e.getMessage());
    }
    
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String login (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            User user = userService.login(account, password);
            if (user != null) {
                transfer(user, User.class);
            }
            else {
                throw new TransactionException("登陆失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/searchUserByUid", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findUserByUid (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            User user = userService.findUserByUid(uid);
            if (user != null) {
                transfer(user, User.class);
            }
            else {
                throw new TransactionException("查找用户失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/searchUserByName", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findUserByName (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            String name = request.getParameter("username");
            List<User> users = userService.findUserByName(name);
            if (users != null) {
                for (User user : users) {
                    transfer(user, User.class);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new TransactionException("查找用户失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/follow", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findAllFollow (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            List<FollowUser> users;
            String type = request.getParameter("type");
            if ("following".equals(type)) {
                users = userService.findAllFollowing(uid);
            }
            else if ("followers".equals(type)) {
                users = userService.findAllFollower(uid);
            }
            else {
                throw new TransactionException("错误的请求");
            }
            if (users != null) {
                for (FollowUser user : users) {
                    transfer(user, FollowUser.class);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new TransactionException("关注模块异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/songList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findAllSongList (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            List<SongList> lists = userService.findAllSongList(uid);
            if (lists != null) {
                for (SongList list : lists) {
                    transfer(list, SongList.class);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SongListErrorException("歌单数据异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/songs", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findAllSongInList (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            Integer listId = getListId(request);
            List<Song> songs = userService.findAllSongInList(uid, listId);
            if (songs != null) {
                for (Song song : songs) {
                    transfer(song, Song.class);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SongListErrorException("歌单数据异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/singer", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findSongBySinger (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            String singer = request.getParameter("singer");
            List<Song> songs = userService.findSongBySinger(singer);
            if (songs != null) {
                for (Song song : songs) {
                    transfer(song, Song.class);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SongListErrorException("歌单加载失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/top10Songs", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String findTopSongs () {
        jsonObject = new JSONObject();
        try {
            List<Song> songs = userService.findTopSongs();
            if (songs != null) {
                for (Song song : songs) {
                    transfer(song, Song.class);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SongListErrorException("歌单加载失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/dailyRecommend", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String DailyRecommend () {
        jsonObject = new JSONObject();
        try {
            List<Song> songs = userService.findRandomSongs();
            if (songs != null) {
                for (Song song : songs) {
                    transfer(song, Song.class);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SongListErrorException("歌单加载失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/myZone", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String myZone (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            List<News> newsList = userService.findAllNews(uid, ShowNewsActionEnum.SELF);
            if (newsList != null) {
                for (News news : newsList) {
                    transfer(news, News.class);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("动态加载失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/friends", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String friends (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            List<News> newsList = userService.findAllNews(uid, ShowNewsActionEnum.ALL);
            if (newsList != null) {
                for (News news : newsList) {
                    transfer(news, News.class);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("动态加载失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String addUser (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            String phone = request.getParameter("phone");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            boolean vip = Boolean.parseBoolean(request.getParameter("vip"));
            boolean sex = Boolean.parseBoolean(request.getParameter("sex"));
            String birthday = request.getParameter("birthday");
            String location = request.getParameter("location");
            User user = new User();
            user.setPhone(phone);
            user.setUsername(username);
            user.setVip(vip);
            user.setSex(sex);
            user.setBirthday(birthday);
            user.setLocation(location);
            boolean flag = userService.addUser(user, password);
            if (flag) {
                transfer(user, User.class);
            }
            else {
                throw new SystemErrorException("用户添加失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateUser (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            String type = request.getParameter("type");
            User user = userService.findUserByUid(uid);
            boolean flag;
            if ("vip".equals(type)) {
                flag = userService.updateUser(user, UpdateUserActionEnum.UPDATE_VIP);
            }
            else if ("info".equals(type)) {
                String username = request.getParameter("username");
                boolean sex = Boolean.parseBoolean(request.getParameter("sex"));
                String birthday = request.getParameter("birthday");
                String location = request.getParameter("location");
                user.setUsername(username);
                user.setSex(sex);
                user.setBirthday(birthday);
                user.setLocation(location);
                flag = userService.updateUser(user, UpdateUserActionEnum.UPDATE_INFO);
            }
            else {
                throw new TransactionException("错误的请求");
            }
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/updatePhone", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateUserPhone (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            String phone = request.getParameter("phone");
            boolean flag = userService.updateUserPhone(uid, phone);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateUserPassword (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            boolean flag = userService.updateUserPassword(uid, oldPassword, newPassword);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateFollow", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateFollow (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid, fUid;
            try {
                uid = Integer.valueOf(request.getParameter("uid"));
                fUid = Integer.valueOf(request.getParameter("fUid"));
            } catch (NumberFormatException e) {
                throw new UidErrorException("uid 错误");
            }
            String type = request.getParameter("type");
            boolean flag;
            if ("addFollow".equals(type)) {
                flag = userService.updateFollow(uid, fUid, FollowActionEnum.ADD_FOLLOW);
            }
            else if ("removeFollow".equals(type)) {
                flag = userService.updateFollow(uid, fUid, FollowActionEnum.REMOVE_FOLLOW);
            }
            else {
                throw new TransactionException("错误的请求");
            }
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/createSongList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String createSongList (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            String listName = request.getParameter("listName");
            boolean flag = userService.createSongList(uid, listName);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SongListErrorException("创建歌单失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/removeSongList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String removeSongList (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            Integer listId = getListId(request);
            boolean flag = userService.removeSongList(uid, listId);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("歌单删除失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateSongList (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            Integer listId = getListId(request);
            Integer songId;
            try {
                songId = Integer.valueOf(request.getParameter("songId"));
            } catch (NumberFormatException e) {
                throw new SongErrorException("歌曲 id 错误");
            }
            String type = request.getParameter("type");
            boolean flag;
            if ("addSong".equals(type)) {
                flag = userService.updateSongList(uid, listId, songId, SongActionEnum.ADD_SONG);
            }
            else if ("removeSong".equals(type)) {
                flag = userService.updateSongList(uid, listId, songId, SongActionEnum.REMOVE_SONG);
            }
            else {
                throw new TransactionException("错误的请求");
            }
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，歌单更新失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateListName", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateSongListName (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            Integer listId = getListId(request);
            String listName = request.getParameter("listName");
            boolean flag = userService.updateSongListName(uid, listId, listName);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，歌单名称修改失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/addNews", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String addNews (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            String info = request.getParameter("info");
            boolean flag = userService.addNews(uid, info);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，动态发布失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/deleteNews", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String deleteNews (HttpServletRequest request) {
        jsonObject = new JSONObject();
        try {
            Integer uid = getUid(request);
            int newsId;
            try {
                newsId = Integer.parseInt(request.getParameter("newsId"));
            } catch (NumberFormatException e) {
                throw new NewsErrorException("动态 id 错误");
            }
            boolean flag = userService.deleteNews(uid, newsId);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，动态删除失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/findAllUser", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String findAll () {
        jsonObject = new JSONObject();
        try {
            List<User> users = userService.findAllUser();
            for (User user : users) {
                transfer(user, User.class);
            }
            jsonObject.put("success", true);
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
//    @ResponseBody
//    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
//    public String  (HttpServletRequest request) {
//        jsonObject = new JSONObject();
//        try {
//
//        } catch (TransactionException e) {
//            txException(e);
//        } catch (Exception e) {
//            exception();
//        }
//        return jsonObject.toString();
//    }
}
