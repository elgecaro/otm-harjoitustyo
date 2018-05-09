
package liikuntapaivakirja.dao;

import liikuntapaivakirja.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbUserDao implements UserDao {
    private Database database;
    private double goal;
    
    /**
     * Metodi asettaa tietokannan osoitteen.
     * @param database tietokannan osoite
     */
    public DbUserDao(Database database) {
        this.database = database;
    }
    
    @Override
    public User create(User user) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO User (username, password)"
                + "VALUES (?,?)");
        String username = user.getUsername();
        String password = user.getPassword();
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.execute();
        connection.close();

        return null;
    }
    
    @Override
    public boolean usernameAndPasswordMatch(String username, String password) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?");
        stmt.setString(1, username);
        stmt.setString(2, password);
        
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return false;
        }
        connection.close();
        return true;
    }
    
    @Override
    public User findByUsername(String user) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setObject(1, user);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String username = rs.getString("username");
        String password = rs.getString("password");
        User newUser = new User(username, password);
        rs.close();
        stmt.close();
        connection.close();

        return newUser;
    }
    
    @Override
    public double getWeeklyGoal(String username) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT weeklyGoal FROM User WHERE username = ?");
        stmt.setObject(1, username);
        goal = 0;
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            goal = rs.getDouble("weeklyGoal");
        }
        
        rs.close();
        stmt.close();
        connection.close();
        
        return goal;
    }
    
    @Override
    public void setWeeklyGoal(double goal, String username) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE User SET weeklyGoal = ? WHERE username = ?");
        stmt.setObject(1, goal);
        stmt.setObject(2, username);     
        stmt.execute();
        connection.close();
    }
}
