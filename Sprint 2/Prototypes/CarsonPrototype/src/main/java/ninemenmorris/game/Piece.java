package ninemenmorris.game;
import static ninemenmorris.game.NineMenMorrisApp.TILE_SIZE;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

public abstract class Piece extends Circle {

    private double startX;
    private double startY;
    private double movedX;
    private double movedY;

    //PieceType type,
    public Piece(){
        setRadius((TILE_SIZE/2)-5);

        // Events
        setOnMousePressed(e -> {
            startX = e.getSceneX() - getTranslateX();
            startY = e.getSceneY() - getTranslateY();
        });

        setOnMouseDragged(e -> {
            this.setTranslateX(e.getSceneX() - startX);
            this.setTranslateY(e.getSceneY() - startY);
        });

        setOnMouseReleased(e -> {
            int moveX = toBoard(getLayoutX());
            int moveY = toBoard(getLayoutY());
            System.out.println(getLayoutX());// WHY IS THIS always the same?
            System.out.println(getLayoutY());
            System.out.println(moveX);
            System.out.println(moveY);
            move(moveX, moveY);
        });
    }

    private int toBoard(double pixel) {
        return (int) Math.floor((pixel+TILE_SIZE/2)/TILE_SIZE);
    }

    private void move(int x, int y) {
        System.out.println("Relocating");
        relocate(x* TILE_SIZE, y*TILE_SIZE);
    }

    private void abort() {
        relocate (startX, startY);
    }
}
