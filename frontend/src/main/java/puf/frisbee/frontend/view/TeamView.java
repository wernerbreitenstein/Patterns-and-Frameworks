package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.TeamViewModel;

public class TeamView {
    @FXML
    private TopPanelView topPanelController;

    @FXML
    private BottomPanelView bottomPanelController;

    @FXML
    private Label labelQuickTip;

    @FXML
    private TextField joinTeamName;

    @FXML
    private TextField createTeamName;

    @FXML
    private Label joinTeamError;

    @FXML
    private Label createTeamError;

    @FXML
    private Label currentTeam;

    @FXML
    private Label player1;

    @FXML
    private Label player2;

    private TeamViewModel teamViewModel;
    private ViewHandler viewHandler;

    public void init(TeamViewModel teamViewModel, ViewHandler viewHandler) {
        this.teamViewModel = teamViewModel;
        this.viewHandler = viewHandler;

        this.topPanelController.init(teamViewModel, viewHandler);
        this.bottomPanelController.init(viewHandler);

        this.currentTeam.setText("Current Team: " + this.teamViewModel.getCurrentTeamLabel());
        this.player1.setText("Player 1: " + this.teamViewModel.getPlayer1Label());
        this.player2.setText("Player 2: " + this.teamViewModel.getPlayer2Label());

        this.joinTeamError.textProperty().bind(this.teamViewModel.getJoinTeamErrorLabelProperty());
        this.createTeamError.textProperty().bind(this.teamViewModel.getCreateTeamErrorLabelProperty());
    }

    @FXML
    private void handleJoinTeamButtonClicked(ActionEvent event) {
        boolean joinTeamSuccessful = this.teamViewModel.joinTeam(this.joinTeamName.getText());

        if (joinTeamSuccessful) {
            this.joinTeamName.clear();
            this.currentTeam.setText("Current Team: " + this.teamViewModel.getCurrentTeamLabel());
        }
    }

    @FXML
    private void handleCreateTeamButtonClicked(ActionEvent event) {
        boolean createTeamSuccessful = this.teamViewModel.createTeam(this.createTeamName.getText());

        if (createTeamSuccessful) {
            this.createTeamName.clear();
            this.currentTeam.setText("Current Team: " + this.teamViewModel.getCurrentTeamLabel());
        }
    }

    @FXML
    private void handleBackToStartButtonClicked(ActionEvent event) {
        this.viewHandler.openStartView();
    }
}
