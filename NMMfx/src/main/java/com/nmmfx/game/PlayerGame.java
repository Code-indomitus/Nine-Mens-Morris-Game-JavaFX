package com.nmmfx.game;

import com.nmmfx.board.*;
import com.nmmfx.moves.AdjacentMove;
import com.nmmfx.pieces.BluePiece;
import com.nmmfx.pieces.Piece;
import com.nmmfx.pieces.RedPiece;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PlayerGame extends Game{


    /**
     * constructor for game board
     */
    public PlayerGame() {
        super();
    }

    /**
     * allows intersection to be interacted with to spawn pieces in the placing phase when clicked
     * @param intersection intersection that is set up with piece spawning properties
     */
    @Override
    protected void setUpIntersection(Intersection intersection) {
        intersection.setOnMouseClicked(event -> {

            // Moving to sliding phase
            if (turn == TOTAL_PIECES) {
                this.infoLabel.setText("MOVE THE MEN TO FORM A MILL");
                this.gamePhase = PhaseType.MOVING_PHASE;
            }
            if (turn > TOTAL_PIECES || this.removingInProgress) {

            } else if (turn % 2 == 0 && !intersection.hasPiece()) {
                // Placing phase
                // Blue turn
                BluePiece bluePiece = new BluePiece(intersection.getTileX(), intersection.getTileY());
                bluePiece.setIntersection(intersection);
                bluePiece.setMoveType(new AdjacentMove());
                this.piecesGroup.getChildren().add(bluePiece);
                this.playerTwoPieces.add(bluePiece);
                intersection.setPiece(bluePiece);

                if (checkForMills()) {
                    setUpPieceRemove(); // Remove a piece
                }
                else {
                    switchPlayerTurn();
                }

            } else if (turn % 2 == 1 && !intersection.hasPiece()) {
                // Red turn
                RedPiece redPiece = new RedPiece(intersection.getTileX(), intersection.getTileY());
                redPiece.setIntersection(intersection);
                redPiece.setMoveType(new AdjacentMove());
                this.piecesGroup.getChildren().add(redPiece);
                this.playerOnePieces.add(redPiece);
                intersection.setPiece(redPiece);

                if (checkForMills()) {
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
     * makes a piece movable on the board
     * @param piece the piece that is made moveable
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
     * Increases the turn count and sets up the next turn
     */
    @Override
    protected void switchPlayerTurn() {
        this.turn += 1;

        // Switching turns after removing a piece
        if (this.gamePhase == PhaseType.PLACING_PHASE && this.removingInProgress) {
            freezePieces(playerOnePieces);
            freezePieces(playerTwoPieces);
            this.removingInProgress = false;
            if (this.turn % 2 == 0) {
                this.turnLabel.setText("BLUE'S TURN");
                this.turnLabel.setTextFill(Color.DARKBLUE);
            } else {
                this.turnLabel.setText("RED'S TURN");
                this.turnLabel.setTextFill(Color.DARKRED);
            }

        } else if (this.turn % 2 == 0 && this.gamePhase == PhaseType.PLACING_PHASE) {
            // Placing phase and blue's turn
            this.turnLabel.setText("BLUE'S TURN");
            this.turnLabel.setTextFill(Color.DARKBLUE);

        } else if (this.turn % 2 == 1 && this.gamePhase == PhaseType.PLACING_PHASE) {
            // Placing phase and red's turn
            this.turnLabel.setText("RED'S TURN");
            this.turnLabel.setTextFill(Color.DARKRED);

        } else if (this.turn % 2 == 0 && this.gamePhase == PhaseType.MOVING_PHASE) {
            // Moving phase and Blue's turn
            freezePieces(playerOnePieces);
            freezePieces(playerTwoPieces);
            makePiecesMovable(playerTwoPieces);
            this.turnLabel.setText("BLUE'S TURN");
            this.turnLabel.setTextFill(Color.DARKBLUE);
            checkWinnerMoves(playerTwoPieces); // Checks if red pieces are trapped

        } else if (this.turn % 2 == 1 && this.gamePhase == PhaseType.MOVING_PHASE){
            // Moving phase and Red's turn
            freezePieces(playerOnePieces);
            freezePieces(playerTwoPieces);
            makePiecesMovable(playerOnePieces);
            this.turnLabel.setText("RED'S TURN");
            this.turnLabel.setTextFill(Color.DARKRED);
            checkWinnerMoves(playerOnePieces); // Checks if blue pieces are trapped
        }
    }

    /**
     * Sets up the removal of a piece by adding click events and freezing the pieces of
     * appropriate players
     */
    @Override
    protected void setUpPieceRemove() {
        this.infoLabel.setText("MILL FORMED! REMOVE PIECE!");
        this.removingInProgress = true;
        if (this.turn % 2 == 0) {
            // Blue forms a mill, remove Red
            makePiecesRemovable(playerOnePieces);
            freezePieces(playerTwoPieces);
        } else {
            // Red forms a mill, remove Blue
            makePiecesRemovable(playerTwoPieces);
            freezePieces(playerOnePieces);
        }
    }

    /**
     * Adds click events to this player's pieces, so they can be removed
     * @param pieces list of a player's pieces that are to be made removable
     */
    @Override
    protected void makePiecesRemovable(ArrayList<Piece> pieces) {

        for (Piece piece: pieces) {
            piece.freezePiece();
//            piece.getMoveType().hideValidMoves(piece);
            piece.setOnMouseClicked(event -> {

                // Check if all the pieces are part of a mill.
                // If all are part of mill then should be able to remove them regardless.
                boolean pieceNotPartMill = false;
                for (Piece piece1: pieces) {
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
                    this.removingInProgress = false;

                    // Reset intersection pointers
                    piece.getIntersection().setPiece(null);
                    piece.setIntersection(null);

                    if (!pieceNotPartMill) {
                        checkForMills();
                    }

                    // Player has 3 pieces, can fly
                    if (this.gamePhase == PhaseType.MOVING_PHASE && pieces.size() == 3){
                        makePiecesFlyable(pieces);
                    }

                    // Updating labels
                    if (this.gamePhase == PhaseType.PLACING_PHASE) {
                        this.infoLabel.setText("SET YOUR MEN ON THE BOARD");
                    } else if (this.gamePhase == PhaseType.MOVING_PHASE) {
                        this.infoLabel.setText("MOVE THE MEN TO FORM A MILL");
                    }

                    // if (in Moving phase) && (pieces are trapped), then declare winner
                    if(this.gamePhase == PhaseType.MOVING_PHASE && checkWinnerPieces(pieces)){
                        declareWinner(pieces);
                    }else{
                        // Otherwise, no one wins yet, switch turns
                        piece.playRemoveSound();
                        switchPlayerTurn();
                    }
                }
            });
        }
    }
}
