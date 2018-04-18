
package liikuntapaivakirja.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import liikuntapaivakirja.domain.Diary;
import liikuntapaivakirja.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DbDiaryDaoTest {
        User user;
        Diary diary;
        UserDao userDao;
        DiaryDao diaryDao;
        Database database;
      
    @Before
    public void setUp() throws SQLException, Exception {
        database = new Database("jdbc:sqlite:test.db");
        Connection connection = database.getConnection();

        userDao = new DbUserDao(database);
        diaryDao = new DbDiaryDao(database);
        user = new User("Testikayttaja", "testisalasana");
        
        diary = new Diary(user, 1, 2, 3, "juoksin");

    }
    
    @Test
    public void diaryEntriesAreReadCorrectly() throws Exception {
        diaryDao.create(diary);
        List<Diary> diaryEntries = diaryDao.getAll(user);
        assertEquals(1, diaryEntries.size());
        Diary entry = diaryEntries.get(0);
        assertEquals(1, entry.getHour(), 0.0);
        assertEquals(2, entry.getDay());
        assertEquals(3, entry.getWeek());
        assertEquals("juoksin", entry.getContent());
        assertEquals("Testikayttaja", entry.getUsername());
    }
    
    @Test
    public void createdDiaryEntriesAreListed() throws Exception {
        diaryDao.create(new Diary(user, 0.5, 1, 2, "uin"));
        List<Diary> diaryEntries = diaryDao.getAll(user);
        assertEquals(2, diaryEntries.size());
        Diary entry = diaryEntries.get(0);
        assertEquals(0.5, entry.getHour(), 0.0);
        assertEquals(1, entry.getDay());
        assertEquals(2, entry.getWeek());
        assertEquals("uin", entry.getContent());
        assertEquals("Testikayttaja", entry.getUsername());
        
        Diary entryOne = diaryEntries.get(1);
        assertEquals(1, entryOne.getHour(), 0.0);
        assertEquals(2, entryOne.getDay());
        assertEquals(3, entryOne.getWeek());
        assertEquals("juoksin", entryOne.getContent());
        assertEquals("Testikayttaja", entry.getUsername());
    }
    
    @After
    public void tearDown() {
    }

}
