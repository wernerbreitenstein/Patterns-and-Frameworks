package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CharacterLeftView {
    @FXML
    private Label labelCharacterLeft;

    @FXML
    private void handleCharacterLeftClicked(MouseEvent event) {
        labelCharacterLeft.setText("Hey, ich bin Bonnie!");
    }
}
