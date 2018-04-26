package liikuntapaivakirja.domain;

import java.sql.SQLException;
import java.util.Arrays;
import liikuntapaivakirja.dao.Database;
import liikuntapaivakirja.dao.DbDiaryEntryDao;
import liikuntapaivakirja.dao.DbUserDao;
import liikuntapaivakirja.dao.UserDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import liikuntapaivakirja.dao.DiaryEntryDao;


public class DiaryServiceEntryTest {
    private DiaryEntryDao diaryDao;
    private UserDao userDao;
    private DiaryService diaryService;
    
    @Before
    public void setUp() throws SQLException, Exception {
        Database database = new Database("jdbc:sqlite:test.db");
        database.getConnection();
        userDao = new DbUserDao(database);
        diaryDao = new DbDiaryEntryDao(database);
        diaryService = new DiaryService(diaryDao, userDao);
        diaryService.createUser("testUser1", "testpassword");
        diaryService.createUser("testUser2", "testpassword");
        diaryService.createUser("testUser3", "testpassword");
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
        assertEquals(10, diaryService.getPointsWeek(3), 0.0);
    }
   
    @Test
    public void getNoPoints() throws Exception {
        assertEquals(0, diaryService.getPointsWeek(1), 0.0);
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
        assertEquals("[Tunteja: 1.0        Päivä: 2        Viikko: 3" + "\n" + "Kuvaus: running]", diaryService.getAll().toString());
    }
    
    @Test
    public void getUsersBestWeeksEmpty() throws Exception {
        diaryService.logout();
        diaryService.login("testUser2", "testpassword");
        assertEquals("{}", diaryService.getUsersBestWeeks().toString());
    }
    
    @Test
    public void getUsersBestWeeks() throws Exception {
        diaryService.createExercise(1.5, 2, 1, "running again");
        diaryService.createExercise(0.5, 3, 2, "swimming");
        assertEquals("{15.0=1, 10.0=3, 5.0=2}", diaryService.getUsersBestWeeks().toString());
    }
    
    @Test
    public void GetBestWeeks() throws Exception {
        diaryService.logout();
        diaryService.login("testUser2", "testpassword");
        diaryService.createExercise(1, 1, 1, "walking");
        diaryService.logout();
        diaryService.login("testUser3", "testpassword");
        diaryService.createExercise(1.5, 1, 1, "swimming");
        assertEquals("{testUser1=15.0, testUser3=15.0, testUser2=10.0}", diaryService.getBestWeeks().toString());
    }
    
    @Test
    public void getLatestWeek() throws Exception {
        assertEquals(3, diaryService.getLatestWeek());
    }

}
