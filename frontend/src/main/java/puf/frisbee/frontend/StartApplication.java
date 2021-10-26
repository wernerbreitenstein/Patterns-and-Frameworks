package puf.frisbee.frontend;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class StartApplication extends Application {
    @Override
    public void start(Stage stage) {
    	try {
    		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("view/LevelView.fxml"));
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("css/level.css").toExternalForm());
            stage.setTitle("Frisbee");
            stage.setScene(scene);
            stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        launch();
    }
}