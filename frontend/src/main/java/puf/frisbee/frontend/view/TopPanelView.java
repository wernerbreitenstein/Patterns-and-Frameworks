package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class TopPanelView {
    @FXML
    private Label labelCountdown;

    @FXML
    private Label labelLevel;

    @FXML
    private Label labelScore;

    @FXML
    private Label labelTeamName;

    private GameViewModel gameViewModel;

    public void init(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;

        this.labelCountdown.textProperty().bind(this.gameViewModel.getLabelCountdownProperty());
        this.labelLevel.textProperty().bind(this.gameViewModel.getLabelLevelProperty());
        this.labelScore.textProperty().bind(this.gameViewModel.getLabelScoreProperty().asString());
        this.labelTeamName.textProperty().bind(this.gameViewModel.getLabelTeamProperty());
    }
}
