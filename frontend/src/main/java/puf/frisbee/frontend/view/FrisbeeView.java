package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class FrisbeeView {
    /**
     * Frisbee element in fxml.
     */
    @FXML
    private ImageView frisbee;

    /**
     * The game view model instance.
     */
    private GameViewModel gameViewModel;

    /**
     * Init method, sets the game view model instance and binds the frisbee
     * position in the game to the position in the game view model.
     *
     * @param gameViewModel the game view model instance
     */
    public void init(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
        this.frisbee.layoutXProperty().bind(
                gameViewModel.getFrisbeeXPositionProperty());
        this.frisbee.layoutYProperty().bind(
                gameViewModel.getFrisbeeYPositionProperty());
    }

    /**
     * This method is executed on click at the frisbee. It triggers the
     * throw functionality in the game view model.
     *
     * @param event mouse event
     */
    @FXML
    private void handleFrisbeeClicked(MouseEvent event) {
        this.gameViewModel.throwFrisbee();
    }
}
