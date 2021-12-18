package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import puf.frisbee.frontend.model.Highscore;
import puf.frisbee.frontend.model.Player;
import puf.frisbee.frontend.model.Team;


public class StartViewModel {
    private Highscore highscoreModel;
    private Player playerModel;

    private StringProperty labelGreetingProperty;
    private BooleanProperty showTeamDataTopPanel;
    private BooleanProperty showSettingsButton;
    private BooleanProperty showLoginRegisterButton;
    private BooleanProperty showStartButton;

    private ObservableList<Team> highscoreTableProperty;

    public StartViewModel(Highscore highscoreModel, Player playerModel) {
        this.highscoreModel = highscoreModel;
        this.playerModel = playerModel;

        this.showTeamDataTopPanel = new SimpleBooleanProperty(false);
        this.labelGreetingProperty = new SimpleStringProperty();
        this.showSettingsButton = new SimpleBooleanProperty(false);
        this.showLoginRegisterButton = new SimpleBooleanProperty(true);
        this.showStartButton = new SimpleBooleanProperty(false);
        this.highscoreTableProperty = FXCollections.observableArrayList();
    }

    public BooleanProperty getTeamDataTopPanelProperty() {
        if (this.playerModel.isLoggedIn()) {
            this.showTeamDataTopPanel.setValue(true);
        } else {
            this.showTeamDataTopPanel.setValue(false);
        }
        return this.showTeamDataTopPanel;
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

    public BooleanProperty getShowStartButtonProperty() {
        this.showStartButton.setValue(this.playerModel.isLoggedIn());
        return this.showStartButton;
    }

    public void logout() {
        this.playerModel.setLoginStatus(false);
        this.showTeamDataTopPanel.setValue(false);
        this.labelGreetingProperty.setValue("Hello Stranger");
        this.showSettingsButton.setValue(false);
        this.showLoginRegisterButton.setValue(true);
        this.showStartButton.setValue(false);
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
        this.highscoreTableProperty.setAll(highscoreModel.getTeams());
    }
}
