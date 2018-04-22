
package liikuntapaivakirja.dao;

import liikuntapaivakirja.domain.User;
import java.util.List;

public interface UserDao {
    User create(User user) throws Exception;
    User findByUsername(String username) throws Exception;
    List<User> findAll() throws Exception;
    boolean usernameAndPasswordMatch(String user, String password)throws Exception;
    void setWeeklyGoal(int goal, String user) throws Exception;
    int getWeeklyGoal(String key) throws Exception;

    
}