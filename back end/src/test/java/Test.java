import com.dao.IUserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Liu
 * Created on 2020/8/9.
 */
public class Test {
    
    public static void main (String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDao userDao = applicationContext.getBean("userDao", IUserDao.class);
//        List<User> userList = userDao.findAll();
//        for (User user : userList) {
//            userDao.deleteUser(user.getUid());
//        }
//        List<User> admins = userDao.addAdministrator();
//        for (User user : admins) {
//            userDao.addUser(user);
//        }
//        userDao.addFollow(100004, 100001);
//        userDao.addFollow(100004, 100002);
//        userDao.addFollow(100004, 100003);
    }
    
}
