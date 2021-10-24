module puf.frisbee.frontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens puf.frisbee.frontend to javafx.fxml;
    exports puf.frisbee.frontend;
}