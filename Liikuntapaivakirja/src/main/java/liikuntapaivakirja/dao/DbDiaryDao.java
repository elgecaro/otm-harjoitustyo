
package liikuntapaivakirja.dao;

import java.sql.Connection;
import java.sql.*;
import liikuntapaivakirja.domain.Diary;
import java.util.List;
import java.util.*;
import liikuntapaivakirja.domain.User;

public class DbDiaryDao implements DiaryDao {
    private Database database;
    private double points;
    private User user;
    private int goal;
    
    public DbDiaryDao(Database database) {
        this.database = database;
    }
    
    @Override
    public void create(Diary diary) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Diary (username, hour, day, week, content)"
                + "VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, diary.getUsername());
        stmt.setDouble(2, diary.getHour());
        stmt.setInt(3, diary.getDay());
        stmt.setInt(4, diary.getWeek());
        stmt.setString(5, diary.getContent());
        stmt.execute();

        connection.close();        
    }

    @Override
    public List<Diary> getAll(User user) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Diary WHERE username = ? ORDER BY week, day DESC");
        stmt.setObject(1, user.getUsername());

        ResultSet rs = stmt.executeQuery();
        List<Diary> diaryEntrys = new ArrayList<>();
        while (rs.next()) {
            String username = rs.getString("username");
            double hour = rs.getDouble("hour");
            int day = rs.getInt("day");
            int week = rs.getInt("week");
            String content = rs.getString("content");

            diaryEntrys.add(new Diary(user, hour, day, week, content));
        }

        rs.close();
        stmt.close();
        connection.close();

        return diaryEntrys;
    }

    @Override
    public int latestWeek(String key) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT week FROM Diary WHERE username = ? ORDER BY week DESC LIMIT 1");
        stmt.setObject(1, key);
        int week = 0;

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            week = rs.getInt("week");
        }
        
        rs.close();
        stmt.close();
        connection.close();
        
        return week;
    }
    
    
    @Override
    public double userPointsWeek(String key, int week) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Diary WHERE username = ? AND week = ?");
        stmt.setObject(1, key);
        stmt.setObject(2, week);
        double hours = 0;
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            double hour = rs.getDouble("hour");
            hours = hours + hour;
        }
        
        points = hours * 10;

        rs.close();
        stmt.close();
        connection.close();
        
        return points;
    }
    
    
    @Override
    public Map bestUserPointsWeeks(String key) throws SQLException {
        Connection connection = database.getConnection();       
        PreparedStatement stmt = connection.prepareStatement("SELECT week, TOTAL(hour) AS hours "
                + "FROM Diary WHERE username = ? "
                + "GROUP BY week ORDER BY hours DESC LIMIT 3"
        );
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        
        Map<Double, Integer> bestWeeks = new LinkedHashMap<>();
        while (rs.next()) {
            double hour = rs.getDouble("hours");
            double points = hour * 10;
            int week = rs.getInt("week");
            bestWeeks.put(points, week);
        }

        rs.close();
        stmt.close();
        connection.close();

        return bestWeeks;
    }
    
    @Override
    public Map bestPointsWeeks() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT week, username, "
                + "SUM(hour) AS hours FROM Diary "
                + "GROUP BY week, username "
                + "ORDER BY hours DESC "
                + "LIMIT 3");
        ResultSet rs = stmt.executeQuery();
        
        Map<String, Double> bestWeeks = new LinkedHashMap<>();
        while (rs.next()) {
            double hour = rs.getDouble("hours");
            double points = hour * 10;
            String username = rs.getString("username");
            bestWeeks.put(username, points);
        }

        rs.close();
        stmt.close();
        connection.close();

        return bestWeeks;
    }

    @Override
    public List<Diary> get15Latest(User user) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Diary WHERE username = ? ORDER BY week DESC, day DESC LIMIT 15");
        stmt.setObject(1, user.getUsername());

        ResultSet rs = stmt.executeQuery();
        List<Diary> diaryEntrys = new ArrayList<>();
        while (rs.next()) {
            String username = rs.getString("username");
            double hour = rs.getDouble("hour");
            int day = rs.getInt("day");
            int week = rs.getInt("week");
            String content = rs.getString("content");

            diaryEntrys.add(new Diary(user, hour, day, week, content));
        }

        rs.close();
        stmt.close();
        connection.close();

        return diaryEntrys;
    }

}
