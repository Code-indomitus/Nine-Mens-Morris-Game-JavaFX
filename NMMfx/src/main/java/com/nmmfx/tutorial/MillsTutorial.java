package com.nmmfx.tutorial;

import static com.nmmfx.board.BoardUtility.toBoard;

import com.nmmfx.board.BoardUtility;
import com.nmmfx.board.Intersection;
import com.nmmfx.moves.AdjacentMove;
import com.nmmfx.pieces.BluePiece;
import com.nmmfx.pieces.Piece;
import com.nmmfx.pieces.RedPiece;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MillsTutorial extends GameTutorial{

  public MillsTutorial(Stage gameAppStage, Scene tutorialScene) {
    super(gameAppStage, tutorialScene);
    this.turnLabel.setText("RED'S TURN");
    this.infoLabel.setText("MOVE THE RED MAN");
  }
  /**
   * Sets up the unique tutorial environment for teaching the Mill forming mechanic of the game
   */
  @Override
  protected void tutorial() {
    // Where player needs to move to
    Intersection destinationIntersection = (Intersection) boardTiles[10][2];
    destinationIntersection.setFill(Color.LAWNGREEN);
    destinationIntersection.setOpacity(0.5);

    // Where player starts
    Intersection startIntersection = (Intersection) boardTiles[10][6];
    startIntersection.setFill(Color.YELLOW);
    startIntersection.setOpacity(0.5);

    // Set up scenario
    // This will be a mill//
    Intersection intersection1 = (Intersection) boardTiles[2][2];
    RedPiece redPiece1 = new RedPiece(intersection1.getTileX(), intersection1.getTileY());
    this.piecesGroup.getChildren().add(redPiece1);

    Intersection intersection3 = (Intersection) boardTiles[6][2];
    RedPiece redPiece2 = new RedPiece(intersection3.getTileX(), intersection3.getTileY());
    this.piecesGroup.getChildren().add(redPiece2);
    // This will be a mill//

    Intersection intersection5 = (Intersection) boardTiles[10][6];
    RedPiece redPiece3 = new RedPiece(intersection5.getTileX(), intersection5.getTileY());
    this.piecesGroup.getChildren().add(redPiece3);

    // This is a mill
    Intersection intersection2 = (Intersection) boardTiles[4][4];
    intersection2.setFill(Color.MAGENTA);
    intersection2.setOpacity(0.5);
    BluePiece bluePiece1 = new BluePiece(intersection2.getTileX(), intersection2.getTileY());
    this.piecesGroup.getChildren().add(bluePiece1);

    Intersection intersection4 = (Intersection) boardTiles[4][6];
    intersection4.setFill(Color.MAGENTA);
    intersection4.setOpacity(0.5);
    BluePiece bluePiece2 = new BluePiece(intersection4.getTileX(), intersection4.getTileY());
    this.piecesGroup.getChildren().add(bluePiece2);

    Intersection intersection6 = (Intersection) boardTiles[4][8];
    intersection6.setFill(Color.MAGENTA);
    intersection6.setOpacity(0.5);
    BluePiece bluePiece3 = new BluePiece(intersection6.getTileX(), intersection6.getTileY());
    this.piecesGroup.getChildren().add(bluePiece3);
    // This is a mill

    Intersection intersection7 = (Intersection) boardTiles[10][10];
    BluePiece bluePiece4 = new BluePiece(intersection7.getTileX(), intersection7.getTileY());
    intersection7.setPiece(bluePiece4);
    bluePiece4.setIntersection(intersection7);
    this.piecesGroup.getChildren().add(bluePiece4);

    // Piece behaviour
    Piece mainPiece = redPiece3;
    mainPiece.setMoveType(new AdjacentMove());
    mainPiece.setIntersection(startIntersection);
    mainPiece.setUpMovement();

    mainPiece.setOnMouseReleased(event -> {
      // Getting new coordinates of dragged piece
      mainPiece.setTranslateX(Piece.PIECE_OFFSET);
      mainPiece.setTranslateY(Piece.PIECE_OFFSET);
      int newX = toBoard(mainPiece.getLayoutX());
      int newY = toBoard(mainPiece.getLayoutY());

      // Checks if the piece is dragged to a valid location on the board, then places it
      if ((newX == 10) && (newY == 2)) {
        if (BoardUtility.isCoordinateIntersection(newX, newY)) {
          Intersection intersection = (Intersection) boardTiles[newX][newY];
          if (intersection.hasPiece()) {
            mainPiece.cancelMove(); // Pieces cannot overlap on same intersection
          }
          else {
            // Setup for when the player does what they are told and completes the tutorials task
            mainPiece.executeMove(intersection, newX, newY);
            mainPiece.freezePiece();
            infoLabel.setText("NOW REMOVE THE BLUE PIECE");

            // Trigger remove piece scenario next
            // Where player starts (after forming a mill)
            startIntersection.setOpacity(0);
            destinationIntersection.setFill(Color.MAGENTA);
            intersection1.setFill(Color.MAGENTA);
            intersection1.setOpacity(0.5);
            intersection3.setFill(Color.MAGENTA);
            intersection3.setOpacity(0.5);

            // Where player needs to remove token
            Intersection destinationIntersectionRemove = intersection7;
            destinationIntersectionRemove.setFill(Color.RED);
            destinationIntersectionRemove.setOpacity(0.5);

            Piece removedPiece = destinationIntersectionRemove.getPiece();

            removedPiece.setOnMouseClicked(e -> {
              // Player is done with tutorial when they remove the opponent's piece successfully
              piecesGroup.getChildren().remove(removedPiece);
              infoLabel.relocate(40,10);
              infoLabel.setText("WELL DONE! MILL FORMED AND PIECE REMOVED!");
              infoLabel.setTextFill(Color.DARKVIOLET);

              Media media = new Media(getClass().getResource("/com/nmmfx/impressive.wav").toString());
              MediaPlayer mediaPlayer = new MediaPlayer(media);
              mediaPlayer.play();
            });
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
   * Creates the image for displaying instructions for the Mill forming tutorial
   * @return ImageView container for image of instructions
   */
  @Override
  protected ImageView createInstructions() {
    ImageView millsInstructions = new ImageView(new Image( "mills_tutorial.png" ));
    millsInstructions.setTranslateX(550);
    millsInstructions.setTranslateY(25);

    return millsInstructions;
  }
}
