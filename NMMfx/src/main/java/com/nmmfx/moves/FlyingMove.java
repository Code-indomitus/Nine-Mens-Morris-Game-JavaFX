package com.nmmfx.moves;

import com.nmmfx.board.BoardUtility;
import com.nmmfx.board.Intersection;
import com.nmmfx.moves.MoveType;
import com.nmmfx.pieces.Piece;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class FlyingMove implements MoveType {
    /**
     * Constructor for this class
     */
    public FlyingMove(){

    }

    /**
     * Move to piece to any intersection available
     * @param piece the piece to be moved
     * @param intersection the new intersection to be set in to
     * @param newX the new x coordinate so move the piece in to
     * @param newY the new y coordinate so move the piece in to
     * @return true
     */
    @Override
    public boolean move(Piece piece, Intersection intersection, int newX, int newY) {

        piece.leavePreviousIntersection(); // Set previous intersection's piece to null when leaving it indicating empty
        piece.placePiece(newX, newY);
        intersection.setPiece(piece);
        piece.setIntersection(intersection);
        return true;
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
                if (!inter.hasPiece()) {
                    validIntersections.add(inter);
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
//        piece.setOnMouseExited(e -> {});
    }
}
