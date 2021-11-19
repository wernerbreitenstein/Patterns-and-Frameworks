package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class GameOverView {
	@FXML
	private StackPane gameOverView;

	@FXML
	private Button buttonYes;

	@FXML
	private Button buttonNo;
	
	@FXML
	private void handleButtonYesClicked(ActionEvent event) {
		System.out.println("Button YES clicked.");
	}

	@FXML
	private void handleButtonNoClicked(ActionEvent event) {
		System.out.println("Button NO clicked.");
	}
}
