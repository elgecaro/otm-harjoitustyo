
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
        
        Scanner lukija = new Scanner(System.in);
        
        System.out.println("Tämä on testikäyttöliittymä. Haluatko 1 luoda testikäyttäjän, vai 2 kirjaudua sisään?. Kirjoita 1 tai 2");
        String vastaus = lukija.nextLine();
        
        if (vastaus.equals("1")) {             
        System.out.println("Anna nimi: ");
        String username = lukija.nextLine();
        System.out.println("salasana (7 merkkiä tai enemmän!):");
        String password = lukija.nextLine();
        
        service.createUser(username, password);
        if (service.createUser(username, password) == false) {
            System.out.println("Käyttäjän luominen ei onnistunut, käyttäjänimi on jo käytössä");
        }
//      Ei toimi oikein tällä hetkellä

        System.out.println("Testikäyttäjä " + username + " on nyt luotu");
        }
        
        if (vastaus.equals ("2")) {
            System.out.println("Kirjaudu sisään. Käyttäjätunnus:");
            String username = lukija.nextLine();
            System.out.println("Salasana:");
            String password = lukija.nextLine();
            
            service.login(username);
            System.out.println("Tervetuloa " + service.getUsername());
        } 
        
        System.out.println("Käyttäjäsi kirjatut tiedot tähän asti:");
        System.out.println(service.getAll());
        
        System.out.print("Lisää käyttäjälle päiväkirjaan liikuntatunteja.");
        System.out.print("Kuinka monta tuntia? ");
        Double hour = lukija.nextDouble();
        System.out.print("Mikä päivä? ");
        Integer day = lukija.nextInt();
        System.out.print("Mikä viikko? ");
        Integer week = lukija.nextInt();
        System.out.print("Kuvaus: ");
        String content = lukija.nextLine();
        lukija.nextLine();

        service.createExcerise(hour, day, week, content);
        // Tulostus vielä väärä + 
        // Tulostaa kuvauksen väärin
        
    }
    
}
