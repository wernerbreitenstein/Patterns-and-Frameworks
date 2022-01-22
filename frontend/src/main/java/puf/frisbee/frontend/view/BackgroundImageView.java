package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import puf.frisbee.frontend.viewmodel.GameViewModel;
import puf.frisbee.frontend.viewmodel.WaitingViewModel;

public class BackgroundImageView {
    /**
     * The imageview instance corresponding the specified FXML-element.
     */
    @FXML
    private ImageView backgroundImage;

    /**
     * The waiting view model instance.
     */
    private WaitingViewModel waitingViewModel;

    /**
     * The game view model instance.
     */
    private GameViewModel gameViewModel;

    /**
     * Init method used in waiting view, sets waiting view model and background image.
     *
     * @param waitingViewModel the waiting view model instance
     */
    public void init(WaitingViewModel waitingViewModel) {
        this.waitingViewModel = waitingViewModel;

        this.backgroundImage.imageProperty().bind(waitingViewModel.getBackgroundImageProperty());
    }

    /**
     * Init method used in game view, sets game view model and background image.
     *
     * @param gameViewModel the game view model instance
     */
    public void init(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;

        this.backgroundImage.imageProperty().bind(gameViewModel.getBackgroundImageProperty());
    }
}
