
package liikuntapaivakirja.ui;

import java.sql.*;
import liikuntapaivakirja.dao.Database;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        Database testi = new Database("jdbc:sqlite:tietokanta.db");
        
    }
    
}
