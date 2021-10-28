package puf.frisbee.frontend.viewmodel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LevelViewModel {
	
	@FXML
	private Label labelCharacterLeft;
	
	@FXML
	private Label labelCharacterRight;
	
	@FXML
	private Label labelFrisbee;
	
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

}