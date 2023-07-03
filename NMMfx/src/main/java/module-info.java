module com.nmmfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
            
                            
    opens com.nmmfx to javafx.fxml;
    exports com.nmmfx;
    exports com.nmmfx.pieces;
    opens com.nmmfx.pieces to javafx.fxml;
    exports com.nmmfx.board;
    opens com.nmmfx.board to javafx.fxml;
    exports com.nmmfx.moves;
    opens com.nmmfx.moves to javafx.fxml;
    exports com.nmmfx.game;
    opens com.nmmfx.game to javafx.fxml;
    exports com.nmmfx.computer;
    opens com.nmmfx.computer to javafx.fxml;
}