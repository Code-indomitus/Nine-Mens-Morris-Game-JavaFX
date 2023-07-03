package com.ninemenmorris.ninemenmorrisfx;

import javafx.scene.paint.Color;

public class Intersection extends Tile{

    Piece piece;
    public Intersection(int x, int y) {
        super(x, y);
        this.piece = null;
        setFill(Color.RED);
        setOpacity(0.3);

        setOnMouseClicked(event -> {
            setFill(Color.GREEN);
        });
    }
}
