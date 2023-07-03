package com.ninemenmorris.ninemenmorrisfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public abstract class Piece extends Circle {


    public Piece() {
        setRadius((GameBoard.TILE_SIZE/2));
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.INSIDE);
        toFront();

        setUpDraggableMovement();
    }

    private void setUpDraggableMovement() {
        setOnMousePressed(event -> {
            // Save the current position of the piece
            setUserData(new double[]{getTranslateX(), getTranslateY()});
        });

        setOnMouseDragged(event -> {
            // Move the piece with the mouse
            setTranslateX(getTranslateX() + event.getX() - getRadius());
            setTranslateY(getTranslateY() + event.getY() - getRadius());
        });

        setOnMouseReleased(event -> {
            // Return the piece to its original position
            double[] originalPosition = (double[]) getUserData();
            setTranslateX(originalPosition[0]);
            setTranslateY(originalPosition[1]);
        });


    }
}
