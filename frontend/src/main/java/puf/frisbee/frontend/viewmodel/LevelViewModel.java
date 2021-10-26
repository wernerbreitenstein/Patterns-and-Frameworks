package puf.frisbee.frontend.viewmodel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.model.Level;

public class LevelViewModel {
	
	@FXML
	private Character character1;
	
	@FXML
	private Character character2;
	
	@FXML
	private Label labelCharacter1;
	
	@FXML
	private Label labelCharacter2;
	
	@FXML
	private Label labelFrisbee;
	
	@FXML
	private void handleCharacter1Clicked(MouseEvent event) {
		labelCharacter1.setText("Hey, ich bin Bonnie!");
//		System.out.println("Yes, you clicked me!");
	}
	
	@FXML
	private void handleCharacter2Clicked(MouseEvent event) {
		labelCharacter2.setText("Hey, ich bin Clyde!");
//		System.out.println("Yes, you clicked me!");
	}
	
	@FXML
	private void handleFrisbeeClicked(MouseEvent event) {
		labelFrisbee.setText("Los geht's!");
//		System.out.println("Yes, you clicked me!");
	}

}