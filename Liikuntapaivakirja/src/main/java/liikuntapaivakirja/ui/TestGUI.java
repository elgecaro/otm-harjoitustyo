
package liikuntapaivakirja.ui;

import java.sql.SQLException;
import java.util.List;
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
                    } else if (!diaryService.createUser(username, password)) {
                        loginMessage.setText("Käyttäjänimi on jo käyössä");
                        loginMessage.setTextFill(Color.RED);
                    } else if (diaryService.createUser(username, password)) {
                        loginMessage.setText("Käyttäjäsi on nyt luotu");
                        loginMessage.setTextFill(Color.GREEN);
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
        BorderPane mainPane = new BorderPane(entriesScrollbar);
        diaryScene = new Scene(mainPane, 400, 550);

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
            } if (isInteger(dayS) != true) {
                createMessage.setText("Päivän muoto väärä");
                createMessage.setTextFill(Color.RED);
            } if (isInteger(weekS) != true) {
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
                    } catch (Exception ex) {
                        Logger.getLogger(TestGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    createMessage.setText("Liikunnan lisääminen ei onnistunut");
                    createMessage.setTextFill(Color.RED);
                    // pitää vielä korjata ettei ohjelma kaadu
                }
            }
        });
        
        diaryNodes = new VBox(10);
        diaryNodes.setMaxWidth(280);
        diaryNodes.setMinWidth(280);
        redrawDiaryList();
        
        entriesScrollbar.setContent(diaryNodes);
        
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
            loggedPane.add(logoutButton, 0, 7);   
            loggedPane.add(entriesScrollbar, 1,8);

            // tyylittelyä: lisätään tyhjää tilaa reunoille ym
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
        List<Diary> allEntries = diaryService.getAll();
        allEntries.forEach(diary->{
            diaryNodes.getChildren().add(createDiaryNode(diary));
        });
    }
    
    public Node createDiaryNode(Diary diary) {
        HBox box = new HBox(10);
        Label label = new Label(diary.toString());
        
        label.setMinHeight(28);
                
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().addAll(label, spacer);
        return box;
}
             
            
    public static void main(String[] args) {
        launch(args);
    }
    
}
