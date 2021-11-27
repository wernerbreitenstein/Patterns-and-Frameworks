package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    
    @FXML
    private HBox overlaysTeamLives;

    private GameViewModel gameViewModel;

    public void init(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;

        this.labelCountdown.textProperty().bind(this.gameViewModel.getLabelCountdownProperty());
        this.labelLevel.textProperty().bind(this.gameViewModel.getLabelLevelProperty());
        this.labelScore.textProperty().bind(this.gameViewModel.getLabelScoreProperty().asString());
        this.labelTeamName.textProperty().bind(this.gameViewModel.getLabelTeamProperty());
    }
    
    public void removeTeamLives() {
    	if (this.gameViewModel.getTeamLives() > 0) {    		    		
    		overlaysTeamLives.getChildren().get(this.gameViewModel.getTeamLives() - 1).setVisible(true);
    		this.gameViewModel.setTeamLives(this.gameViewModel.getTeamLives() - 1);
    	}
    	if (this.gameViewModel.getTeamLives() == 0) {
			this.gameViewModel.showGameOverDialog();
		}
    }
    
    public void restoreRemainingTeamLives(int remainingLives) {
    	for (int i = overlaysTeamLives.getChildren().size(); i > remainingLives; i-- ) {
    		this.overlaysTeamLives.getChildren().get(i - 1).setVisible(true);
    	}
    }
}
