package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import puf.frisbee.frontend.model.*;


public class StartViewModel {
    private Game gameModel;
    private Level levelModel;
    private Team teamModel;
    private Highscore highscoreModel;
    private Player playerModel;

    private StringProperty labelTeamName;
    private StringProperty labelLevel;
    private IntegerProperty labelScore;
    private IntegerProperty labelLives;

    private StringProperty labelGreetingProperty;
    private BooleanProperty showTeamDataTopPanel;
    private BooleanProperty showSettingsButton;
    private BooleanProperty showLoginRegisterButton;

    private BooleanProperty showStartButton;
    private BooleanProperty showJoinCreateTeamButton;

    private ObservableList<Team> highscoreTableProperty;

    public StartViewModel(Game gameModel, Level levelModel, Team teamModel, Highscore highscoreModel, Player playerModel) {
        this.gameModel = gameModel;
        this.levelModel = levelModel;
        this.teamModel = teamModel;
        this.highscoreModel = highscoreModel;
        this.playerModel = playerModel;

        this.labelTeamName = new SimpleStringProperty();
        this.labelLevel = new SimpleStringProperty();
        this.labelScore = new SimpleIntegerProperty();
        this.labelLives = new SimpleIntegerProperty();

        this.showTeamDataTopPanel = new SimpleBooleanProperty(false);
        this.labelGreetingProperty = new SimpleStringProperty();
        this.showSettingsButton = new SimpleBooleanProperty(false);
        this.showLoginRegisterButton = new SimpleBooleanProperty(true);
        // set start view to true, if player has a team
        this.showStartButton = new SimpleBooleanProperty(this.teamModel.isTeamSet());
        this.showJoinCreateTeamButton = new SimpleBooleanProperty(!this.teamModel.isTeamSet());
        this.highscoreTableProperty = FXCollections.observableArrayList();
    }

    private boolean teamIsActive() {
        return teamModel.isTeamSet() && teamModel.getActive();
    }

    public BooleanProperty getShowTeamDataTopPanelProperty() {
        this.showTeamDataTopPanel.setValue(this.playerModel.isLoggedIn() && this.teamIsActive());
        return this.showTeamDataTopPanel;
    }

    public StringProperty getLabelTeamProperty() {
        this.labelTeamName.setValue(this.teamModel.getName());
        return this.labelTeamName;
    }

    public StringProperty getLabelLevelProperty() {
        this.labelLevel.setValue(String.valueOf(this.teamModel.getLevel()));
        return this.labelLevel;
    }

    public IntegerProperty getLabelScoreProperty() {
        this.labelScore.setValue(this.teamModel.getScore());
        return this.labelScore;
    }

    public IntegerProperty getLabelLivesProperty() {
        this.labelLives.setValue(this.teamModel.getLives());
        return this.labelLives;
    }

    public StringProperty getLabelGreetingProperty() {
        String name = this.playerModel.isLoggedIn() ? this.playerModel.getName() : "Stranger";
        this.labelGreetingProperty.setValue("Hello " + name);
        return this.labelGreetingProperty;
    }

    public BooleanProperty getShowSettingsButtonProperty() {
        this.showSettingsButton.setValue(this.playerModel.isLoggedIn());
        return this.showSettingsButton;
    }

    public BooleanProperty getShowLoginRegisterButtonProperty() {
        this.showLoginRegisterButton.setValue(!this.playerModel.isLoggedIn());
        return this.showLoginRegisterButton;
    }

    public BooleanProperty getShowJoinCreateTeamButtonProperty() {
        this.showJoinCreateTeamButton.setValue(this.playerModel.isLoggedIn() && !this.teamIsActive());
        return this.showJoinCreateTeamButton;
    }

    public BooleanProperty getShowStartButtonProperty() {
        this.showStartButton.setValue(this.playerModel.isLoggedIn() && this.teamIsActive());
        return this.showStartButton;
    }

    public void logout() {
        // set player data
        this.playerModel.setLoginStatus(false);
        this.showTeamDataTopPanel.setValue(false);
        this.labelGreetingProperty.setValue("Hello Stranger");

        // remove team data
        this.teamModel.resetTeamData();

        // set button display
        this.showLoginRegisterButton.setValue(true);
        this.showSettingsButton.setValue(false);
        this.showStartButton.setValue(false);
        this.showJoinCreateTeamButton.setValue(false);
    }

    public void resetCountdown() {
        this.gameModel.setCurrentCountdown(this.gameModel.getInitialCountdown());
    }

    /**
     * Returns the available highscore data of all teams
     * @return an obersable list of teams with name, level and score
     */
    public ObservableList<Team> getHighscoreTableItems() {
        this.refreshData();
        return this.highscoreTableProperty;
    }

    public void refreshData() {
        this.highscoreTableProperty.setAll(highscoreModel.getHighscoreData());
    }
}
