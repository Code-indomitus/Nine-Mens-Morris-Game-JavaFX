package ninemenmorris.game;

import javafx.scene.paint.Color;

public class IntersectionTile extends Tile{

  private Piece piece = null;

  public IntersectionTile(int x, int y) {
   super(x, y);
   setFill(new Color(0, 0.9, 0.2, 0.2));

   // Events
    setOnMouseDragEntered(e -> {
      System.out.println("Drag entered");
      //
    });

    setOnMouseDragOver(e -> {
      System.out.println("Drag over");
      //
    });

    setOnMouseDragReleased(e -> {
      System.out.println("Drag released");
      System.out.println(x);
      System.out.println(y);
    });

    setOnMouseDragExited(e -> {
      //
    });

  }

  public void setPiece(Piece piece) {
    this.piece = piece;
  }
}
