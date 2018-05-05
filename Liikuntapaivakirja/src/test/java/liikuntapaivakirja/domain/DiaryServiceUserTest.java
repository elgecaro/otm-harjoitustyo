
package liikuntapaivakirja.domain;

import java.io.File;
import liikuntapaivakirja.dao.Database;
import liikuntapaivakirja.dao.DbDiaryEntryDao;
import liikuntapaivakirja.dao.DbUserDao;
import liikuntapaivakirja.dao.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import liikuntapaivakirja.dao.DiaryEntryDao;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


public class DiaryServiceUserTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    DiaryEntryDao diaryDao;
    UserDao userDao;
    DiaryService diaryService;
    Database database;
    File testDatabase;

    @Before
    public void setUp() throws Exception {
        testDatabase = testFolder.newFile("test.db");
        database = new Database("jdbc:sqlite:" + testDatabase.getAbsolutePath());
        database.checkForTables(database.getConnection(), database);
        
        userDao = new DbUserDao(database);
        diaryDao = new DbDiaryEntryDao(database);
        diaryService = new DiaryService(diaryDao, userDao);
        diaryService.createUser("testUser", "password");
    }
    
    @Test
    public void createUserTrue() throws Exception {
        assertTrue(diaryService.createUser("newUser", "testpassword"));
    }
    
    @Test
    public void createAlreadyExistingUserFalse() throws Exception {
        assertFalse(diaryService.createUser("testUser", "password"));
    }
    
    @Test
    public void createUserFalseShortPassword() throws Exception {
        assertFalse(diaryService.createUser("username", "short"));

    }
    
    @Test
    public void loginTrue() throws Exception {
        assertTrue(diaryService.login("testUser", "password"));
        User loggedIn = diaryService.getLoggedUser();
        assertEquals("testUser", loggedIn.getUsername());
    }
    
    @Test
    public void loginFalseNotAUser() throws Exception {
        assertFalse(diaryService.login("notAUser", "password"));
        assertEquals(null, diaryService.getLoggedUser());
    }
    
    @Test
    public void loginFalseWrongPassword() throws Exception {
        assertFalse(diaryService.login("testUser", "wrongPassword"));
        assertEquals(null, diaryService.getLoggedUser());
    }
    
    @Test 
    public void getUsername() throws Exception {
        diaryService.login("testUser", "password");
        assertEquals("testUser", diaryService.getUsername());        
    }
    
    @Test 
    public void logout() throws Exception {
        diaryService.logout();
        assertEquals(null, diaryService.getLoggedUser());        
    }
    
    @After
    public void tearDown() {
        testDatabase.delete();
        testFolder.delete();
    }
}
