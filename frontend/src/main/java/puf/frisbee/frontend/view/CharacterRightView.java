package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class CharacterRightView {
    @FXML
    private Group character;

    private GameViewModel gameViewModel;

    public void init(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
        this.character.layoutXProperty().bind(gameViewModel.getCharacterRightXPositionProperty());
        this.character.layoutYProperty().bind(gameViewModel.getCharacterRightYPositionProperty());
    }
}
