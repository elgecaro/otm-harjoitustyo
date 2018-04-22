
package liikuntapaivakirja.dao;

import java.sql.*;

public class Database {
    
    private String databaseAddress;
    
    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
    
    public boolean tableExist(Connection conn, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) { 
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }
    
    public void createTableUser(Connection conn, Database database) throws SQLException {
        PreparedStatement stmt = database.getConnection().prepareStatement("CREATE TABLE User (\n" + 
            "username varchar(15) PRIMARY KEY CHECK (LENGTH (username) > 2), \n" + 
            "password varchar NOT NULL CHECK (LENGTH (password) > 5), \n" +
            "weeklyGoal integer \n" +
            ");"); 
        stmt.executeUpdate(); 
        stmt.close();    
        conn.close();
    }
    
    public void createTableDiary(Connection conn, Database database) throws SQLException {
        PreparedStatement stmt = database.getConnection().prepareStatement("CREATE TABLE Diary (\n" + 
            "username varchar(15), \n" +
            "hour float NOT NULL, \n" +
            "day integer NOT NULL, \n" +
            "week INTEGER NOT NULL, \n" +
            "content varchar(200), \n" +
            "FOREIGN KEY(username) REFERENCES User(username) \n" +
            ");");
        stmt.executeUpdate(); 
        stmt.close();    
        conn.close();
    }
}
