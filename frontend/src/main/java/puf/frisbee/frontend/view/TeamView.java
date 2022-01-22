package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.TeamViewModel;

public class TeamView {
    @FXML
    private TopPanelView topPanelController;

    @FXML
    private BottomPanelView bottomPanelController;

    @FXML
    private HBox teamForm;

    @FXML
    private TextField joinTeamName;

    @FXML
    private TextField createTeamName;

    @FXML
    private Label joinTeamError;

    @FXML
    private Label createTeamError;

    @FXML
    private Label readyToGo;

    @FXML
    private Label currentTeam;

    @FXML
    private Label playerLeft;

    @FXML
    private Label playerRight;

    private TeamViewModel teamViewModel;
    private ViewHandler viewHandler;

    public void init(TeamViewModel teamViewModel, ViewHandler viewHandler) {
        this.teamViewModel = teamViewModel;
        this.viewHandler = viewHandler;

        this.topPanelController.init(teamViewModel, viewHandler);
        this.bottomPanelController.init(viewHandler);

        this.teamForm.visibleProperty().bind(this.teamViewModel.getTeamFormVisibilityProperty());

        this.readyToGo.visibleProperty().bind(this.teamViewModel.getReadyToGoVisibilityProperty());
        this.currentTeam.textProperty().bind(this.teamViewModel.getCurrentTeamLabel());
        this.playerLeft.textProperty().bind(this.teamViewModel.getPlayerLeftLabel());
        this.playerRight.textProperty().bind(this.teamViewModel.getPlayerRightLabel());

        this.joinTeamError.textProperty().bind(this.teamViewModel.getJoinTeamErrorLabelProperty());
        this.createTeamError.textProperty().bind(this.teamViewModel.getCreateTeamErrorLabelProperty());
    }

    @FXML
    private void handleJoinTeamButtonClicked(ActionEvent event) {
        boolean joinTeamSuccessful = this.teamViewModel.joinTeam(this.joinTeamName.getText());

        if (joinTeamSuccessful) {
            this.joinTeamName.clear();
        }
    }

    @FXML
    private void handleCreateTeamButtonClicked(ActionEvent event) {
        boolean createTeamSuccessful = this.teamViewModel.createTeam(this.createTeamName.getText());

        if (createTeamSuccessful) {
            this.createTeamName.clear();
        }
    }

    @FXML
    private void handleBackToStartButtonClicked(ActionEvent event) {
        this.viewHandler.openStartView();
    }
}
