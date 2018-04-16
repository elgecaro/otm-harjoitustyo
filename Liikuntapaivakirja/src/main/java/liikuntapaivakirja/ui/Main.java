
package liikuntapaivakirja.ui;

import java.sql.*;
import java.util.Scanner;
import liikuntapaivakirja.dao.Database;
import liikuntapaivakirja.dao.DbDiaryDao;
import liikuntapaivakirja.dao.DbUserDao;
import liikuntapaivakirja.dao.DiaryDao;
import liikuntapaivakirja.dao.UserDao;
import liikuntapaivakirja.domain.Diary;
import liikuntapaivakirja.domain.DiaryService;
import liikuntapaivakirja.domain.TestTextBasedUI;
import liikuntapaivakirja.domain.User;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        
        Database testitietokanta = new Database("jdbc:sqlite:tietokanta.db");
        testitietokanta.getConnection();
        
        UserDao userdao = new DbUserDao(testitietokanta);
        DiaryDao diarydao = new DbDiaryDao(testitietokanta);
        DiaryService service = new DiaryService(diarydao, userdao);
        
        Scanner reader = new Scanner(System.in);
        TestTextBasedUI uI = new TestTextBasedUI(reader, service);
        
        uI.start();
    }
}
