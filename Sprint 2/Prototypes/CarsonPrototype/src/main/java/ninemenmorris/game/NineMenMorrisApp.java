package ninemenmorris.game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

//520*520 // 13*13
/**so basically, the idea is we need to not initialize it at the brown tiles but another way is to hard code the positions,*/
public class NineMenMorrisApp extends Application {
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 600;

    public static final int PIECE_PER_PLAYER = 9;

    public static final double BACKGROUND_SIZE = 528;
    public static int WIDTH = 13;
    public static int HEIGHT = 13;
    public static final double TILE_SIZE = BACKGROUND_SIZE/(WIDTH-2);

    private Board board = new Board();

    private VBox playerOnePieces = new VBox();
    private VBox playerTwoPieces = new VBox();

    private ArrayList<Coordinates> intersections = new ArrayList<>();

    private Parent CreateContent(){

        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        root.setPadding(new Insets(
            // Centre board and pieces
            (WINDOW_HEIGHT/2)-(BACKGROUND_SIZE/2), 0, 0,0
        ));

        // Add board image
        ImageView background = new ImageView();
        background.setImage(new Image("/board_final.png"));

        // Initialise board
        board = board.constructBoard();

        Pane boardLayer = new Pane();
        boardLayer.getChildren().add(board);
        boardLayer.getChildren().add(background);

        // Initialise tokens
        for (int i = 0; i < PIECE_PER_PLAYER; i++) {
            playerOnePieces.getChildren().add(new RedPiece());
            playerTwoPieces.getChildren().add(new BluePiece());
        }

        playerOnePieces.setPadding(new Insets(
            50,30,0,0)); // Insets T R B L
        playerTwoPieces.setPadding(new Insets(
            50,0,0,30));
        playerOnePieces.setSpacing(10);
        playerTwoPieces.setSpacing(10);

        root.setPrefSize(WIDTH*TILE_SIZE, HEIGHT*TILE_SIZE);
        root.getChildren().addAll(
            playerOnePieces,
            boardLayer,
            playerTwoPieces);

        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 900, 900);
        Scene scene = new Scene(CreateContent(), WINDOW_WIDTH, WINDOW_HEIGHT);

//        Scene scene = new Scene(CreateContent());

        stage.setTitle("9 Man Morris Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}