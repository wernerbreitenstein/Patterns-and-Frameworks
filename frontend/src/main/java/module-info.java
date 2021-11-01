module puf.frisbee.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens puf.frisbee.frontend to javafx.fxml;
    exports puf.frisbee.frontend;

    opens puf.frisbee.frontend.model to javafx.fxml;
    exports puf.frisbee.frontend.model;

    opens puf.frisbee.frontend.viewmodel to javafx.fxml;
    exports puf.frisbee.frontend.viewmodel;

    opens puf.frisbee.frontend.view to javafx.fxml;
    exports puf.frisbee.frontend.view;
}