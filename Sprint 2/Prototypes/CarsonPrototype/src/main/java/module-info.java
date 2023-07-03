module com.example.carsonjavafxprojectthree {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens ninemenmorris.game to javafx.fxml;
    exports ninemenmorris.game;
}