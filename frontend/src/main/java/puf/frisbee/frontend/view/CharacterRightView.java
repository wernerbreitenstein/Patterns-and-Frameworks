package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class CharacterRightView {
    @FXML
    private ImageView character;

    private GameViewModel gameViewModel;

    public void init(GameViewModel gameViewModel) {
        System.out.println("test");
        this.gameViewModel = gameViewModel;
        this.character.layoutXProperty().bind(gameViewModel.getCharacterRightXPositionProperty());
        this.character.layoutYProperty().bind(gameViewModel.getCharacterRightYPositionProperty());
    }
}
