
package liikuntapaivakirja.ui;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import liikuntapaivakirja.dao.Database;
import liikuntapaivakirja.dao.DbDiaryDao;
import liikuntapaivakirja.dao.DbUserDao;
import liikuntapaivakirja.dao.DiaryDao;
import liikuntapaivakirja.dao.UserDao;
import liikuntapaivakirja.domain.Diary;
import liikuntapaivakirja.domain.DiaryService;


public class TestGUI extends Application {
        
    private DiaryService diaryService; 
    private Scene diaryScene;
    private Scene newUserScene;
    private Scene loginScene;
    private VBox diaryNodes;
    private VBox userHighscoreNodes;
    private VBox highscoreNodes;
    private VBox weeklyPoints;
    
    @Override
    public void init() throws SQLException {
        Database testitietokanta = new Database("jdbc:sqlite:tietokanta.db");
        testitietokanta.getConnection();
        if (testitietokanta.tableExist((testitietokanta.getConnection()), "User") == false) {
            testitietokanta.createTableUser(testitietokanta.getConnection(), testitietokanta);
        } if (testitietokanta.tableExist((testitietokanta.getConnection()), "Diary") == false) {
            testitietokanta.createTableDiary(testitietokanta.getConnection(), testitietokanta);
        }
        
        UserDao userdao = new DbUserDao(testitietokanta);
        DiaryDao diarydao = new DbDiaryDao(testitietokanta);
        diaryService = new DiaryService(diarydao, userdao);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        // login scene
        Label loginText = new Label("Tämä on sovelluksen testaus GUI.");
        loginText.setTranslateY(-40);
        Label loginMessage = new Label("");
        Button loginButton = new Button("kirjaudu sisään");
        Button createButton = new Button("luo uusi käyttäjä");
        createButton.setTranslateY(30);
        
        loginButton.setOnAction((event)-> {
            
            Label usernameLabel = new Label("Käyttäjänimi:");
            TextField usernameField = new TextField();
            Label passwordLabel = new Label("Salasana:");
            PasswordField passwordField = new PasswordField();
            Button loginUserButton = new Button("Kirjaudu sisään");
            Button goBackButton = new Button("takaisin");
            
            loginUserButton.setOnAction(e-> {
                
                String username = usernameField.getText();
                String password = passwordField.getText();
                
                try {
                    if (diaryService.login(username, password)) {
                        loginMessage.setText("Kirjautuminen onnistui");
                        loginMessage.setTextFill(Color.GREEN);
                        loggedIn(primaryStage, username);

                    } else {
                        loginMessage.setText("Käyttäjänimi/salasana väärä");      
                        loginMessage.setTextFill(Color.RED);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(TestGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            
            goBackButton.setOnAction(ev-> {
                start(primaryStage);
            });
            
            // Tarkoitus on, että GridPanea EI käytetä lopullisessa sovelluksessa,
            // sitä käytetään juuri nyt vain toimintojen testaukseen! :)
            
            GridPane loginPane = new GridPane();
            loginPane.add(usernameLabel, 0, 0);
            loginPane.add(usernameField, 1, 0);
            loginPane.add(passwordLabel, 0, 1);
            loginPane.add(passwordField, 1, 1);
            loginPane.add(loginUserButton, 1, 2);
            loginPane.add(loginMessage, 1, 3);
            loginPane.add(goBackButton, 0, 4);

            // tyylittelyä: lisätään tyhjää tilaa reunoille ym
            loginPane.setHgap(10);
            loginPane.setVgap(10);
            loginPane.setPadding(new Insets(10, 10, 10, 10));
                       
            Scene scene = new Scene(loginPane);

            primaryStage.setScene(scene);
            primaryStage.show();
        });
            
        createButton.setOnAction(e-> {
            Label newUsernameLabel = new Label("Käyttäjänimi:");
            TextField newUsernameField = new TextField();
            Label newPasswordLabel = new Label("Salasana:");
            PasswordField newPasswordField = new PasswordField();
            Button createUserButton = new Button("Luo käyttäjä");
            Button goBackButton = new Button("takaisin");
            createUserButton.setOnAction(ev-> {
                
                try {
                    String username = newUsernameField.getText();
                    String password = newPasswordField.getText();

                    if (newPasswordField.getText().length() < 7) {
                        loginMessage.setText("Salasana on liian lyhyt");
                        loginMessage.setTextFill(Color.RED);
                    } else if (newUsernameField.getText().length() < 3) {
                        loginMessage.setText("Käyttäjänimi on liian lyhyt");
                        loginMessage.setTextFill(Color.RED);
                    } else if (diaryService.createUser(username, password)) {
                        loginMessage.setText("Käyttäjäsi on nyt luotu");
                        loginMessage.setTextFill(Color.GREEN);
                    } else {
                        loginMessage.setText("Käyttäjänimi on jo käyössä");
                        loginMessage.setTextFill(Color.RED);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(TestGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }); 
        
            goBackButton.setOnAction(ev-> {
                start(primaryStage);
            });
            
            GridPane createUserPane = new GridPane();
            createUserPane.add(newUsernameLabel, 0, 0);
            createUserPane.add(newUsernameField, 1, 0);
            createUserPane.add(newPasswordLabel, 0, 1);
            createUserPane.add(newPasswordField, 1, 1);
            createUserPane.add(createUserButton, 1, 2);
            createUserPane.add(loginMessage, 1, 3);
            createUserPane.add(goBackButton, 0, 4);

            // tyylittelyä: lisätään tyhjää tilaa reunoille ym
            createUserPane.setHgap(10);
            createUserPane.setVgap(10);
            createUserPane.setPadding(new Insets(10, 10, 10, 10));
                       
            Scene scene = new Scene(createUserPane);

            primaryStage.setScene(scene);
            primaryStage.show();
        });
        
        StackPane startPane = new StackPane();
        startPane.getChildren().addAll(loginText, loginMessage, loginButton, createButton);
   
        Scene startScene = new Scene(startPane, 260, 150);
        
        primaryStage.setTitle("Liikuntapäiväkirja");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    public void loggedIn(Stage primaryStage, String username) throws Exception {
        Label loginText = new Label("Tervetuloa " + username);
        Label newHourLabel = new Label("Tuntia:");
        TextField newHourField = new TextField();
        Label newDayLabel = new Label("Päivä:");
        TextField newDayField = new TextField();
        Label newWeekLabel = new Label("Viikko:");
        TextField newWeekField = new TextField();
        Label newContentLabel = new Label("Kuvaus:");
        TextField newContentField = new TextField();
        Label createMessage = new Label("");

        Button createExcercise = new Button("lisää");
        Button logoutButton = new Button("kirjaudu ulos");

        ScrollPane entriesScrollbar = new ScrollPane();
        entriesScrollbar.setPrefViewportHeight(300);   
        entriesScrollbar.setPrefViewportWidth(400);        

        logoutButton.setOnAction(ev-> {
            diaryService.logout();
            start(primaryStage);
        });
        
        createExcercise.setOnAction(e->{
            String hourS = newHourField.getText();
            String dayS = newDayField.getText();
            String weekS = newWeekField.getText();
            String content = newContentField.getText();

            if (isDouble(hourS) != true) {
                createMessage.setText("Tunnin muoto väärä");
                createMessage.setTextFill(Color.RED);
            } else if (isInteger(dayS) != true) {
                createMessage.setText("Päivän muoto väärä");
                createMessage.setTextFill(Color.RED);
            } else if (isInteger(weekS) != true) {
                createMessage.setText("Viikon muoto väärä");
                createMessage.setTextFill(Color.RED);
            } else {
                double hour = Double.parseDouble(hourS);
                int day = Integer.parseInt(dayS);
                int week = Integer.parseInt(weekS);
                    
                if (diaryService.createExercise(hour, day, week, content) == true) {
                    createMessage.setText("Liikunnan lisääminen onnistui");
                    createMessage.setTextFill(Color.GREEN);
                    try {
                        redrawDiaryList();
                        redrawUserHighscoreList();
                        redrawhighscoreList();
                        redrawWeeklyPoints();

                    } catch (Exception ex) {
                        Logger.getLogger(TestGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    createMessage.setText("Liikunnan lisääminen ei onnistunut");
                    createMessage.setTextFill(Color.RED);
                }
            }
        });
        
        Label entriesLabel = new Label("15 viimeistä kirjoitusta:");
        entriesLabel.setFont((Font.font(null, FontWeight.BOLD, 12)));
        diaryNodes = new VBox(10);
        diaryNodes.setMaxWidth(400);
        diaryNodes.setMinWidth(280);
        redrawDiaryList();
        entriesScrollbar.setContent(diaryNodes);
        
        ScrollPane userHighscoreScrollbar = new ScrollPane();
        userHighscoreScrollbar.setPrefViewportHeight(200);   
        userHighscoreScrollbar.setPrefViewportWidth(200); 
        
        Label userHighscoreLabel = new Label("3 parasta viikkopisteesi:");
        userHighscoreNodes = new VBox(10);
        userHighscoreNodes.setMaxWidth(280);
        userHighscoreNodes.setMinWidth(200);
        redrawUserHighscoreList();

        userHighscoreScrollbar.setContent(userHighscoreNodes);
        
        ScrollPane highscoreScrollbar = new ScrollPane();
        highscoreScrollbar.setPrefViewportHeight(200);   
        highscoreScrollbar.setPrefViewportWidth(200); 
        
        Label highscoreLabel = new Label("3 parasta viikkopistettä käyttäjien kesken:");
        highscoreNodes = new VBox(10);
        highscoreNodes.setMaxWidth(280);
        highscoreNodes.setMinWidth(200);
        redrawhighscoreList();

        highscoreScrollbar.setContent(highscoreNodes);
        
        Label weeklyPointsLabel = new Label("Tämän/viimeisen viikon pisteet:");
        weeklyPoints = new VBox(10);
        redrawWeeklyPoints();
        
        // Tulossa: näe viikkotavoite
        // + aseta uusi viikkotavoite
        // + näe kaikki (myös vanhemmat) kirjoitukset
        // näe kaikkien viikkojen pisteet(?)


        GridPane loggedPane = new GridPane();
            loggedPane.add(loginText, 0, 0);
            loggedPane.add(newHourLabel, 0, 1);
            loggedPane.add(newHourField, 1, 1);
            loggedPane.add(newDayLabel, 0, 2);
            loggedPane.add(newDayField, 1, 2);
            loggedPane.add(newWeekLabel, 0, 3);
            loggedPane.add(newWeekField, 1, 3);
            loggedPane.add(newContentLabel, 0, 4);
            loggedPane.add(newContentField, 1, 4);
            loggedPane.add(createExcercise, 1, 5);
            loggedPane.add(createMessage, 1, 6);
            loggedPane.add(entriesLabel, 1, 7);
            loggedPane.add(entriesScrollbar, 1, 8);
            loggedPane.add(userHighscoreLabel, 4, 7);
            loggedPane.add(userHighscoreScrollbar, 4, 8);
            loggedPane.add(highscoreLabel, 5, 7);
            loggedPane.add(highscoreScrollbar, 5, 8);
            loggedPane.add(weeklyPointsLabel, 5, 0);
            loggedPane.add(weeklyPoints, 6, 0);


            loggedPane.add(logoutButton, 0, 9);

            loggedPane.setHgap(10);
            loggedPane.setVgap(10);
            loggedPane.setPadding(new Insets(10, 10, 10, 10));
                       
            Scene scene = new Scene(loggedPane);

            primaryStage.setScene(scene);
            primaryStage.show();
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
    
    public void redrawDiaryList() throws Exception {
        diaryNodes.getChildren().clear();
        List<Diary> allEntries = diaryService.get15Latest();
        allEntries.forEach(diary->{
            diaryNodes.getChildren().add(createDiaryNode(diary));
        });
    }
    
    public void redrawUserHighscoreList() throws Exception {
        userHighscoreNodes.getChildren().clear();
        Map<Double, Integer> bestWeeks = diaryService.getUsersBestWeeks();
        
        int number = 1;
        Iterator<Map.Entry<Double, Integer>> entries = bestWeeks.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Double, Integer> entry = entries.next();
            userHighscoreNodes.getChildren().add(createUserHighscoreNode(entry, number));
            number++;
        }
    }
    
    private void redrawhighscoreList() throws Exception {
        highscoreNodes.getChildren().clear();
        Map<String, Double> allBestWeeks = diaryService.getBestWeeks();
        int number = 1;
        Iterator<Map.Entry<String, Double>> entries = allBestWeeks.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Double> entry = entries.next();
            highscoreNodes.getChildren().add(createhighscoreNode(entry, number));
            number++;
        
        }
    }
    
    private void redrawWeeklyPoints() throws Exception {
        weeklyPoints.getChildren().clear();
        
        HBox box = new HBox(10);
        double points = diaryService.getPointsWeek(diaryService.getLatestWeek());
        String pointsString = String.valueOf(points);
        
        Label label = new Label(pointsString);
        
        label.setMinHeight(28);
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().add(label);
        weeklyPoints.getChildren().add(box);
        
    }
    
    public Node createDiaryNode(Diary diary) {
        HBox box = new HBox(10);
        Label label = new Label(diary.toString());
        
        label.setMinHeight(28);     
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().addAll(label);
        return box;
    }
    
    public Node createUserHighscoreNode(Entry entry, int number) throws Exception {
        HBox box = new HBox(10);
        Label label = new Label(number + ". Pisteet: " + entry.getKey() + ", viikko: " + entry.getValue());
        
        label.setMinHeight(28);
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().add(label);
        return box;
    }
    
    public Node createhighscoreNode(Entry entry, int number) throws Exception {
        HBox box = new HBox(10);
        Label label = new Label(number + ". Käyttäjä: " + entry.getKey() + ", pisteet: " + entry.getValue());
        
        label.setMinHeight(28);
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().add(label);
        return box;
    }
          
    public static void main(String[] args) {
        launch(args);
    }

    
}
