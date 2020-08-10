package com.controller;

import com.domain.*;
import com.enums.FollowActionEnum;
import com.enums.ShowNewsActionEnum;
import com.exception.SongListErrorException;
import com.exception.SystemErrorException;
import com.exception.TransactionException;
import com.service.IUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/27.
 */
@Controller
public class ServiceController {
    
    private IUserService userService;
    
    private JSONObject jsonObject;
    
    private JSONObject dataJson;
    
    @Autowired
    public void setUserService (IUserService userService) {
        this.userService = userService;
    }
    
    private void transfer (Object object) {
        JSONObject subObject = new JSONObject();
        if (object instanceof User) {
            User user = (User) object;
            subObject.put("uid", user.getUid());
            subObject.put("phone", user.getPhone());
            subObject.put("username", user.getUsername());
            subObject.put("sex", user.getSex());
            subObject.put("birthday", user.getBirthday());
            subObject.put("location", user.getLocation());
            dataJson.accumulate("User", subObject);
        }
        else if (object instanceof FollowUser) {
            FollowUser user = (FollowUser) object;
            subObject.put("uid", user.getUid());
            subObject.put("username", user.getUsername());
            subObject.put("sex", user.getSex());
            subObject.put("birthday", user.getBirthday());
            subObject.put("location", user.getLocation());
            dataJson.accumulate("FollowUser", subObject);
        }
        else if (object instanceof SongList) {
            SongList songList = (SongList) object;
            subObject.put("uid", songList.getUid());
            subObject.put("listIndex", songList.getListIndex());
            subObject.put("listName", songList.getListName());
            dataJson.accumulate("SongList", subObject);
        }
        else if (object instanceof Song) {
            Song song = (Song) object;
            subObject.put("songIndex", song.getSongIndex());
            subObject.put("name", song.getName());
            subObject.put("singer", song.getSinger());
            subObject.put("url", song.getUrl());
            dataJson.accumulate("Song", subObject);
        }
        else if (object instanceof News) {
            News news = (News) object;
            subObject.put("newsId", news.getNewsId());
            subObject.put("uid", news.getUid());
            subObject.put("username", news.getUsername());
            subObject.put("info", news.getInfo());
            subObject.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(news.getTime()));
            dataJson.accumulate("News", subObject);
        }
        jsonObject.put("success", true);
    }
    
    private JSONObject getJsonMap (String json) {
        return new JSONObject(json);
    }
    
    private void txException (TransactionException e) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("err", e.getClass().getSimpleName() + " : " + e.getMessage());
    }
    
    private void exception (Exception e) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("err", "系统异常：" + e.getMessage());
    }
    
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String login (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = new JSONObject(json);
//            String account = jsonMap.getString("account");
            String account = jsonMap.getString("username");
            String password = jsonMap.getString("password");
            User user = userService.login(account, password);
            if (user != null) {
                transfer(user);
                jsonObject.accumulate("data", dataJson);
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
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findUser (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            String account = jsonMap.getString("account");
            if (account == null) {
                throw new TransactionException("账户为空");
            }
            else {
                List<User> users = new LinkedList<>();
                if (account.length() == 6) {
                    try {
                        int uid = Integer.parseInt(account);
                        User user = userService.findUserByUid(uid);
                        if (user != null) {
                            users.add(user);
                        }
                    } catch (Exception ignored) {
                        //尝试解析 uid，若可以解析为 uid 则将 uid 查找结果加入到结果集中，否则只按照查找用户名处理
                    }
                }
                users.addAll(userService.findUserByName(account));
                for (User user : users) {
                    transfer(user);
                }
                if (!dataJson.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
                }
                jsonObject.put("success", true);
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
    public String findAllFollow (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            List<FollowUser> users;
            String type = jsonMap.getString("type");
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
                    transfer(user);
                }
                if (!jsonObject.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
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
    @RequestMapping(value = "/playlist", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findAllSongList (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            List<SongList> lists = userService.findAllSongList(uid);
            if (lists != null) {
                lists.sort(Comparator.comparingInt(SongList::getListIndex));
                for (SongList list : lists) {
                    transfer(list);
                }
                if (!jsonObject.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
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
    public String findAllSongInList (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            List<Song> songs = userService.findAllSongInList(uid, listIndex);
            if (songs != null) {
                songs.sort((song1, song2) -> song2.getSongIndex() - song1.getSongIndex());
                for (Song song : songs) {
                    transfer(song);
                }
                if (!jsonObject.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
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
    @RequestMapping(value = "/play", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String playSong (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            Integer songIndex = jsonMap.getInt("songIndex");
            String url = userService.findSongUrl(uid, listIndex, songIndex);
            dataJson.put("url", url);
            jsonObject.put("data", dataJson);
            jsonObject.put("success", true);
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/news", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String news (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String type = jsonMap.getString("type");
            List<News> newsList;
            if ("selfzone".equals(type)) {
                newsList = userService.findAllNews(uid, ShowNewsActionEnum.SELF);
            }
            else if ("friends".equals(type)) {
                newsList = userService.findAllNews(uid, ShowNewsActionEnum.ALL);
            }
            else {
                throw new TransactionException("错误的请求");
            }
            if (newsList != null) {
                newsList.sort((news1, news2) -> news2.getTime().compareTo(news1.getTime()));
                for (News news : newsList) {
                    transfer(news);
                }
                if (!dataJson.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
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
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String addUser (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            String phone = jsonMap.getString("phone");
            String username = jsonMap.getString("username");
            String password = jsonMap.getString("password");
            boolean sex = jsonMap.getBoolean("sex");
            String birthday = jsonMap.getString("birthday");
            String location = jsonMap.getString("location");
            User user = new User();
            user.setPhone(phone);
            user.setUsername(username);
            user.setSex(sex);
            user.setBirthday(birthday);
            user.setLocation(location);
            boolean flag = userService.addUser(user, password);
            if (flag) {
                transfer(user);
                jsonObject.accumulate("data", dataJson);
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
    @RequestMapping(value = "/user/update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateUser (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            User user = userService.findUserByUid(uid);
            boolean flag;
            String username = jsonMap.getString("username");
            Boolean sex = jsonMap.getBoolean("sex");
            String birthday = jsonMap.getString("birthday");
            String location = jsonMap.getString("location");
            user.setUsername(username);
            user.setSex(sex);
            user.setBirthday(birthday);
            user.setLocation(location);
            flag = userService.updateUser(user);
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
    public String updateUserPhone (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String phone = jsonMap.getString("phone");
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
    public String updateUserPassword (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String oldPassword = jsonMap.getString("oldPassword");
            String newPassword = jsonMap.getString("newPassword");
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
    public String updateFollow (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer fUid = jsonMap.getInt("fUid");
            String type = jsonMap.getString("type");
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
    @RequestMapping(value = "/createPlaylist", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String createSongList (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String listName = jsonMap.getString("listName");
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
    @RequestMapping(value = "/removePlaylist", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String removeSongList (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            boolean flag = userService.removeSongList(uid, listIndex);
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
    @RequestMapping(value = "/addSong", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String addSong (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            String name = jsonMap.getString("name");
            String singer = jsonMap.getString("singer");
            String url = jsonMap.getString("url");
            boolean flag = userService.addSong(uid, listIndex, name, singer, url);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，添加歌曲失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/removeSong", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String removeSong (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            Integer songIndex = jsonMap.getInt("songIndex");
            boolean flag = userService.removeSong(uid, listIndex, songIndex);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，添加歌曲失败");
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
    public String updateSongListName (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            String listName = jsonMap.getString("listName");
            boolean flag = userService.updateSongListName(uid, listIndex, listName);
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
    public String addNews (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String info = jsonMap.getString("info");
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
    public String deleteNews (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer newsIndex = jsonMap.getInt("newsIndex");
            boolean flag = userService.deleteNews(uid, newsIndex);
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
    
}
