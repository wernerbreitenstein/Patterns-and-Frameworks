module puf.frisbee.frontend {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.base;
	requires java.net.http;
	requires com.fasterxml.jackson.databind;
    requires dotenv.java;
    requires java.desktop;

    opens puf.frisbee.frontend to javafx.fxml;
	exports puf.frisbee.frontend;

	opens puf.frisbee.frontend.core to javafx.fxml;
	exports puf.frisbee.frontend.core;

	opens puf.frisbee.frontend.model to javafx.fxml;
	exports puf.frisbee.frontend.model;

	opens puf.frisbee.frontend.viewmodel to javafx.fxml;
	exports puf.frisbee.frontend.viewmodel;

	opens puf.frisbee.frontend.view to javafx.fxml;
	exports puf.frisbee.frontend.view;

	// needed for jackson databind
	exports puf.frisbee.frontend.network;
}