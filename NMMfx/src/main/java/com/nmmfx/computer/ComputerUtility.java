package com.nmmfx.computer;

import com.nmmfx.board.BoardUtility;
import com.nmmfx.board.Intersection;
import com.nmmfx.pieces.Piece;

import java.util.ArrayList;
import java.util.Random;

/**
 * Utility class that houses all the important decision-making methods for computer to make moves
 */
public final class ComputerUtility {
    /**
     * choose intersections that are not occupied by a piece from a list of intersections
     * @param intersections list of intersections
     * @return a list of all empty intersections
     */
    public static Intersection chooseAvailableIntersection(ArrayList<Intersection> intersections) {
        Random random = new Random();
        // Choose an available intersection for piece to be placed on
        ArrayList<Intersection> emptyIntersections = new ArrayList<>();

        // Find intersections that are empty
        for (Intersection intersection: intersections) {
            if (!intersection.hasPiece()) {
                emptyIntersections.add(intersection);
            }
        }
        return emptyIntersections.get(random.nextInt(emptyIntersections.size()));
    }

    /**
     * choose an intersection that is adjacent to a given piece from a list of intersections
     * @param intersections list of all intersections
     * @param piece the piece we want to find the adjacent intersection for
     * @return the chosen adjacent intersection
     */
    public static Intersection chooseAdjacentIntersection(ArrayList<Intersection> intersections, Piece piece) {
        Random random = new Random();
        // Choose an available intersection for piece to be placed on
        ArrayList<Intersection> adjacentIntersections = new ArrayList<>();
        for (Intersection intersection: intersections) {
            if (BoardUtility.checkIntersectionAdjacent(piece.getIntersection(), intersection) && !intersection.hasPiece()) {
                adjacentIntersections.add(intersection);
            }
        }

        return adjacentIntersections.get(random.nextInt(adjacentIntersections.size()));
    }

    /**
     * returns a piece that can be moved from a list of pieces and decides using a list of intersections
     * @param intersections a list of intersections
     * @param pieces a list of pieces
     * @return the piece that is to be moved by the computer
     */
    public static Piece choosePieceToMove(ArrayList<Intersection> intersections, ArrayList<Piece> pieces) {
        Random random = new Random();
        // choose piece with possible moves
        ArrayList<Piece> piecesToMove = new ArrayList<>();
        for (Piece piece: pieces) {
            for (Intersection intersection: intersections) {
                if (BoardUtility.checkIntersectionAdjacent(piece.getIntersection(), intersection) && !intersection.hasPiece()) {
                    piecesToMove.add(piece);
                }
            }
        }

        return piecesToMove.get(random.nextInt(piecesToMove.size()));
    }

    /**
     * chooses a piece to be removed from a list of pieces based on game rules
     * @param piecesToRemove a list of pieces
     * @return the piece that is to be removed by the computer from the game
     */
    public static Piece choosePieceToRemove(ArrayList<Piece> piecesToRemove) {
        Random random = new Random();
        // Select a random piece to be removed
        return piecesToRemove.get(random.nextInt(piecesToRemove.size()));
    }

    /**
     * chooses an intersection that a computer's piece can fly to
     * @param intersections a list of all the intersections
     * @return the intersection that the computer's piece flies to
     */
    public static Intersection chooseFlyingIntersection(ArrayList<Intersection> intersections) {
        Random random = new Random();
        // Select A random intersection on board to move to.
        ArrayList<Intersection> availableIntersections = new ArrayList<>();
        for (Intersection intersection: intersections) {
            if (!intersection.hasPiece()) {
                availableIntersections.add(intersection);
            }
        }

        return availableIntersections.get(random.nextInt(availableIntersections.size()));
    }
}
