
package liikuntapaivakirja.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import liikuntapaivakirja.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DbUserDaoTest {
    User user;
    UserDao userDao;
    Database database;
    
    @Before
    public void setUp() throws SQLException, Exception {
        user = new User("Testikayttaja", "testisalasana");
        database = new Database("jdbc:sqlite:test.db");
        Connection connection = database.getConnection();
        userDao = new DbUserDao(database);
    }
        
    @Test
    public void usersAreReadCorrectly() throws Exception {
        userDao.create(user);
        List<User> users = userDao.findAll();
        assertEquals(1, users.size());
        User listedUser = users.get(0);
        assertEquals("Testikayttaja", listedUser.getUsername());
    }
    
    @Test
    public void existingUserIsFound() throws Exception {
        user = userDao.findByUsername("Testikayttaja");
        assertEquals("Testikayttaja", user.getUsername());
    }
    
    @Test
    public void nonExistingUserIsFound() throws Exception {
        user = userDao.findByUsername("EiOlemassa");
        assertEquals(null, user);
    }
  
    @Test
    public void savedUserIsFound() throws Exception {
        User newUser = new User("Testikayttaja2", "salasana");
        userDao.create(newUser);
        
        user = userDao.findByUsername("Testikayttaja2");
        assertEquals("Testikayttaja2", user.getUsername());
    }

}
