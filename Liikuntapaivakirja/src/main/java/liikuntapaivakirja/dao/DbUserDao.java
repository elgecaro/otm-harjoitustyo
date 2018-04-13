
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
    private List<User> users;
    
    public DbUserDao(Database database) {
        this.database = database;
    }
    
    @Override
    public User create(User t) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO User (username, password)"
                + "VALUES (?,?)");
        String username = t.getUsername();
        String password = t.getPassword();
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.execute();
        connection.close();

        return null;
    }
    
    @Override
    public boolean usernameAndPasswordMatch(String user, String password) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?");
        stmt.setString(1, user);
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
    public User findByUsername(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String username = rs.getString("username");
        String password = rs.getString("password");
        User o = new User(username, password);
        rs.close();
        stmt.close();
        connection.close();

        return o;
    }
    
    @Override
    public List<User> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User");
        ResultSet rs = stmt.executeQuery();
        List<User> user = new ArrayList<>();

        while (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            user.add(new User(username, password));
        }
        
        rs.close();
        stmt.close();
        connection.close();

        return user;
    }

}
