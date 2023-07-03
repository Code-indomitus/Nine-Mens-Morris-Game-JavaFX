package com.nmmfx.moves;

import com.nmmfx.board.Intersection;
import com.nmmfx.pieces.Piece;
import java.util.ArrayList;
import javafx.scene.Parent;

public interface MoveType {
  /**
   * An abstract class to set a blueprint for types of moves to be made
   *
   * @param piece the piece to be moved
   * @param intersection the new intersection to be set in to
   * @param newX the new x coordinate so move the piece in to
   * @param newY the new y coordinate so move the piece in to
   * @return boolean, true if move was made
   */
  public abstract boolean move(Piece piece, Intersection intersection, int newX, int newY);

  /**
   * Highlights all the valid moves for a piece
   * @param piece
   * @param intersections
   */
  public abstract void showValidMoves(Piece piece, ArrayList<Intersection> intersections);

  /**
   * Hides all the previously highlighted board elements
   * @param piece
   */
  public abstract void hideValidMoves(Piece piece);
}
