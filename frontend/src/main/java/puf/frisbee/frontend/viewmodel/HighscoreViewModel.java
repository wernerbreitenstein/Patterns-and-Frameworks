package puf.frisbee.frontend.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import puf.frisbee.frontend.model.HighscoreModel;
import puf.frisbee.frontend.model.Team;


public class HighscoreViewModel {
    private HighscoreModel highscoreModel;
    private ObservableList<Team> highscoreTableProperty;

    public HighscoreViewModel(HighscoreModel highscoreModel) {
        this.highscoreModel = highscoreModel;
        this.highscoreTableProperty = FXCollections.observableArrayList();
    }

    /**
     * Returns the available highscore data of all teams
     * @return an obersable list of teams with name, level and score
     */
    public ObservableList<Team> getHighscoreTableItems() {
        this.highscoreTableProperty.setAll(highscoreModel.getTeams());
        return this.highscoreTableProperty;
    }
}
