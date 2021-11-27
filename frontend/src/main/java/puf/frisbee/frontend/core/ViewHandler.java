package puf.frisbee.frontend.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import puf.frisbee.frontend.view.StartView;
import puf.frisbee.frontend.view.GameView;
import puf.frisbee.frontend.view.WaitingView;

public class ViewHandler {
	private Stage stage;
	private ViewModelFactory viewModelFactory;

	public ViewHandler(ViewModelFactory viewModelFactory) {
		this.stage = new Stage();
		this.viewModelFactory = viewModelFactory;
	}

	public void start() {
		openStartView();
		this.stage.show();
	}
	
	public void end() {
		this.stage.close();
	}

	/**
	 * Loads the view for the game.
	 * 
	 * @param level that should be loaded via css
	 */
	public void openGameView(int level) {
		FXMLLoader gameViewLoader = new FXMLLoader();
		gameViewLoader.setLocation(getClass().getResource("/puf/frisbee/frontend/view/GameView.fxml"));

		// TODO: load additional background css depending on level

		try {
			Parent root = gameViewLoader.load();
			GameView gameView = gameViewLoader.getController();
			gameView.init(viewModelFactory.getGameViewModel(), this);
			this.stage.setTitle("Frisbee Level " + level);
			Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
			this.stage.setScene(scene);
			root.requestFocus();
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
			waitingView.init(viewModelFactory.getGameViewModel(), this);
			this.stage.setTitle("Frisbee - waiting...");
			Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
			this.stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openStartView() {
		FXMLLoader startViewLoader = new FXMLLoader();
		startViewLoader.setLocation(getClass().getResource("/puf/frisbee/frontend/view/StartView.fxml"));
		try {
			Parent root = startViewLoader.load();
			StartView startView = startViewLoader.getController();
			startView.init(viewModelFactory.getHighscoreViewModel(),this);
			this.stage.setTitle("Frisbee");
			Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
			this.stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
