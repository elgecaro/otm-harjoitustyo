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


public class DiaryServiceEntryTest {
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
        diaryService.createUser("testUser1", "testpassword");
        diaryService.createUser("testUser2", "testpassword");
        diaryService.createUser("testUser3", "testpassword");
        diaryService.createUser("testUser4", "testpassword");
        
        diaryService.login("testUser1", "testpassword");
        diaryService.createExercise(1, 2, 3, "running");
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
        diaryService.createWeeklyGoal(10.5);
        assertEquals(10.5, diaryService.getWeeklyGoal(), 0.0);
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
        diaryService.createExercise(0.5, 2, 2, "walking");
        assertEquals("[Tunteja: 0.5        Päivä: 2        Viikko: 2" + "\n" + 
                "Kuvaus: walking, Tunteja: 1.0        Päivä: 2        Viikko: 3" + "\n" + 
                "Kuvaus: running]", diaryService.getAll().toString());
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
        assertEquals("{1=15.0, 3=10.0, 2=5.0}", diaryService.getUsersBestWeeks().toString());
    }
    
    @Test
    public void GetBestWeeks() throws Exception {
        diaryService.logout();
        diaryService.login("testUser2", "testpassword");
        diaryService.createExercise(0.5, 1, 1, "walking");
        diaryService.logout();
        diaryService.login("testUser3", "testpassword");
        diaryService.createExercise(1.0, 1, 1, "swimming");
        diaryService.createExercise(0.5, 2, 1, "swimming again");
        assertEquals("{testUser3=15.0, testUser1=10.0, testUser2=5.0}", diaryService.getBestWeeks().toString());
    }
    
    @Test
    public void getLatestWeek() throws Exception {
        diaryService.createExercise(1, 2, 1, "running");
        diaryService.createExercise(1, 2, 2, "running more");
        diaryService.createExercise(1, 2, 3, "running even more");
        assertEquals(3, diaryService.getLatestWeek());
    }
    
    @Test
    public void get15Latest() throws Exception {
        assertEquals("[Tunteja: 1.0        Päivä: 2        Viikko: 3" + "\n" + "Kuvaus: running]", diaryService.get15Latest().toString());
    }
    
    @Test
    public void weeklyGoalAchieved() throws Exception {
        diaryService.createWeeklyGoal(10);
        assertTrue(diaryService.weeklyGoalAchieved(diaryService.getWeeklyGoal()));
    }
    
    @Test
    public void weeklyGoalNotAchieved() throws Exception {
        diaryService.logout();
        diaryService.login("testUser4", "testpassword");
        assertFalse(diaryService.weeklyGoalAchieved(diaryService.getWeeklyGoal()));
        
        diaryService.createWeeklyGoal(100);
        assertFalse(diaryService.weeklyGoalAchieved(diaryService.getWeeklyGoal()));
    }
   
    @Test
    public void isDouble() throws Exception {
        assertTrue(diaryService.isDouble("1.0"));
        assertTrue(diaryService.isDouble("2"));
        assertFalse(diaryService.isDouble("tuntia"));
    }
    
    @Test
    public void isInteger() throws Exception {
        assertTrue(diaryService.isInteger("1"));
        assertFalse(diaryService.isInteger("päivä"));
    }
    
    @Test
    public void getAllWeekPoints() throws Exception {
        diaryService.createExercise(1.5, 2, 1, "running");
        diaryService.createExercise(0.5, 2, 2, "running");
        assertEquals("{1=15.0, 2=5.0, 3=10.0}", diaryService.getAllWeekPoints().toString());
    }
    
    @Test
    public void deleteEntryWorks() throws Exception {
        diaryService.logout();
        diaryService.login("testUser4", "testpassword");
        diaryService.createExercise(2, 1, 2, "olin ulkona");
        DiaryEntry entry = new DiaryEntry(diaryService.getLoggedUser(), 2, 1, 2, "olin ulkona");
        assertEquals("[Tunteja: 2.0        Päivä: 1        Viikko: 2" + "\n" + "Kuvaus: olin ulkona]", diaryService.getAll().toString());
        
        diaryService.deleteEntry(entry);
        assertEquals("[]", diaryService.getAll().toString());
    }
    
    @After
    public void tearDown() {
        testDatabase.delete();
        testFolder.delete();
    }

}
