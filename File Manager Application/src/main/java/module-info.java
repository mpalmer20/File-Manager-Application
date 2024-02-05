module palmer.matthew.filehandler {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;
    requires java.sql; 
    requires jsch;
    

    opens palmer.matthew.filehandler to javafx.fxml;
    opens palmer.matthew.filehandler.controller to javafx.fxml;
    exports palmer.matthew.filehandler;
    exports palmer.matthew.filehandler.controller;
}
