package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.viewmodel.WaitingViewModel;

public class WaitingView {
    @FXML
    private Label startLabel;

    @FXML
    private Button startButton;

    private WaitingViewModel waitingViewModel;

    public void init(WaitingViewModel waitingViewModel) {
        // TODO: use dependency injection later on
        // also later we will have the ViewHandler as dependency, so we can load different views when clicking a button
        // (e.g. after confirming game over)
        this.waitingViewModel = waitingViewModel;
    }

    @FXML
    private void handleStartButtonClicked(MouseEvent event) {
        startLabel.setText("Warte auf zweites Monster, um das Spiel zu beginnen...");
    }
}
