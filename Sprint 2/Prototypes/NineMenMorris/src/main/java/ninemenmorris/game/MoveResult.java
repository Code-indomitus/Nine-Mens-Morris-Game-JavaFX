package ninemenmorris.game;

public class MoveResult {
    private MoveType type;

    private Piece piece;

    public MoveType getType(){
        return type;
    }

    public Piece getPiece(){
        return piece;
    }


    //for normal movement
    public MoveResult(MoveType type){
        this(type, null);
    }

    //for killing
    public MoveResult(MoveType type, Piece piece){
        this.type = type;
        this.piece = piece;
    }

}
