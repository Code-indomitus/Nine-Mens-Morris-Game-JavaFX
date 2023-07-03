package com.nmmfx.pieces;

import javafx.scene.paint.Color;

public class BluePiece extends Piece{
    /**
     * constructor for a class that represents a blue piece
     * @param x x-coordinate for the piece on the board
     * @param y y-coordinate for the piece on the board
     */
    public BluePiece(int x, int y) {
        super(x, y);
        this.colour = PieceType.BLUE;
        setFill(Color.DARKBLUE);
    }
}
