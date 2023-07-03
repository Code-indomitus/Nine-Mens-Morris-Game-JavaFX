package com.nmmfx.pieces;

import com.nmmfx.game.PlayerGame;
import com.nmmfx.board.Intersection;
import com.nmmfx.moves.MoveType;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.media.Media;

public abstract class Piece extends Circle {

    public static final int PIECE_OFFSET = 6;
    /**
     * Old x and y position of the piece on the board
     */
    private double oldX, oldY;
    /**
     * The position of the mouse interacting with this piece
     */

    private double mouseX, mouseY;
    /**
     * The type of move this piece can do
     */
    private MoveType moveType;
    /**
     * The intersection this piece is in
     */
    private Intersection intersection;
    /**
     * The colour of this piece
     */
    protected PieceType colour;

    /**
     * constructor for a class that represents a Piece
     * @param x x-coordinate for the piece on the board
     * @param y y-coordinate for the piece on the board
     */
    public Piece(int x, int y) {
        this.intersection = null;
        placePiece(x, y);

        // Set up the visual details for the piece
        setRadius((PlayerGame.TILE_SIZE/2) - PIECE_OFFSET);
        setStroke(Color.GOLDENROD);
        setStrokeType(StrokeType.INSIDE);
        setStrokeWidth(1.5);

        // Translate piece into the center of the intersection
        setTranslateX(PlayerGame.TILE_SIZE / 2);
        setTranslateY(PlayerGame.TILE_SIZE / 2);
    }

    /**
     * place a piece on the nine men's morris board
     * @param x x-coordinate for the piece on the board
     * @param y y-coordinate for the piece on the board
     */
    public void placePiece(int x, int y) {
        this.oldX = x * PlayerGame.TILE_SIZE;
        this.oldY = y * PlayerGame.TILE_SIZE;
        relocate(this.oldX, this.oldY);
        playPieceSound();
    }

    /**
     * plays a piece placing sound. used when a piece is placed on an intersection
     */
    private void playPieceSound() {
        // piece_sound.wav sound from Zapsplat.com
        Media media = new Media(getClass().getResource("/com/nmmfx/piece_sound.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    /**
     * allows the piece to be dragged around on the board
     */
    public void setUpMovement() {

        setOnMousePressed(event -> {
            this.mouseX = event.getSceneX();
            this.mouseY = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            setTranslateX(PIECE_OFFSET);
            setTranslateY(PIECE_OFFSET);
            relocate(event.getSceneX() - this.mouseX + this.oldX, event.getSceneY() - this.mouseY + this.oldY);
            toFront();
        });
    }

    /**
     * place the piece back to its original spot
     */
    public void cancelMove() {
        setTranslateX(PIECE_OFFSET);
        setTranslateY(PIECE_OFFSET);
        relocate(this.oldX, this.oldY);
        playErrorSound();
    }
    /**
     * plays an error sound when an illegal move is trying to be made
     */
    private void playErrorSound() {
        // error_move.wav sound from Zapsplat.com
        Media media = new Media(getClass().getResource("/com/nmmfx/error_move.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    /**
     * plays a removing sound effect when a piece is removed from the board
     */
    public void playRemoveSound() {
        // fatality.wav (Mortal Kombat Game) sound from samplefocus.com
        Media media = new Media(getClass().getResource("/com/nmmfx/fatality.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    /**
     * Setter method to set the intersection this piece is in
     * @param intersection the intersection to set the piece in to
     */
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * returns the intersection the piece is on
     * @return intersection the piece is on
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * the piece leaves its previous intersection when it is moved to a new intersection point
     */
    public void leavePreviousIntersection() {
        this.intersection.setPiece(null);
    }

    /**
     * sets the movetype attribute
     * @param moveType the type of move available for this piece
     */
    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    /**
     * executes the move depending on the move type
     * @param intersection the new intersection to set the piece into
     * @param newX the new x coordinate to move the piece in to
     * @param newY the new y coordinate to move the piece in to
     */

    public MoveType getMoveType() {
        return this.moveType;
    }

    public boolean executeMove(Intersection intersection, int newX, int newY) {
        return moveType.move(this, intersection, newX, newY);
    }

    /**
     * Freezing the pieces meaning nothing can be done to this piece
     */
    public void freezePiece() {
        setOnMouseClicked(event -> {});

        setOnMousePressed(event -> {});

        setOnMouseDragged(event -> {});

        setOnMouseReleased(event -> {});

        getMoveType().hideValidMoves(this);
    }

    /**
     * Getter method to get the colour attributes of this piece
     * @return enum, the colour attribute of this piece
     */
    public PieceType getColour() {
        return this.colour;
    }
}
