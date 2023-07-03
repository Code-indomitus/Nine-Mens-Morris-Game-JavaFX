package com.nmmfx.game;

import com.nmmfx.board.*;
import com.nmmfx.moves.FlyingMove;
import com.nmmfx.pieces.Piece;
import com.nmmfx.pieces.PieceType;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public abstract class Game {

    public static final int BOARD_SIZE = 520;
    public static final int BOARD_HEIGHT = 13;
    public static final int BOARD_WIDTH = 13;
    public static final int TILE_SIZE = BOARD_SIZE/BOARD_HEIGHT;
    public static final int PIECES_PER_PLAYER = 9;
    public static final int TOTAL_PIECES = 18;

    protected BoardTile[][] boardTiles = new BoardTile[BOARD_WIDTH][BOARD_HEIGHT];
    protected Group tilesGroup;
    protected Group piecesGroup;

    protected ArrayList<Intersection> intersections;
    protected ArrayList<Intersection> prevMillIntersections = new ArrayList<>();
    protected ArrayList<Intersection> currMillIntersections = new ArrayList<>();
    protected ArrayList<Mill> mills = new ArrayList<>();

    protected ArrayList<Piece> playerOnePieces;
    protected ArrayList<Piece> playerTwoPieces;
    protected int turn;
    protected boolean removingInProgress;

    protected PhaseType gamePhase;

    protected final Label infoLabel;
    protected final Label turnLabel;

    private boolean hintsOn = false;

    public Game() {
        this.tilesGroup = new Group();
        this.piecesGroup = new Group();
        this.intersections = new ArrayList<Intersection>();
        this.playerOnePieces = new ArrayList<Piece>();
        this.playerTwoPieces = new ArrayList<Piece>();
        this.turn = 1;
        this.removingInProgress = false;
        this.gamePhase = PhaseType.PLACING_PHASE;
        this.infoLabel = new Label("SET YOUR MEN ON THE BOARD");
        this.infoLabel.setFont(Font.font(24));
        this.infoLabel.setTextFill(Color.HONEYDEW);
        this.turnLabel = new Label("RED'S TURN");
        this.turnLabel.setTextFill(Color.DARKRED);
        this.turnLabel.setFont(Font.font(22));
    }

    /**
     * return the pieces that player one currently has
     * @return list of pieces
     */
    public ArrayList<Piece> getPlayerOnePieces() {
        return this.playerOnePieces;
    }

    /**
     * return the pieces that player two currently has
     * @return list of pieces
     */
    public ArrayList<Piece> getPlayerTwoPieces() {
        return this.playerTwoPieces;
    }

    /**
     * returns the list of intersections in the game board
     * @return list of intersections
     */
    public ArrayList<Intersection> getIntersections() {
        return intersections;
    }

    /**
     * returns the matrix of board tiles that make up the game board
     * @return board tile matrix
     */
    public BoardTile[][] getBoardTiles() {
        return boardTiles;
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public Label getTurnLabel() {
        return turnLabel;
    }

    /**
     * creates the board UI grid and initial setup
     * @return the node that represents the game board
     */
    public Parent createBoardGrid() {
        Pane boardPane = new Pane();

        StackPane root = new StackPane();
        Image boardImage = new Image( "9mm_board.png" );
        ImageView boardView = new ImageView(boardImage);


        boardPane.setPrefSize(BOARD_SIZE, BOARD_SIZE);

        boardPane.getChildren().addAll(tilesGroup, piecesGroup);

        root.getChildren().add(boardView);
        root.getChildren().add(boardPane);
        root.setAlignment(Pos.TOP_LEFT);

        // Build the board UI in a grid form using boardTile and Intersections
        for (int x = 0; x < BOARD_HEIGHT; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                if (BoardUtility.isCoordinateIntersection(x, y)) {
                    // Create an intersection if coordinate is an intersection coordinate
                    Intersection intersection = makeIntersection(x, y);
                    intersections.add(intersection);
                    tilesGroup.getChildren().add(intersection);
                    boardTiles[x][y] = intersection;
                }
                else {
                    BoardTile tile = new BoardTile(x, y);
                    tilesGroup.getChildren().add(tile);
                    boardTiles[x][y] = tile;
                }
            }
        }

        return root;
    }

    /**
     * creates an intersection with special properties of spawning pieces
     * @param x x-coordinate of intersection on board
     * @param y y-coordinate of intersection on board
     * @return an intersection object
     */
    protected Intersection makeIntersection(int x, int y) {
        Intersection intersection = new Intersection(x, y);
        setUpIntersection(intersection);
        return intersection;
    }

    /**
     * allows intersection to be interacted with to spawn pieces in the placing phase when clicked
     * @param intersection intersection that is set up with piece spawning properties
     */
    protected abstract void setUpIntersection(Intersection intersection);

    /**
     * convert a pixel value to a coordinate on the board. used for understanding where the piece
     * is being moved on the board
     * @param pixelValue
     * @return the respective coordinate value
     */
    protected int toBoard(double pixelValue) {
        return (int) (pixelValue + TILE_SIZE / 2) / TILE_SIZE;
    }

    /**
     * makes pieces movable on the board by calling certain methods
     * @param pieces pieces that are to be made movable
     */
    protected void makePiecesMovable(ArrayList<Piece> pieces) {
        for (Piece piece: pieces) {
            piece.setUpMovement();
            makePieceMovable(piece);
        }
    }

    /**
     * makes a piece movable on the board
     * @param piece pieces that is made movable
     */
    protected abstract void makePieceMovable(Piece piece);

    /**
     * Functionality and logic to switch player turn in the game to continue gameplay in the correct way
     */
    protected abstract void switchPlayerTurn();


    protected boolean checkForMills() {

        boolean millFormed = false; // Flag to check mill formation

        // Store mills from previous turn
        prevMillIntersections.clear();
        for (Mill mill: mills) {
            for (Intersection inter: mill.getIntersections()) {
                prevMillIntersections.add(inter);
            }
        }

        // Clear all mills from previous turn
        for (Mill mill: mills) {
            mill.deleteMill();
        }
        this.mills.clear();

        // Determine if each piece forms a mill
        for (Piece piece: playerOnePieces) {
            // if (piece forms mill && piece is not part of a mill), then create a new mill
            if (hasFormedMill(piece) != null && piece.getIntersection().getMill() == null) {
                this.mills.add(new Mill(hasFormedMill(piece)));
            }
        }
        for (Piece piece: playerTwoPieces) {
            if (hasFormedMill(piece) != null && piece.getIntersection().getMill() == null) {
                this.mills.add(new Mill(hasFormedMill(piece)));
            }
        }

        // Get all mills for current turn
        currMillIntersections.clear();
        for (Mill mill: mills) {
            for (Intersection inter: mill.getIntersections()) {
                currMillIntersections.add(inter);
            }
        }

        // Update flag if mill(s) formed
        if (!(currMillIntersections.equals(prevMillIntersections))) {
            millFormed = true;
        }

        // if (millFormed) AND (equal or more no. of mills this turn)
        if (millFormed && (currMillIntersections.size() >= prevMillIntersections.size())) {
            Media media = new Media(getClass().getResource("/com/nmmfx/finish_him.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return true;
        }
        return false;
    }

    /**
     * Determines if a new mill was formed in the x or y-axis
     * @param piece - All pieces currently in play
     * @return Intersection forming the new mill; null otherwise
     */
    private ArrayList<Intersection> hasFormedMill(Piece piece) {
        // Check for mills along x-axis
        if (checkMillsY(piece) != null) {
            return checkMillsY(piece);
        } // Check y-axis
        else if (checkMillsX(piece) != null) {
            return checkMillsX(piece);
        }
        return null;
    }

    /**
     * For a piece, checks if it forms a mill in the y-axis,
     * @param piece
     * @return 3 intersections that form the new mill; null otherwise
     */
    private ArrayList<Intersection> checkMillsY(Piece piece) {
        int adjPieces = 0; // Keeps track of pieces adjacent to current piece
        ArrayList<Intersection> checkMill = new ArrayList<>(); // Intersections of potential mill
        checkMill.add(piece.getIntersection());

        // Check along x-axis
        for (Intersection intersection: intersections) {

            // Found mill along x-axis
            if (adjPieces == 2) {
                break;
            }

            // Intersection has same x coordinate, potentially adjacent intersection
            if (piece.getIntersection().getTileX() == intersection.getTileX() &&
                    piece.getIntersection().getTileY() != intersection.getTileY()) {

                // Special case: if x = 6, can form 2 different mills on same row
                if (piece.getIntersection().getTileX() == 6) {
                    // Check (6,8), (6,10), (6,12)
                    if (piece.getIntersection().getTileY() >= 8 && intersection.getTileY() >= 8) {
                        if (intersection.hasPiece() && intersection.getPiece().getColour() == piece.getColour()) {
                            checkMill.add(intersection);
                            adjPieces += 1;
                        }
                    }
                    // Check (6,0), (6,2), (6,4)
                    else if (piece.getIntersection().getTileY() <= 4 && intersection.getTileY() <= 4) {
                        if (intersection.hasPiece() && intersection.getPiece().getColour() == piece.getColour()) {
                            checkMill.add(intersection);
                            adjPieces += 1;
                        }
                    }
                }

                // Normal case: x != 6, each row can only form 1 mill
                else if (intersection.hasPiece() && intersection.getPiece().getColour() == piece.getColour()) {
                    checkMill.add(intersection);
                    adjPieces += 1;
                }
            }
        }

        if (adjPieces == 2) {
            return checkMill;
        }

        return null;
    }

    /**
     * For a piece, checks if it forms a mill in the x-axis,
     * @param piece
     * @return 3 intersections that form the new mill; null otherwise
     */
    private ArrayList<Intersection> checkMillsX(Piece piece) {

        int adjPieces = 0; // Keeps track of pieces adjacent to current piece
        ArrayList<Intersection> checkMill = new ArrayList<>(); // Intersections of potential mill
        checkMill.add(piece.getIntersection());

        // Check along y-axis
        for (Intersection intersection: intersections) {

            // Found mill along y-axis
            if (adjPieces == 2) {
                break;
            }

            if (piece.getIntersection().getTileY() == intersection.getTileY() &&
                    piece.getIntersection().getTileX() != intersection.getTileX()) {

                if (piece.getIntersection().getTileY() == 6) {

                    if (piece.getIntersection().getTileX() >= 8 && intersection.getTileX() >= 8) {
                        if (intersection.hasPiece() && intersection.getPiece().getColour() == piece.getColour()) {
                            checkMill.add(intersection);
                            adjPieces += 1;
                        }
                    }
                    else if (piece.getIntersection().getTileX() <= 4 && intersection.getTileX() <= 4) {
                        if (intersection.hasPiece() && intersection.getPiece().getColour() == piece.getColour()) {
                            checkMill.add(intersection);
                            adjPieces += 1;
                        }
                    }
                }

                else if (intersection.hasPiece() && intersection.getPiece().getColour() == piece.getColour()) {
                    checkMill.add(intersection);
                    adjPieces += 1;
                }
            }
        }

        if (adjPieces == 2) {
            return checkMill;
        }

        return null;
    }

    /**
     * method that makes pieces in a list removable on clicking them
     * @param pieces list of pieces that are to be made removable
     */
    protected abstract void makePiecesRemovable(ArrayList<Piece> pieces);

    /**
     * setup which players pieces are to be made removable
     */
    protected abstract void setUpPieceRemove();

    /**
     * freeze a list of pieces so that they cannot be interacted with in the game
     * @param pieces list of pieces that are to be frozen
     */
    protected void freezePieces(ArrayList<Piece> pieces) {
        for (Piece piece: pieces) {
            piece.freezePiece();
        }
    }

    /**
     * Make a list of pieces able to fly on the board meaning move to any vacant intersection
     * on the board.
     * @param pieces list of pieces that are to made able to fly
     */
    protected void makePiecesFlyable(ArrayList<Piece> pieces){
        for (Piece piece: pieces) {
            piece.setMoveType(new FlyingMove());
        }

    }

    /**
     * check if the list of pieces is equal to 2 to check if winning condition is met
     * @param pieces list of pieces
     * @return true if list of pieces only has 2 pieces false otherwise
     */
    protected boolean checkWinnerPieces(ArrayList<Piece> pieces){
        return pieces.size() == 2;
    }

    /**
     * Check if a list of pieces can move anywhere on the board and
     * declare the winner of the game accordingly
     * @param pieces list of pieces
     */
    protected boolean checkWinnerMoves(ArrayList<Piece> pieces){
        if(!BoardUtility.checkMovesPossible(this.intersections, pieces)){
            declareWinner(pieces);
            return true;
        }
        return false;
    }

    /**
     * Method that declares the winner and changes the UI to convey the message
     * @param pieces the list of pieces of the losing player
     */
    protected void declareWinner(ArrayList<Piece> pieces){
        // Play winning sound from mortal kombat - sound acquired from samplefocus.com
        Media media = new Media(getClass().getResource("/com/nmmfx/flawless_victory.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        this.infoLabel.setTextFill(Color.DARKVIOLET);
        // Check which player has won and update the info label to let the users know
        if(pieces.get(0).getColour() == PieceType.RED){
            this.infoLabel.setText("CONGRATULATIONS! BLUE WINS!");
        }else{
            this.infoLabel.setText("CONGRATULATIONS! RED WINS!");
        }
        // Make sure the game cannot be played anymore by freezing the pieces
        freezePieces(this.playerOnePieces);
        freezePieces(this.playerTwoPieces);
    }

    /**
     * Tells whether the user requested for hints or not
     * @return Returns if hints is requested
     */
    public boolean getHintsOn() {
        return this.hintsOn;
    }

    /**
     * Toggles the hints in the game on and off
     */
    public void toggleHints() {
        this.hintsOn = !(this.hintsOn);

        // Hints enabled
        if (this.hintsOn == true) {
            System.out.println("Hints enabled");
            // Blue's turn
            if (this.turn % 2 == 0 && !this.removingInProgress) {
                System.out.println("Showing hints for Blue");
                for (Piece piece: playerTwoPieces) {
                    piece.getMoveType().showValidMoves(piece, intersections);
                }
            } // Red's turn
            else if (this.turn % 2 == 1 && !this.removingInProgress) {
                System.out.println("Showing hints for Red");
                for (Piece piece: playerOnePieces) {
                    piece.getMoveType().showValidMoves(piece, intersections);
                }
            }
        }

        // Hints disabled
        if (this.hintsOn == false) {
            System.out.println("Hints disabled");
            for (Piece piece: playerOnePieces) {
                piece.getMoveType().hideValidMoves(piece);
            }
            for (Piece piece: playerTwoPieces) {
                piece.getMoveType().hideValidMoves(piece);
            }
        }
    }
}
