module puf.frisbee.frontend {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;


    opens puf.frisbee.frontend to javafx.fxml;
    exports puf.frisbee.frontend;
    
    /* Da das Package 'puf.frisbee.frontend.model' noch leer ist, wurden
     * die Anweisungen auskommentiert, um Fehlermeldungen zu vermeiden.
     */
//    opens puf.frisbee.frontend.model to javafx.fxml;
//    exports puf.frisbee.frontend.model;
    
    opens puf.frisbee.frontend.viewmodel to javafx.fxml;
    exports puf.frisbee.frontend.viewmodel;
}