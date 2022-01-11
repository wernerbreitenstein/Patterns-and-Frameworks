package puf.frisbee.frontend.view;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.WaitingViewModel;

public class WaitingView {
    @FXML
    private TopPanelView topPanelController;

    @FXML
    private BottomPanelView bottomPanelController;

    @FXML
    private ImageView level01BackgroundImage;

    @FXML
    private ImageView level02BackgroundImage;

    @FXML
    private ImageView level03BackgroundImage;

    @FXML
    private ImageView frisbee;

    @FXML
    private Button buttonStart;

    private WaitingViewModel waitingViewModel;
    private ViewHandler viewHandler;

    public void init(WaitingViewModel waitingViewModel, ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
        this.waitingViewModel = waitingViewModel;

        this.level01BackgroundImage.visibleProperty().bind(this.waitingViewModel.getShowLevel01BackgroundImageProperty());
        this.level02BackgroundImage.visibleProperty().bind(this.waitingViewModel.getShowLevel02BackgroundImageProperty());
        this.level03BackgroundImage.visibleProperty().bind(this.waitingViewModel.getShowLevel03BackgroundImageProperty());

        this.topPanelController.init(waitingViewModel, viewHandler);
        this.bottomPanelController.init(waitingViewModel, viewHandler);

        this.startFrisbeeTransition();
    }

    @FXML
    private void startFrisbeeTransition() {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(3000), frisbee);
        rotateTransition.setToAngle(360.0);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), frisbee);
        scaleTransition.setFromX(0.1);
        scaleTransition.setFromY(0.1);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(rotateTransition, scaleTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
    }

    @FXML
    private void handleButtonStartClicked(ActionEvent event) {
        this.viewHandler.openGameView();
    }
}
