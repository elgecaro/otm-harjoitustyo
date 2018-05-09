
package liikuntapaivakirja.ui;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.application.Application;
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
import javafx.event.ActionEvent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import liikuntapaivakirja.dao.DiaryEntryDao;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;


public class Main extends Application {
        
    private DiaryService diaryService; 
    private Scene startScene;
    private Scene newUserScene;
    private Scene loginScene;
    private Scene loggedInScene;
    private Scene allEntriesScene;
    private Scene statisticsScene;
    private VBox diaryEntryNodes;
    private VBox allDiaryNodes;
    private VBox userHighscoreNodes;
    private VBox highscoreNodes;
    private VBox weeklyPoints;
    private VBox weeklyGoal;
    private VBox allWeekPointsNodes;
    private Label errorMessage;
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties")); 
        String databaseAddress = properties.getProperty("databaseAddress");
        
        Database database = new Database(databaseAddress);
        Connection conn = database.getConnection();
        database.checkForTables(conn, database);
        UserDao userdao = new DbUserDao(database);
        DiaryEntryDao diarydao = new DbDiaryEntryDao(database);
        diaryService = new DiaryService(diarydao, userdao);
    }
    
    @Override
    public void start(Stage primaryStage) {
        // start scene
        
        Label loginText = new Label("Tervetuloa liikuntapäiväkirjaan!");
        loginText.setFont((Font.font(null, FontWeight.BOLD, 14)));
        loginText.setTranslateY(-40);
        Button loginButton = new Button("kirjaudu sisään");
        Button createButton = new Button("luo uusi käyttäjä");
        createButton.setTranslateY(35);
        
        loginButton.setOnAction((ActionEvent event)-> {
            loginUser(primaryStage);
        });
             
        createButton.setOnAction(e-> {
            createUser(primaryStage);
        });
        
        StackPane startPane = new StackPane();
        startPane.getChildren().addAll(loginText, loginButton, createButton);
   
        startScene = new Scene(startPane, 250, 180);
        primaryStage.setTitle("Liikuntapäiväkirja");
        primaryStage.setScene(startScene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    
    public void loginUser(Stage primaryStage) {
        // login scene
        
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
                loginMessage.setText("Kirjautuminen ei onnistunut");      
                loginMessage.setTextFill(Color.RED);
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

        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(10, 10, 10, 10));
                       
        loginScene = new Scene(loginPane, 250, 180);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
    
    public void createUser(Stage primaryStage) {
        // new user scene
        
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
                    loginUser(primaryStage);
                } else {
                    loginMessage.setText("Käyttäjänimi on jo käyössä");
                    loginMessage.setTextFill(Color.RED);
                }
            } catch (Exception ex) {
                loginMessage.setText("Käyttäjän luominen ei onnistunut");
                loginMessage.setTextFill(Color.RED);
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

        createUserPane.setHgap(10);
        createUserPane.setVgap(10);
        createUserPane.setPadding(new Insets(10, 10, 10, 10));
                       
        newUserScene = new Scene(createUserPane, 250, 180);
        primaryStage.setScene(newUserScene);
        primaryStage.show();
    }
    
    public void loggedIn(Stage primaryStage, String username) {
        // logged in scene
        
        BorderPane borderPane = new BorderPane();   
        borderPane.setTop(getTopBox(primaryStage, username));
        borderPane.setBottom(errorMessage);
        
        GridPane createPane = createEntry(primaryStage);

        ScrollPane entriesScrollbar = new ScrollPane();
        entriesScrollbar.setPrefViewportHeight(400);   
        entriesScrollbar.setPrefViewportWidth(500); 
        entriesScrollbar.setMaxSize(700, 600);

        Label entriesLabel = new Label("15 viimeisintä merkintää:");
        entriesLabel.setFont((Font.font(null, FontWeight.BOLD, 14)));
        diaryEntryNodes = new VBox(10);
        diaryEntryNodes.setMinWidth(300);
        diaryEntryNodes.setStyle("-fx-background-color: #ffffff;");
        
        redrawDiaryList();
        
        entriesScrollbar.setContent(diaryEntryNodes);
        entriesScrollbar.setStyle("-fx-background: rgb(255,255,255);");
        
        Button viewAll = new Button("kaikki merkinnät");
        Button viewStatistics = new Button("tilastoja");
        
        viewAll.setOnAction(ev-> {
            allEntriesScene(primaryStage, username);
        });
        
        viewStatistics.setOnAction(ev-> {
            statisticsScene(primaryStage, username);
        });
        
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        buttonBox.getChildren().addAll(viewAll, viewStatistics);
        
        Label addExerciseLabel = new Label("Lisää uusi merkintä:");
        addExerciseLabel.setFont((Font.font(null, FontWeight.BOLD, 14)));
        
        VBox allEntries = new VBox();
        allEntries.setSpacing(10);
        allEntries.setPadding(new Insets(20));
        allEntries.setStyle("-fx-background-color: #f4f8ff;");
        allEntries.getChildren().addAll(addExerciseLabel, createPane, entriesLabel, entriesScrollbar, buttonBox);
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
        highscoreNodes.setMaxWidth(220);
        highscoreNodes.setMinWidth(100);
        redrawHighscoreList();
        highscores.getChildren().addAll(highscoreLabel, highscoreNodes);
        
        VBox allHighscores = new VBox();
        allHighscores.setMaxWidth(300);
        Label allHighscoresLabel = new Label("Tuloslistat");
        allHighscoresLabel.setFont((Font.font(null, FontWeight.BOLD, 14)));
        allHighscores.setSpacing(10);
        allHighscores.setPadding(new Insets(20, 10, 10, 10));
        allHighscores.getChildren().addAll(allHighscoresLabel, userHighscores, highscores);
        borderPane.setRight(allHighscores);
        
        Label infoText = new Label("Jokaisesta liikuntatunnista ansaitset 10 pistettä (eli esim. 2.5 tuntia = 25 pistettä)");
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
        allPointsAndGoals.setPadding(new Insets(20, 10, 10, 10));
        allPointsAndGoals.getChildren().addAll(infoText, weeklyPoints, weeklyGoal, goalFieldAndButton, goalLabel);
        borderPane.setLeft(allPointsAndGoals);
        
        newGoalButton.setOnAction(ev-> {
            String goalS = newGoalField.getText();

            if (diaryService.isDouble(goalS) != true) {
                goalLabel.setText("Muoto väärä");
                goalLabel.setTextFill(Color.RED);
            } else {
                double goal = Double.parseDouble(goalS);
                diaryService.createWeeklyGoal(goal);
                goalLabel.setText("");
                newGoalField.clear();
                redrawWeeklyGoal();
            }
        });
        
        loggedInScene = new Scene(borderPane, 1000, 700);
        primaryStage.setScene(loggedInScene);
        primaryStage.centerOnScreen();
        primaryStage.show(); 
    }
    
    public void allEntriesScene(Stage primaryStage, String username) {
        // get all entries-scene
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getTopBox(primaryStage, username));
        borderPane.setBottom(errorMessage);

        VBox allEntries = new VBox();
        allEntries.setPadding(new Insets(10));
        Label entriesLabel = new Label("Kaikki päiväkirjamerkinnät:");
        entriesLabel.setFont((Font.font(null, FontWeight.BOLD, 16)));

        ScrollPane entriesScrollbar = new ScrollPane();
        entriesScrollbar.setPrefViewportHeight(500);   
        entriesScrollbar.setPrefViewportWidth(650); 
        entriesScrollbar.setMaxSize(620, 620);

        allDiaryNodes = new VBox(10);
        allDiaryNodes.setMinWidth(280);
        redrawAllDiaryList();
        entriesScrollbar.setStyle("-fx-background: rgb(255,255,255);");
        entriesScrollbar.setContent(allDiaryNodes);
        
        Button backButton = new Button("takaisin");
        
        backButton.setOnAction(ev-> {
            loggedIn(primaryStage, username);
        });
        
        allEntries.getChildren().addAll(entriesLabel, entriesScrollbar, backButton);
        allEntries.setStyle("-fx-background-color: #f4f8ff;");
        allEntries.setSpacing(10);
        borderPane.setCenter(allEntries);
        
        Node weekPointsBox = createWeekPointsBox();
        redrawAllWeekPoints();
        borderPane.setRight(weekPointsBox);
        
        HBox emptyBox = new HBox();
        emptyBox.setMinSize(100, 100);
        borderPane.setLeft(emptyBox);
        
        allEntriesScene = new Scene(borderPane, 1000, 700);
        primaryStage.setScene(allEntriesScene);
        primaryStage.show();
    }
    
    public void statisticsScene(Stage primaryStage, String username) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getTopBox(primaryStage, username));
        borderPane.setBottom(errorMessage);
        
        Button backButton = new Button("takaisin");
        
        backButton.setOnAction(ev-> {
            loggedIn(primaryStage, username);
        });
        
        Node chart = createChart();
        chart.setStyle("-fx-background: rgb(255,255,255);");
        Node statistics = createStatistics();
        
        VBox centerBox = new VBox(10);
        Label statsLabel = new Label("Tilastoja:");
        statsLabel.setFont((Font.font(null, FontWeight.BOLD, 16)));
        centerBox.setPadding(new Insets(10));
        centerBox.setStyle("-fx-background-color: #f4f8ff;");
        centerBox.getChildren().addAll(statsLabel, chart, statistics, backButton);
        
        borderPane.setCenter(centerBox);
        
        Node weekPointsBox = createWeekPointsBox();
        redrawAllWeekPoints();
        borderPane.setRight(weekPointsBox);
        
        HBox emptyBox = new HBox();
        emptyBox.setMinSize(100, 100);
        borderPane.setLeft(emptyBox);
        
        statisticsScene = new Scene(borderPane, 1000, 720);
        primaryStage.setScene(statisticsScene);
        primaryStage.show();
    }
    
    public Node getTopBox(Stage primaryStage, String username) {
        Label titelText = new Label("Liikuntapäiväkirja");
        titelText.setFont((Font.font(null, FontWeight.BOLD, 30)));
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
        topBox.setPadding(new Insets(20, 20, 20, 350));
        topBox.setStyle("-fx-background-color: #d3efff;");
        
        logoutButton.setOnAction(ev-> {
            diaryService.logout();
            start(primaryStage);
        });
        
        return topBox;
    }
    
    public GridPane createEntry(Stage primaryStage) {
        
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
        newContentField.setMinHeight(80);
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
        
        createExcercise.setOnAction(e-> {
            String hourS = newHourField.getText();
            String dayS = newDayField.getText();
            String weekS = newWeekField.getText();
            String content = newContentField.getText();

            if (diaryService.isDouble(hourS) != true) {
                createMessage.setText("Tunnin muoto väärä");
                createMessage.setTextFill(Color.RED);
            } else if (diaryService.isInteger(dayS) != true) {
                createMessage.setText("Päivän muoto väärä");
                createMessage.setTextFill(Color.RED);
            } else if (diaryService.isInteger(weekS) != true) {
                createMessage.setText("Viikon muoto väärä");
                createMessage.setTextFill(Color.RED);
            } else if (content.length() > 200) {
                createMessage.setText("Liian pitkä kuvaus");
                createMessage.setTextFill(Color.RED);
            } else {
                double hour = Double.parseDouble(hourS);
                int day = Integer.parseInt(dayS);
                int week = Integer.parseInt(weekS);
                
                if (hour < 0) {
                    createMessage.setText("Tunnin muoto väärä");
                    createMessage.setTextFill(Color.RED);
                } else if (day > 7 || day < 1) {
                    createMessage.setText("Päivän muoto väärä");
                    createMessage.setTextFill(Color.RED);
                } else if (week < 1) {
                    createMessage.setText("Viikon muoto väärä");
                    createMessage.setTextFill(Color.RED);
                } else if (diaryService.createExercise(hour, day, week, content) == true) {
                    createMessage.setText("Merkinnän lisääminen onnistui");
                    createMessage.setTextFill(Color.GREEN);
                    newHourField.clear();
                    newDayField.clear();
                    newWeekField.clear();
                    newContentField.clear();
                    redrawAll();
                } else {
                    createMessage.setText("Merkinnän lisääminen ei onnistunut");
                    createMessage.setTextFill(Color.RED);
                }
            }
        });

        return createPane;
    }
    
    public Node createWeekPointsBox() {
        Label weekPointsLabel = new Label("Kaikki viikkopisteet:");
        weekPointsLabel.setFont((Font.font(null, FontWeight.BOLD, 14)));
        allWeekPointsNodes = new VBox();
        VBox weekPointsBox = new VBox(10);
        weekPointsBox.setMinSize(240, 400);
        weekPointsBox.setPadding(new Insets(20));
        weekPointsBox.getChildren().addAll(weekPointsLabel, allWeekPointsNodes);
        
        return weekPointsBox;
    }
    
    public Node createChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("viikko");
        yAxis.setLabel("pisteet");
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
                
        lineChart.setTitle("Viikkojen pisteet");
        lineChart.setLegendVisible(false);
        XYChart.Series series = new XYChart.Series();
        lineChart.setStyle("CHART_COLOR_1: #84b3ff ;");
        
        Map<Integer, Double> allPoints = new LinkedHashMap<>();
        try {
            allPoints = diaryService.getAllWeekPoints();
        } catch (Exception ex) {
            errorMessage.setText("Päiväkirjamerkintöjen hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);
        }
        
        Iterator<Map.Entry<Integer, Double>> entries = allPoints.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Double> entry = entries.next();
            series.getData().add(new XYChart.Data(entry.getKey().toString(), entry.getValue()));
        }     
        
        lineChart.getData().add(series);
        ScrollPane lineChartPane = new ScrollPane();
        lineChartPane.setMaxSize(600, 600);
        lineChartPane.setContent(lineChart);
        return lineChartPane;
    }
    
    public Node createStatistics() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        Map<Integer, Double> allPoints = new LinkedHashMap<>();
        
        try {
            allPoints = diaryService.getAllWeekPoints();
        } catch (Exception ex) {
            errorMessage.setText("Viikkopisteiden hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);
        }
        
        Iterator<Map.Entry<Integer, Double>> entries = allPoints.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Double> entry = entries.next();
            stats.addValue(entry.getValue());
        }

        double mean = stats.getMean();
        String meanS = String.valueOf(mean);
        double min = stats.getMin();
        String minS = String.valueOf(min);
        double max = stats.getMax();
        String maxS = String.valueOf(max);
        
        VBox statisticsBox = new VBox(10);
        Label meanLabel = new Label("Viikopisteiden keskiarvo: " + meanS);
        Label minLabel = new Label("Viikkopisteiden pienin arvo: " + minS);
        Label maxLabel = new Label("Viikkopisteiden suurin arvo: " + maxS); 
        statisticsBox.getChildren().addAll(meanLabel, minLabel, maxLabel);
        statisticsBox.setPadding(new Insets(10, 10, 20, 10));
        
        return statisticsBox;
    }
    
    // redraw-metodit:
    public void redrawAll() {
        redrawDiaryList();
        redrawUserHighscoreList();
        redrawHighscoreList();
        redrawWeeklyPoints();
        redrawWeeklyGoal();
    }
    
    public void redrawDiaryList() {
        diaryEntryNodes.getChildren().clear();
        List<DiaryEntry> allEntries = new ArrayList<>();
        try {
            allEntries = diaryService.get15Latest();
        } catch (Exception ex) {
            errorMessage.setText("Päiväkirjamerkintöjen hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);
        }
        
        allEntries.forEach(diaryEntry-> {
            diaryEntryNodes.getChildren().add(createDiaryEntryNode(diaryEntry));
        });
    }
    
    public void redrawAllDiaryList() {
        allDiaryNodes.getChildren().clear();
        List<DiaryEntry> allEntries = new ArrayList<>();
        
        try {
            allEntries = diaryService.getAll();
        } catch (Exception ex) {
            errorMessage.setText("Päiväkirjamerkintöjen hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);
        }
        
        allEntries.forEach(diaryEntry-> {
            allDiaryNodes.getChildren().add(createDeleteDiaryEntryNode(diaryEntry));
        });
    }
    
    public void redrawUserHighscoreList() {
        userHighscoreNodes.getChildren().clear();
        Map<Double, Integer> bestWeeks = new LinkedHashMap<>();
        
        try {
            bestWeeks = diaryService.getUsersBestWeeks();
        } catch (Exception ex) {
            errorMessage.setText("Tuloslistan hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);        
        }
        
        int number = 1;
        Iterator<Map.Entry<Double, Integer>> entries = bestWeeks.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Double, Integer> entry = entries.next();
            userHighscoreNodes.getChildren().add(createUserHighscoreNode(entry, number));
            number++;
        }
    }
    
    private void redrawHighscoreList() {
        highscoreNodes.getChildren().clear();
        Map<String, Double> allBestWeeks = new LinkedHashMap<>();
        
        try {
            allBestWeeks = diaryService.getBestWeeks();
        } catch (Exception ex) {
            errorMessage.setText("Tuloslistan hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);
        }
        
        int number = 1;
        Iterator<Map.Entry<String, Double>> entries = allBestWeeks.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Double> entry = entries.next();
            highscoreNodes.getChildren().add(createHighscoreNode(entry, number));
            number++;
        }
    }
    
    private void redrawWeeklyPoints() {
        weeklyPoints.getChildren().clear();
        Label weeklyPointsLabel = new Label("Tämän/viimeisen viikon pisteet: ");
        weeklyPointsLabel.setFont((Font.font(null, FontWeight.BOLD, 12)));
        HBox box = new HBox();
        double points = 0;
        
        try {
            points = diaryService.getPointsWeek(diaryService.getLatestWeek());
        } catch (Exception ex) {
            errorMessage.setText("Viikkopisteiden hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);
        }
        
        String pointsString = String.valueOf(points);
        Label pointsLabel = new Label(pointsString);
        
        box.getChildren().addAll(weeklyPointsLabel, pointsLabel);
        weeklyPoints.getChildren().add(box);    
    }
    
    private void redrawWeeklyGoal() {
        weeklyGoal.getChildren().clear();
        
        Label goalText = new Label("");
        Label weeklyGoalLabel = new Label("Viikkotavoitteesi: ");
        weeklyGoalLabel.setFont((Font.font(null, FontWeight.BOLD, 12)));
        HBox box = new HBox();
        double points = 0;
        
        try {
            points = diaryService.getWeeklyGoal();
        } catch (Exception ex) {
            errorMessage.setText("Viikkotavoitteen hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);
        }
        String pointsString = String.valueOf(points);
        Label label = new Label(pointsString);
        
        try {
            if (diaryService.weeklyGoalAchieved(points)) {
                goalText.setText("Tavoite saavutettu!");
                goalText.setTextFill(Color.GREEN);
            }
        } catch (Exception ex) {
            errorMessage.setText("Viikkotavoitteen saavuttamisen tiedon hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);
        }
   
        box.getChildren().addAll(weeklyGoalLabel, label);
        VBox goalBox = new VBox();
        goalBox.getChildren().addAll(box, goalText);
        weeklyGoal.getChildren().add(goalBox);     
    }
    
    private void redrawAllWeekPoints() {
        allWeekPointsNodes.getChildren().clear();
        
        Map<Integer, Double> allPoints = new LinkedHashMap<>();
        try {
            allPoints = diaryService.getAllWeekPoints();
        } catch (Exception ex) {
            errorMessage.setText("Päiväkirjamerkintöjen hakeminen ei onnistunut");      
            errorMessage.setTextFill(Color.RED);
        }
        
        Iterator<Map.Entry<Integer, Double>> entries = allPoints.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Double> entry = entries.next();
            allWeekPointsNodes.getChildren().add(createAllWeekPointsNode(entry));        
        }     
    }
    
    // Nodet
    public Node createDiaryEntryNode(DiaryEntry entry) {
        HBox box = new HBox(10);
        Label label = new Label(entry.toString());
        label.setMinHeight(28);
        label.setPrefWidth(460);
        box.setPadding(new Insets(5));
        box.setStyle("-fx-border-color: #ade1ff");
        box.setMaxWidth(460);
        label.setWrapText(true);
        box.getChildren().add(label);
        return box;
    }
    
    public Node createDeleteDiaryEntryNode (DiaryEntry entry) {
        HBox box = new HBox(10);
        Label label = new Label(entry.toString());
        label.setMinHeight(28);
        label.setPrefWidth(580);

        box.setStyle("-fx-border-color: #ade1ff");
        box.setMaxWidth(600);
        label.setWrapText(true);
        Button button = new Button("poista");
        button.setMinWidth(60);

        button.setOnAction(e-> {
            try {
                diaryService.deleteEntry(entry);
            } catch (Exception ex) {
                errorMessage.setText("Merkinnän poistaminen ei onnistunut");      
                errorMessage.setTextFill(Color.RED);
            }
            redrawDiaryList();
            redrawAllDiaryList();
            redrawAllWeekPoints();
        });
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(5));
        box.getChildren().addAll(label, spacer, button);
        return box;
    }
    
    public Node createUserHighscoreNode(Entry entry, int number) {
        HBox box = new HBox();
        Label label = new Label(number + ". Pisteet: " + entry.getValue() + ", viikko: " + entry.getKey());
        label.setPadding(new Insets(5));
        label.setWrapText(true);
        box.getChildren().add(label);
        return box;
    }
    
    public Node createHighscoreNode(Entry entry, int number) {
        HBox box = new HBox();
        Label label = new Label(number + ". Pisteet: " + entry.getValue() + ", käyttäjä: " + entry.getKey()); 
        label.setPadding(new Insets(5));
        label.setWrapText(true);
        if (entry.getKey().equals(diaryService.getUsername())) {
            label.setTextFill(Color.GREEN);
        }

        box.getChildren().add(label);
        return box;
    }
    
    public Node createAllWeekPointsNode(Entry entry) {
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