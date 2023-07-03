package ninemenmorris.game;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class Board extends Pane {

  private ArrayList<Tile> tiles = new ArrayList<>();
  private ArrayList<Coordinates> intersections = new ArrayList<>();
  private Group tileGroup = new Group();

  public Board() {}

  public Board constructBoard() {

    // Adding tiles
    // Intersections are found on
    // x and y = 1, 3, 5, 6, 7, 9, 11
    // x and y = 0, 2, 4, 5, 6, 8, 10

    // Determine intersection locations
    for (int x = 0; x < 11; x++) {
      for (int y = 0; y < 11; y++) {

        // Outer ring of intersections
        if (Arrays.asList(0, 5, 10).contains(x) &&
            Arrays.asList(0, 5, 10).contains(y)) {
          if (!(x == 5 && y == 5)) {
            this.intersections.add(new Coordinates(x, y));
          }
        }

        // Middle ring of intersections
        if (Arrays.asList(2, 5, 8).contains(x) &&
            Arrays.asList(2, 5, 8).contains(y)) {
          if (!(x == 5 && y == 5)) {
            this.intersections.add(new Coordinates(x, y));
          }
        }

        // Inner ring of intersections
        if (Arrays.asList(4, 5, 6).contains(x) &&
            Arrays.asList(4, 5, 6).contains(y)) {
          if (!(x == 5 && y == 5)) {
            this.intersections.add(new Coordinates(x, y));
          }
        }
      }
    }

    // Construct board
    for (int x = 0; x < 11; x++) {
      for (int y = 0; y < 11; y++) {
        boolean isIntersection = false;
        for (Coordinates coordinates : intersections) {
          // determine if this coordinate is an intersection
          if (coordinates.getX() == x && coordinates.getY() == y) {
            isIntersection = true;
          }
        }
        if (isIntersection) {
          this.getChildren().add(new IntersectionTile(x, y));
//          tileGroup.getChildren().add(new IntersectionTile(x, y));
        } else {
          this.getChildren().add(new NormalTile(x, y));
//          tileGroup.getChildren().add(new NormalTile(x, y));
        }
      }
    }
   return this;
  }

  public Group getTileGroup() {
    return this.tileGroup;
  }
}
