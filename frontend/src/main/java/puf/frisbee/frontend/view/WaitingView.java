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
    /**
     * The background image element in fxml.
     */
    @FXML
    private BackgroundImageView backgroundImageController;

    /**
     * The top panel element in fxml.
     */
    @FXML
    private TopPanelView topPanelController;

    /**
     * The bottom panel element in fxml.
     */
    @FXML
    private BottomPanelView bottomPanelController;

    /**
     * The frisbee element in fxml.
     */
    @FXML
    private ImageView frisbee;

    /**
     * The start button in fxml.
     */
    @FXML
    private Button startButton;

    /**
     * The label for the greeting of the player in fxml.
     */
    @FXML
    private Label playerGreeting;

    /**
     * The waiting view model instance.
     */
    private WaitingViewModel waitingViewModel;

    /**
     * The view handler instance.
     */
    private ViewHandler viewHandler;

    /**
     * Init method, sets waiting view model instance and view handler instance.
     * Also initializes the controller of all imported views and the bindings
     * of all fxml elements with the waiting view model.
     * Starts the frisbee animation.
     * Subscribes as listener to the waiting view model to trigger redirects.
     *
     * @param waitingViewModel waiting view model instance
     * @param viewHandler      view handler instance
     */
    public void init(WaitingViewModel waitingViewModel,
                     ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
        this.waitingViewModel = waitingViewModel;

        this.backgroundImageController.init(waitingViewModel);
        this.topPanelController.init(waitingViewModel, viewHandler);
        this.bottomPanelController.init(waitingViewModel, viewHandler);

        this.playerGreeting.textProperty().bind(
                waitingViewModel.getLabelPlayerGreetingProperty());
        this.startButton.disableProperty().bind(
                waitingViewModel.getStartButtonDisabledProperty());

        // subscribe also to changes of the "game running status"
        this.waitingViewModel.addPropertyChangeListener(this::redirectToGame);

        this.startFrisbeeTransition();
    }

    /**
     * Triggers a redirect to the game view when the event, this method is
     * subscribed to, is triggered.
     *
     * @param event property change event
     */
    private void redirectToGame(PropertyChangeEvent event) {
        // add execution to javafx application
        Platform.runLater(() -> this.viewHandler.openGameView());
    }

    /**
     * Executes the frisbee animation on the waiting view.
     */
    @FXML
    private void startFrisbeeTransition() {
        RotateTransition rotateTransition = new RotateTransition(
                Duration.millis(3000), frisbee);
        rotateTransition.setToAngle(360.0);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);

        ScaleTransition scaleTransition = new ScaleTransition(
                Duration.millis(500), frisbee);
        scaleTransition.setFromX(0.1);
        scaleTransition.setFromY(0.1);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(rotateTransition,
                scaleTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
    }

    /**
     * This method is executed when the start button is clicked.
     * Triggers the corresponding method in the waiting view model and
     * redirects to the game view.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonStartClicked(ActionEvent event) {
        this.waitingViewModel.startGame();
        this.viewHandler.openGameView();
    }
}
