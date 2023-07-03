package com.nmmfx;


import com.nmmfx.computer.ComputerGame;
import com.nmmfx.game.PlayerGame;
import com.nmmfx.tutorial.TutorialManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameApp extends Application {

    public static final int APP_WIDTH = 1138;
    public static final int APP_HEIGHT = 660;
    private Scene mainMenuScene = null;

    private Stage gameAppStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.gameAppStage = primaryStage;
        primaryStage.setTitle("Nine Men's Morris");
        Image gameIcon = new Image("game_app_icon.png");
        primaryStage.getIcons().add(gameIcon);
        primaryStage.setResizable(false);
        primaryStage.setWidth(APP_WIDTH);
        primaryStage.setHeight(APP_HEIGHT);
        primaryStage.setScene(createMainMenu());
        primaryStage.show();
    }

    /**
     * Create the Player vs Player Game scene that contains the playable board game and includes
     * the main menu and hints option.
     * @return the game scene
     */
    private Scene createPlayerGameScene() {
        PlayerGame playerGame = new PlayerGame();

        Label infoLabel = playerGame.getInfoLabel();
        Label turnLabel = playerGame.getTurnLabel();

        Button menuButton = new Button("MAIN MENU");
        menuButton.setTextFill(Color.FIREBRICK);
        menuButton.setFont(Font.font(14));
        menuButton.setOnMouseClicked(event -> {
            this.gameAppStage.setScene(createMainMenu());
        });
        menuButton.relocate(APP_WIDTH - 250, 20);

        Button hintsButton = new Button("HINTS");
        hintsButton.relocate(APP_WIDTH - 140, 20);
        hintsButton.setFont(Font.font(14));
        hintsButton.setTextFill(Color.FIREBRICK);
        hintsButton.setStyle("-fx-background-color: white;");

        // Toggle hints feature button
        hintsButton.setOnMouseClicked(e -> {
            playerGame.toggleHints();
            if (hintsButton.getStyle().equals("-fx-background-color: white;")) {
                hintsButton.setStyle("-fx-background-color: #39FF14;"); // Change to green
                hintsButton.setTextFill(Color.DARKBLUE);
            } else {
                hintsButton.setStyle("-fx-background-color: white;"); // Change back to white/red
                hintsButton.setTextFill(Color.FIREBRICK);
            }
        });

        infoLabel.relocate(APP_WIDTH / 2 - 170, 10);
        turnLabel.relocate(APP_WIDTH / 2 - 70, APP_HEIGHT - 80);;


        Pane gamePane = new Pane();
        // Create the game board node to be displayed on the scene and build the game scene
        Parent gameBoardNode = playerGame.createBoardGrid();
        gamePane.getChildren().add(createBackground());
        gamePane.getChildren().add(menuButton);
        gamePane.getChildren().add(hintsButton);
        gamePane.getChildren().add(gameBoardNode);
        gamePane.getChildren().add(infoLabel);
        gamePane.getChildren().add(turnLabel);
        gameBoardNode.relocate(300, 50);
        Scene gameScene = new Scene(gamePane);

        return gameScene;
    }

    /**
     * Create the Player vs Computer Game scene that contains the playable board game and includes
     * the main menu and hints option.
     * @return the game scene
     */
    private Scene createComputerGameScene() {
        ComputerGame computerGame = new ComputerGame();

        Label infoLabel = computerGame.getInfoLabel();
        Label turnLabel = computerGame.getTurnLabel();

        Button menuButton = new Button("MAIN MENU");
        menuButton.setTextFill(Color.FIREBRICK);
        menuButton.setFont(Font.font(14));
        menuButton.setOnMouseClicked(event -> {
            this.gameAppStage.setScene(createMainMenu());
        });
        menuButton.relocate(APP_WIDTH - 250, 20);

        Button hintsButton = new Button("HINTS");
        hintsButton.relocate(APP_WIDTH - 140, 20);
        hintsButton.setFont(Font.font(14));
        hintsButton.setTextFill(Color.FIREBRICK);
        hintsButton.setStyle("-fx-background-color: white;");

        // Toggle hints feature
        hintsButton.setOnMouseClicked(e -> {
            computerGame.toggleHints();
            if (hintsButton.getStyle().equals("-fx-background-color: white;")) {
                hintsButton.setStyle("-fx-background-color: #39FF14;"); // Change to green
                hintsButton.setTextFill(Color.DARKBLUE);
            } else {
                hintsButton.setStyle("-fx-background-color: white;"); // Change back to white/red
                hintsButton.setTextFill(Color.FIREBRICK);
            }
        });

        infoLabel.relocate(APP_WIDTH / 2 - 170, 10);
        turnLabel.relocate(APP_WIDTH / 2 - 95, APP_HEIGHT - 80);;

        Pane gamePane = new Pane();
        // Create the game board node to be displayed on the scene and build the game scene
        Parent gameBoardNode = computerGame.createBoardGrid();
        gamePane.getChildren().add(createBackground());
        gamePane.getChildren().add(menuButton);
        gamePane.getChildren().add(hintsButton);
        gamePane.getChildren().add(gameBoardNode);
        gamePane.getChildren().add(infoLabel);
        gamePane.getChildren().add(turnLabel);
        gameBoardNode.relocate(300, 50);
        Scene gameScene = new Scene(gamePane);

        return gameScene;
    }

    /**
     *  Returns the main menu scene
     * @return the main menu scene
     */
    private Scene createMainMenu() {
        if (this.mainMenuScene != null) {
            return mainMenuScene;
        }

        Pane mainMenuPane = new Pane();
        mainMenuPane.setPrefSize(APP_WIDTH, APP_HEIGHT);
        mainMenuPane.getChildren().add(createBackground());
        mainMenuPane.getChildren().add(createTitle());
        mainMenuPane.getChildren().add(createOptions());

        Scene mainMenuScene = new Scene(mainMenuPane);
        this.mainMenuScene = mainMenuScene;
        return mainMenuScene;
    }

    /**
     * creates the background image for the main menu
     * @return imageview object for the background
     */
    private ImageView createBackground() {
        Image bgImage = new Image( "game_background.png" );
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(APP_WIDTH);
        bgView.setFitHeight(APP_HEIGHT);
        return bgView;
    }

    /**
     * create a vertical box that contains buttons that represent the main menu options
     * @return the vertical box node that contains all main menu options
     */
    private VBox createOptions(){

        // Set the button background
        BackgroundImage backgroundImage = new BackgroundImage(new Image("button_background.png"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        // Button font
        Font font = Font.font("Arial", FontWeight.BOLD, 20);

        // CSS styling
        String textColor = "-fx-text-fill: #FFF5C4; ";

        String hoverOn = "-fx-border-color: #FFF5C4; " +
                         "-fx-border-width: 3px; " +
                         "-fx-text-fill: #FFF5C4; " +
                         "-fx-padding: 11px 21px; " +
                         "-fx-border-radius: 10px;";

        String hoverOff = "-fx-border: none; " +
                         "-fx-text-fill: #FFF5C4; " +
                         "-fx-padding: 10px 20px; " +
                         "-fx-border-radius: 10px;";

        // Create the container for all the buttons
        VBox options = new VBox();

        // Create all the functionality and appearance for all the buttons in main menu
        Button playerVsPlayerButton = new Button("Player Vs Player");
        playerVsPlayerButton.setStyle(textColor);
        playerVsPlayerButton.setFont(font);
        playerVsPlayerButton.setBackground(background);
        playerVsPlayerButton.setPrefSize(240, 50);
        playerVsPlayerButton.setOnMouseEntered(e -> { playerVsPlayerButton.setStyle(hoverOn);});
        playerVsPlayerButton.setOnMouseExited(e -> {playerVsPlayerButton.setStyle(hoverOff);});

        Button playerVsComputerButton = new Button("Player Vs Computer");
        playerVsComputerButton.setStyle(textColor);
        playerVsComputerButton.setFont(font);
        playerVsComputerButton.setPrefSize(240, 50);
        playerVsComputerButton.setBackground(background);
        playerVsComputerButton.setOnMouseEntered(e -> { playerVsComputerButton.setStyle(hoverOn);});
        playerVsComputerButton.setOnMouseExited(e -> {playerVsComputerButton.setStyle(hoverOff);});

        Button tutorialButton = new Button("Tutorial");
        tutorialButton.setStyle(textColor);
        tutorialButton.setFont(font);
        tutorialButton.setPrefSize(240, 50);
        tutorialButton.setBackground(background);
        tutorialButton.setOnMouseEntered(e -> { tutorialButton.setStyle(hoverOn);});
        tutorialButton.setOnMouseExited(e -> {tutorialButton.setStyle(hoverOff);});

        Button exitButton = new Button("Exit Game");
        exitButton.setStyle(textColor);
        exitButton.setFont(font);
        exitButton.setPrefSize(240, 50);
        exitButton.setBackground(background);
        exitButton.setOnMouseEntered(e -> { exitButton.setStyle(hoverOn);});
        exitButton.setOnMouseExited(e -> {exitButton.setStyle(hoverOff);});

        options.setTranslateX(440);
        options.setTranslateY(240);
        options.setSpacing(15);

        // Functionality for playerVsPlayerButton
        playerVsPlayerButton.setOnMouseClicked(event -> {
            this.gameAppStage.setScene(createPlayerGameScene());
        });

        // Functionality for playerVsComputerButton
        playerVsComputerButton.setOnMouseClicked(event -> {
            this.gameAppStage.setScene(createComputerGameScene());
        });

        // Functionality for tutorialButton
        tutorialButton.setOnMouseClicked(event -> {
            TutorialManager tutorialManager = TutorialManager.getInstance(this.gameAppStage, this.mainMenuScene);
            this.gameAppStage.setScene(tutorialManager.getTutorialScene());
        });
        // Functionality for exitButton
        exitButton.setOnMouseClicked(event -> {
            Platform.exit();
        });

        options.getChildren().addAll(playerVsPlayerButton,playerVsComputerButton, tutorialButton, exitButton);

        return options;
    }

    /**
     * create the title for the main menu
     * @return return the title node for the main menu
     */
    private StackPane createTitle(){
        StackPane title = new StackPane();
        title.setAlignment(Pos.TOP_CENTER);
        title.setTranslateX(285);
        title.setTranslateY(90);

        ImageView titleBg = new ImageView(new Image( "title_logo.png" ));
        title.getChildren().add(titleBg);

        return title;
    }

    /**
     * main method to run the nine men's morris game app
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}