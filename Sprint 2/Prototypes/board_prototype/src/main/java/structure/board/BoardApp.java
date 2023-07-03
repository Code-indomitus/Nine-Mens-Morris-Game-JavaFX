package structure.board;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import java.util.ArrayList;

public class BoardApp extends GameApplication {

  public static ArrayList<Coordinates> intersections = new ArrayList<Coordinates>();

  @Override
  protected void initSettings(GameSettings settings) {
    settings.setTitle("Board Prototype");
    settings.setWidth(1280);
    settings.setHeight(720);
//    settings.setFullScreenAllowed(true);
//    settings.setFullScreenFromStart(true);
  }

  @Override
  protected void initGame() {
    // Create background
    FXGL.getGameWorld().addEntityFactory(new BoardFactory());
    FXGL.spawn("background");

    // Build board
    // Find intersections
    intersections.add(new Coordinates(0,0));
    intersections.add(new Coordinates(0,6));
    intersections.add(new Coordinates(0,12));

    intersections.add(new Coordinates(2,2));
    intersections.add(new Coordinates(2,6));
    intersections.add(new Coordinates(2,10));

    intersections.add(new Coordinates(4,4));
    intersections.add(new Coordinates(4,6));
    intersections.add(new Coordinates(4,8));

    intersections.add(new Coordinates(6,0));
    intersections.add(new Coordinates(6,2));
    intersections.add(new Coordinates(6,4));
    intersections.add(new Coordinates(6,8));
    intersections.add(new Coordinates(6,10));
    intersections.add(new Coordinates(6,12));

    intersections.add(new Coordinates(8,4));
    intersections.add(new Coordinates(8,6));
    intersections.add(new Coordinates(8,8));

    intersections.add(new Coordinates(10,2));
    intersections.add(new Coordinates(10,6));
    intersections.add(new Coordinates(10,10));

    intersections.add(new Coordinates(12,0));
    intersections.add(new Coordinates(12,6));
    intersections.add(new Coordinates(12,12));

//    for (Coordinates coordinates : intersections) {
//      System.out.println(coordinates.getX() + "," + coordinates.getY());
//    }

    // Place tiles
    for (int x = 0; x < BoardFactory.BOARD_HEIGHT; x++) {
      for (int y = 0; y < BoardFactory.BOARD_HEIGHT; y++){

        Coordinates tileCoordinates = new Coordinates(x,y);
        boolean isIntersection = false;

        // determine if this coordinate is an intersection
        for (Coordinates coordinates : intersections) {
          if (coordinates.getX() == tileCoordinates.getX()
              && coordinates.getY() == tileCoordinates.getY()) {
            isIntersection = true;
          }
        }

        // print one tile
        if (isIntersection) {
          FXGL.spawn("intersection",
              (BoardFactory.BOARD_START_X + x * BoardFactory.TILE_SIZE),
              (BoardFactory.BOARD_START_Y + y * BoardFactory.TILE_SIZE));
        } else {
          Entity tile = FXGL.spawn("tile",
              (BoardFactory.BOARD_START_X + x * BoardFactory.TILE_SIZE),
              (BoardFactory.BOARD_START_Y + y * BoardFactory.TILE_SIZE));

          tile.addComponent(tileCoordinates); // add coordinates for each tile

//          System.out.println(tile.getComponent(Coordinates.class).getX() + ","
//              + tile.getComponent(Coordinates.class).getY());
        }
      }
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
