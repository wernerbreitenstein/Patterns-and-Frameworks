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
    @FXML
    private HBox teamDataTopPanel;

    @FXML
    private GridPane topPanelStartView;

    @FXML
    private GridPane topPanelProfileRegistrationLoginView;

    @FXML
    private GridPane topPanelGameView;

    @FXML
    private Label labelTeamNameGameView;

    @FXML
    private Label labelTeamNameStartView;

    @FXML
    private Label labelLevelGameView;

    @FXML
    private Label labelLevelStartView;

    @FXML
    private Label labelCountdownGameView;

    @FXML
    private Label labelScoreGameView;

    @FXML
    private Label labelScoreStartView;

    @FXML
    private Label labelLivesStartView;

    @FXML
    private Label labelGreeting;

    @FXML
    private Button buttonLoginRegister;

    @FXML
    private MenuButton buttonSettings;
    
    @FXML
    private HBox overlaysTeamLives;

    private StartViewModel startViewModel;
    private ProfileViewModel profileViewModel;
    private RegistrationLoginViewModel registrationLoginViewModel;
    private GameViewModel gameViewModel;
    private ViewHandler viewHandler;

    public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
        this.gameViewModel = gameViewModel;
        this.viewHandler = viewHandler;

        this.topPanelStartView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(false);
        this.topPanelGameView.setVisible(true);

        this.labelTeamNameGameView.textProperty().bind(this.gameViewModel.getLabelTeamProperty());
        this.labelLevelGameView.textProperty().bind(this.gameViewModel.getLabelLevelProperty());
        this.labelCountdownGameView.textProperty().bind(this.gameViewModel.getLabelCountdownProperty());
        this.labelScoreGameView.textProperty().bind(this.gameViewModel.getLabelScoreProperty().asString());

        for (int i = 0; i < this.overlaysTeamLives.getChildren().size(); i++) {
            this.overlaysTeamLives.getChildren().get(i).visibleProperty().bind(this.gameViewModel.getTeamLivesHiddenProperty(i));
        }
    }

    public void init(StartViewModel startViewModel, ViewHandler viewHandler) {
        this.startViewModel = startViewModel;
        this.viewHandler = viewHandler;

        this.topPanelGameView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(false);
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

    public void init(RegistrationLoginViewModel registrationLoginViewModel, ViewHandler viewHandler) {
        this.registrationLoginViewModel = registrationLoginViewModel;
        this.viewHandler = viewHandler;

        this.topPanelStartView.setVisible(false);
        this.topPanelGameView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(true);
    }

    public void init(ProfileViewModel profileViewModel, ViewHandler viewHandler) {
        this.profileViewModel = profileViewModel;
        this.viewHandler = viewHandler;

        this.topPanelStartView.setVisible(false);
        this.topPanelGameView.setVisible(false);
        this.topPanelProfileRegistrationLoginView.setVisible(true);
    }

    @FXML
    private void handleLoginRegisterButtonClicked(ActionEvent event) {
        this.viewHandler.openRegistrationLoginView();
    }

    @FXML
    private void handleLogoutButtonClicked(ActionEvent event) {
        this.startViewModel.logout();
    }

    @FXML
    private void handleProfileButtonClicked(ActionEvent event) {
        this.viewHandler.openProfileView();
    }
}
