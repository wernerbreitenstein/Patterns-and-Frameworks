package puf.frisbee.frontend.view;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.WaitingViewModel;

import java.beans.PropertyChangeEvent;

public class WaitingView {
    @FXML
    private BackgroundImageView backgroundImageController;

    @FXML
    private TopPanelView topPanelController;

    @FXML
    private BottomPanelView bottomPanelController;

    @FXML
    private ImageView frisbee;

    @FXML
    private Button startButton;

    @FXML
    private Label playerGreeting;

    private WaitingViewModel waitingViewModel;
    private ViewHandler viewHandler;

    public void init(WaitingViewModel waitingViewModel, ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
        this.waitingViewModel = waitingViewModel;

        this.backgroundImageController.init(waitingViewModel, viewHandler);
        this.topPanelController.init(waitingViewModel, viewHandler);
        this.bottomPanelController.init(waitingViewModel, viewHandler);

        this.playerGreeting.textProperty().bind(waitingViewModel.getLabelPlayerGreetingProperty());
        this.startButton.disableProperty().bind(waitingViewModel.getStartButtonDisabledProperty());

        // subscribe also to changes of the "game running status"
        this.waitingViewModel.addPropertyChangeListener(this::redirectToGame);

        this.startFrisbeeTransition();
    }

    // redirect opens game view
    private void redirectToGame(PropertyChangeEvent event) {
        // add execution to javafx application
        Platform.runLater(() -> this.viewHandler.openGameView());
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
        this.waitingViewModel.startGame();
        this.viewHandler.openGameView();
    }
}
