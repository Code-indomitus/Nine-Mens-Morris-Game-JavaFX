package structure.board;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardFactory implements EntityFactory {

  public static final int BOARD_SIZE = 520;
  public static final int BOARD_HEIGHT = 13;
  public static final int TILE_SIZE = BOARD_SIZE/BOARD_HEIGHT;
  public static double BOARD_START_X = Math.floor(FXGL.getAppWidth()-500)/2;
  public static double BOARD_START_Y = Math.floor(FXGL.getAppHeight()-500)/2;

  @Spawns("background")
  public Entity spawnBackground(SpawnData data) {
    return FXGL.entityBuilder(data)
        .at(BOARD_START_X,
            BOARD_START_Y)
        .zIndex(-1)
        .view("board.png")
        .buildAndAttach();
  }

  @Spawns("tile")
  public Entity spawnTile(SpawnData data) {
    return FXGL.entityBuilder(data)
        .view(new Rectangle(BoardFactory.TILE_SIZE-1, BoardFactory.TILE_SIZE-1, new Color(0,0.3,0.8,0.1)))
        .build();
  }

  @Spawns("intersection")
  public Entity spawnIntersection(SpawnData data) {
    return FXGL.entityBuilder(data)
        .view(new Rectangle(BoardFactory.TILE_SIZE-1, BoardFactory.TILE_SIZE-1,
            new Color(0.6,0.1,0,0.1)))
        .onClick((entity) -> {
          System.out.println("Clicked!");
          System.out.println(entity);
          FXGL.spawn("wtoken",
              entity.getX(),
              entity.getY());
        })
        .build();
  }

  @Spawns("wtoken")
  public Entity spawnWToken(SpawnData data) {
    return FXGL.entityBuilder(data)
        .view("white_token.png")
        .zIndex(300)
        .build();
  }
}
