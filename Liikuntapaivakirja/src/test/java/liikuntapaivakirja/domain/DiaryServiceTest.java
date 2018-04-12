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


public class DiaryServiceTest {
    private DiaryDao diaryDao;
    private UserDao userDao;
    private DiaryService diaryService;
    
    public DiaryServiceTest() throws Exception {
        
    }
    
    @Before
    public void setUp() throws SQLException, Exception {
        Database database = new Database("jdbc:sqlite:test.db");
        database.getConnection();
        userDao = new DbUserDao(database);
        diaryDao = new DbDiaryDao(database);
        diaryService = new DiaryService(diaryDao, userDao);
        

        
//        User u1 = new User("testUser1", "testpassword");
//        User u2 = new User("testUser2", "testpassword2");
//        userDao.create(u1);
//        userDao.create(u2);   
//        diaryDao.create(new Diary(u1, 1, 2, 3, "kuvaus"));
//        diaryService = new DiaryService(diaryDao, userDao);
//        diaryService.login("testUser1", "testpassword");

    }
    
    @Test
    public void loginTrue() throws Exception {
        User u1 = new User("testUser1", "testpassword");
        User u2 = new User("testUser2", "testpassword");
        userDao.create(u1);
        boolean result = diaryService.login("testUser1", "testpassword");
        assertTrue(result);
        User loggedIn = diaryService.getLoggedUser();
        assertEquals("testUser1", loggedIn.getUsername());
    }
    
    @Test
    public void loginFalse() throws Exception {
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
    
    @Test 
    public void setWeeklyGoalTrue() throws Exception {
        diaryService.login("testUser1", "testpassword");
        assertTrue(diaryService.createWeeklyGoal(10));
    }
    
    @Test
    public void getWeeklyGoal() throws Exception {
        diaryService.login("testUser1", "testpassword");
        assertEquals(10, diaryService.getWeeklyGoal());
    }
    
    @Test
    public void getNoPoints() throws Exception {
        diaryService.login("testUser1", "testpassword");
        assertEquals(0, diaryService.getPointsWeek(1));
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    

    
    @After
    public void tearDown() {
    }

}
