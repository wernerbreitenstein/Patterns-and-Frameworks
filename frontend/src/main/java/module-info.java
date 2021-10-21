module patternsandframeworks.frontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens patternsandframeworks.frontend to javafx.fxml;
    exports patternsandframeworks.frontend;
}