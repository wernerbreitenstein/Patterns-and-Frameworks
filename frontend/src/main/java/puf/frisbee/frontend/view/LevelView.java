package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.viewmodel.LevelViewModel;

public class LevelView {
    @FXML
    private Label labelCharacterLeft;

    @FXML
    private Label labelCharacterRight;

    @FXML
    private Label labelFrisbee;

    @FXML
    private Label labelCountdown;

    private LevelViewModel levelViewModel;

    public void init(LevelViewModel levelViewModel) {
        // also later we will have the ViewHandler as dependency, so we can load different views when clicking a button
        // (e.g. after confirming game over)
        this.levelViewModel = levelViewModel;
        this.labelCountdown.textProperty().bind(levelViewModel.getCountdownProperty());
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
}
