package com.nmmfx.computer;

import com.nmmfx.board.BoardUtility;
import com.nmmfx.board.Intersection;
import com.nmmfx.board.PhaseType;
import com.nmmfx.game.Game;
import com.nmmfx.moves.AdjacentMove;
import com.nmmfx.pieces.BluePiece;
import com.nmmfx.pieces.Piece;
import com.nmmfx.pieces.PieceType;
import com.nmmfx.pieces.RedPiece;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class ComputerGame extends Game {
    // Thread instance to allow delay for computer to make its move
    private Thread computerThread = null;

    // Indicates if the computer can fly its pieces or not
    private boolean canCompFly = false;

    // The delay constant that the computer will have whenever it makes a move
    private final int COMPUTER_DELAY = 1000;

    public ComputerGame(){
        super();
        this.turnLabel.setText("PLAYER'S TURN");
    }

    /**
     * Allows intersection to be interacted with to spawn pieces in the placing phase when clicked specific
     * to the computer game scenario
     * @param intersection intersection that is set up with piece spawning properties
     */
    @Override
    protected void setUpIntersection(Intersection intersection) {
        intersection.setOnMouseClicked(event -> {

            if (turn > TOTAL_PIECES || this.removingInProgress) {

            } else if (turn % 2 == 1 && !intersection.hasPiece()) {
                // HUMAN PLAYER'S TURN
                // Red turn
                RedPiece redPiece = new RedPiece(intersection.getTileX(), intersection.getTileY());
                redPiece.setIntersection(intersection);
                redPiece.setMoveType(new AdjacentMove());
                this.piecesGroup.getChildren().add(redPiece);
                this.playerOnePieces.add(redPiece);
                intersection.setPiece(redPiece);

                if (this.checkForMills()) {
                    setUpPieceRemove(); // Remove a piece
                }
                else {
                    switchPlayerTurn();
                }
            }

            // Make pieces fly at the end of placing if they only have 3 pieces
            if (this.gamePhase == PhaseType.MOVING_PHASE) {
                if ( this.playerOnePieces.size() == 3){
                    makePiecesFlyable(this.playerOnePieces);
                } else if(this.playerTwoPieces.size() == 3) {
                    makePiecesFlyable(this.playerTwoPieces);
                }
            }

        });
    }

    /**
     * makes a piece movable on the board for the human player in the computer vs player game
     * @param piece pieces that is made movable
     */
    @Override
    protected void makePieceMovable(Piece piece) {
        // Enable hints if it is requested by the user
        if (this.getHintsOn()) {
            piece.getMoveType().showValidMoves(piece, intersections);
        }

        piece.setOnMouseReleased(event -> {
            // Getting new coordinates of dragged piece
            piece.setTranslateX(Piece.PIECE_OFFSET);
            piece.setTranslateY(Piece.PIECE_OFFSET);
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            // Checks if the piece is dragged to a valid location on the board, then places it
            if ((newX >= 0 && newX <= 12) && (newY >= 0 && newY <= 12)) {
                if (BoardUtility.isCoordinateIntersection(newX, newY)) {
                    Intersection intersection = (Intersection) boardTiles[newX][newY];
                    if (intersection.hasPiece()) {
                        piece.cancelMove(); // Pieces cannot overlap on same intersection
                    }
                    else {
                        if (piece.executeMove(intersection, newX, newY)) {

                            // Check if a new mill is formed
                            if (checkForMills()) {
                                setUpPieceRemove(); // Remove a piece
                            }
                            else {
                                switchPlayerTurn();
                            }

                        }
                        System.out.println("Turn: " + this.turn);
                    }
                }
                else {
                    piece.cancelMove();
                }
            }
            else {
                piece.cancelMove();
            }

        });
    }

    /**
     * Executes the computers move based on the phase of the game
     */
    private void executeComputerMove() {

        if (this.gamePhase == PhaseType.PLACING_PHASE) {
            placePieceComputer();
        } else if (this.gamePhase == PhaseType.MOVING_PHASE) {
            movePieceComputer();
        }

    }

    /**
     * Allows the computer to place its piece on the board during the placing phase
     */
    private void placePieceComputer() {
        // create a new thread to introduce delay in the placing move
        computerThread = new Thread( () -> {
            try {
                Thread.sleep(COMPUTER_DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Computer will do their placing of pieces after the delay
            Platform.runLater(() -> doPlaceComputer());
        });
        // Start the thread
        computerThread.start();
    }

    /**
     * Allows the computer to actually place pieces and check for mills and do the needful actions once a mill is formed.
     */
    private void doPlaceComputer() {
        // Computer finds intersection to place on and then place the piece
        Intersection intersection = ComputerUtility.chooseAvailableIntersection(this.intersections);
        BluePiece bluePiece = new BluePiece(intersection.getTileX(), intersection.getTileY());
        bluePiece.setIntersection(intersection);
        bluePiece.setMoveType(new AdjacentMove());
        this.piecesGroup.getChildren().add(bluePiece);
        this.playerTwoPieces.add(bluePiece);
        intersection.setPiece(bluePiece);

        // Checks if the new move by the computer results in a mill
        if (this.checkForMills()) {
            this.infoLabel.setText("MILL FORMED! REMOVE PIECE!");
            // Introduce another delay for when the computer removes human player's piece
            Thread removeThread = new Thread( () -> {
                try {
                    Thread.sleep(COMPUTER_DELAY);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // Computer will remove human player's piece after
                Platform.runLater(() -> doRemoveComputer());
            });
            removeThread.start();
        }
        else {
            switchPlayerTurn();
        }
    }
    /**
     * Allows the computer to move its piece on the board during the moving phase
     */
    private void movePieceComputer() {
        // create a new thread to introduce delay in the moving of pieces by the computer
        computerThread = new Thread( () -> {
            try {
                Thread.sleep(COMPUTER_DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Computer will do their moving of pieces after the delay
            Platform.runLater(() -> doMoveComputer());
        });
        // Start the thread
        computerThread.start();
    }

    // Computer moves one of its pieces to a valid spot
    private void doMoveComputer() {
        // Find a valid piece for the computer to move
        Piece pieceToMove = ComputerUtility.choosePieceToMove(this.intersections, this.playerTwoPieces);
        Intersection intersectionMoveTo = null;

        // Find an intersection for the computer piece to move to
        if (this.canCompFly){
            intersectionMoveTo = ComputerUtility.chooseFlyingIntersection(this.intersections);
        }else {
            intersectionMoveTo = ComputerUtility.chooseAdjacentIntersection(this.intersections, pieceToMove);
        }

        pieceToMove.setTranslateX(Piece.PIECE_OFFSET);
        pieceToMove.setTranslateY(Piece.PIECE_OFFSET);
        pieceToMove.executeMove(intersectionMoveTo, intersectionMoveTo.getTileX(), intersectionMoveTo.getTileY());

        // Once move is done check if a new mill has formed. Set up remove of pieces for computer when it forms a mill.
        if (this.checkForMills()) {
            this.infoLabel.setText("MILL FORMED! REMOVE PIECE!");
            // Introduce delay for the removal of opponent pieces
            Thread removeThread = new Thread( () -> {
                try {
                    Thread.sleep(COMPUTER_DELAY);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> doRemoveComputer());
            });
            removeThread.start();
        }
        else {
            // go to next turn
            switchPlayerTurn();
        }
    }

    /**
     * Computer does the removing of pieces from human player's pieces
     */
    private void doRemoveComputer() {
        ArrayList<Piece> piecesNotPartMill = new ArrayList<>();
        Piece pieceToRemove = null;
        // Find if all pieces are in mill or not
        for (Piece piece : this.playerOnePieces) {
            if (!piece.getIntersection().getIsPartMill()) {
                piecesNotPartMill.add(piece);
            }
        }
        // If all are in mill allow to remove all and check for mills after
        if (piecesNotPartMill.size() == 0) {
            pieceToRemove = ComputerUtility.choosePieceToRemove(this.playerOnePieces);
            // Remove piece from the board and from the players list
            this.playerOnePieces.remove(pieceToRemove);
            this.piecesGroup.getChildren().remove(pieceToRemove);

            // Reset intersection pointers
            pieceToRemove.getIntersection().setPiece(null);
            pieceToRemove.setIntersection(null);
            pieceToRemove.playRemoveSound();
            checkForMills();
        } else {
            // if some are not in mill only allow choosing from them
            pieceToRemove = ComputerUtility.choosePieceToRemove(piecesNotPartMill);

            // Remove piece from the board and from the players list
            this.playerOnePieces.remove(pieceToRemove);
            this.piecesGroup.getChildren().remove(pieceToRemove);

            // Reset intersection pointers
            pieceToRemove.getIntersection().setPiece(null);
            pieceToRemove.setIntersection(null);
            pieceToRemove.playRemoveSound();
        }

        // After removing if opponent has only 3 pieces left make them flyable
        if (this.gamePhase == PhaseType.MOVING_PHASE && this.playerOnePieces.size() == 3) {
            makePiecesFlyable(this.playerOnePieces);
        }

        // Updating labels
        if (this.gamePhase == PhaseType.PLACING_PHASE) {
            this.infoLabel.setText("SET YOUR MEN ON THE BOARD");
        } else if (this.gamePhase == PhaseType.MOVING_PHASE) {
            this.infoLabel.setText("MOVE THE MEN TO FORM A MILL");
        }

        // if (in Moving phase) && (pieces are trapped), then declare winner
        if(this.gamePhase == PhaseType.MOVING_PHASE && checkWinnerPieces(this.playerOnePieces)){
            declareWinner(this.playerOnePieces);
        }else{
            // Otherwise, no one wins yet, switch turns
            switchPlayerTurn();
        }
    }
    /**
     * Increases the turn count and sets up the next turn
     */
    @Override
    protected void switchPlayerTurn() {
        // Switch to moving phase
        if (turn == TOTAL_PIECES) {
            this.infoLabel.setText("MOVE THE MEN TO FORM A MILL");
            this.gamePhase = PhaseType.MOVING_PHASE;
        }

        // increment the turn
        this.turn += 1;

        // Switching turns after removing a piece
        if (this.gamePhase == PhaseType.PLACING_PHASE && this.removingInProgress) {
            freezePieces(playerOnePieces);
            freezePieces(playerTwoPieces);
            this.removingInProgress = false;
            if (this.turn % 2 == 0) {
                this.turnLabel.setText("COMPUTER'S TURN");
                this.turnLabel.setTextFill(Color.DARKBLUE);
                executeComputerMove();
            } else {
                this.turnLabel.setText("PLAYER'S TURN");
                this.turnLabel.setTextFill(Color.DARKRED);
            }

        } else if (this.turn % 2 == 0 && this.gamePhase == PhaseType.PLACING_PHASE) {
            // Placing phase and blue's turn
            this.turnLabel.setText("COMPUTER'S TURN");
            this.turnLabel.setTextFill(Color.DARKBLUE);
            executeComputerMove();

        } else if (this.turn % 2 == 1 && this.gamePhase == PhaseType.PLACING_PHASE) {
            // Placing phase and red's turn
            this.turnLabel.setText("PLAYER'S TURN");
            this.turnLabel.setTextFill(Color.DARKRED);

        } else if (this.turn % 2 == 0 && this.gamePhase == PhaseType.MOVING_PHASE) {
            // Moving phase and Blue's turn
            freezePieces(playerOnePieces);
            freezePieces(playerTwoPieces);
            this.turnLabel.setText("COMPUTER'S TURN");
            this.turnLabel.setTextFill(Color.DARKBLUE);
            if(!checkWinnerMoves(playerTwoPieces)){
                //Computer makes their move
                executeComputerMove();
            }

        } else if (this.turn % 2 == 1 && this.gamePhase == PhaseType.MOVING_PHASE){
            // Moving phase and Red's turn
            freezePieces(playerOnePieces);
            freezePieces(playerTwoPieces);
            makePiecesMovable(playerOnePieces);
            this.turnLabel.setText("PLAYER'S TURN");
            this.turnLabel.setTextFill(Color.DARKRED);
            checkWinnerMoves(playerOnePieces); // Checks if blue pieces are trapped
        }
    }
    /**
     * Adds click events to this computer's pieces, so they can be removed by the human player
     * @param pieces list of computer pieces that are to be made removable
     */
    @Override
    protected void makePiecesRemovable(ArrayList<Piece> pieces) {
        for (Piece piece: pieces) {
            piece.freezePiece();
            piece.setOnMouseClicked(event -> {

                // Check if all the pieces are part of a mill.
                // If all are part of mill then should be able to remove them regardless.
                boolean pieceNotPartMill = false;
                for (Piece piece1 : pieces) {
                    if (!piece1.getIntersection().getIsPartMill()) {
                        pieceNotPartMill = true;
                        break;
                    }
                }

                // Piece is part of a mill AND pieces > 3; cannot remove
                if ((piece.getIntersection().getIsPartMill() && pieces.size() > 3) && pieceNotPartMill) {
                    System.out.println("Cannot remove a piece that is part of a mill.");
                }
                // (Piece not part of mill) OR (Piece part of mill and piece < 3); can remove
                else if ((!(piece.getIntersection().getIsPartMill()) ||
                        (piece.getIntersection().getIsPartMill() && pieces.size() <= 3)) ||
                        (!pieceNotPartMill)) {

                    // Remove piece from the board and from the players list
                    pieces.remove(piece);
                    this.piecesGroup.getChildren().remove(piece);

                    // Reset intersection pointers
                    piece.getIntersection().setPiece(null);
                    piece.setIntersection(null);

                    if (!pieceNotPartMill) {
                        checkForMills();
                    }

                    // Player has 3 pieces, can fly
                    if (this.gamePhase == PhaseType.MOVING_PHASE && pieces.size() == 3) {
                        makePiecesFlyable(pieces);
                        this.canCompFly = true;
                    }

                    // Updating labels
                    if (this.gamePhase == PhaseType.PLACING_PHASE) {
                        this.infoLabel.setText("SET YOUR MEN ON THE BOARD");
                    } else if (this.gamePhase == PhaseType.MOVING_PHASE) {
                        this.infoLabel.setText("MOVE THE MEN TO FORM A MILL");
                    }

                    // if (in Moving phase) && (pieces are trapped), then declare winner
                    if (this.gamePhase == PhaseType.MOVING_PHASE && checkWinnerPieces(pieces)) {
                        declareWinner(pieces);
                    } else {
                        // Otherwise, no one wins yet, switch turns
                        piece.playRemoveSound();
                        switchPlayerTurn();
                    }
                }
            });
        }
    }
    /**
     * Sets up the removal of a piece by adding click events and freezing the pieces of
     * appropriate players
     */
    @Override
    protected void setUpPieceRemove() {
        // Red forms a mill, remove Blue
        this.infoLabel.setText("MILL FORMED! REMOVE PIECE!");
        this.removingInProgress = true;
        makePiecesRemovable(playerTwoPieces);
        freezePieces(playerOnePieces);
    }

    /**
     * Method that declares the winner and changes the UI to convey the message
     * @param pieces the list of pieces of the losing player
     */
    @Override
    protected void declareWinner(ArrayList<Piece> pieces){
        // Play winning sound from mortal kombat - sound acquired from samplefocus.com
        Media media = new Media(getClass().getResource("/com/nmmfx/flawless_victory.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        this.infoLabel.setTextFill(Color.DARKVIOLET);
        // Check which player has won and update the info label to let the users know
        if(pieces.get(0).getColour() == PieceType.RED){
            this.infoLabel.setText("CONGRATULATIONS! COMPUTER WINS!");
        }else{
            this.infoLabel.setText("CONGRATULATIONS! PLAYER WINS!");
        }
        // Make sure the game cannot be played anymore by freezing the pieces
        freezePieces(this.playerOnePieces);
        freezePieces(this.playerTwoPieces);
    }

}
