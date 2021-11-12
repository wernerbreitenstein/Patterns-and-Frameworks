package puf.frisbee.frontend.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import puf.frisbee.frontend.view.HighscoreView;
import puf.frisbee.frontend.view.LevelView;
import puf.frisbee.frontend.view.WaitingView;

public class ViewHandler {
	private Stage stage;
	private ViewModelFactory viewModelFactory;
	private final int sceneWidth = 1280;
	private final int sceneHeight = 720;

	public ViewHandler(ViewModelFactory viewModelFactory) {
		this.stage = new Stage();
		// TODO: use dependency injection later on
		this.viewModelFactory = viewModelFactory;
	}

	public void start() {
		openHighscoreView();
		this.stage.show();
	}

	/**
	 * Loads the view for a level.
	 * 
	 * @param level that should be loaded
	 */
	public void openLevelView(int level) {
		FXMLLoader levelViewLoader = new FXMLLoader();

		switch (level) {
		case 1:
		default:
			levelViewLoader.setLocation(getClass().getResource("/puf/frisbee/frontend/view/LevelView.fxml"));
		}

		try {
			Parent root = levelViewLoader.load();
			LevelView levelView = levelViewLoader.getController();
			levelView.init(viewModelFactory.getLevelViewModel(), this);
			this.stage.setTitle("Frisbee Level " + level);
			Scene scene = new Scene(root, sceneWidth, sceneHeight);
			this.stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openWaitingView() {
		FXMLLoader waitingViewLoader = new FXMLLoader();
		waitingViewLoader.setLocation(getClass().getResource("/puf/frisbee/frontend/view/WaitingView.fxml"));
		try {
			Parent root = waitingViewLoader.load();
			WaitingView waitingView = waitingViewLoader.getController();
			waitingView.init(viewModelFactory.getLevelViewModel(), this);
			this.stage.setTitle("Frisbee - waiting...");
			Scene scene = new Scene(root, sceneWidth, sceneHeight);
			this.stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openHighscoreView() {
		FXMLLoader highscoreViewLoader = new FXMLLoader();
		highscoreViewLoader.setLocation(getClass().getResource("/puf/frisbee/frontend/view/HighscoreView.fxml"));
		try {
			Parent root = highscoreViewLoader.load();
			HighscoreView highscoreView = highscoreViewLoader.getController();
			highscoreView.init(viewModelFactory.getHighscoreViewModel(),this);
			this.stage.setTitle("Frisbee");
			Scene scene = new Scene(root, sceneWidth, sceneHeight);
			this.stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
