package puf.frisbee.frontend.view;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class WaitingView {

    @FXML
    private Button startButton;

    @FXML
    private Text startText;

    @FXML
    private ImageView frisbee;

    private GameViewModel gameViewModel;
    private ViewHandler viewHandler;

    public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
        this.gameViewModel = gameViewModel;
        this.startFrisbeeTransition();
    }

    @FXML
    private void startFrisbeeTransition(){

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(3000), frisbee);
        rotateTransition.setToAngle(360.0);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(3000), frisbee);
        scaleTransition.setFromX(10);
        scaleTransition.setFromY(10);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(rotateTransition, scaleTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
    }

    @FXML
    private void handleStartButtonClicked(MouseEvent event) {
        this.viewHandler.openGameView(this.gameViewModel.getLevel());
    }
}
