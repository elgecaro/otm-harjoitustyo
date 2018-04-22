package liikuntapaivakirja.domain;


import liikuntapaivakirja.domain.Diary;
import liikuntapaivakirja.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DiaryTest {
    Diary diary;
    User user;
    
    @Before
    public void setUp() {
        user = new User("Testikayttaja", "testisalasana");
        diary = new Diary(user, 1, 2, 3, "content");
    }
    
    @Test
    public void newDiaryEntryCorrectInformation() {
        assertEquals("Testikayttaja", diary.getUsername());
        assertEquals(1, diary.getHour(), 0);
        assertEquals(2, diary.getDay());
        assertEquals(3, diary.getWeek());
        assertEquals("content", diary.getContent());
    }
    
    @Test
    public void newDiaryEntryCorrectContent() {
        Diary diary = new Diary(user, 2, 2, 3, "olin juoksemassa");
        assertEquals("olin juoksemassa", diary.getContent());
    }
    
    @Test
    public void setAndGetUser() {
        diary.setUser(user);
        assertEquals(user, diary.getUser());
    }
    
    @Test
    public void toStringWorking() {
        assertEquals("Tunteja: 1.0, päivä: 2, viikko: 3, kuvaus: content", diary.toString());
    }
    
    
    @After
    public void tearDown() {
    }

}
