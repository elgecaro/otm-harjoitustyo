
package liikuntapaivakirja.domain;

import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class TestTextBasedUI {
    
    private Scanner reader;
    private Map<String, String> commandsStart;
    private Map<String, String> commandsLogged;
    private DiaryService service;

    public TestTextBasedUI(Scanner reader, DiaryService service) {
        this.reader = reader;
        this.service = service;
 
        commandsStart = new TreeMap<>();
        commandsLogged = new TreeMap<>();
        
        commandsStart.put("x", "x lopeta");
        commandsStart.put("1", "1 luo uusi käyttäjä");
        commandsStart.put("2", "2 kirjaudu sisään");
        commandsStart.put("x", "x lopeta");
        
        commandsLogged.put("3", "3 lisää liikuntatunteja käyttäjälle");
        commandsLogged.put("4", "4 hae käyttäjän kirjoittamat liikuntatunnit");
        commandsLogged.put("5", "5 hae käyttäjän viimeisen viikon pisteet");
        commandsLogged.put("6", "6 hae käyttäjän viikottaiset pisteet");
        commandsLogged.put("7", "7 hae käyttäjän viikkotavoite");
        commandsLogged.put("8", "8 aseta uusi viikkotavoite");
        commandsLogged.put("u", "kirjaudu ulos");
  
    }
 
    public void start() throws Exception {
        System.out.println("Tämä on tämän sovelluksen tekstuaalinen testikäyttöliittymä. "
                + "Voit luoda uuden käyttäjän tai kirjautua sisään.");
        System.out.println("Voit kirjautua sisään testikäyttäjällä\n"
                + "käyttäjänimi: testikäyttäjä, salasana: salasana");
        System.out.println();
        printInstructionStart();
        while (true) {
            System.out.println();
            System.out.print("komento: ");
            String command = reader.nextLine();
            if (!commandsStart.keySet().contains(command)) {
                System.out.println("virheellinen komento.");
                printInstructionStart();
            }
 
            if (command.equals("x")) {
                break;
                // JOS käyttäjä on kirjautunut ulos ja kirjoittaa x, 
                // mitään ei tapahdu, jos kirjoiittaa x uudestaan sovellus sulkeutuu
            } else if (command.equals("1")) {
                addUser();
            } else if (command.equals("2")) {
                loginUser();
            }
        }          
    }
    
    public void loggedIn() throws Exception {
        printInstruction();
        while (true) {
            System.out.println();
            System.out.print("komento: ");
            String command = reader.nextLine();
            if (!commandsLogged.keySet().contains(command)) {
                System.out.println("virheellinen komento.");
                printInstruction();
            }
        
            if (command.equals("3")) {
                createExercise();
            } else if (command.equals("4")) {
                getDiaryEntrys();
            } else if (command.equals("5")) {
                getLatestWeeklyPoints();
            } else if (command.equals("6")) {
                getWeeklyPoints();
            } else if (command.equals("7")) {
                getWeeklyGoal();
            } else if (command.equals("8")) {
                setWeeklyGoal();
            } else if (command.equals("9")) {
                getUserHighscoreWeeks();
//            } else if (command.equals("10")) {
//                getHighscoreWeeks();
//              Ei vielä toimi toivotusti
            } else if (command.equals("u")) {
                logout();
                break;
            }
        }
    }
    
    private void printInstructionStart() {
        System.out.println("Käytettävissä olevat komennot ovat:");
        System.out.println("1 lisää käyttäjä\n" +
            "2 kirjaudu sisään\n" +
            "x lopeta");
    }
    
    private void printInstruction() {
        System.out.println("Käytettävissä olevat komennot ovat:");
        System.out.println("3 lisää liikuntatunteja käyttäjälle\n" +
            "4 hae käyttäjän kirjoittamat liikuntatunnit\n" +
            "5 hae käyttäjän viimeisen viikon pisteet\n" +
            "6 hae käyttäjän viikottaiset pisteet\n" +
            "7 hae käyttäjän viikkotavoite\n" +
            "8 aseta uusi viikkotavoite\n" +
            "u kirjaudu ulos");
    }
    
    private void addUser() throws Exception {
        System.out.println("Anna nimi: ");
        String username = reader.nextLine();
        System.out.println("Anna salasana (7 merkkiä tai enemmän!):");
        String password = reader.nextLine();
        
        if (password.length() < 7) {
            System.out.println("Salasana on liian lyhyt");
        } else if (service.createUser(username, password) == true) {
            System.out.println("Testikäyttäjä " + username + " on nyt luotu");
            loginUser();
        } else {
            System.out.println("Käyttäjän luominen ei onnistunut, käyttäjänimi on jo käytössä");
            addUser();
        }
    }

    private void loginUser() throws Exception {
        System.out.println("Kirjaudu sisään.");
        System.out.print("Käyttäjätunnus: ");
        String username = reader.nextLine();
        System.out.print("Salasana: ");
        String password = reader.nextLine();
            
        if (service.login(username, password) == true) {
            System.out.println();
            System.out.println("Tervetuloa " + service.getUsername());
            loggedIn();
        } else {
            System.out.println("Syötit väärän käyttäjätunnuksen tai salasananan");
            loginUser();
        }
    }

    private void getLatestWeeklyPoints() throws Exception {
        System.out.println("Tämän viikon pisteet:");
        System.out.println(service.getPointsWeek(service.getLatestWeek()));
    }
    
    private void getWeeklyPoints() throws Exception {
        System.out.println("Minkä viikon pisteet haluat nähdä?");
        String weekS = reader.nextLine();
        if (isInteger(weekS) != true) {
            System.out.println("Viikon muoto väärä");
            getWeeklyPoints();
        }
        int week = Integer.parseInt(weekS);
        System.out.println("Sinulla on nyt " + service.getPointsWeek(week) + " pistettä");
    }

    private void getDiaryEntrys() throws Exception {
        System.out.println("Kaikki päiväkirjaan lisätyt tiedot tähän asti:");
        List<Diary> diaryEntrys = service.getAll();
        Iterator<Diary> it = diaryEntrys.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    private void getWeeklyGoal() throws Exception {
        System.out.println("Nykyinen viikkotavoite on: " + service.getWeeklyGoal());
    }

    private void setWeeklyGoal() throws Exception {
        System.out.println("Anna uusi viikkotavoite:");
        String goalS = reader.nextLine();
        if (isInteger(goalS) != true) {
            System.out.println("Väärä muoto");
            setWeeklyGoal();
        }
        int goal = Integer.parseInt(goalS);
        service.createWeeklyGoal(goal);
        getWeeklyGoal();
    }

    private void logout() throws Exception {
        service.logout();
        start();
    }

    private void createExercise() throws Exception {     
        System.out.println("Lisää liikuntatunteja käyttäjälle. Anna tunnit esim. muodossa 1 tai 1,5, päivät esim. 1 tai 10, viikko esim 1 tai 10");
        System.out.println("Tunnit: ");
        String hourS = reader.next();
        if (isDouble(hourS) != true) {
            System.out.println("Tunnin muoto väärä");
            createExercise();
        }
        System.out.println("Päivä: ");
        String dayS = reader.next();
        if (isInteger(dayS) != true) {
            System.out.println("Päivän muoto väärä");
            createExercise();
        }
        System.out.println("Viikko: ");
        String weekS = reader.next();
        if (isInteger(weekS) != true) {
            System.out.println("Viikon muoto väärä");
            createExercise();
        } else {
            System.out.println("Kuvaus: ");
            String content = reader.next();
            
            double hour = Double.parseDouble(hourS);
            int day = Integer.parseInt(dayS);
            int week = Integer.parseInt(weekS);
                    
            if (service.createExercise(hour, day, week, content) == true) {
                System.out.println("Liikunnan lisääminen onnistui");
            } else {
                System.out.println("Liikunnan lisääminen ei onnistunut");
            }
        } 
        loggedIn();
    }
    
    private void getUserHighscoreWeeks() throws Exception {
        Map<Double, Integer> bestWeeks = service.getUsersBestWeeks();
        
        Iterator<Map.Entry<Double, Integer>> entries = bestWeeks.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Double, Integer> entry = entries.next();
            System.out.println("Pisteet: " + entry.getKey() + ", Viikko: " + entry.getValue());    
        }
    }
    
    private void getHighscoreWeeks() throws Exception {     
        Map<Double, Integer> bestWeeks = service.getBestWeeks();
        
        Iterator<Map.Entry<Double, Integer>> entries = bestWeeks.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Double, Integer> entry = entries.next();
            System.out.println("Käyttäjä: " + entry.getKey() + ", Pisteet ja viikko: " + entry.getValue());
        }
    }
    

    public static boolean isDouble(String s) {
        try { 
            Double.parseDouble(s); 
        } catch (NumberFormatException | NullPointerException e) { 
            return false; 
        }
        return true;
    }

    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch (NumberFormatException | NullPointerException e) { 
            return false; 
        }
        return true;
    }
 
}
