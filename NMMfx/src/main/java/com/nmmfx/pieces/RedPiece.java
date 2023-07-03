package com.nmmfx.pieces;

import javafx.scene.paint.Color;

public class RedPiece extends Piece{
    /**
     * constructor for a class that represents a red piece
     * @param x x-coordinate for the piece on the board
     * @param y y-coordinate for the piece on the board
     */
    public RedPiece(int x, int y) {
        super(x, y);
        this.colour = PieceType.RED;
        setFill(Color.DARKRED);
    }
}
