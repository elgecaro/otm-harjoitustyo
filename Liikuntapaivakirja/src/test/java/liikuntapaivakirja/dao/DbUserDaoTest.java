
package liikuntapaivakirja.dao;

import java.io.File;
import java.sql.SQLException;
import liikuntapaivakirja.domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class DbUserDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    User user;
    UserDao userDao;
    Database database;
    
    @Before
    public void setUp() throws SQLException, Exception {
        File testDatabase = testFolder.newFile("test.db");
        database = new Database("jdbc:sqlite:" + testDatabase.getAbsolutePath());
        database.checkForTables(database.getConnection(), database);
        
        userDao = new DbUserDao(database);
        user = new User("Testikayttaja", "testisalasana");
        userDao.create(user);
        database = new Database("jdbc:sqlite:test.db");

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
