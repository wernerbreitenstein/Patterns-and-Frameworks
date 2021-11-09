package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CharacterRightView {
    @FXML
    private Label labelCharacterRight;

    @FXML
    private void handleCharacterRightClicked(MouseEvent event) {
        labelCharacterRight.setText("Hey, ich bin Clyde!");
    }
}
