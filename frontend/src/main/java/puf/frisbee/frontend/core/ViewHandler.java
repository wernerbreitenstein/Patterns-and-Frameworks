package puf.frisbee.frontend.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import puf.frisbee.frontend.view.LevelView;

public class ViewHandler {
    private Stage stage;
    private ViewModelFactory viewModelFactory;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.stage = new Stage();
        this.viewModelFactory = viewModelFactory;
    }

    public void start()
    {
        // Here we can change later to the waitScreen as first view
        openLevelView();
        this.stage.show();
    }

    private void openLevelView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/puf/frisbee/frontend/view/LevelView.fxml"));
        try {
            Parent root = loader.load();
            LevelView levelView = loader.getController();
            levelView.init(viewModelFactory.getLevelViewModel());
            this.stage.setTitle("Frisbee");
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("/puf/frisbee/frontend/css/level.css").toExternalForm());
            this.stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
