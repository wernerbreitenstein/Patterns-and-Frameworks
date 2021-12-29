package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.TeamViewModel;

public class TeamView {
    @FXML
    TextField joinTeamName;

    @FXML
    TextField createTeamName;

    @FXML
    Label joinTeamError;

    @FXML
    Label createTeamError;

    TeamViewModel teamViewModel;
    ViewHandler viewHandler;

    public void init(TeamViewModel teamViewModel, ViewHandler viewHandler) {
        this.teamViewModel = teamViewModel;
        this.viewHandler = viewHandler;

        this.joinTeamError.textProperty().bind(this.teamViewModel.getJoinTeamErrorLabelProperty());
        this.createTeamError.textProperty().bind(this.teamViewModel.getCreateTeamErrorLabelProperty());
    }

    @FXML
    private void handleJoinTeamButtonClicked(ActionEvent event) {
        boolean joinTeamSuccessful = this.teamViewModel.joinTeam(this.joinTeamName.getText());

        if (joinTeamSuccessful) {
            this.viewHandler.openStartView();
        }
    }

    @FXML
    private void handleCreateTeamButtonClicked(ActionEvent event) {
        boolean createTeamSuccessful = this.teamViewModel.createTeam(this.createTeamName.getText());

        if (createTeamSuccessful) {
            this.viewHandler.openStartView();
        }
    }
}
