package puf.frisbee.frontend.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.GameViewModel;

import java.beans.PropertyChangeEvent;

public class GameView {
	@FXML
	private StackPane quitConfirmDialog;

	@FXML
	private StackPane gameOverDialog;

	@FXML
	private StackPane levelSuccessDialog;

	@FXML
	private StackPane gameSuccessDialog;

	@FXML
	private StackPane disconnectDialog;

	@FXML
	private Label labelLevelSuccess;

	@FXML
	private Button buttonLevelContinue;

	@FXML
	private BackgroundImageView backgroundImageController;

	@FXML
	private TopPanelView topPanelController;

	@FXML
	private BottomPanelView bottomPanelController;

	@FXML
	private CharacterLeftView characterLeftController;

	@FXML
	private CharacterRightView characterRightController;

	@FXML
	private FrisbeeView frisbeeController;

	private GameViewModel gameViewModel;
	private ViewHandler viewHandler;

	public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
		this.gameViewModel = gameViewModel;
		this.viewHandler = viewHandler;

		this.backgroundImageController.init(gameViewModel);

		this.topPanelController.init(gameViewModel, viewHandler);
		this.bottomPanelController.init(gameViewModel, viewHandler);
		this.characterLeftController.init(gameViewModel);
		this.characterRightController.init(gameViewModel);
		this.frisbeeController.init(gameViewModel);

		this.labelLevelSuccess.textProperty().bind(this.gameViewModel.getLabelLevelSuccessProperty());
		this.buttonLevelContinue.textProperty().bind(this.gameViewModel.getButtonLevelContinueTextProperty());
		this.levelSuccessDialog.visibleProperty().bind(this.gameViewModel.getLevelSuccessDialogProperty());
		this.gameOverDialog.visibleProperty().bind(this.gameViewModel.getGameOverDialogProperty());
		this.quitConfirmDialog.visibleProperty().bind(this.gameViewModel.getQuitConfirmDialogProperty());
		this.gameSuccessDialog.visibleProperty().bind(this.gameViewModel.getGameSuccessDialogProperty());
		this.disconnectDialog.visibleProperty().bind(this.gameViewModel.getDisconnectDialogProperty());

		// subscribe also to changes of the "game running status", triggered when true (e.g. the other character continues the next level)
		this.gameViewModel.addPropertyChangeListener(this::redirectToGame);
	}

	// redirect opens game view
	private void redirectToGame(PropertyChangeEvent event) {
		// add execution to javafx application
		Platform.runLater(() -> this.viewHandler.openGameView());
	}

	@FXML
	private void handleKeyPressed(KeyEvent event) {
		switch (event.getCode()) {
		case LEFT -> this.gameViewModel.moveCharacterLeft();
		case RIGHT -> this.gameViewModel.moveCharacterRight();
		case UP -> this.gameViewModel.jumpCharacter();
		}
	}

	@FXML
	private void handleKeyReleased(KeyEvent event) {
		switch (event.getCode()) {
		case LEFT -> this.gameViewModel.stopCharacterMoveLeft();
		case RIGHT -> this.gameViewModel.stopCharacterMoveRight();
		}
	}

	@FXML
	private void handleButtonLevelSuccessContinue(ActionEvent event) {
		this.gameViewModel.saveAfterLevelSucceeded();
		this.gameViewModel.continueGame();
		this.viewHandler.openGameView();
	}

	@FXML
	private void handleButtonLevelSuccessQuit(ActionEvent event) {
		this.gameViewModel.saveAfterLevelSucceeded();
		this.gameViewModel.disconnect();
		this.viewHandler.openStartView();
	}

	@FXML
	private void handleButtonGameSuccessQuit(ActionEvent event) {
		// the game ends here
		this.gameViewModel.saveAfterGameSucceeded();
		this.viewHandler.openStartView();
	}

	@FXML
	private void handleButtonGameOverQuit(ActionEvent event) {
		// the game ends here
		this.gameViewModel.saveAfterGameOver();
		this.viewHandler.openStartView();
	}

	@FXML
	private void handleButtonQuitGameContinue(ActionEvent event) {
		this.gameViewModel.continueGame();
		this.viewHandler.openGameView();
	}

	@FXML
	private void handleButtonQuitGame(ActionEvent event) {
		this.gameViewModel.disconnect();
		this.viewHandler.openStartView();
	}
}
