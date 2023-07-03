package com.ninemenmorris.ninemenmorrisfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Nine Men's Morris");
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
//        primaryStage.setFullScreen(true);
        primaryStage.setScene(createGameScene());
        primaryStage.show();

    }

    public Scene createGameScene() {
        GameBoard gameBoard = new GameBoard();
        Scene gameScene = new Scene(gameBoard.createGameScene());
        return gameScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
