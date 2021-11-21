package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class GameView {
	@FXML
	private Button buttonYes_ReturnToWaitingView;
	
	@FXML
	private Button buttonNo_QuitGame;
	
	@FXML
	private StackPane gameOverDialog;
	
	@FXML
	private GridPane levelSuccessDialog;

	@FXML
	private Label labelFrisbee;

	@FXML
	private Label labelLevelSuccess;

	@FXML
	private Button buttonLevelContinue;

	@FXML
	private TopPanelView topPanelController;

	@FXML
	private CharacterLeftView characterLeftController;

	@FXML
	private CharacterRightView characterRightController;

	private GameViewModel gameViewModel;
	private ViewHandler viewHandler;

	public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
		this.gameViewModel = gameViewModel;
		this.viewHandler = viewHandler;

		this.topPanelController.init(gameViewModel);
		this.characterLeftController.init(gameViewModel);
		this.characterRightController.init(gameViewModel);

		this.labelLevelSuccess.textProperty().bind(this.gameViewModel.getLabelLevelSuccessProperty());
		this.buttonLevelContinue.textProperty().bind(this.gameViewModel.getButtonLevelContinueTextProperty());
		this.levelSuccessDialog.visibleProperty().bind(this.gameViewModel.getLevelSuccessDialogOpenProperty());
		this.gameOverDialog.visibleProperty().bind(this.gameViewModel.getGameOverDialogOpenProperty());
	}

	@FXML
	private void handleFrisbeeClicked(MouseEvent event) {
		this.gameViewModel.addScore(1);
		labelFrisbee.setText("Los geht's!");
	}

	@FXML
	private void handleLevelContinueClicked(ActionEvent event) {
		gameViewModel.continueLevel();
		this.viewHandler.openGameView(this.gameViewModel.getLevel());
	}

	@FXML
	private void handleKeyPressed(KeyEvent event) {
		switch (event.getCode()) {
			case LEFT -> this.gameViewModel.moveCharacterLeft("right");
			case RIGHT -> this.gameViewModel.moveCharacterRight("right");

			// TODO: this will be removed in the future, the second character position will probably come via websocket
			case A -> this.gameViewModel.moveCharacterLeft("left");
			case D -> this.gameViewModel.moveCharacterRight("left");
		}
	}

	@FXML
	private void handleKeyReleased(KeyEvent event) {
		switch (event.getCode()) {
			case LEFT -> this.gameViewModel.stopCharacterMoveLeft("right");
			case RIGHT -> this.gameViewModel.stopCharacterMoveRight("right");

			// TODO: this will be removed in the future, the second character position will probably come via websocket
			case A -> this.gameViewModel.stopCharacterMoveLeft("left");
			case D -> this.gameViewModel.stopCharacterMoveRight("left");
		}
	}
	
	@FXML
	private void handleButtonYesClicked(ActionEvent event) {
		this.viewHandler.openWaitingView();
	}
	
	@FXML
	private void handleButtonNoClicked(ActionEvent event) {
		this.viewHandler.end();
	}
}
