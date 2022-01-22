package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.GameViewModel;
import puf.frisbee.frontend.viewmodel.WaitingViewModel;

public class BackgroundImageView {
    @FXML
    private ImageView backgroundImage;

    private WaitingViewModel waitingViewModel;
    private GameViewModel gameViewModel;
    private ViewHandler viewHandler;

    public void init(WaitingViewModel waitingViewModel, ViewHandler viewHandler) {
        this.waitingViewModel = waitingViewModel;
        this.viewHandler = viewHandler;

        this.backgroundImage.imageProperty().bind(waitingViewModel.getBackgroundImageProperty());
    }

    public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
        this.gameViewModel = gameViewModel;
        this.viewHandler = viewHandler;

        this.backgroundImage.imageProperty().bind(gameViewModel.getBackgroundImageProperty());
    }
}
