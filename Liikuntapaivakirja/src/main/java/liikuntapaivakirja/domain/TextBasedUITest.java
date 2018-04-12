
package liikuntapaivakirja.domain;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class TextBasedUITest {
    
    private Scanner reader;
    private Map<String, String> commandsStart;
    private Map<String, String> commandsLogged;
    private DiaryService service;

    public TextBasedUITest(Scanner reader, DiaryService service) {
        this.reader = reader;
        this.service = service;
 
        commandsStart = new TreeMap<>();
        commandsLogged = new TreeMap<>();
        
        commandsStart.put("x", "x lopeta");
        commandsStart.put("1", "1 luo uusi käyttäjä");
        commandsStart.put("2", "2 kirjaudu sisään");
        commandsStart.put("x", "x lopeta");
        
        commandsLogged.put("x", "x lopeta");
        commandsLogged.put("3", "3 hae käyttäjän viikottaiset pisteet");
        commandsLogged.put("4", "4 hae käyttäjän kirjoittamat liikuntatunnit ym");
        commandsLogged.put("5", "5 hae käyttäjän viikkotavoite");
        commandsLogged.put("6", "6 aseta uusi viikkotavoite");
        commandsLogged.put("7", "kirjaudu ulos");
        commandsLogged.put("x", "x lopeta");
    }
 
    public void start() throws Exception {
        System.out.println("Tämä on tämän sovelluksen tekstuaalinen testikäyttöliittymä");
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
 
            if (command.equals("x")) {
                break;
            } else if (command.equals("3")) {
                getWeeklyPoints();
            } else if (command.equals("4")) {
                getDiaryEntrys();
            } else if (command.equals("5")) {
                getWeeklyGoal();
            } else if (command.equals("6")) {
                setWeeklyGoal();
            } else if (command.equals("7")) {
                logout();
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
        System.out.println("3 hae käyttäjän viikottaiset pisteet\n" +
            "4 hae käyttäjän kirjoittamat liikuntatunnit ym\n" +
            "5 hae käyttäjän viikkotavoite\n" +
            "6 aseta uusi viikkotavoite\n" +
            "7 kirjaudu ulos\n" +
            "x lopeta");
    }
    
    private void addUser() throws Exception {
        System.out.println("Anna nimi: ");
        String username = reader.nextLine();
        System.out.println("salasana (7 merkkiä tai enemmän!):");
        String password = reader.nextLine();
        
        if (service.createUser(username, password) == true) {
            System.out.println("Testikäyttäjä " + username + " on nyt luotu");
            loginUser();
        } else {
            System.out.println("Käyttäjän luominen ei onnistunut, käyttäjänimi on jo käytössä tai salasana on liian lyhyt");
            addUser();
            // Ilmoittaa jos käyttäjä jo on olemassa ja/tai jos salasana on liian lyhyt
        }
    }

    private void loginUser() throws Exception {
        System.out.println("Kirjaudu sisään.");
        System.out.print("Käyttäjätunnus:");
        String username = reader.nextLine();
        System.out.print("Salasana:");
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

    private void getWeeklyPoints() throws Exception {
        System.out.println("Minkä viikon pisteet haluat nähdä?");
        int week = reader.nextInt();
        System.out.println("Sinulla on nyt " + service.getPointsWeek(week) + " pistettä");
    }

    private void getDiaryEntrys() throws Exception {
        System.out.println("Kaikki päiväkirjaan lisätyt tiedot tähän asti:");
        System.out.println(service.getAll());
    }

    private void getWeeklyGoal() throws Exception {
        System.out.println("Nykyinen viikkotavoite on: " + service.getWeeklyGoal());
    }

    private void setWeeklyGoal() throws Exception {
        System.out.println("Anna uusi viikkotavoite:");
        int goal = reader.nextInt();
        service.createWeeklyGoal(goal);
        getWeeklyGoal();
    }

    private void logout() throws Exception {
        service.logout();
        start();
    }
 
}
