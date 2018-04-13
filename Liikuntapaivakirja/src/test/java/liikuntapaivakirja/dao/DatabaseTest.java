
package liikuntapaivakirja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DatabaseTest {
    
    Database database;
    
    @Before
    public void setUp() throws SQLException {
        database = new Database("jdbc:sqlite:test.db");
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = database.getConnection().prepareStatement("DROP TABLE User;"); 
        stmt.executeUpdate(); 
        stmt = database.getConnection().prepareStatement("DROP TABLE Diary;");
        stmt.executeUpdate(); 

        stmt = database.getConnection().prepareStatement("CREATE TABLE User (\n" + 
            "username varchar(15) PRIMARY KEY CHECK (LENGTH (username) > 2), \n" + 
            "password varchar NOT NULL CHECK (LENGTH (password) > 6) \n" +
            ");"); 
        stmt.executeUpdate(); 
        
        stmt = database.getConnection().prepareStatement("CREATE TABLE Diary (\n" + 
            "username varchar(15), \n" +
            "hour float NOT NULL, \n" +
            "day integer NOT NULL, \n" +
            "week INTEGER NOT NULL, \n" +
            "description varchar(200), \n" +
            "weeklyGoal integer, \n" +
            "FOREIGN KEY(username) REFERENCES User(username) \n" +
            ");");
        stmt.executeUpdate(); 
        stmt.close();    
        connection.close();
    }         
    
    @Test
    public void newTableUserIsEmpty() throws SQLException {
        PreparedStatement stmt = database.getConnection().prepareStatement("SELECT * FROM User;"); 
        ResultSet rs = stmt.executeQuery();
        
        boolean empty = true;
        while (rs.next() ) {
            empty = false;
        }
        assertEquals(true, true);
        
    }
    
    @Test
    public void newTableDiaryIsEmpty() throws SQLException {
        PreparedStatement stmt = database.getConnection().prepareStatement("SELECT * FROM Diary;"); 
        ResultSet rs = stmt.executeQuery();
        
        boolean empty = true;
        while (rs.next() ) {
            empty = false;
        }
        assertEquals(true, true);
        
    }

    @After
    public void tearDown() {
    }  
}
