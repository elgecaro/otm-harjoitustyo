
package liikuntapaivakirja.dao;

import java.sql.*;

/**
 * Ohjelman tietokantaa edustavaa luokka.
 */

public class Database {

    private String databaseAddress;
    
    /**
     * Asetetaan tietokannan osoite.
     * @param databaseAddress tietokannan osoite
     */
    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    /**
     * Luodaan yhteyden tietokantaan.
     * @return yhteyden tietokantaan
     * @throws SQLException jos tietokantaan ei saada yhteyttä
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
    
    /**
     * Kontrolloidaan jos tietokannan taulukot ovat olemassa.
     * @param conn yhteys tietokantaan
     * @param tableName tietokannan taulukon nimi
     * @return true jos taulukko löytyy tietokannasta, muuten false
     * @throws SQLException jos tietokantaan ei saada yhteyttä
     */
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
    
    /**
     * Jos tietokannassa ei ole User-taulukkoa, luodaan sellaista.
     * @param conn yhteys tietokantaan
     * @param database tietokanta
     * @throws SQLException jos tulee virhe taulukon luonnissa
     */
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
    
    /**
     * Jos tietokannassa ei ole Diary-taulukkoa, luodaan sellaista.
     * @param conn yhteys tietokantaan
     * @param database tietokanta
     * @throws SQLException jos tulee virhe taulukon luonnissa
     */
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
