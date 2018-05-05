
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
     * Metodi luo uudet tauluko tietokantaan, jos eivät ole ennestään olemassa.
     * @param conn Tietokannan yhteys
     * @param database Tietokannan nimi
     * @throws SQLException jos tapahtuu virhe?
     */
    public void checkForTables(Connection conn, Database database) throws SQLException {
        PreparedStatement stmt = database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS User (\n" + 
            "username varchar(15) PRIMARY KEY CHECK (LENGTH (username) > 2), \n" + 
            "password varchar NOT NULL CHECK (LENGTH (password) > 5), \n" +
            "weeklyGoal float \n" +
            ");"); 
        stmt.executeUpdate(); 
        
        PreparedStatement stmt2 = database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Diary (\n" + 
            "username varchar(15), \n" +
            "hour float NOT NULL, \n" +
            "day integer NOT NULL, \n" +
            "week INTEGER NOT NULL, \n" +
            "content varchar(200), \n" +
            "FOREIGN KEY(username) REFERENCES User(username) \n" +
            ");");
        stmt2.executeUpdate(); 
    }
}
