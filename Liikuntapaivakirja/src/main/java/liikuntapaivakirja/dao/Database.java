
package liikuntapaivakirja.dao;

import java.sql.*;


public class Database {
    
    // Kesken
    
    private String databaseAddress;
    
    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
}
