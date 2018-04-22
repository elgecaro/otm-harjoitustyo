package liikuntapaivakirja.domain;


import liikuntapaivakirja.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class UserTest {
    
    User user;
    
    @Before
    public void setUp() {
        user = new User("Testikayttaja", "testisalasana");
    }
    
    @Test
    public void createdUserExist() {
        assertTrue(user!=null);      
    }
    
    @Test
    public void usernameCorrect() {
        assertEquals("Testikayttaja", user.getUsername());
    }
    
    @Test
    public void passwordCorrect() {
        assertEquals("testisalasana", user.getPassword());
    }      
    
    @Test
    public void equalWhenSameUsername() {
        User user2 = new User("Testikayttaja", "testisalasana");
        assertTrue(user.getUsername().equals(user2.getUsername()));
    }
    
    @Test
    public void notEqualWhenDifferentUsername() {
        User user2 = new User("Testikayttaja2", "testisalasana");
        assertFalse(user.getUsername().equals(user2.getUsername()));
    } 
    
    @Test
    public void setAndGetWeeklyGoal() {
        user.setWeeklyGoal(20);
        assertEquals(20, user.getWeeklyGoal());
    }

}
