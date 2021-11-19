package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.viewmodel.LevelViewModel;

public class CharacterLeftView {
    @FXML
    private ImageView character;

    private LevelViewModel levelViewModel;

    public void init(LevelViewModel levelViewModel) {
        this.levelViewModel = levelViewModel;
        this.character.layoutXProperty().bind(levelViewModel.getCharacterLeftXPositionProperty());
    }
}
