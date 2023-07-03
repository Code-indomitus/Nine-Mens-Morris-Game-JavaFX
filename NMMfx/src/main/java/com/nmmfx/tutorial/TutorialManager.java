package com.nmmfx.tutorial;

import com.nmmfx.GameApp;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TutorialManager {
    // Instance of app stage to change scene
    private Stage gameAppStage;
    // Instance of main menu scene to go back to the main menu
    private Scene mainMenuScene;

    private Scene tutorialScene;

    // Instance to itself
    private static TutorialManager tutorialManagerInstance;

    private TutorialManager(Stage gameAppStage, Scene mainMenuScene) {

        this.gameAppStage = gameAppStage;
        this.mainMenuScene = mainMenuScene;
        createTutorialScene();
    }

    /**
     * Get instance method that only allows a single instance of the TutorialManager class in the system
     * @param gameAppStage the stage of the app
     * @param mainMenuScene the scene for the main menu
     * @return the TutorialManager instance
     */
    public static TutorialManager getInstance(Stage gameAppStage, Scene mainMenuScene) {
        if (tutorialManagerInstance == null) {
            tutorialManagerInstance = new TutorialManager(gameAppStage, mainMenuScene);
        }
        return tutorialManagerInstance;
    }

    /**
     * Returns the tutorial scene
     * @return
     */
    public Scene getTutorialScene() {
        return this.tutorialScene;
    }

    /**
     * Creates the UI scene for the tutorial
     * @return the tutorial scene
     */
    private Scene createTutorialScene() {
        Pane tutorialPane = new Pane();
        tutorialPane.setPrefSize(GameApp.APP_WIDTH, GameApp.APP_HEIGHT);
        tutorialPane.getChildren().add(createBackground());
        tutorialPane.getChildren().add(createTitle());

        Button menuButton = new Button("MAIN MENU");
        menuButton.setTextFill(Color.FIREBRICK);
        menuButton.setFont(Font.font(14));
        menuButton.setOnMouseClicked(event -> {
            this.gameAppStage.setScene(this.mainMenuScene);
        });
        menuButton.relocate(GameApp.APP_WIDTH - 250, 20);
        tutorialPane.getChildren().add(menuButton);
        tutorialPane.getChildren().add(createTutorialOptions());

        this.tutorialScene = new Scene(tutorialPane);
        return tutorialScene;
    }

    /**
     * creates the background for the tutorial scene
     * @return background in the form of an ImageView container
     */
    private ImageView createBackground() {
        Image bgImage = new Image( "game_background.png" );
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(GameApp.APP_WIDTH);
        bgView.setFitHeight(GameApp.APP_HEIGHT);
        return bgView;
    }

    /**
     * Creates the title container for the tutorial scene
     * @return the title in the form of a stackpane
     */
    private StackPane createTitle(){

        StackPane title = new StackPane();
        title.setAlignment(Pos.TOP_CENTER);
        title.setTranslateX(285);
        title.setTranslateY(40);

        ImageView titleBg = new ImageView(new Image( "tutorial_logo.png" ));
        title.getChildren().add(titleBg);

        return title;
    }

    /**
     * Creates the container containing all the options for the  tutorial and provides access to the various tutorials
     * @return a vertical box container containing all the necessary button options for tutorials
     */
    private VBox createTutorialOptions(){

        // Button background image
        BackgroundImage backgroundImage = new BackgroundImage(new Image("button_background.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        // Button font
        Font font = Font.font("Arial", FontWeight.BOLD, 20);

        // CSS styling for all the buttons
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

        // Create the vertical box container for all the buttons
        VBox options = new VBox();

        // Creates all the necessary button UI for the different tutorials
        Button placingButton = new Button("Placing Pieces");
        placingButton.setStyle(textColor);
        placingButton.setFont(font);
        placingButton.setBackground(background);
        placingButton.setPrefSize(240, 50);
        placingButton.setOnMouseEntered(e -> { placingButton.setStyle(hoverOn);});
        placingButton.setOnMouseExited(e -> {placingButton.setStyle(hoverOff);});

        Button movingButton = new Button("Moving Pieces");
        movingButton.setStyle(textColor);
        movingButton.setFont(font);
        movingButton.setPrefSize(240, 50);
        movingButton.setBackground(background);
        movingButton.setOnMouseEntered(e -> { movingButton.setStyle(hoverOn);});
        movingButton.setOnMouseExited(e -> {movingButton.setStyle(hoverOff);});

        Button flyingButton = new Button("Flying Pieces");
        flyingButton.setStyle(textColor);
        flyingButton.setFont(font);
        flyingButton.setPrefSize(240, 50);
        flyingButton.setBackground(background);
        flyingButton.setOnMouseEntered(e -> { flyingButton.setStyle(hoverOn);});
        flyingButton.setOnMouseExited(e -> {flyingButton.setStyle(hoverOff);});

        Button millFormingButton = new Button("Forming Mills");
        millFormingButton.setStyle(textColor);
        millFormingButton.setFont(font);
        millFormingButton.setPrefSize(240, 50);
        millFormingButton.setBackground(background);
        millFormingButton.setOnMouseEntered(e -> { millFormingButton.setStyle(hoverOn);});
        millFormingButton.setOnMouseExited(e -> {millFormingButton.setStyle(hoverOff);});

        Button blockingWinButton = new Button("Win: By Blocking");
        blockingWinButton.setStyle(textColor);
        blockingWinButton.setFont(font);
        blockingWinButton.setPrefSize(240, 50);
        blockingWinButton.setBackground(background);
        blockingWinButton.setOnMouseEntered(e -> { blockingWinButton.setStyle(hoverOn);});
        blockingWinButton.setOnMouseExited(e -> {blockingWinButton.setStyle(hoverOff);});

        Button lessPiecesWinButton = new Button("Win: 2 Pieces Left");
        lessPiecesWinButton.setStyle(textColor);
        lessPiecesWinButton.setFont(font);
        lessPiecesWinButton.setPrefSize(240, 50);
        lessPiecesWinButton.setBackground(background);
        lessPiecesWinButton.setOnMouseEntered(e -> { lessPiecesWinButton.setStyle(hoverOn);});
        lessPiecesWinButton.setOnMouseExited(e -> {lessPiecesWinButton.setStyle(hoverOff);});
        
        options.setTranslateX(440);
        options.setTranslateY(185);
        options.setSpacing(15);

        // Functionality for placingButton
        placingButton.setOnMouseClicked(event -> {
            PlacingTutorial placingTutorial = new PlacingTutorial(this.gameAppStage, this.tutorialScene);
            this.gameAppStage.setScene(placingTutorial.createGameTutorialScene());
        });

        // Functionality for movingButton
        movingButton.setOnMouseClicked(event -> {
            MovingTutorial movingTutorial = new MovingTutorial(this.gameAppStage, this.tutorialScene);
            this.gameAppStage.setScene(movingTutorial.createGameTutorialScene());
        });

        // Functionality for flyingButton
        flyingButton.setOnMouseClicked(event -> {
            FlyingTutorial flyingTutorial = new FlyingTutorial(this.gameAppStage, this.tutorialScene);
            this.gameAppStage.setScene(flyingTutorial.createGameTutorialScene());
        });

        // Functionality for millFormingButton
        millFormingButton.setOnMouseClicked(event -> {
            MillsTutorial millsTutorial = new MillsTutorial(this.gameAppStage, this.tutorialScene);
            this.gameAppStage.setScene(millsTutorial.createGameTutorialScene());
        });

        // Functionality for blockingWinButton
        blockingWinButton.setOnMouseClicked(event -> {
            WinBlockingTutorial winBlockingTutorial = new WinBlockingTutorial(this.gameAppStage, this.tutorialScene);
            this.gameAppStage.setScene(winBlockingTutorial.createGameTutorialScene());
        });

        // Functionality for millFormingButton
        lessPiecesWinButton.setOnMouseClicked(event -> {
            WinPiecesTutorial winPiecesTutorial = new WinPiecesTutorial(this.gameAppStage, this.tutorialScene);
            this.gameAppStage.setScene(winPiecesTutorial.createGameTutorialScene());
        });

        // Add all the buttons to the vertical box container
        options.getChildren().addAll(placingButton, movingButton, millFormingButton, flyingButton, blockingWinButton, lessPiecesWinButton);

        return options;
    }
}
