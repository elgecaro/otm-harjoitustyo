
package liikuntapaivakirja.dao;

import java.sql.Connection;
import java.sql.*;
import liikuntapaivakirja.domain.Diary;
import java.util.List;
import java.util.*;
import liikuntapaivakirja.domain.User;

public class DbDiaryDao implements DiaryDao {
    private Database database;
    private int points;
    private User user;
    
    public DbDiaryDao(Database database) {
        this.database = database;
    }
    
    @Override
    public void create(Diary diary) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Diary (username, hour, day, week, description)"
                + "VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, diary.getUsername());
        stmt.setDouble(2, diary.getHour());
        stmt.setInt(3, diary.getDay());
        stmt.setInt(4, diary.getWeek());
        stmt.execute();

        connection.close();        
    }

    @Override
    public List<Diary> getAll(User user) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Diary WHERE username = ?");
        stmt.setObject(1, user.getUsername());

        ResultSet rs = stmt.executeQuery();
        List<Diary> diaryEntrys = new ArrayList<>();
        while (rs.next()) {
            String username = rs.getString("username");
            double hour = rs.getDouble("hour");
            int day = rs.getInt("day");
            int week = rs.getInt("week");
            String content = rs.getString("description");

            diaryEntrys.add(new Diary(user, hour, day, week, content));
        }

        rs.close();
        stmt.close();
        connection.close();

        return diaryEntrys;
    }

    
    public int userPointsWeek(String key, int week) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Diary WHERE username = ? AND week = ?");
        stmt.setObject(1, key);
        stmt.setObject(2, week);
        int hours = 0;
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int hour = rs.getInt("hour");
            hours = hours + hour;
        }
        
        points = hours * 10;

        rs.close();
        stmt.close();
        connection.close();
        
        return points;
    }

    @Override
    public int getWeeklyGoal(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT weeklyGoal FROM Diary WHERE username = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        return rs.getInt("weeklyGoal");
    }
    
    @Override
    public void setWeeklyGoal(int goal, String user) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE Diary SET weeklyGoal = ?, WHERE username = ?");
        stmt.setObject(1, goal);
        stmt.setObject(2, user);     
        stmt.execute();
        connection.close();
    }
    
}
