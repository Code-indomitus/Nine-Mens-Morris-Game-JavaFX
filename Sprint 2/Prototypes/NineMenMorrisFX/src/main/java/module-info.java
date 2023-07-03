module com.ninemenmorris.ninemenmorrisfx {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.ninemenmorris.ninemenmorrisfx to javafx.fxml;
    exports com.ninemenmorris.ninemenmorrisfx;
}