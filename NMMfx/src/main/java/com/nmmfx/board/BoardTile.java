package com.nmmfx.board;

import com.nmmfx.game.PlayerGame;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardTile extends Rectangle {

    private int tileX;

    private int tileY;

    /**
     * constructor for board tile
     * @param tileX
     * @param tileY
     */
    public BoardTile(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;

        setWidth(PlayerGame.TILE_SIZE);
        setHeight(PlayerGame.TILE_SIZE);
        relocate(tileX * PlayerGame.TILE_SIZE, tileY * PlayerGame.TILE_SIZE);
        setStroke(Color.rgb(255, 255, 255, 0.5));
        setStrokeWidth(1.5);
        setFill(Color.YELLOW);
        setOpacity(0);
        toBack();
    }

    /**
     * get the tile x coordinate
     * @return integer coordinate of tile x.
     */
    public int getTileX() {
        return tileX;
    }

    /**
     * get the tile y coordinate
     * @return integer coordinate of tile y.
     */
    public int getTileY() {
        return tileY;
    }

    /**
     * return if tile is an intersection or not
     * @return false as it is a normal tile
     */
    public boolean isIntersection() {
        return false;
    }
}
