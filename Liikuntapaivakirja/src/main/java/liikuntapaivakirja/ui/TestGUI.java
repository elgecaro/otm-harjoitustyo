
package liikuntapaivakirja.ui;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import liikuntapaivakirja.dao.Database;
import liikuntapaivakirja.dao.DbDiaryDao;
import liikuntapaivakirja.dao.DbUserDao;
import liikuntapaivakirja.dao.DiaryDao;
import liikuntapaivakirja.dao.UserDao;
import liikuntapaivakirja.domain.DiaryService;


public class TestGUI extends Application {
        
    private DiaryService diaryService; 
    private Scene diaryScene;
    private Scene newUserScene;
    private Scene loginScene;
    
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

    public static void main(String[] args) {
        launch(args);
    }
    
}
