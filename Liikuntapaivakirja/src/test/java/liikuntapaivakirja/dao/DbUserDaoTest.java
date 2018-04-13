
package liikuntapaivakirja.dao;

import java.sql.Connection;
import java.sql.SQLException;
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
    
    public DbUserDaoTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        user = new User("Testikayttaja", "testisalasana");
        
        database = new Database("jdbc:sqlite:test.db");
        Connection connection = database.getConnection();
        userDao = new DbUserDao(database);

    }
    
    @After
    public void tearDown() {
    }
}
