package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.TeamViewModel;

public class TeamView {
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
     * The team form element in fxml.
     */
    @FXML
    private HBox teamForm;

    /**
     * The text field for the team name (join team form) in fxml.
     */
    @FXML
    private TextField joinTeamName;

    /**
     * The text field for the team name (create team form) in fxml.
     */
    @FXML
    private TextField createTeamName;

    /**
     * Label for errors in the join team form in fxml.
     */
    @FXML
    private Label joinTeamError;

    /**
     * Label for errors in the create team form in fxml.
     */
    @FXML
    private Label createTeamError;

    /**
     * Label in fxml that is displayed when a team is complete.
     */
    @FXML
    private Label readyToGo;

    /**
     * Label in fxml to display the current team name.
     */
    @FXML
    private Label currentTeam;

    /**
     * Label in fxml to display the left player name.
     */
    @FXML
    private Label playerLeft;

    /**
     * Label in fxml to display the right player name.
     */
    @FXML
    private Label playerRight;

    /**
     * The team view model instance.
     */
    private TeamViewModel teamViewModel;

    /**
     * The view handler instance.
     */
    private ViewHandler viewHandler;

    /**
     * Init method to set the team view model instance and the view handler
     * instance.
     * Also initializes all the needed bindings between fxml and team view
     * model.
     *
     * @param teamViewModel team view model instance
     * @param viewHandler   view handler instance
     */
    public void init(TeamViewModel teamViewModel, ViewHandler viewHandler) {
        this.teamViewModel = teamViewModel;
        this.viewHandler = viewHandler;

        this.topPanelController.init(teamViewModel, viewHandler);
        this.bottomPanelController.init(viewHandler);

        this.teamForm.visibleProperty().bind(
                this.teamViewModel.getTeamFormVisibilityProperty());

        this.readyToGo.visibleProperty().bind(
                this.teamViewModel.getReadyToGoVisibilityProperty());
        this.currentTeam.textProperty().bind(
                this.teamViewModel.getCurrentTeamLabel());
        this.playerLeft.textProperty().bind(
                this.teamViewModel.getPlayerLeftLabel());
        this.playerRight.textProperty().bind(
                this.teamViewModel.getPlayerRightLabel());

        this.joinTeamError.textProperty().bind(
                this.teamViewModel.getJoinTeamErrorLabelProperty());
        this.createTeamError.textProperty().bind(
                this.teamViewModel.getCreateTeamErrorLabelProperty());
    }

    /**
     * This method is executed when the join team button is clicked.
     * It triggers the corresponding method in the team view model and clears
     * the team name input field if the join was successful.
     *
     * @param event action event
     */
    @FXML
    private void handleJoinTeamButtonClicked(ActionEvent event) {
        boolean joinTeamSuccessful = this.teamViewModel.joinTeam(
                this.joinTeamName.getText());

        if (joinTeamSuccessful) {
            this.joinTeamName.clear();
        }
    }

    /**
     * This method is executed when the create team button is clicked.
     * It triggers the corresponding method in the team view model and clears
     * the team name input field if the creation was successful.
     *
     * @param event action event
     */
    @FXML
    private void handleCreateTeamButtonClicked(ActionEvent event) {
        boolean createTeamSuccessful = this.teamViewModel.createTeam(
                this.createTeamName.getText());

        if (createTeamSuccessful) {
            this.createTeamName.clear();
        }
    }

    /**
     * This method is executed when the back to start button is clicked.
     * It redirects to the start view.
     *
     * @param event action event
     */
    @FXML
    private void handleBackToStartButtonClicked(ActionEvent event) {
        this.viewHandler.openStartView();
    }
}
