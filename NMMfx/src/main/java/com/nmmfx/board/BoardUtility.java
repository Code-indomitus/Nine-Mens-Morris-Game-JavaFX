package com.nmmfx.board;

import com.nmmfx.game.Game;
import com.nmmfx.pieces.Piece;

import java.util.ArrayList;

import static com.nmmfx.game.Game.TILE_SIZE;

public final class BoardUtility {
    /**
     * check if a given coordinate is for an intersection or not
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if coordinate is an intersection coordinate and false otherwise
     */
    public static boolean isCoordinateIntersection(int x, int y) {
        boolean result = false;

        if (x == 0) {
            if (y == 0 || y == 6 || y == 12) {
                result = true;
            }
        } else if (x == 2) {
            if (y == 2 || y == 6 || y == 10) {
                result = true;
            }
        } else if (x == 4) {
            if (y == 4 || y == 6 || y == 8) {
                result = true;
            }
        } else if (x == 6) {
            if (y == 0 || y == 2 || y == 4 || y == 8 || y == 10 || y == 12) {
                result = true;
            }
        } else if (x == 8) {
            if (y == 4 || y == 6 || y == 8) {
                result = true;
            }
        } else if (x == 10) {
            if (y == 2 || y == 6 || y == 10) {
                result = true;
            }
        } else if (x == 12) {
            if (y == 0 || y == 6 || y == 12) {
                result = true;
            }
        }

        return result;
    }

    /**
     * check if two intersections are adjacent (neighbouring)
     * @param currIntersection the current intersection the piece is on
     * @param possAdjacentIntersection the possible adjacent intersection the piece is tyring to go to
     * @return true if two intersections are adjacent and false otherwise 
     */
    public static boolean checkIntersectionAdjacent(Intersection currIntersection, Intersection possAdjacentIntersection) {
        int currX = currIntersection.getTileX();
        int currY = currIntersection.getTileY();

        int adjacentX = possAdjacentIntersection.getTileX();
        int adjacentY = possAdjacentIntersection.getTileY();

        int diffX = Math.abs(currX - adjacentX);
        int diffY = Math.abs(currY - adjacentY);

        if (currX == adjacentX && currY == adjacentY) {
             return false;
        }

        if (currX == adjacentX) {
            if (currX == 4 || currX == 6 || currX == 8) {
                if (diffY == 2) {
                    return true;
                }
            }
            else if (diffY == 2 || diffY == 4 || diffY == 6) {
                return true;
            }
        }

        if (currY == adjacentY) {
            if (currY == 4 || currY == 6 || currY == 8) {
                if (diffX== 2) {
                    return true;
                }
            }
            else if (diffX == 2 || diffX == 4 || diffX == 6) {
                return true;
            }
        }

        return false;
    }
    /**
     * check all possible moves of pieces
     * @param intersections ArrayList<Intersection>
     * @param pieces ArrayList<Piece>
     * @return true if number of pieces equals 3 or at least one of the pieces can move and false otherwise
     */

    public static boolean checkMovesPossible(ArrayList<Intersection> intersections, ArrayList<Piece> pieces) {
        if (pieces.size() == 3) {
            return true;
        }
        for (Piece piece: pieces) {
            for (Intersection intersection: intersections) {
                if (checkIntersectionAdjacent(piece.getIntersection(), intersection) && !intersection.hasPiece()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * convert a pixel value to a coordinate on the board. used for understanding where the piece
     * is being moved on the board
     * @param pixelValue
     * @return the respective coordinate value
     */
    public static int toBoard(double pixelValue) {
        return (int) (pixelValue + TILE_SIZE / 2) / TILE_SIZE;
    }
}
