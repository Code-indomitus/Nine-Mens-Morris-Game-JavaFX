package ninemenmorris.game;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*{}
*
* */
public class Tile extends Rectangle {

    private Piece piece;
    private int tileType;
    private int pos;

    public boolean hasPiece(){
        return piece != null;
    }
    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public int getNull(){
        return this.pos;
    }

    //public void setTileType(Integer type){
        //this.tileType = type;
    //}

    public int getTileType(){
        return this.tileType;
    }


    public Tile(boolean intersection, int x, int y){
        setWidth(NineMenMorrisApp.TILE_SIZE);
        setHeight(NineMenMorrisApp.TILE_SIZE);
        //need to figure out how to change relocate function
        relocate(x* NineMenMorrisApp.TILE_SIZE, y* NineMenMorrisApp.TILE_SIZE);
        setFill(intersection ? new Color(0.9,0.1,0,0.2):
            new Color(0, 0.9, 0.2, 0.2));
        /**we set the tileType to be 0*/
        /*if (intersection == true){
            this.tileType = 0;
        }*/
        if (intersection == false){
            this.tileType = 1; // 2
        }
        this.pos = 1;
        //if (color == "intersection")
        //    setTileType();
    }
}
