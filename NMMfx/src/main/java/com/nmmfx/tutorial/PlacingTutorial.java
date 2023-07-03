package com.nmmfx.tutorial;

import com.nmmfx.board.Intersection;
import com.nmmfx.moves.AdjacentMove;
import com.nmmfx.pieces.BluePiece;
import com.nmmfx.pieces.RedPiece;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static com.nmmfx.GameApp.APP_WIDTH;

public class PlacingTutorial extends GameTutorial{
    public PlacingTutorial(Stage gameAppStage, Scene tutorialScene) {
        super(gameAppStage, tutorialScene);
        this.turnLabel.setText("RED'S TURN");
        this.infoLabel.setText("PLACE MEN ON THE BOARD");
    }
    /**
     * Sets up the unique tutorial environment for teaching the placing pieces mechanic of the game
     */
    @Override
    protected void tutorial() {
        // Manually place pieces at intersections to setup board for tutorial
        Intersection destinationIntersection = (Intersection) boardTiles[8][6];
        destinationIntersection.setFill(Color.LAWNGREEN);
        destinationIntersection.setOpacity(0.5);

        Intersection intersection1 = (Intersection) boardTiles[2][6];
        RedPiece redPiece1 = new RedPiece(intersection1.getTileX(), intersection1.getTileY());
        this.piecesGroup.getChildren().add(redPiece1);

        Intersection intersection2 = (Intersection) boardTiles[4][8];
        BluePiece bluePiece1 = new BluePiece(intersection2.getTileX(), intersection2.getTileY());
        this.piecesGroup.getChildren().add(bluePiece1);

        Intersection intersection3 = (Intersection) boardTiles[6][8];
        RedPiece redPiece2 = new RedPiece(intersection3.getTileX(), intersection3.getTileY());
        this.piecesGroup.getChildren().add(redPiece2);

        Intersection intersection4 = (Intersection) boardTiles[8][8];
        BluePiece bluePiece2 = new BluePiece(intersection4.getTileX(), intersection4.getTileY());
        this.piecesGroup.getChildren().add(bluePiece2);

        Intersection intersection5 = (Intersection) boardTiles[10][6];
        RedPiece redPiece3 = new RedPiece(intersection5.getTileX(), intersection5.getTileY());
        this.piecesGroup.getChildren().add(redPiece3);

        Intersection intersection6 = (Intersection) boardTiles[8][4];
        BluePiece bluePiece3 = new BluePiece(intersection6.getTileX(), intersection6.getTileY());
        this.piecesGroup.getChildren().add(bluePiece3);


        destinationIntersection.setOnMouseClicked(event -> {
            // does the needfull to let the user know when they have completed the tutorial successfully
            RedPiece redPiece = new RedPiece(destinationIntersection.getTileX(), destinationIntersection.getTileY());
            this.piecesGroup.getChildren().add(redPiece);
            this.infoLabel.setText("WELL DONE! PLACING COMPLETED!");
            this.infoLabel.setTextFill(Color.DARKVIOLET);
            infoLabel.relocate(125, 10);
            this.turnLabel.setText("BLUE'S TURN");
            this.turnLabel.setTextFill(Color.DARKBLUE);

            Media media = new Media(getClass().getResource("/com/nmmfx/well_done.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });
    }
    /**
     * Creates the image for displaying instructions for the piece placing tutorial
     * @return ImageView container for image of instructions
     */
    @Override
    protected ImageView createInstructions() {
        ImageView placingInstructions = new ImageView(new Image( "placing_tutorial.png" ));
        placingInstructions.setTranslateX(550);
        placingInstructions.setTranslateY(25);

        return placingInstructions;
    }

}
