package ninemenmorris.game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

//520*520 // 13*13
/**so basically, the idea is we need to not initialize it at the brown tiles but another way is to hard code the positions,*/
public class NineMenMorrisApp extends Application {
    public static final double BACKGROUND_SIZE = 528;
    public static int WIDTH = 13;
    public static int HEIGHT = 13;
    public static final double TILE_SIZE = BACKGROUND_SIZE/(WIDTH-2);

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    public ArrayList<Tuple> posLst;
    public ArrayList<Tuple> invalidPos;



    private Piece makePiece(PieceType type, int x, int y){
        Piece piece = new Piece(type,x,y);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());
            System.out.println(newX);
            System.out.println(newY);
            MoveResult result = tryMove(piece, newX, newY);
            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());
            switch (result.getType()){
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    break;
            }
        });
        return piece;
    }
    private Parent CreateContent(){
        Pane root = new Pane();

        // Add board image
        ImageView background = new ImageView();
        background.setImage(new Image("/board_final.png"));
        background.setX(TILE_SIZE); // offset
        background.setY(TILE_SIZE);

        root.setPrefSize(WIDTH*TILE_SIZE, HEIGHT*TILE_SIZE);
        root.getChildren().addAll(tileGroup,pieceGroup,background);

        //init the board

        for (int y=1; y <9; y++){
            Tile tile = new Tile(true,0,y);
            // red tiles
            board[12][y] = tile;
            tileGroup.getChildren().add(tile);


            Piece piece = null;
            piece = makePiece(PieceType.BLUE,0,y);
            pieceGroup.getChildren().add(piece);


        }


        for (int y=1; y <9; y++){
            Tile tile = new Tile(true,12,y);
            board[12][y] = tile;
            tileGroup.getChildren().add(tile);

            Piece piece = null;
            piece = makePiece(PieceType.RED,12,y);
            pieceGroup.getChildren().add(piece);


        }
        //List<Tuple> posLst= new ArrayList<Tuple>();



        posLst = new ArrayList<Tuple>();

        //List posLstX = new ArrayList();
        //List posLstY = new ArrayList();
        //init the tokens below the board.
        for (int x=1; x< HEIGHT; x++){
            Piece piece = null;
            piece = makePiece(PieceType.RED,x,14);
            pieceGroup.getChildren().add(piece);
            piece = makePiece(PieceType.BLUE,x,15);
            /** maybe below we set it to tile? then maybe can?*/
            //tile.setPiece(piece);
            pieceGroup.getChildren().add(piece);
        }

        //init board for 9 man morris
        //init values of x and y for 9 man morris for all placable positions and then another one
        //another way is init on the diagonal and the horizontal and vertical

        //i think u keep the white bg then just init a circle in black color
        /**for (int x=1; x< HEIGHT-1; x++){
            if (x%2==1) {
                //Tile tile = new Tile(false,x,x);
                tileGroup.getChildren().add(new Tile(false,x,x));

                //posLstX.add(x);
                //posLstX.add(x);
                posLst.add(new Tuple(x,x));
            }
            Piece piece = null;
            piece = makePiece(PieceType.BLUE,x,x);*/


            /*if (y<=2 && (x+y)%2 != 0){
                piece = makePiece(PieceType.RED,x,y);
            }
            if (y>=5 && (x+y)%2 != 0){
                piece = makePiece(PieceType.BLUE,x,y);
            }*/

            /*if (piece != null){
                tile.setPiece(piece);
                pieceGroup.getChildren().add(piece);

            }*/
            // so we dont init it here, we init on the sides on the right and left most sides of the board.


        /**}*/

        /**Tile tile = new Tile(false,1,1);

        board[1][1] = tile;

        tileGroup.getChildren().add(tile);*/

        /**for (int x=12; x > -1; x--){
            if (x%2==1) {
                Tile tile = new Tile(false,x,WIDTH-x-1);
                tileGroup.getChildren().add(tile);
                posLst.add(new Tuple(x,WIDTH-x-1));


            }
        }

        for (int x=1; x<HEIGHT-1; x++){
            if (x%2==1) {
                Tile tile = new Tile(false,6,x);
                tileGroup.getChildren().add(tile);
                posLst.add(new Tuple(6,x));
            }
        }


        for (int x=1; x<HEIGHT-1; x++){
            if (x%2==1) {
                Tile tile = new Tile(false,x,6);
                tileGroup.getChildren().add(tile);



                //posLstX.add(x);
                //posLstX.add(x);
                posLst.add(new Tuple(x,6));
            }
        }
        System.out.println("valid positions");
        System.out.println(posLst);*/

        //posLst = new ArrayList<Tuple>();
        invalidPos = new ArrayList<Tuple>();

        for (int y=1; y< HEIGHT-1; y++){
            for (int x=1; x<WIDTH-1; x++){
                //if (board[x][y]. != 1){
                /*boolean flag = true;
                for (int k=0; k < posLst.size(); k++){
                    Tuple curr_val = posLst.get(k);
                    //System.out.println(curr_val);
                    if (curr_val.x == x && curr_val.y == y){
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    Tile tile = new Tile(true,x,y);
                    //System.out.println("white tile positions: ");
                    //System.out.println(x); System.out.println(y);
                    board[x][y] = tile;
                    invalidPos.add(new Tuple(x,y));
                    tileGroup.getChildren().add(tile);
                }*/
                /*Piece piece = null;
                if (y<=3 && (x+y)%2 != 0){
                    piece = makePiece(PieceType.RED,x,y);
                }
                if (y>=9 && (x+y)%2 != 0){
                    piece = makePiece(PieceType.BLUE,x,y);
                }*/

               /* if (piece != null){
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);

                }*/

            }
        }

        System.out.println("invalid positions");
        System.out.println(invalidPos);



        //init lines
        /*Line line = new Line();
        line.setStartX(150.0);
        line.setStartY(150.0);
        line.setEndX(550.0);
        line.setEndY(150.0);
        line.setStroke(Color.RED);
        line.setStrokeWidth(10.0);
        tileGroup.getChildren().add(line);

        //init the mid to be invalid
        Tile tile = new Tile(true,3,3);
        tileGroup.getChildren().add(tile);*/

        /**the box that positions token in the middel.  */

        //Tile tile = new Tile(false,0,0);
        //board[0][0] = tile;
        //tileGroup.getChildren().add(tile);

        //tile = new Tile(false,1,1);
        //board[1][1] = tile;
        //tileGroup.getChildren().add(tile);

        //tile = new Tile(false,3,3);
        //board[3][3] = tile;
        //tileGroup.getChildren().add(tile);


        //Tile tile1 = new Tile(false,0,0);

        Tile tile;
        for (int x=1; x< HEIGHT-1; x++){
            if (x%2==1) {
                tile = new Tile(false,x,x);
                board[x][x] = tile;
                tileGroup.getChildren().add(tile);



                posLst.add(new Tuple(x,x));
            }
            //Piece piece = null;
            //piece = makePiece(PieceType.BLUE,x,x);
        }

        for (int x=12; x > -1; x--){
             if (x%2==1) {
                 tile = new Tile(false,x,WIDTH-x-1);
                 board[x][WIDTH-x-1] = tile;
                 tileGroup.getChildren().add(tile);
                 posLst.add(new Tuple(x,WIDTH-x-1));


             }
         }

         for (int x=1; x<HEIGHT-1; x++){
             if (x%2==1) {
                 tile = new Tile(false,6,x);
                 board[6][x] = tile;
                 tileGroup.getChildren().add(tile);
                 posLst.add(new Tuple(6,x));
             }
         }


         for (int x=1; x<HEIGHT-1; x++){
             if (x%2==1) {
                 tile = new Tile(false,x,6);
                 board[x][6] = tile;
                 tileGroup.getChildren().add(tile);



                 //posLstX.add(x);
                 //posLstX.add(x);
                 posLst.add(new Tuple(x,6));
             }
         }
         System.out.println("valid positions");
         System.out.println(posLst);

        for (int y=1; y< HEIGHT-1; y++){
            for (int x=1; x<WIDTH-1; x++){
                //if (board[x][y]. != 1){
                boolean flag = true;
                for (int k=0; k < posLst.size(); k++){
                    Tuple curr_val = posLst.get(k);
                    //System.out.println(curr_val);
                    if (curr_val.x == x && curr_val.y == y){
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    tile = new Tile(true,x,y);
                    //System.out.println("white tile positions: ");
                    //System.out.println(x); System.out.println(y);
                    board[x][y] = tile;
                    invalidPos.add(new Tuple(x,y));
                    tileGroup.getChildren().add(tile);
                }
                /*Piece piece = null;
                if (y<=3 && (x+y)%2 != 0){
                    piece = makePiece(PieceType.RED,x,y);
                }
                if (y>=9 && (x+y)%2 != 0){
                    piece = makePiece(PieceType.BLUE,x,y);
                }*/

               /* if (piece != null){
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);

                }*/

            }
        }



        //tile1 = new Tile(false,3,3);

        //board[3][3] = tile1;

        //tileGroup.getChildren().add(tile1);

        /**for (int x=1; x< HEIGHT-1; x++){
            if (x%2==1) {
                //Tile tile = new Tile(false,x,x);
                tile = new Tile(false,x,x);
                tileGroup.getChildren().add(tile);

                //posLstX.add(x);
                //posLstX.add(x);
                posLst.add(new Tuple(x,x));
            }
            Piece piece = null;
            piece = makePiece(PieceType.BLUE,x,x);
        }*/

        /**for (int x=12; x > -1; x--){
            if (x%2==1) {
                //Tile tile = new Tile(false,x,WIDTH-x-1);
                tileGroup.getChildren().add(new Tile(false,x,WIDTH-x-1));
                posLst.add(new Tuple(x,WIDTH-x-1));


            }
        }*/








        //init the midddle of the validpos to a circle with
        //the colors will not be real it is just used for our logic. for US to understand.
        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY){
        /**This is where u should add the limitation cannot move on light tiles. **/
        /**so you should check your light tiles here only dark tiles allowed to move. */
        /*if (board[newX][newY].hasPiece()){
            return new MoveResult(MoveType.NONE);
        }*/
        /**where we add the movement for placement*/
        System.out.println(board[newX][newY].getTileType());
        if (board[newX][newY].getTileType() == 1){
            return new MoveResult(MoveType.NORMAL);
        }
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        /**even more movement restrictions (22.36)*/
        /**movedir identifies if moving down or up. */
        /*if (Math.abs(newX - x0)==1 && newY - y0 == piece.getType().moveDir){
            return new MoveResult(MoveType.NORMAL);
        }*/
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel){
        return (int) Math.floor((pixel+TILE_SIZE/2)/TILE_SIZE);
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 900, 900);
        Scene scene = new Scene(CreateContent());

        stage.setTitle("9 Man Morris Game");
        stage.setScene(scene);
        stage.show();
    }

    /*@Override
    protected void initInput(){

    }*/



    public static void main(String[] args) {

        launch();
    }
}