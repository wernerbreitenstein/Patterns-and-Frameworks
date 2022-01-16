package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.GameViewModel;

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
	private Label labelLevelSuccess;

	@FXML
	private Button buttonLevelContinue;

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

	@FXML
	private ImageView level01BackgroundImage;

	@FXML
	private ImageView level02BackgroundImage;

	@FXML
	private ImageView level03BackgroundImage;

	private GameViewModel gameViewModel;
	private ViewHandler viewHandler;

	public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
		this.gameViewModel = gameViewModel;
		this.viewHandler = viewHandler;

		this.level01BackgroundImage.visibleProperty().bind(this.gameViewModel.getShowLevel01BackgroundImageProperty());
		this.level02BackgroundImage.visibleProperty().bind(this.gameViewModel.getShowLevel02BackgroundImageProperty());
		this.level03BackgroundImage.visibleProperty().bind(this.gameViewModel.getShowLevel03BackgroundImageProperty());

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
		this.viewHandler.openGameView();
	}

	@FXML
	private void handleButtonLevelSuccessQuit(ActionEvent event) {
		this.gameViewModel.saveAfterLevelSucceeded();
		this.viewHandler.openStartView();
	}

	@FXML
	private void handleButtonGameSuccessQuit(ActionEvent event) {
		this.gameViewModel.saveAfterGameSucceeded();
		this.viewHandler.openStartView();
	}

	@FXML
	private void handleButtonGameOverQuit(ActionEvent event) {
		this.gameViewModel.saveAfterGameOver();
		this.viewHandler.openStartView();
	}

	@FXML
	private void handleButtonQuitGameContinue(ActionEvent event) {
		this.gameViewModel.continueGame();
		this.viewHandler.openGameView();
	}

	@FXML
	private void handleButtonQuitGameQuit(ActionEvent event) {
		this.viewHandler.openStartView();
	}
}
