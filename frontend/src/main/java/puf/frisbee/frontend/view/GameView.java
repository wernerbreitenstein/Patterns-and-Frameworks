package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class GameView {
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

	private GameViewModel gameViewModel;
	private ViewHandler viewHandler;

	public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
		this.gameViewModel = gameViewModel;
		this.viewHandler = viewHandler;

		this.topPanelController.init(gameViewModel);
		this.characterLeftController.init(gameViewModel);

		this.labelLevelSuccess.textProperty().bind(this.gameViewModel.getLabelLevelSuccessProperty());
		this.buttonLevelContinue.textProperty().bind(this.gameViewModel.getButtonLevelContinueTextProperty());
		this.levelSuccessDialog.visibleProperty().bind(this.gameViewModel.getLevelSuccessDialogOpenProperty());
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
		switch(event.getCode()) {
			case LEFT:
			case RIGHT:
				// TODO: implement
				// TODO: implement
				break;
			case A:
				this.gameViewModel.setCharacterLeftXPosition(-20);
				break;
			case D:
				this.gameViewModel.setCharacterLeftXPosition(20);
				break;
		}
	}
}
