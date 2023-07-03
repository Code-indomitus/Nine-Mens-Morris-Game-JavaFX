package com.ninemenmorris.ninemenmorrisfx;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.lang.Math;
import java.util.ArrayList;

public class GameBoard {

    public static final int BOARD_SIZE = 520;
    public static final int BOARD_HEIGHT = 13;
    public static final int BOARD_WIDTH = 13;
    public static final int TILE_SIZE = BOARD_SIZE/BOARD_HEIGHT;
    public static final int PIECES_PER_PLAYER = 9;

//    private Tile[][] board = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
    private Group tileGroup = new Group();

    private ArrayList<Intersection> intersections = new ArrayList<Intersection>();

    private ArrayList<Piece> playerOnePieces = new ArrayList<Piece>();
    private ArrayList<Piece> playerTwoPieces = new ArrayList<Piece>();

    private ArrayList<Piece> allPieces = new ArrayList<Piece>();
//    private Intersection[] intersectionsList = new Intersection[24];

    public GameBoard() {

    }
    public Parent createGameScene() {
        BorderPane gameSceneLayout = new BorderPane();
        VBox redPieceLayout = new VBox();
        VBox bluePieceLayout = new VBox();
        redPieceLayout.toFront();
        bluePieceLayout.toFront();
        Label top = new Label("Top");
        Label bottom = new Label("Bottom");
        Label left = new Label("Left");
        Label right = new Label("Right");
        redPieceLayout.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bluePieceLayout.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        redPieceLayout.setSpacing(5);
        bluePieceLayout.setSpacing(5);

        redPieceLayout.setAlignment(Pos.CENTER);
        bluePieceLayout.setAlignment(Pos.CENTER);

        redPieceLayout.setPrefWidth((1280 - BOARD_SIZE) / 2);
        bluePieceLayout.setPrefWidth((1280 - BOARD_SIZE) / 2);

        HBox boardLayout = new HBox();
//        boardLayout.getChildren().add(createBoardGrid());
        boardLayout.getChildren().add(redPieceLayout);
        boardLayout.getChildren().add(createBoardGrid());
        boardLayout.getChildren().add(bluePieceLayout);


        top.setPrefHeight(140);
        bottom.setPrefHeight(20);

        for (int i = 0; i < PIECES_PER_PLAYER; i++) {
            RedPiece newRedPiece = new RedPiece();
            playerOnePieces.add(newRedPiece);
            redPieceLayout.getChildren().add(newRedPiece);
            allPieces.add(newRedPiece);
        }

        for (int i = 0; i < PIECES_PER_PLAYER; i++) {
            BluePiece newBluePiece = new BluePiece();
            playerTwoPieces.add(newBluePiece);
            bluePieceLayout.getChildren().add(newBluePiece);
            allPieces.add(newBluePiece);
        }

        gameSceneLayout.setLeft(left);
        gameSceneLayout.setRight(right);
        gameSceneLayout.setTop(top);
        gameSceneLayout.setBottom(bottom);
        gameSceneLayout.setCenter(boardLayout);
        gameSceneLayout.centerProperty();

        placePieceIntersection();

        return gameSceneLayout;
    }

    private void placePieceIntersection() {
        for (Piece piece: allPieces) {
            piece.setOnMouseReleased(event -> {
                for (Intersection intersection: intersections) {
                    if (intersection.getBoundsInParent().contains(event.getX(), event.getY())) {
                        // Place the circle on the rectangle
                        piece.setTranslateX(intersection.getLayoutX() + intersection.getWidth() / 2);
                        piece.setTranslateY(intersection.getLayoutY() + intersection.getHeight() / 2);
                        return;
                    }
                }

                double[] originalPosition = (double[]) piece.getUserData();
                piece.setTranslateX(originalPosition[0]);
                piece.setTranslateY(originalPosition[1]);
            });
        }

    }

    private Parent createBoardGrid() {
        Pane root = new Pane();

        StackPane boardStack = new StackPane();
        Image board_image = new Image( "board_with_intersection.png" );
        ImageView boardView = new ImageView(board_image);

        boardStack.getChildren().addAll(boardView, tileGroup);
        root.setPrefSize(BOARD_SIZE, BOARD_SIZE);
        root.getChildren().add(boardStack);

        for (int x = 0; x < BOARD_HEIGHT; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                if (isCoordinateIntersection(x, y)) {
                    Intersection intersection = new Intersection(x, y);
                    intersections.add(intersection);
                    tileGroup.getChildren().add(intersection);
                }
                else {
                    Tile tile = new Tile(x, y);
                    tileGroup.getChildren().add(tile);
                }
            }
        }

        root.toFront();

        return root;
    }

    public boolean isCoordinateIntersection(int x, int y) {
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


}
