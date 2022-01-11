package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.*;
import puf.frisbee.frontend.model.*;

import java.util.ArrayList;

public class WaitingViewModel {
    private Game gameModel;
    private Level levelModel;
    private Team teamModel;

    private StringProperty labelTeamName;
    private StringProperty labelLevel;
    private IntegerProperty labelScore;
    private StringProperty labelCountdown;
    private IntegerProperty labelLives;

    private ArrayList<BooleanProperty> teamLivesHidden;

    public WaitingViewModel(Game gameModel, Level levelModel, Team teamModel) {
        this.gameModel = gameModel;
        this.levelModel = levelModel;
        this.teamModel = teamModel;
        this.teamLivesHidden = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            BooleanProperty hidden = new SimpleBooleanProperty(i >= this.teamModel.getLives());
            this.teamLivesHidden.add(hidden);
        }

        this.labelTeamName = new SimpleStringProperty();
        this.labelLevel = new SimpleStringProperty();
        this.labelCountdown = new SimpleStringProperty();
        this.labelScore = new SimpleIntegerProperty();
        this.labelLives = new SimpleIntegerProperty();
    }

    public StringProperty getLabelTeamProperty() {
        this.labelTeamName.setValue(this.teamModel.getName());
        return this.labelTeamName;
    }

    public StringProperty getLabelLevelProperty() {
        this.labelLevel.setValue(String.valueOf(this.teamModel.getLevel()));
        return this.labelLevel;
    }

    public StringProperty getLabelCountdownProperty() {
        this.labelCountdown.setValue(String.valueOf(this.gameModel.getInitialCountdown()));
        return this.labelCountdown;
    }

    public IntegerProperty getLabelScoreProperty() {
        this.labelScore.setValue(this.teamModel.getScore());
        return this.labelScore;
    }

    public BooleanProperty getTeamLivesHiddenProperty(int lifeIndex) {
        return this.teamLivesHidden.get(lifeIndex);
    }
}
