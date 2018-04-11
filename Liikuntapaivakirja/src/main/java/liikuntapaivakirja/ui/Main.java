
package liikuntapaivakirja.ui;

import java.sql.*;
import java.util.Scanner;
import liikuntapaivakirja.dao.Database;
import liikuntapaivakirja.dao.DbDiaryDao;
import liikuntapaivakirja.dao.DbUserDao;
import liikuntapaivakirja.dao.DiaryDao;
import liikuntapaivakirja.dao.UserDao;
import liikuntapaivakirja.domain.DiaryService;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        
        Database testitietokanta = new Database("jdbc:sqlite:tietokanta.db");
        testitietokanta.getConnection();
        
        UserDao userdao = new DbUserDao(testitietokanta);
        DiaryDao diarydao = new DbDiaryDao(testitietokanta);
        DiaryService service = new DiaryService(diarydao, userdao);
        
//        service.login("Ella111", "salasananionpitka");
//        service.createExcerise(1, 2, 3, "testaan");
//        System.out.println(service.getAll());
//        service.createWeeklyGoal(100);
//        System.out.println(service.getWeeklyGoal());
        
//        Scanner lukija = new Scanner(System.in);
//        
//        System.out.println("Tämä on testikäyttöliittymä. Haluatko 1 luoda testikäyttäjän, vai 2 kirjaudua sisään?. Kirjoita 1 tai 2");
//        String vastaus = lukija.nextLine();
//        
//        if (vastaus.equals("1")) {             
//            System.out.println("Anna nimi: ");
//            String username = lukija.nextLine();
//            System.out.println("salasana (7 merkkiä tai enemmän!):");
//            String password = lukija.nextLine();
//        
//        if (service.createUser(username, password) == true) {
//            System.out.println("Testikäyttäjä " + username + " on nyt luotu");
//        } else {
//            System.out.println("Käyttäjän luominen ei onnistunut, käyttäjänimi on jo käytössä");
//            }
//        // Ilmoittaa jos käyttäjä jo on olemassa ja/tai jos salasana on liian lyhyt
//        }
//        
//        if (vastaus.equals ("2")) {
//            System.out.println("Kirjaudu sisään. Käyttäjätunnus:");
//            String username = lukija.nextLine();
//            System.out.println("Salasana:");
//            String password = lukija.nextLine();
//            
//            if (service.login(username, password) == true) {
//            System.out.println("Tervetuloa " + service.getUsername());
//            } 
//            else {
//                System.out.println("Syötit väärän käyttäjätunnuksen tai salasananan");
//            }
//        
//            System.out.println("Käyttäjäsi kirjatut tiedot tähän asti:" + service.getAll());
//            // Tulostus vielä väärä + tulostaa kuvauksen väärin ("null")
//        
//            System.out.println("Haluatko lisätä käyttäjälle liikuntatunteja? Kirjoita 1");
//            String vastaus2 = lukija.nextLine();
//        
//            if (vastaus2.equals(1)) {  
//                System.out.print("Lisää käyttäjälle päiväkirjaan liikuntatunteja.");
//                System.out.print("Kuinka monta tuntia? ");
//                Double hour = lukija.nextDouble();
//                System.out.print("Mikä päivä? ");
//                Integer day = lukija.nextInt();
//                System.out.print("Mikä viikko? ");
//                Integer week = lukija.nextInt();
//                System.out.print("Kuvaus: ");
//                String tyhja = lukija.nextLine();
//                String content = lukija.nextLine();
//
//                service.createExcerise(hour, day, week, content);
//                System.out.println("Käyttäjäsi kirjatut tiedot tähän asti:" + service.getAll());
//                // Tulostus vielä väärä + tulostaa kuvauksen väärin ("null")
//            }
//        
//            System.out.println("Viikko 1 pisteet: " + service.getPointsWeek(1));
//            System.out.println("Viikko 2 pisteet: " + service.getPointsWeek(2));
//        
//            System.out.println("Aseta viikkotavoite");
//            int userGoal = lukija.nextInt();
//            service.createWeeklyGoal(userGoal);
//        
//            System.out.println("Nykyinen viikkotavoite:");
//            System.out.println(service.getWeeklyGoal());
//            // Ei vielä toimi oikein
//        }
//
    }
    
}
