
package liikuntapaivakirja.ui;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Iterator;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import liikuntapaivakirja.dao.Database;
import liikuntapaivakirja.dao.DbDiaryEntryDao;
import liikuntapaivakirja.dao.DbUserDao;
import liikuntapaivakirja.dao.UserDao;
import liikuntapaivakirja.domain.DiaryEntry;
import liikuntapaivakirja.domain.DiaryService;
import java.util.Properties;
import liikuntapaivakirja.dao.DiaryEntryDao;


public class Main extends Application {
    
    // Tulossa: eri näkymät omiksi luokiksi(?) ja loggedIn-koodiin enemmän metodeja
    // + toiminallisuutta, esim. käyttäjä näkee kaikkien viikkojen pisteet
        
    private DiaryService diaryService; 
    private Scene newUserScene;
    private Scene loginScene;
    private VBox diaryNodes;
    private VBox allDiaryNodes;
    private VBox userHighscoreNodes;
    private VBox highscoreNodes;
    private VBox weeklyPoints;
    private VBox weeklyGoal;
    private VBox allWeekPointsNodes;
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
    
        String databaseAddress = properties.getProperty("databaseAddress");

        Database database = new Database(databaseAddress);
        database.getConnection();
        if (database.tableExist((database.getConnection()), "User") == false) {
            database.createTableUser(database.getConnection(), database);
        } if (database.tableExist((database.getConnection()), "Diary") == false) {
            database.createTableDiary(database.getConnection(), database);
        }
        
        UserDao userdao = new DbUserDao(database);
        DiaryEntryDao diarydao = new DbDiaryEntryDao(database);
        diaryService = new DiaryService(diarydao, userdao);
    }
    
    @Override
    public void start(Stage primaryStage) {
        // login scene
        
        Label loginText = new Label("Tervetuloa liikuntapäiväkirjaan");
        loginText.setTranslateY(-40);
        Button loginButton = new Button("kirjaudu sisään");
        Button createButton = new Button("luo uusi käyttäjä");
        createButton.setTranslateY(30);
        
        loginButton.setOnAction((event)-> {
            try {
                loginUser(primaryStage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
            
            
        createButton.setOnAction(e-> {
            createUser(primaryStage);
        });
        
        StackPane startPane = new StackPane();
        startPane.getChildren().addAll(loginText, loginButton, createButton);
   
        Scene startScene = new Scene(startPane, 260, 150);
        
        primaryStage.setTitle("Liikuntapäiväkirja");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }
    
    public void loginUser(Stage primaryStage) throws Exception {
        Label loginMessage = new Label("");
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
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
                       
        loginScene = new Scene(loginPane);

        primaryStage.setScene(loginScene);
        primaryStage.show();

    }
    
    public void createUser(Stage primaryStage) {
        Label loginMessage = new Label("");

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

                if (newPasswordField.getText().length() < 6) {
                    loginMessage.setText("Salasana on liian lyhyt");
                    loginMessage.setTextFill(Color.RED);
                } else if (newUsernameField.getText().length() < 3) {
                    loginMessage.setText("Käyttäjänimi on liian lyhyt");
                    loginMessage.setTextFill(Color.RED);
                } else if (diaryService.createUser(username, password)) {
                    loginMessage.setText("Käyttäjäsi on nyt luotu");
                    loginMessage.setTextFill(Color.GREEN);
                    loginUser(primaryStage);
                } else {
                    loginMessage.setText("Käyttäjänimi on jo käyössä");
                    loginMessage.setTextFill(Color.RED);
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
                       
        newUserScene = new Scene(createUserPane);

        primaryStage.setScene(newUserScene);
        primaryStage.show();

    }
    
    public void loggedIn(Stage primaryStage, String username) throws Exception {
        BorderPane borderPane = new BorderPane();
                
        Label titelText = new Label("Liikuntapäiväkirja");
        titelText.setFont((Font.font(null, FontWeight.BOLD, 20)));
        Label loginText = new Label("Olet kirjautunut sisään käyttäjällä " + username);
        loginText.setFont((Font.font(null, FontWeight.NORMAL, 14)));
        
        VBox welcomeBox = new VBox();
        welcomeBox.setSpacing(10);

        welcomeBox.getChildren().addAll(titelText, loginText);

        Button logoutButton = new Button("kirjaudu ulos");
        HBox buttonBox = new HBox(logoutButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(buttonBox, Priority.ALWAYS);
        
        HBox topBox = new HBox();
        topBox.setSpacing(10);
        topBox.getChildren().addAll(welcomeBox, buttonBox);
        topBox.setPadding(new Insets(20));
        topBox.setStyle("-fx-background-color: #d3efff;");
        borderPane.setTop(topBox);
        
        logoutButton.setOnAction(ev-> {
            diaryService.logout();
            start(primaryStage);
        });
        
        Label newHourLabel = new Label("Tuntia:");
        TextField newHourField = new TextField();
        newHourField.setMaxWidth(50);
        Label newDayLabel = new Label("Päivä:");
        TextField newDayField = new TextField();
        newDayField.setMaxWidth(50);
        Label newWeekLabel = new Label("Viikko:");
        TextField newWeekField = new TextField();
        newWeekField.setMaxWidth(50);
        Label newContentLabel = new Label("Kuvaus:");
        TextArea newContentField = new TextArea();
        newContentField.setPrefWidth(400);
        newContentField.setPrefHeight(300);
        Label createMessage = new Label("");
        Button createExcercise = new Button("lisää");
        
        GridPane createPane = new GridPane();
        createPane.setHgap(15);
        createPane.setVgap(15);
        
        createPane.add(newHourLabel, 0, 0);
        createPane.add(newHourField, 1, 0);
        createPane.add(newDayLabel, 2, 0);
        createPane.add(newDayField, 3, 0);
        createPane.add(newWeekLabel, 4, 0);
        createPane.add(newWeekField, 5, 0);
        createPane.add(newContentLabel, 0, 1);
        createPane.add(newContentField, 0, 2, 6, 1);
        createPane.add(createExcercise, 0, 3);
        createPane.add(createMessage, 0, 4, 4, 1);
        
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
            } else if (content.length() > 200) {
                createMessage.setText("Liian pitkä kuvaus");
                createMessage.setTextFill(Color.RED);
            } else {
                double hour = Double.parseDouble(hourS);
                int day = Integer.parseInt(dayS);
                int week = Integer.parseInt(weekS);
                    
                if (diaryService.createExercise(hour, day, week, content) == true) {
                    createMessage.setText("Merkinnän lisääminen onnistui");
                    createMessage.setTextFill(Color.GREEN);
                    newHourField.clear();
                    newDayField.clear();
                    newWeekField.clear();
                    newContentField.clear();
                    
                    try {
                        redrawAll();
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    createMessage.setText("Merkinnän lisääminen ei onnistunut");
                    createMessage.setTextFill(Color.RED);
                }
            }
        });
        
        ScrollPane entriesScrollbar = new ScrollPane();
        entriesScrollbar.setPrefViewportHeight(400);   
        entriesScrollbar.setPrefViewportWidth(400); 
        entriesScrollbar.setMaxSize(700, 600);

        Label entriesLabel = new Label("15 viimeisintä merkintää:");
        entriesLabel.setFont((Font.font(null, FontWeight.BOLD, 12)));
        diaryNodes = new VBox(10);
        diaryNodes.setMinWidth(280);
        diaryNodes.setStyle("-fx-background-color: #ffffff;");
        redrawDiaryList();
        entriesScrollbar.setContent(diaryNodes);
        entriesScrollbar.setStyle("-fx-background: rgb(255,255,255);");
        
        Button viewAll = new Button("näe kaikki merkinnät");
        
        viewAll.setOnAction(ev-> {
            try {
                allEntriesScene(primaryStage, username);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Label addExerciseLabel = new Label("Lisää uusi merkintä:");
        addExerciseLabel.setFont((Font.font(null, FontWeight.BOLD, 14)));
        
        VBox allEntries = new VBox();
        allEntries.setSpacing(10);
        allEntries.setPadding(new Insets(20));
        allEntries.setStyle("-fx-background-color: #f4f8ff;");

        allEntries.getChildren().addAll(addExerciseLabel, createPane, entriesLabel, entriesScrollbar, viewAll);
        borderPane.setCenter(allEntries);

        VBox userHighscores = new VBox();
        Label userHighscoreLabel = new Label("Parhaat viikkopisteeesi");
        userHighscoreLabel.setFont((Font.font(null, FontWeight.BOLD, 12)));

        userHighscoreNodes = new VBox();
        userHighscoreNodes.setMaxWidth(200);
        userHighscoreNodes.setMinWidth(100);
        redrawUserHighscoreList();
        userHighscores.getChildren().addAll(userHighscoreLabel, userHighscoreNodes);

        VBox highscores = new VBox();
        Label highscoreLabel = new Label("Parhaat viikkopisteet käyttäjien kesken");
        highscoreLabel.setFont((Font.font(null, FontWeight.BOLD, 12)));

        highscoreNodes = new VBox();
        highscoreNodes.setMaxWidth(200);
        highscoreNodes.setMinWidth(100);
        redrawHighscoreList();
        highscores.getChildren().addAll(highscoreLabel, highscoreNodes);
        
        VBox allHighscores = new VBox();
        Label allHighscoresLabel = new Label("Tuloslistat");
        allHighscoresLabel.setFont((Font.font(null, FontWeight.BOLD, 14)));

        allHighscores.setSpacing(10);
        allHighscores.setPadding(new Insets(20));
        allHighscores.getChildren().addAll(allHighscoresLabel, userHighscores, highscores);
        borderPane.setRight(allHighscores);
        
        Label infoText =new Label("Jokaisesta liikuntatunnista ansaitset 10 pistettä (eli esim. 2.5 tuntia = 25 pistettä)");
        infoText.setWrapText(true);
        
        weeklyPoints = new VBox(10);
        redrawWeeklyPoints();
        weeklyGoal = new VBox(10);
        redrawWeeklyGoal();
        
        HBox goalFieldAndButton = new HBox(10);
        TextField newGoalField = new TextField();
        newGoalField.setMaxWidth(50);
        Button newGoalButton = new Button("aseta uusi tavoite");
        goalFieldAndButton.getChildren().addAll(newGoalButton, newGoalField);
        Label goalLabel = new Label("");
        
        VBox allPointsAndGoals = new VBox();
        allPointsAndGoals.setSpacing(10);
        allPointsAndGoals.setMaxWidth(240);
        allPointsAndGoals.setAlignment(Pos.CENTER);
        allPointsAndGoals.setPadding(new Insets(10));
        allPointsAndGoals.getChildren().addAll(infoText, weeklyPoints, weeklyGoal, goalFieldAndButton, goalLabel);
        borderPane.setLeft(allPointsAndGoals);
        
        newGoalButton.setOnAction(ev-> {
            String goalS = newGoalField.getText();

            if (isInteger(goalS) != true) {
                goalLabel.setText("Muoto väärä");
                goalLabel.setTextFill(Color.RED);
            } else {
                int goal = Integer.parseInt(goalS);
                diaryService.createWeeklyGoal(goal);
                goalLabel.setText("");
                newGoalField.clear();
                try {
                    redrawWeeklyGoal();
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Scene scene = new Scene(borderPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
         
    }
    
    public void allEntriesScene(Stage primaryStage, String username) throws Exception {
        // get all entries-view
        BorderPane borderPane = new BorderPane();
        
        Label titelText = new Label("Liikuntapäiväkirja");
        titelText.setFont((Font.font(null, FontWeight.BOLD, 20)));
        Label loginText = new Label("Olet kirjautunut sisään käyttäjällä " + username);
        loginText.setFont((Font.font(null, FontWeight.NORMAL, 14)));
        
        VBox welcomeBox = new VBox();
        welcomeBox.setSpacing(10);
        welcomeBox.setPadding(new Insets(20));

        welcomeBox.getChildren().addAll(titelText, loginText);
        welcomeBox.setStyle("-fx-background-color: #d3efff;");
        borderPane.setTop(welcomeBox);
        
        //Yllä melkein sama koodi kun loggedIn alussa, muutetaan omaksi metodiksi?
        // + muutetaan ehkä pohja BorderPane:ksi, niin voidaan myös helposti listätä esim. kaikkien viikkojem tuloslista sivulle

        VBox allEntries = new VBox();
        allEntries.setPadding(new Insets(10));
        Label entriesLabel = new Label("Kaikki kirjoittamasi merkinnät");
        entriesLabel.setFont((Font.font(null, FontWeight.BOLD, 14)));

        ScrollPane entriesScrollbar = new ScrollPane();
        entriesScrollbar.setPrefViewportHeight(500);   
        entriesScrollbar.setPrefViewportWidth(600); 
        entriesScrollbar.setMaxSize(600, 600);

        allDiaryNodes = new VBox(10);
        allDiaryNodes.setMinWidth(280);
        redrawAllDiaryList();
        entriesScrollbar.setStyle("-fx-background: rgb(255,255,255);");

        entriesScrollbar.setContent(allDiaryNodes);
        Button backButton = new Button("takaisin");
        
        backButton.setOnAction(ev-> {
            try {
                loggedIn(primaryStage, username);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        allEntries.getChildren().addAll(entriesLabel, entriesScrollbar, backButton);
        allEntries.setStyle("-fx-background-color: #f4f8ff;");
        allEntries.setSpacing(10);
        borderPane.setCenter(allEntries);
        
        Label weekPointsLabel = new Label("Kaikki viikkopisteet");
        weekPointsLabel.setFont((Font.font(null, FontWeight.BOLD, 12)));
        allWeekPointsNodes = new VBox(10);
        VBox weekPointsBox = new VBox();
        weekPointsBox.getChildren().addAll(weekPointsLabel, allWeekPointsNodes);
        redrawAllWeekPoints();

        borderPane.setRight(weekPointsBox);
        
        Scene scene = new Scene(borderPane, 1000, 700);
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
    
    public void redrawAll() throws Exception {
        redrawDiaryList();
        redrawUserHighscoreList();
        redrawHighscoreList();
        redrawWeeklyPoints();
        redrawWeeklyGoal();
    }
    
    public void redrawDiaryList() throws Exception {
        diaryNodes.getChildren().clear();
        List<DiaryEntry> allEntries = diaryService.get15Latest();
        allEntries.forEach(diaryEntry->{
            diaryNodes.getChildren().add(createDiaryNode(diaryEntry));
        });
    }
    
    public void redrawAllDiaryList() throws Exception {
        allDiaryNodes.getChildren().clear();
        List<DiaryEntry> allEntries = diaryService.getAll();
        allEntries.forEach(diaryEntry->{
            allDiaryNodes.getChildren().add(createDiaryNode(diaryEntry));
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
    
    private void redrawHighscoreList() throws Exception {
        highscoreNodes.getChildren().clear();
        
        Map<String, Double> allBestWeeks = diaryService.getBestWeeks();
        int number = 1;
        Iterator<Map.Entry<String, Double>> entries = allBestWeeks.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Double> entry = entries.next();
            highscoreNodes.getChildren().add(createHighscoreNode(entry, number));
            number++;
        }
    }
    
    private void redrawWeeklyPoints() throws Exception {
        weeklyPoints.getChildren().clear();
        
        Label weeklyPointsLabel = new Label("Tämän/viimeisen viikon pisteet: ");
        weeklyPointsLabel.setFont((Font.font(null, FontWeight.BOLD, 12)));
        
        HBox box = new HBox();
        double points = diaryService.getPointsWeek(diaryService.getLatestWeek());
        String pointsString = String.valueOf(points);
        Label pointsLabel = new Label(pointsString);
        
        box.getChildren().addAll(weeklyPointsLabel, pointsLabel);
        weeklyPoints.getChildren().add(box);    
    }
    
    private void redrawWeeklyGoal() throws Exception {
        weeklyGoal.getChildren().clear();
        
        Label goalText = new Label("");
        Label weeklyGoalLabel = new Label("Viikkotavoitteesi: ");
        weeklyGoalLabel.setFont((Font.font(null, FontWeight.BOLD, 12)));

        VBox goalBox = new VBox();
        
        HBox box = new HBox();
        double points = diaryService.getWeeklyGoal();
        String pointsString = String.valueOf(points);
        Label label = new Label(pointsString);
        
        if (diaryService.getPointsWeek(diaryService.getLatestWeek()) >= points) {
            goalText.setText("Tavoite saavutettu!");
            goalText.setTextFill(Color.GREEN);
        }
        
        box.getChildren().addAll(weeklyGoalLabel, label);
        goalBox.getChildren().addAll(box, goalText);
        weeklyGoal.getChildren().add(goalBox);    
        
    }
    
    private void redrawAllWeekPoints() throws Exception {
        allWeekPointsNodes.getChildren().clear();
        
        Map<Double, Integer> allPoints = diaryService.getAllWeekPoints();
        Iterator<Map.Entry<Double, Integer>> entries = allPoints.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Double, Integer> entry = entries.next();
            allWeekPointsNodes.getChildren().add(createAllWeekPointsNode(entry));        
        }     
    }
    
        
    public Node createDiaryNode(DiaryEntry entry) {
        HBox box = new HBox(10);
        Label label = new Label(entry.toString());
        label.setMinHeight(28); 
        box.setPadding(new Insets(5));
        box.setStyle("-fx-border-color: #ade1ff");
        box.setMaxWidth(600);
        label.setWrapText(true);
        box.getChildren().add(label);
        return box;
    }
    
    public Node createUserHighscoreNode(Entry entry, int number) {
        HBox box = new HBox();
        Label label = new Label(number + ". Pisteet: " + entry.getValue() + ", viikko: " + entry.getKey());
        label.setPadding(new Insets(5));
        box.getChildren().add(label);
        return box;
    }
    
    public Node createHighscoreNode(Entry entry, int number) {
        HBox box = new HBox();
        Label label = new Label(number + ". Pisteet: " + entry.getValue() + ", käyttäjä: " + entry.getKey()); 
        label.setPadding(new Insets(5));
        box.getChildren().add(label);
        return box;
    }
    
    public Node createAllWeekPointsNode (Entry entry) {
        HBox box = new HBox();
        Label label = new Label("Viikko: " + entry.getKey() + ", pisteet: " + entry.getValue());
        label.setPadding(new Insets(5));
        box.getChildren().add(label);
        return box;
    }
          
    public static void main(String[] args) {
        launch(args);
    }


}
