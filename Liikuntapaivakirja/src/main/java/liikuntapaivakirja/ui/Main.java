
package liikuntapaivakirja.ui;

import java.sql.*;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        Connection connection = DriverManager.getConnection("jdbc:sqlite:tietokanta.db");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT 1");
        if(resultSet.next()) {
            System.out.println("Hei tietokantamaailma!");
        } else {
            System.out.println("Yhteyden muodostaminen epäonnistui.");
        }
    }
    
}
