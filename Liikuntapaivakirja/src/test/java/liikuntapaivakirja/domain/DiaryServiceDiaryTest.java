package liikuntapaivakirja.domain;

import java.sql.SQLException;
import java.util.Arrays;
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


public class DiaryServiceDiaryTest {
    private DiaryDao diaryDao;
    private UserDao userDao;
    private DiaryService diaryService;
    
    @Before
    public void setUp() throws SQLException, Exception {
        Database database = new Database("jdbc:sqlite:test.db");
        database.getConnection();
        userDao = new DbUserDao(database);
        diaryDao = new DbDiaryDao(database);
        diaryService = new DiaryService(diaryDao, userDao);
        diaryService.createUser("testUser1", "testpassword");
        diaryService.login("testUser1", "testpassword");
    }
    
    @Test 
    public void setWeeklyGoalTrue() throws Exception {
        assertTrue(diaryService.createWeeklyGoal(10));
    }
    
    @Test
    public void setWeeklyGoalFalse() throws Exception {
        diaryService.logout();
        diaryService.login("notAUser", "testpassword");
        assertFalse(diaryService.createWeeklyGoal(15));
    }
    
    @Test
    public void getWeeklyGoal() throws Exception {
        diaryService.createWeeklyGoal(10);
        assertEquals(10, diaryService.getWeeklyGoal());
    }
    
    @Test
    public void getPoints() throws Exception {
        assertEquals(10, diaryService.getPointsWeek(3));
    }
   
    @Test
    public void getNoPoints() throws Exception {
        assertEquals(0, diaryService.getPointsWeek(1));
    }
    
    @Test
    public void createExerciseTrue() throws Exception {
        assertTrue(diaryService.createExercise(1, 2, 3, "content"));
    }
    
    @Test
    public void createExerciseFalse() throws Exception {
        diaryService.logout();
        diaryService.login("notAUser", "testpassword");
        assertFalse(diaryService.createExercise(1, 2, 3, "content"));
    }
    
    @Test
    public void getAllNull() throws Exception {
        diaryService.logout();
        diaryService.login("testUser2", "testpassword");
        assertEquals("[]", diaryService.getAll().toString());
    }
    
    @Test
    public void getAll() throws Exception {
        diaryService.createExercise(1, 2, 3, "running");
        assertEquals("[Tunteja: 1.0, päivä: 2, viikko: 3, kuvaus: running]", diaryService.getAll().toString());
    }
    
    @After
    public void tearDown() {
    }

}
