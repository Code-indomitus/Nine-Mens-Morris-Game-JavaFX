package com.nmmfx.tutorial;

import com.nmmfx.board.BoardUtility;
import com.nmmfx.board.Intersection;
import com.nmmfx.moves.AdjacentMove;
import com.nmmfx.moves.FlyingMove;
import com.nmmfx.pieces.BluePiece;
import com.nmmfx.pieces.Piece;
import com.nmmfx.pieces.RedPiece;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FlyingTutorial extends GameTutorial{
    public FlyingTutorial(Stage gameAppStage, Scene tutorialScene) {
        super(gameAppStage, tutorialScene);
        this.turnLabel.setText("BLUE'S TURN");
        this.turnLabel.setTextFill(Color.DARKBLUE);
        this.infoLabel.setText("MOVE THE MEN TO FORM A MILL");
    }
    /**
     * Sets up the unique tutorial environment for teaching the Flying mechanic of the game
     */
    @Override
    protected void tutorial() {
        Intersection startingIntersection = (Intersection) boardTiles[2][6];
        startingIntersection.setFill(Color.YELLOW);
        startingIntersection.setOpacity(0.5);

        // destination intersection at coordinate (6, 10)
        Intersection destinationIntersection = (Intersection) boardTiles[10][6];
        destinationIntersection.setFill(Color.LAWNGREEN);
        destinationIntersection.setOpacity(0.5);

        // Manually place pieces at intersections to setup board for tutorial
        Intersection intersection1 = (Intersection) boardTiles[6][0];
        RedPiece redPiece1 = new RedPiece(intersection1.getTileX(), intersection1.getTileY());
        this.piecesGroup.getChildren().add(redPiece1);

        Intersection intersection2 = (Intersection) boardTiles[12][0];
        RedPiece redPiece2 = new RedPiece(intersection2.getTileX(), intersection2.getTileY());
        this.piecesGroup.getChildren().add(redPiece2);

        Intersection intersection3 = (Intersection) boardTiles[10][2];
        BluePiece bluePiece1 = new BluePiece(intersection3.getTileX(), intersection3.getTileY());
        this.piecesGroup.getChildren().add(bluePiece1);

        Intersection intersection4 = (Intersection) boardTiles[6][4];
        BluePiece bluePiece2 = new BluePiece(intersection4.getTileX(), intersection4.getTileY());
        this.piecesGroup.getChildren().add(bluePiece2);

        Intersection intersection5 = (Intersection) boardTiles[0][12];
        RedPiece redPiece3 = new RedPiece(intersection5.getTileX(), intersection5.getTileY());
        this.piecesGroup.getChildren().add(redPiece3);

        Intersection intersection6 = (Intersection) boardTiles[12][12];
        RedPiece redPiece4 = new RedPiece(intersection6.getTileX(), intersection6.getTileY());
        this.piecesGroup.getChildren().add(redPiece4);

        // Setup logic for the piece that is to be moved
        BluePiece mainPiece = new BluePiece(startingIntersection.getTileX(), startingIntersection.getTileY());
        mainPiece.setIntersection(startingIntersection);
        mainPiece.setMoveType(new FlyingMove());
        this.piecesGroup.getChildren().add(mainPiece);

        // moving logic for the main piece to be moved
        mainPiece.setUpMovement();
        mainPiece.setOnMouseReleased(event -> {
            // Getting new coordinates of dragged piece
            mainPiece.setTranslateX(Piece.PIECE_OFFSET);
            mainPiece.setTranslateY(Piece.PIECE_OFFSET);
            int newX = BoardUtility.toBoard(mainPiece.getLayoutX());
            int newY = BoardUtility.toBoard(mainPiece.getLayoutY());

            // Checks if the piece is dragged to a valid location on the board, then places it
            if ((newX >= 0 && newX <= 12) && (newY >= 0 && newY <= 12)) {
                if (BoardUtility.isCoordinateIntersection(newX, newY)) {
                    Intersection intersection = (Intersection) boardTiles[newX][newY];
                    if (newX == destinationIntersection.getTileX() && newY == destinationIntersection.getTileY()) {
                        // Setup for when the player does what they are told and completes the tutorials task
                        mainPiece.executeMove(intersection, newX, newY);
                        startingIntersection.setOpacity(0);
                        mainPiece.freezePiece();
                        this.turnLabel.setText("RED'S TURN");
                        this.turnLabel.setTextFill(Color.DARKRED);
                        this.infoLabel.setText("WELL DONE! FLYING COMPLETED!");
                        this.infoLabel.setTextFill(Color.DARKVIOLET);
                        infoLabel.relocate(125, 10);

                        Media media = new Media(getClass().getResource("/com/nmmfx/well_done.wav").toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.play();
                    }
                    else {
                        mainPiece.cancelMove(); // Pieces cannot overlap on same intersection
                    }
                }
                else {
                    mainPiece.cancelMove();
                }
            }
            else {
                mainPiece.cancelMove();
            }

        });

    }
    /**
     * Creates the image for displaying instructions for the Piece Flying tutorial
     * @return ImageView container for image of instructions
     */
    @Override
    protected ImageView createInstructions() {
        ImageView flyingInstructions = new ImageView(new Image( "flying_tutorial.png" ));
        flyingInstructions.setTranslateX(550);
        flyingInstructions.setTranslateY(25);

        return flyingInstructions;
    }
}