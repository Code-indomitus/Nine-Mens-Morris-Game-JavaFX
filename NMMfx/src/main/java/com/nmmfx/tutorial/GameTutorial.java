package com.nmmfx.tutorial;

import com.nmmfx.GameApp;
import com.nmmfx.board.BoardTile;
import com.nmmfx.board.BoardUtility;
import com.nmmfx.board.Intersection;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.nmmfx.GameApp.APP_HEIGHT;
import static com.nmmfx.GameApp.APP_WIDTH;
import static com.nmmfx.game.Game.*;

public abstract class GameTutorial {
    protected Group tilesGroup;
    protected Group piecesGroup;

    // Instance of main app stage to set the scene back to tutorial main page
    private Stage gameAppStage;

    // Acces to the main tutorial scene so that the back button can work
    private Scene tutorialScene;

    // Labels to inform the user during tutorial
    protected Label infoLabel;
    protected Label turnLabel;

    protected ArrayList<Intersection> intersections;

    protected BoardTile[][] boardTiles = new BoardTile[BOARD_WIDTH][BOARD_HEIGHT];


    public GameTutorial(Stage gameAppStage, Scene tutorialScene) {
        this.gameAppStage = gameAppStage;
        this.tutorialScene = tutorialScene;

        this.tilesGroup = new Group();
        this.piecesGroup = new Group();

        this.intersections = new ArrayList<Intersection>();

        this.infoLabel = new Label("");
        this.infoLabel.setFont(Font.font(24));
        this.infoLabel.setTextFill(Color.HONEYDEW);

        this.turnLabel = new Label("");
        this.turnLabel.setTextFill(Color.DARKRED);
        this.turnLabel.setFont(Font.font(22));

        infoLabel.relocate(APP_WIDTH / 2 - 400, 10);
        turnLabel.relocate(APP_WIDTH / 2 - 325, APP_HEIGHT - 80);
    }

    /**
     * creates the board UI grid and initial setup similar to normal game classes
     * @return the node that represents the game board
     */
    protected Parent createBoardGrid() {
        Pane boardPane = new Pane();

        StackPane root = new StackPane();
        Image boardImage = new Image( "9mm_board.png" );
        ImageView boardView = new ImageView(boardImage);


        boardPane.setPrefSize(BOARD_SIZE, BOARD_SIZE);

        boardPane.getChildren().addAll(tilesGroup, piecesGroup);

        root.getChildren().add(boardView);
        root.getChildren().add(boardPane);
        root.setAlignment(Pos.TOP_LEFT);

        // Build the board UI in a grid form using boardTile and Intersections
        for (int x = 0; x < BOARD_HEIGHT; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                if (BoardUtility.isCoordinateIntersection(x, y)) {
                    // Create an intersection if coordinate is an intersection coordinate
                    Intersection intersection = new Intersection(x,y);
                    intersections.add(intersection);
                    tilesGroup.getChildren().add(intersection);
                    boardTiles[x][y] = intersection;
                }
                else {
                    BoardTile tile = new BoardTile(x, y);
                    tilesGroup.getChildren().add(tile);
                    boardTiles[x][y] = tile;
                }
            }
        }
        return root;

    }

    /**
     * creates the scene for the specific tutorial
     * @return the game tutorial scene
     */
    public Scene createGameTutorialScene() {
        Pane gameTutorialPane = new Pane();

        Parent gameBoardNode = createBoardGrid();
        gameTutorialPane.getChildren().add(createBackground());
        gameTutorialPane.getChildren().add(createBackButton());
        gameTutorialPane.getChildren().add(gameBoardNode);
        gameTutorialPane.getChildren().add(this.infoLabel);
        gameTutorialPane.getChildren().add(this.turnLabel);
        gameBoardNode.relocate(50, 50);

        gameTutorialPane.getChildren().add(createInstructions());

        // call tutorial to initialise the game logic that the user has to follow
        tutorial();

        Scene gameTutorialScene = new Scene(gameTutorialPane);

        return gameTutorialScene;
    }

    /**
     * Creates the button for going back to the tutorial page
     * @return button that takes back to tutorial page
     */
    protected Button createBackButton() {
        Button backButton = new Button("BACK TO TUTORIAL");
        backButton.setTextFill(Color.FIREBRICK);
        backButton.setFont(Font.font(14));
        backButton.setOnMouseClicked(event -> {
            this.gameAppStage.setScene(this.tutorialScene);
        });
        backButton.relocate(APP_WIDTH - 250, 20);

        return backButton;
    }

    /**
     * Sets up the unique tutorial environment
     */
    protected abstract void tutorial();

    /**
     * Creates the image for displaying instructions for the particular tutorial
     * @return ImageView container for image of instructions
     */
    protected abstract ImageView createInstructions();

    /**
     * Creates the background for the tutorial just like the other parts of the app
     * @return ImageView container that has the background
     */
    protected ImageView createBackground() {
        Image bgImage = new Image( "game_background.png" );
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(APP_WIDTH);
        bgView.setFitHeight(APP_HEIGHT);
        return bgView;
    }




}


