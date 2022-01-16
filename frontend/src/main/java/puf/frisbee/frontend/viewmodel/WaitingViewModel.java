package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.*;
import puf.frisbee.frontend.model.*;

import java.util.ArrayList;

public class WaitingViewModel {
    private Game gameModel;
    private Team teamModel;

    private BooleanProperty showLevel01BackgroundImage;
    private BooleanProperty showLevel02BackgroundImage;
    private BooleanProperty showLevel03BackgroundImage;
    private StringProperty labelTeamName;
    private StringProperty labelLevel;
    private IntegerProperty labelScore;
    private StringProperty labelCountdown;
    private StringProperty labelPlayerGreeting;

    private ArrayList<BooleanProperty> teamLivesHidden;

    public WaitingViewModel(Game gameModel, Level levelModel, Team teamModel) {
        this.gameModel = gameModel;
        this.teamModel = teamModel;
        this.teamLivesHidden = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            BooleanProperty hidden = new SimpleBooleanProperty(i >= this.teamModel.getLives());
            this.teamLivesHidden.add(hidden);
        }

        this.showLevel01BackgroundImage = new SimpleBooleanProperty(false);
        this.showLevel02BackgroundImage = new SimpleBooleanProperty(false);
        this.showLevel03BackgroundImage = new SimpleBooleanProperty(false);

        this.labelTeamName = new SimpleStringProperty();
        this.labelLevel = new SimpleStringProperty();
        this.labelCountdown = new SimpleStringProperty();
        this.labelScore = new SimpleIntegerProperty();
        this.labelPlayerGreeting = new SimpleStringProperty(
                teamModel.getOwnCharacterType() == CharacterType.LEFT ? "PLEASE WAIT PLAYER LEFT." : "PLEASE WAIT PLAYER RIGHT."
        );
    }

    public BooleanProperty getShowLevel01BackgroundImageProperty() {
        if (this.teamModel.getLevel() <= 1) {
            this.showLevel01BackgroundImage.setValue(true);
        }
        return this.showLevel01BackgroundImage;
    }

    public BooleanProperty getShowLevel02BackgroundImageProperty() {
        if (this.teamModel.getLevel() == 2) {
            this.showLevel02BackgroundImage.setValue(true);
        }
        return this.showLevel02BackgroundImage;
    }

    public BooleanProperty getShowLevel03BackgroundImageProperty() {
        if (this.teamModel.getLevel() >= 3) {
            this.showLevel03BackgroundImage.setValue(true);
        }
        return this.showLevel03BackgroundImage;
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

    public StringProperty getLabelPlayerGreetingProperty() {
        return this.labelPlayerGreeting;
    }
}
