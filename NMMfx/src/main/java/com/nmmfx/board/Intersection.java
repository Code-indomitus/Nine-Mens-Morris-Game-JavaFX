package com.nmmfx.board;

import com.nmmfx.moves.AdjacentMove;
import com.nmmfx.pieces.BluePiece;
import com.nmmfx.pieces.Piece;
import com.nmmfx.pieces.RedPiece;
import javafx.scene.paint.Color;

public class Intersection extends BoardTile {

    private Piece piece;
    private boolean isPartMill = false;
    private Mill mill = null;

    /**
     * constructor for intersection class
     * @param x x-coordinate for the intersection
     * @param y y-coordinate for the intersection
     */
    public Intersection(int x, int y) {
        super(x, y);
        this.piece = null;
        setFill(Color.RED);
        setOpacity(0);
    }

    /**
     * returns the instance of piece that is currently on the intersection
     * @return piece instance (could be null if there is no piece on the intersection)
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * set the piece for the intersection
     * @param piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * check if the intersection has a piece on it or not
     * @return true if piece is not null and false if piece is null
     */
    public boolean hasPiece() {
        return !(this.piece == null);
    }

    /**
     * return if tile is an intersection or not
     * @return true as it is an intersection
     */
    @Override
    public boolean isIntersection() {
        return true;
    }
    /**
     * check if current position of piece in boardtile is part of a mill
     * @return true if current piece is part of a mill and false otherwise. 
     */

    public boolean getIsPartMill() {
        return this.isPartMill;
    }

    /**
     * set the property of isPartMill of boardTile to be true or false
     * 
     */

    public void setIsPartMill(boolean b) {
        this.isPartMill = b;
    }

    /**
     * gets the current mill of boardTile
     * @return the current mill of boardTile 
     */

    public Mill getMill() {
        return this.mill;
    }
    /**
     * sets a mill color to boardTile to red if mill is null and magenta otherwise  
     * @param mill Mill  
     */

    public void setMill(Mill mill) {
       this.mill = mill;
       if (mill == null) {
           setFill(Color.RED);
           setOpacity(0);
       }
       else {
           setFill(Color.MAGENTA);
           setOpacity(0.6);
       }
    }
}
