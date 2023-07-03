package com.gameapp.trialproject;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameApp extends GameApplication {
    private Entity player;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Basic Game App");
        settings.setVersion("1.0");
        settings.setIntroEnabled(true);
        settings.setFullScreenAllowed(true);
        settings.setGameMenuEnabled(true);
        settings.setFullScreenFromStart(true);
    }
    @Override
    protected void initGame() {
        // What needs to be setup before game starts
        this.player = FXGL.entityBuilder()
                .at(300,300)
                .view(new Rectangle(25, 25, Color.BLUE))
                .buildAndAttach();

        // Adding background (Option 1)
        FXGL.getGameWorld().addEntityFactory(new GameFactory());
        FXGL.spawn("background");

//        // Adding background (Option 2)
//        Texture background = FXGL.texture("background.png"); // Texture is also a JavaFX Node
//        FXGL.getGameScene().addGameView(new GameView(background, -1));
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5); // move right 5 pixels
            FXGL.inc("pixelsMoved", +5); // update global variable
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5); // move right 5 pixels
            FXGL.inc("pixelsMoved", +5);
        });

        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5); // move right 5 pixels
            FXGL.inc("pixelsMoved", +5);
        });

        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5); // move right 5 pixels
            FXGL.inc("pixelsMoved", +5);
        });
    }

    @Override
    protected void initUI() {
        // NOTE: FXGL automatically adds Entity to scene graph, but not UI
        Text textDistance = new Text();
        textDistance.setTranslateX(50);
        textDistance.setTranslateY(100);

        FXGL.getGameScene().addUINode(textDistance);
        // System.out.println(FXGL.getGameScene().getGameWorld().getEntities());

        textDistance.textProperty() // Bind the UI to global variable for automatic updates
            .bind(FXGL.getWorldProperties()
                .intProperty("pixelsMoved")
                .asString());

        // Distance travelled title
        Text textTitleDistance = new Text();
        textTitleDistance.setText("Distance travelled (pixels):");
        textTitleDistance.setTranslateX(50);
        textTitleDistance.setTranslateY(80);
        FXGL.getGameScene().addUINode(textTitleDistance);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        // Global game variables
        vars.put("pixelsMoved", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
