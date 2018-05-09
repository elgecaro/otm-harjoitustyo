package liikuntapaivakirja.domain;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class DiaryEntryTest {
    DiaryEntry entry;
    User user;
    
    @Before
    public void setUp() {
        user = new User("Testikayttaja", "testisalasana");
        entry = new DiaryEntry(user, 1, 2, 3, "content");
    }
    
    @Test
    public void newDiaryEntryCorrectInformation() {
        assertEquals("Testikayttaja", entry.getUsername());
        assertEquals(1, entry.getHour(), 0);
        assertEquals(2, entry.getDay());
        assertEquals(3, entry.getWeek());
        assertEquals("content", entry.getContent());
    }
    
    @Test
    public void newDiaryEntryCorrectContent() {
        DiaryEntry diary = new DiaryEntry(user, 2, 2, 3, "olin juoksemassa");
        assertEquals("olin juoksemassa", diary.getContent());
    }
    
    @Test
    public void setAndGetUser() {
        entry.setUser(user);
        assertEquals(user, entry.getUser());
    }
    
    @Test
    public void toStringWorking() {
        assertEquals("Tunteja: 1.0        Päivä: 2        Viikko: 3" + "\n" + "Kuvaus: content", entry.toString());
    }
    
    @After
    public void tearDown() {
    }

}
