package ninemenmorris.game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {
    private PieceType type;

    private double mouseX, mouseY;
    private double oldX, oldY;

    public PieceType getType() {
        return type;
    }

    public double getOldX(){
        return oldX;
    }
    public double getOldY(){
        return oldY;
    }
    //PieceType type,
    public Piece(PieceType type, int x, int y){
        this.type = type;

        move(x, y);
        /**basically if u times with tile size, it will move to from 0,0 cause u reset it basically. */
        //relocate(x*TILE_SIZE, y*TILE_SIZE); //this is the one causeing the error.
        Ellipse ellipse = new Ellipse(NineMenMorrisApp.TILE_SIZE*0.3125, NineMenMorrisApp.TILE_SIZE*0.26);

        ellipse.setFill(type == PieceType.BLUE? Color.valueOf("#0000FF"):Color.valueOf("#FFFFFF"));



        ellipse.setTranslateX(NineMenMorrisApp.TILE_SIZE*0.3125/2);
        ellipse.setTranslateY(NineMenMorrisApp.TILE_SIZE*0.3125/2);



        getChildren().addAll(ellipse);
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();

        });

        setOnMouseDragged(e->{
            //why is this here shouldnt this logic be in the mouseletgo?
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);

        });
    }

    public void move(int x, int y){
        oldX = x* NineMenMorrisApp.TILE_SIZE;
        oldY = y* NineMenMorrisApp.TILE_SIZE;
        relocate(oldX, oldY);
        relocate(oldX, oldY);
        //to move successfully, we need to check the tiletype in tiles.
    }

    public void abortMove(){
        relocate(oldX, oldY);
    }


}
