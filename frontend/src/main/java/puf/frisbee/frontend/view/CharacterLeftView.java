package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class CharacterLeftView {
    /**
     * Character element in fxml.
     */
    @FXML
    private Group character;

    /**
     * Game view model instance.
     */
    private GameViewModel gameViewModel;

    /**
     * Init method, sets the game view model and the character position in
     * the game.
     *
     * @param gameViewModel the game view model instance
     */
    public void init(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
        this.character.layoutXProperty().bind(
                gameViewModel.getCharacterLeftXPositionProperty());
        this.character.layoutYProperty().bind(
                gameViewModel.getCharacterLeftYPositionProperty());
    }
}
