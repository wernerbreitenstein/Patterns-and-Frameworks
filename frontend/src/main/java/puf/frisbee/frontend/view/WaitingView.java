package puf.frisbee.frontend.view;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.WaitingViewModel;

public class WaitingView {

    @FXML
    private JFXButton startButton;

    @FXML
    private Text startText;

    @FXML
    private ImageView frisbee;

    private WaitingViewModel waitingViewModel;
    private ViewHandler viewHandler;

    public void init(WaitingViewModel waitingViewModel, ViewHandler viewHandler) {
        // TODO: use dependency injection later on
        // also later we will have the ViewHandler as dependency, so we can load different views when clicking a button
        // (e.g. after confirming game over)
        this.viewHandler = viewHandler;
        this.waitingViewModel = waitingViewModel;
    }

    @FXML
    private void handleStartButtonClicked(MouseEvent event) {
//        TODO: Animate frisbee
//        TranslateTransition tt = new TranslateTransition(Duration.seconds(2), frisbee);
//        tt.setByX(200);
//        tt.play();
        this.viewHandler.openLevelView(this.waitingViewModel.getLevel());
    }
}
