
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
import liikuntapaivakirja.domain.User;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        
        Database testitietokanta = new Database("jdbc:sqlite:tietokanta.db");
        testitietokanta.getConnection();
        if (testitietokanta.tableExist((testitietokanta.getConnection()), "User") == false) {
            testitietokanta.createTableUser(testitietokanta.getConnection(), testitietokanta);
        } 
        
        if (testitietokanta.tableExist((testitietokanta.getConnection()), "Diary") == false) {
            testitietokanta.createTableDiary(testitietokanta.getConnection(), testitietokanta);
        }
        
        UserDao userdao = new DbUserDao(testitietokanta);
        DiaryDao diarydao = new DbDiaryDao(testitietokanta);
        DiaryService service = new DiaryService(diarydao, userdao);
        
        Scanner reader = new Scanner(System.in);
        TestTextBasedUI uI = new TestTextBasedUI(reader, service);
        
        uI.start();
    }
}
