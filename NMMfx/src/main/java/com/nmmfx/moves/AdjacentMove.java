package com.nmmfx.moves;

import com.nmmfx.board.BoardUtility;
import com.nmmfx.board.Intersection;
import com.nmmfx.game.PlayerGame;
import com.nmmfx.pieces.Piece;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class AdjacentMove implements MoveType {
  /**
   * Constructor for this class
   */
  public AdjacentMove(){
  }

  /**
   * Move the piece adjacently on the board to a new intersection
   * @param piece the piece to be moved
   * @param intersection the new intersection to be set in to
   * @param newX the new x coordinate so move the piece in to
   * @param newY the new y coordinate so move the piece in to
   * @return boolean, true if move succeeded, false if failed.
   */
  @Override
  public boolean move(Piece piece, Intersection intersection, int newX, int newY) {
    // Check if the intersection selected is adjacent to the selected piece's intersection
    if (BoardUtility.checkIntersectionAdjacent(piece.getIntersection(), intersection)) {

      piece.leavePreviousIntersection(); // Set previous intersection's piece to null when leaving it indicating empty
      piece.placePiece(newX, newY);
      intersection.setPiece(piece);
      piece.setIntersection(intersection);
      return true;

      // If not, cancel move and return False, meaning move action failed
    } else {
      piece.cancelMove();
      return false;
    }
  }
  /**
   * Highlights all the valid moves for a piece
   * @param piece
   * @param intersections
   */
  @Override
  public void showValidMoves(Piece piece, ArrayList<Intersection> intersections) {
    Intersection pieceInter = piece.getIntersection();
    ArrayList<Intersection> validIntersections = new ArrayList<Intersection>();

    // Create a list of all the legal intersections the piece can move to
    piece.setOnMouseEntered((e -> {
      for (Intersection inter: intersections) {
        if (BoardUtility.checkIntersectionAdjacent(pieceInter, inter)) {
          if (!inter.hasPiece()) {
            validIntersections.add(inter);
          }
        }
      }
      // Highlight all the legal intersections on the board
      for (Intersection inter: validIntersections) {
        inter.setFill(Color.LAWNGREEN);
        inter.setOpacity(0.5);
      }
    }));

    // Reset the highlighted intersections back to their previous color magenta for intersections that are part of a mill
    piece.setOnMouseExited(e -> {
      for (Intersection inter: validIntersections) {
        if (inter.getIsPartMill()) {
          inter.setFill(Color.MAGENTA);
        } else {
          inter.setOpacity(0);
        }
    }});
  }
  /**
   * Hides all the previously highlighted board elements
   * @param piece
   */
  @Override
  public void hideValidMoves(Piece piece) {
    piece.setOnMouseEntered(e -> {});
//    piece.setOnMouseExited(e -> {});
  }
}

