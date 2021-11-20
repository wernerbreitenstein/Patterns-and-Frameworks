package puf.frisbee.frontend.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import puf.frisbee.frontend.model.Highscore;
import puf.frisbee.frontend.model.Team;


public class HighscoreViewModel {
    private Highscore highscoreModel;
    private ObservableList<Team> highscoreTableProperty;

    public HighscoreViewModel(Highscore highscoreModel) {
        this.highscoreModel = highscoreModel;
        this.highscoreTableProperty = FXCollections.observableArrayList();
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
