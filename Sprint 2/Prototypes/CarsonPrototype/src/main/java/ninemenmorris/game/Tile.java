package ninemenmorris.game;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*{}
*
* */
public abstract class Tile extends Rectangle {

    private Piece piece;
    private Coordinates coordinate;

    public Tile(int x, int y){
        // Set coordinates
        this.coordinate = new Coordinates(x, y);

        setWidth(NineMenMorrisApp.TILE_SIZE-2);
        setHeight(NineMenMorrisApp.TILE_SIZE-2);
        relocate(x* NineMenMorrisApp.TILE_SIZE, y* NineMenMorrisApp.TILE_SIZE);
    }

    public boolean hasPiece(){
        return piece != null;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }
}
