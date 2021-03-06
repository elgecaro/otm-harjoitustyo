
package liikuntapaivakirja.dao;

import java.io.File;
import java.util.List;
import liikuntapaivakirja.domain.DiaryEntry;
import liikuntapaivakirja.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


public class DbDiaryDaoTest {
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
            
    User user;
    DiaryEntry diary;
    UserDao userDao;
    DiaryEntryDao diaryDao;
    Database database;
    File testDatabase;
      
    @Before
    public void setUp() throws Exception {
        testDatabase = testFolder.newFile("test.db");
        database = new Database("jdbc:sqlite:" + testDatabase.getAbsolutePath());
        database.checkForTables(database.getConnection(), database);
       
        userDao = new DbUserDao(database);
        diaryDao = new DbDiaryEntryDao(database);
        user = new User("Testikayttaja", "testisalasana");
        
        diary = new DiaryEntry(user, 1, 2, 3, "juoksin");
    }
    
    @Test
    public void diaryEntriesAreReadCorrectly() throws Exception {
        diaryDao.create(diary);
        List<DiaryEntry> diaryEntries = diaryDao.getAll(user);
        assertEquals(1, diaryEntries.size());
        DiaryEntry entry = diaryEntries.get(0);
        assertEquals(1, entry.getHour(), 0.0);
        assertEquals(2, entry.getDay());
        assertEquals(3, entry.getWeek());
        assertEquals("juoksin", entry.getContent());
        assertEquals("Testikayttaja", entry.getUsername());
    }
    
    @Test
    public void createdDiaryEntriesAreListed() throws Exception {
        diaryDao.create(diary);
        diaryDao.create(new DiaryEntry(user, 0.5, 1, 2, "uin"));
        List<DiaryEntry> diaryEntries = diaryDao.getAll(user);
        assertEquals(2, diaryEntries.size());
        DiaryEntry entry = diaryEntries.get(0);
        assertEquals(0.5, entry.getHour(), 0.0);
        assertEquals(1, entry.getDay());
        assertEquals(2, entry.getWeek());
        assertEquals("uin", entry.getContent());
        assertEquals("Testikayttaja", entry.getUsername());
        
        DiaryEntry entryOne = diaryEntries.get(1);
        assertEquals(1, entryOne.getHour(), 0.0);
        assertEquals(2, entryOne.getDay());
        assertEquals(3, entryOne.getWeek());
        assertEquals("juoksin", entryOne.getContent());
        assertEquals("Testikayttaja", entry.getUsername());
    }
    
    @After
    public void tearDown() {
        testDatabase.delete();
        testFolder.delete();
    }
}
