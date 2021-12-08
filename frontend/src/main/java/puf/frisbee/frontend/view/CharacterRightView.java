package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class CharacterRightView {
    @FXML
    private Group character;

    @FXML
    private Circle catcherRightHand;

    @FXML
    private Circle catcherLeftHand;

    private GameViewModel gameViewModel;

    public void init(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
        this.character.layoutXProperty().bind(gameViewModel.getCharacterRightXPositionProperty());
        this.character.layoutYProperty().bind(gameViewModel.getCharacterRightYPositionProperty());
    }
}
