package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import puf.frisbee.frontend.model.*;


public class StartViewModel {
    /**
     * The game model instance.
     */
    private final Game gameModel;

    /**
     * The team model instance.
     */
    private final Team teamModel;

    /**
     * The highscore model instance.
     */
    private final Highscore highscoreModel;

    /**
     * The player model instance.
     */
    private final Player playerModel;

    /**
     * The value of the team name label.
     */
    private final StringProperty labelTeamName;

    /**
     * The value of the team level label.
     */
    private final StringProperty labelLevel;

    /**
     * The value of the team score label.
     */
    private final IntegerProperty labelScore;

    /**
     * The value of the team lives label.
     */
    private final IntegerProperty labelLives;

    /**
     * The value of the greeting label.
     */
    private final StringProperty labelGreetingProperty;

    /**
     * The value of the flag that indicates if the team data top panel is shown.
     */
    private final BooleanProperty showTeamDataTopPanel;

    /**
     * The value of the flag that indicates if the settings button is shown.
     */
    private final BooleanProperty showSettingsButton;

    /**
     * The value of the flag that indicates if the login/register button is shown.
     */
    private final BooleanProperty showLoginRegisterButton;

    /**
     * The value of the flag that indicates if the start button is shown.
     */
    private final BooleanProperty showStartButton;

    /**
     * The value of the flag that indicates if the join/create team button is
     * shown.
     */
    private final BooleanProperty showJoinCreateTeamButton;

    /**
     * The values of the highscore table as a list of teams.
     */
    private final ObservableList<Team> highscoreTableProperty;

    /**
     * Constructs the start view model and sets all needed model instances.
     * Initializes all values needed for the bindings with the view.
     *
     * @param gameModel      game model instance
     * @param teamModel      team model instance
     * @param highscoreModel highscore model instance
     * @param playerModel    player model instance
     */
    public StartViewModel(Game gameModel, Team teamModel,
                          Highscore highscoreModel, Player playerModel) {
        this.gameModel = gameModel;
        this.teamModel = teamModel;
        // reload team data from backend to be sure to have the newest data to display
        this.teamModel.reloadTeamData();
        this.highscoreModel = highscoreModel;
        this.playerModel = playerModel;

        this.labelTeamName = new SimpleStringProperty(this.teamModel.getName());
        this.labelLevel = new SimpleStringProperty(
                String.valueOf(this.teamModel.getLevel()));
        this.labelScore = new SimpleIntegerProperty(this.teamModel.getScore());
        this.labelLives = new SimpleIntegerProperty(this.teamModel.getLives());

        this.showTeamDataTopPanel = new SimpleBooleanProperty(false);
        this.labelGreetingProperty = new SimpleStringProperty(
                this.playerModel.isLoggedIn()
                        ? "Hello " + this.playerModel.getName() : "Welcome "
                        + "Stranger");
        this.showSettingsButton = new SimpleBooleanProperty(
                this.playerModel.isLoggedIn());
        this.showLoginRegisterButton = new SimpleBooleanProperty(
                !this.playerModel.isLoggedIn());
        this.showStartButton = new SimpleBooleanProperty(
                this.playerModel.isLoggedIn() && this.teamIsActive());
        this.showJoinCreateTeamButton = new SimpleBooleanProperty(
                this.playerModel.isLoggedIn() && !this.teamIsActive());
        this.highscoreTableProperty = FXCollections.observableArrayList();
    }

    /**
     * Checks if a team is active.
     *
     * @return true if the team is active otherwise false
     */
    private boolean teamIsActive() {
        return teamModel.isTeamSet() && teamModel.getActive();
    }

    /**
     * Triggers the logout of a player. Resets all data and flags.
     */
    public void logout() {
        // set player data
        this.playerModel.setLoginStatus(false);
        this.showTeamDataTopPanel.setValue(false);
        this.labelGreetingProperty.setValue("Welcome Stranger");

        // remove team data
        this.teamModel.resetTeamData();

        // set button display
        this.showLoginRegisterButton.setValue(true);
        this.showSettingsButton.setValue(false);
        this.showStartButton.setValue(false);
        this.showJoinCreateTeamButton.setValue(false);
    }

    /**
     * Reset the timer of the game.
     */
    public void resetCountdown() {
        this.gameModel.setCurrentCountdown(
                this.gameModel.getInitialCountdown());
    }

    /**
     * Refreshes the team data in the highscore table.
     */
    public void refreshData() {
        this.highscoreTableProperty.setAll(highscoreModel.getHighscoreData());
    }

    /**
     * Method for the binding of the visibility of the team data top panel
     * with an element in the view.
     *
     * @return the team data top panel visibility flag
     */
    public BooleanProperty getShowTeamDataTopPanelProperty() {
        this.showTeamDataTopPanel.setValue(
                this.playerModel.isLoggedIn() && this.teamIsActive());
        return this.showTeamDataTopPanel;
    }

    /**
     * Method for the binding of the team name value with an element in the
     * view.
     *
     * @return team name label value
     */
    public StringProperty getLabelTeamProperty() {
        return this.labelTeamName;
    }

    /**
     * Method for the binding of the team level value with an element in the
     * view.
     *
     * @return team level value
     */
    public StringProperty getLabelLevelProperty() {
        return this.labelLevel;
    }

    /**
     * Method for the binding of the team score value with an element in the
     * view.
     *
     * @return team score value
     */
    public IntegerProperty getLabelScoreProperty() {
        return this.labelScore;
    }

    /**
     * Method for the binding of the team lives value with an element in the
     * view.
     *
     * @return team lives value
     */
    public IntegerProperty getLabelLivesProperty() {
        return this.labelLives;
    }

    /**
     * Method for the binding of the greeting label value with an element in the
     * view.
     *
     * @return greeting label value
     */
    public StringProperty getLabelGreetingProperty() {
        return this.labelGreetingProperty;
    }

    /**
     * Method for the binding of the visibility of the settings button
     * with an element in the view.
     *
     * @return the settings button visibility flag
     */
    public BooleanProperty getShowSettingsButtonProperty() {
        return this.showSettingsButton;
    }

    /**
     * Method for the binding of the visibility of the login/register button
     * with an element in the view.
     *
     * @return the login/register button visibility flag
     */
    public BooleanProperty getShowLoginRegisterButtonProperty() {
        return this.showLoginRegisterButton;
    }

    /**
     * Method for the binding of the visibility of the join/create team button
     * with an element in the view.
     *
     * @return the join/create team button visibility flag
     */
    public BooleanProperty getShowJoinCreateTeamButtonProperty() {
        return this.showJoinCreateTeamButton;
    }

    /**
     * Method for the binding of the visibility of the start button
     * with an element in the view.
     *
     * @return the start button visibility flag
     */
    public BooleanProperty getShowStartButtonProperty() {
        return this.showStartButton;
    }


    /**
     * Method for the binding of the team data with an element in the view.
     * Returns the available highscore data of all teams.
     *
     * @return an observable list of all teams with name, level and score
     */
    public ObservableList<Team> getHighscoreTableItems() {
        this.refreshData();
        return this.highscoreTableProperty;
    }
}
