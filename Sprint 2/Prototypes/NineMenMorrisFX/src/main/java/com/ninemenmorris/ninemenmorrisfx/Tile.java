package com.ninemenmorris.ninemenmorrisfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    public Tile(int x, int y) {
        setWidth(GameBoard.TILE_SIZE);
        setHeight(GameBoard.TILE_SIZE);
        relocate(x * GameBoard.TILE_SIZE, y * GameBoard.TILE_SIZE);
        setStroke(Color.rgb(255, 255, 255, 0.5));
        setStrokeWidth(1.5);
        setFill(Color.YELLOW);
        setOpacity(0.5);
        toBack();
    }

}
