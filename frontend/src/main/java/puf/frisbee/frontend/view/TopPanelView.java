package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.*;

public class TopPanelView {
    /**
     * The team data to display in the top panel in fxml.
     */
    @FXML
    private HBox teamDataTopPanel;

    /**
     * The top panel to display in the start view in fxml.
     */
    @FXML
    private GridPane topPanelStartView;

    /**
     * The top panel to display in the login/registration view in fxml.
     */
    @FXML
    private GridPane topPanelProfileRegistrationLoginView;

    /**
     * The top panel to display in the waiting view in fxml.
     */
    @FXML
    private GridPane topPanelWaitingView;

    /**
     * The top panel to display in the game view in fxml.
     */
    @FXML
    private GridPane topPanelGameView;

    /**
     * The label for displaying the team name in the waiting view in fxml.
     */
    @FXML
    private Label labelTeamNameWaitingView;

    /**
     * The label for displaying the team name in the game view in fxml.
     */
    @FXML
    private Label labelTeamNameGameView;

    /**
     * The label for displaying the team name in the start view in fxml.
     */
    @FXML
    private Label labelTeamNameStartView;

    /**
     * The label for displaying the level in the waiting view in fxml.
     */
    @FXML
    private Label labelLevelWaitingView;

    /**
     * The label for displaying the level in the game view in fxml.
     */
    @FXML
    private Label labelLevelGameView;

    /**
     * The label for displaying the level in the start view in fxml.
     */
    @FXML
    private Label labelLevelStartView;

    /**
     * The label for displaying the countdown in the waiting view in fxml.
     */
    @FXML
    private Label labelCountdownWaitingView;

    /**
     * The label for displaying the countdown in the game view in fxml.
     */
    @FXML
    private Label labelCountdownGameView;

    /**
     * The label for displaying the score in the waiting view in fxml.
     */
    @FXML
    private Label labelScoreWaitingView;

    /**
     * The label for displaying the score in the game view in fxml.
     */
    @FXML
    private Label labelScoreGameView;

    /**
     * The label for displaying the score in the start view in fxml.
     */
    @FXML
    private Label labelScoreStartView;

    /**
     * The label for displaying the lives in the start view in fxml.
     */
    @FXML
    private Label labelLivesStartView;

    /**
     * The label for displaying the greeting in fxml.
     */
    @FXML
    private Label labelGreeting;

    /**
     * The button to trigger the login/registration view in fxml.
     */
    @FXML
    private Button buttonLoginRegister;

    /**
     * The button to open the settings menue in fxml.
     */
    @FXML
    private MenuButton buttonSettings;

    /**
     * The overlays to darken the lost lives in the game view in fxml.
     */
    @FXML
    private HBox overlaysTeamLivesGameView;

    /**
     * The overlays to darken the lost lives in the waiting view in fxml.
     */
    @FXML
    private HBox overlaysTeamLivesWaitingView;

    /**
     * Start view model instance.
     */
    private StartViewModel startViewModel;

    /**
     * Profile view model instance.
     */
    private ProfileViewModel profileViewModel;

    /**
     * Registration login view model instance.
     */
    private RegistrationLoginViewModel registrationLoginViewModel;

    /**
     * Game view model instance.
     */
    private GameViewModel gameViewModel;

    /**
     * Team view model instance.
     */
    private TeamViewModel teamViewModel;

    /**
     * Waiting view model instance.
     */
    private WaitingViewModel waitingViewModel;

    /**
     * View Handler instance.
     */
    private ViewHandler viewHandler;

    /**
     * Init method used in waiting view, sets waiting view model and view
     * handler.
     *
     * @param waitingViewModel the waiting view model instance
     * @param viewHandler      the view handler instance
     */
    public void init(WaitingViewModel waitingViewModel, ViewHandler viewHandler) {
        this.waitingViewModel = waitingViewModel;
        this.viewHandler = viewHandler;

        this.topPanelStartView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(false);
        this.topPanelGameView.setVisible(false);
        this.topPanelWaitingView.setVisible(true);

        this.labelTeamNameWaitingView.textProperty().bind(this.waitingViewModel.getLabelTeamProperty());
        this.labelLevelWaitingView.textProperty().bind(this.waitingViewModel.getLabelLevelProperty());
        this.labelCountdownWaitingView.textProperty().bind(this.waitingViewModel.getLabelCountdownProperty());
        this.labelScoreWaitingView.textProperty().bind(this.waitingViewModel.getLabelScoreProperty().asString());

        for (int i = 0; i < this.overlaysTeamLivesWaitingView.getChildren().size(); i++) {
            this.overlaysTeamLivesWaitingView.getChildren().get(i).visibleProperty().bind(this.waitingViewModel.getTeamLivesHiddenProperty(i));
        }
    }

    /**
     * Init method used in game view, sets game view model and view
     * handler.
     *
     * @param gameViewModel the game view model instance
     * @param viewHandler   the view handler instance
     */
    public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
        this.gameViewModel = gameViewModel;
        this.viewHandler = viewHandler;

        this.topPanelStartView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(false);
        this.topPanelWaitingView.setVisible(false);
        this.topPanelGameView.setVisible(true);

        this.labelTeamNameGameView.textProperty().bind(this.gameViewModel.getLabelTeamProperty());
        this.labelLevelGameView.textProperty().bind(this.gameViewModel.getLabelLevelProperty());
        this.labelCountdownGameView.textProperty().bind(this.gameViewModel.getLabelCountdownProperty());
        this.labelScoreGameView.textProperty().bind(this.gameViewModel.getLabelScoreProperty().asString());

        for (int i = 0; i < this.overlaysTeamLivesGameView.getChildren().size(); i++) {
            this.overlaysTeamLivesGameView.getChildren().get(i).visibleProperty().bind(this.gameViewModel.getTeamLivesHiddenProperty(i));
        }
    }

    /**
     * Init method used in start view, sets start view model and view
     * handler.
     *
     * @param startViewModel the start view model instance
     * @param viewHandler   the view handler instance
     */
    public void init(StartViewModel startViewModel, ViewHandler viewHandler) {
        this.startViewModel = startViewModel;
        this.viewHandler = viewHandler;

        this.topPanelGameView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(false);
        this.topPanelWaitingView.setVisible(false);
        this.topPanelStartView.setVisible(true);

        this.labelTeamNameStartView.textProperty().bind(this.startViewModel.getLabelTeamProperty());
        this.labelLevelStartView.textProperty().bind(this.startViewModel.getLabelLevelProperty());
        this.labelScoreStartView.textProperty().bind(this.startViewModel.getLabelScoreProperty().asString());
        this.labelLivesStartView.textProperty().bind(this.startViewModel.getLabelLivesProperty().asString());

        this.teamDataTopPanel.visibleProperty().bind(this.startViewModel.getShowTeamDataTopPanelProperty());
        this.labelGreeting.textProperty().bind(this.startViewModel.getLabelGreetingProperty());
        this.buttonLoginRegister.visibleProperty().bind(this.startViewModel.getShowLoginRegisterButtonProperty());
        this.buttonSettings.visibleProperty().bind(this.startViewModel.getShowSettingsButtonProperty());
    }

    /**
     * Init method used in login/registration view, sets login/registration view model and view
     * handler.
     *
     * @param registrationLoginViewModel the login/registration view model instance
     * @param viewHandler   the view handler instance
     */
    public void init(RegistrationLoginViewModel registrationLoginViewModel, ViewHandler viewHandler) {
        this.registrationLoginViewModel = registrationLoginViewModel;
        this.viewHandler = viewHandler;

        this.topPanelStartView.setVisible(false);
        this.topPanelGameView.setVisible(false);
        this.topPanelWaitingView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(true);
    }

    /**
     * Init method used in profile view, sets profile view model and view
     * handler.
     *
     * @param profileViewModel the profile view model instance
     * @param viewHandler   the view handler instance
     */
    public void init(ProfileViewModel profileViewModel, ViewHandler viewHandler) {
        this.profileViewModel = profileViewModel;
        this.viewHandler = viewHandler;

        this.topPanelStartView.setVisible(false);
        this.topPanelGameView.setVisible(false);
        this.topPanelWaitingView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(true);
    }

    /**
     * Init method used in team view, sets team view model and view
     * handler.
     *
     * @param teamViewModel the team view model instance
     * @param viewHandler   the view handler instance
     */
    public void init(TeamViewModel teamViewModel, ViewHandler viewHandler) {
        this.teamViewModel = teamViewModel;
        this.viewHandler = viewHandler;

        this.topPanelStartView.setVisible(false);
        this.topPanelGameView.setVisible(false);
        this.topPanelWaitingView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(true);
    }

    /**
     * Handles click on login/register button in the start view
     * to open the login/registration view.
     *
     * @param event the action event
     */
    @FXML
    private void handleLoginRegisterButtonClicked(ActionEvent event) {
        this.viewHandler.openRegistrationLoginView();
    }

    /**
     * Handles click on logout button in the settings menue in the start view
     * to logout the player.
     *
     * @param event the action event
     */
    @FXML
    private void handleLogoutButtonClicked(ActionEvent event) {
        this.startViewModel.logout();
    }

    /**
     * Handles click on profile button in the settings menue in the start view
     * to open the profile view for the player.
     *
     * @param event the action event
     */
    @FXML
    private void handleProfileButtonClicked(ActionEvent event) {
        this.viewHandler.openProfileView();
    }

    /**
     * Handles click on team button in the settings menue in the start view
     * to open the team view for the player.
     *
     * @param event the action event
     */
    @FXML
    public void handleTeamButtonClicked(ActionEvent event) { this.viewHandler.openTeamView(); }
}
