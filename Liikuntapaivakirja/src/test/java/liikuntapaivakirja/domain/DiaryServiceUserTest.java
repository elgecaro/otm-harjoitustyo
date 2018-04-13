
package liikuntapaivakirja.domain;

import java.sql.SQLException;
import liikuntapaivakirja.dao.Database;
import liikuntapaivakirja.dao.DbDiaryDao;
import liikuntapaivakirja.dao.DbUserDao;
import liikuntapaivakirja.dao.DiaryDao;
import liikuntapaivakirja.dao.UserDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DiaryServiceUserTest {
    
    private DiaryDao diaryDao;
    private UserDao userDao;
    private DiaryService diaryService;
    
    @Before
    public void setUp() throws SQLException {
        Database database = new Database("jdbc:sqlite:test.db");
        database.getConnection();
        userDao = new DbUserDao(database);
        diaryDao = new DbDiaryDao(database);
        diaryService = new DiaryService(diaryDao, userDao);
    }
    
    @Test
    public void createUserTrue() throws Exception {
        assertTrue(diaryService.createUser("newUser", "testpassword"));
    }
    
    @Test
    public void createAlreadyExistingUserFalse() throws Exception {
        assertFalse(diaryService.createUser("testUser1", "testpassword"));
    }
    
    @Test
    public void createUserFalseShortPassword() throws Exception {
        assertFalse(diaryService.createUser("username", "short"));

    }
    
    @Test
    public void loginTrue() throws Exception {
        boolean result = diaryService.login("testUser1", "testpassword");
        assertTrue(result);
        User loggedIn = diaryService.getLoggedUser();
        assertEquals("testUser1", loggedIn.getUsername());
    }
    
    @Test
    public void loginFalseNotAUser() throws Exception {
        boolean result = diaryService.login("notAUser", "password");
        assertFalse(result);
        assertEquals(null, diaryService.getLoggedUser());
    }
    
    @Test
    public void loginFalseWrongPassword() throws Exception {
        boolean result = diaryService.login("testUser1", "wrongPassword");
        assertFalse(result);
        assertEquals(null, diaryService.getLoggedUser());
    }
    
    @Test 
    public void getUsername() throws Exception {
        diaryService.login("testUser1", "testpassword");
        assertEquals("testUser1", diaryService.getUsername());        
    }
    
    @Test 
    public void logout() throws Exception {
        diaryService.logout();
        assertEquals(null, diaryService.getLoggedUser());        
    }
    
    @After
    public void tearDown() {
    }

}
