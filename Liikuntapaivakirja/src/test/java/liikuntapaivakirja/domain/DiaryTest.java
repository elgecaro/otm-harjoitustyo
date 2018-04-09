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
    }
    
    @Test
    public void newDiaryEntryCorrectInformation() {
        diary = new Diary(user, 1, 2, 3);
        assertEquals("Testikayttaja", diary.getUsername());
        assertEquals(1, diary.getHour(), 0);
        assertEquals(2, diary.getDay());
        assertEquals(3, diary.getWeek());
    }
    
    @Test
    public void newDiaryEntryCorrectContent() {
        diary = new Diary(user, 1, 2, 3, "olin juoksemassa");
        assertEquals("olin juoksemassa", diary.getContent());
    }
    
    public DiaryTest() {
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
