package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.LevelViewModel;

public class LevelView {
	@FXML
	private VBox modalRoot;

	@FXML
	private GridPane levelSuccessDialog;
	
	@FXML
	private Label labelPlayerLeft;

	@FXML
	private Label labelPlayerRight;

	@FXML
	private Label labelCharacterLeft;

	@FXML
	private Label labelCharacterRight;

	@FXML
	private Label labelFrisbee;

	@FXML
	private Label labelCountdown;
	
	@FXML
	private Label labelLevel;
	
	@FXML
	private Label labelScore;

	@FXML
	private Label labelLevelSuccess;

	@FXML
	private Button buttonLevelContinue;

	private LevelViewModel levelViewModel;
	private ViewHandler viewHandler;

	public void init(LevelViewModel levelViewModel, ViewHandler viewHandler) {
		this.levelViewModel = levelViewModel;
		this.viewHandler = viewHandler;

		this.labelCountdown.textProperty().bind(this.levelViewModel.getLabelCountdownProperty());
		this.labelLevelSuccess.textProperty().bind(this.levelViewModel.getLabelLevelSuccessProperty());
		this.buttonLevelContinue.textProperty().bind(this.levelViewModel.getButtonLevelContinueTextProperty());

		this.levelSuccessDialog.visibleProperty().bind(this.levelViewModel.getLevelSuccessDialogOpenProperty());
	}

	@FXML
	private void handleCharacterLeftClicked(MouseEvent event) {
		labelCharacterLeft.setText("Hey, ich bin Bonnie!");
	}

	@FXML
	private void handleCharacterRightClicked(MouseEvent event) {
		labelCharacterRight.setText("Hey, ich bin Clyde!");
	}

	@FXML
	private void handleFrisbeeClicked(MouseEvent event) {
		labelFrisbee.setText("Los geht's!");
	}

	@FXML
	private void handleLevelContinueClicked(ActionEvent event) {
		levelViewModel.continueLevel();
		this.viewHandler.openLevelView(this.levelViewModel.getLevel());
	}
}
