
package liikuntapaivakirja.dao;

import java.sql.Connection;
import java.sql.*;
import liikuntapaivakirja.domain.Diary;
import java.util.List;
import java.util.*;

public class DbDiaryDao implements DiaryDao {
    private Database database;
    private int points;
    
    // Kesken!
    
    @Override
    public Diary create(Diary diary) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Diary> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDone(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int userPointsWeek(String key, String week) throws SQLException {
        // Kesken!
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ExcerciseDiary WHERE username = ? AND week = ?");
        stmt.setObject(1, key);
        stmt.setObject(2, week);
        
        ResultSet rs = stmt.executeQuery();
        int hours = 0;
        while (rs.next()) {
            Integer hour = rs.getInt("hour");
            hours =+ hour;
        }
        
        points = hours * 10;

        rs.close();
        stmt.close();
        connection.close();
        
        return points;
    }
    
}
