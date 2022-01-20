package puf.frisbee.frontend.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import puf.frisbee.frontend.model.*;
import puf.frisbee.frontend.model.Character;
import puf.frisbee.frontend.network.SocketRequestType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class WaitingViewModel {
    private Game gameModel;
    private Team teamModel;
    private Character characterModel;

    private BooleanProperty showLevel01BackgroundImage;
    private BooleanProperty showLevel02BackgroundImage;
    private BooleanProperty showLevel03BackgroundImage;
    private StringProperty labelTeamName;
    private StringProperty labelLevel;
    private IntegerProperty labelScore;
    private StringProperty labelCountdown;
    private StringProperty labelPlayerGreeting;
    private BooleanProperty startButtonDisabled;

    private ArrayList<BooleanProperty> teamLivesHidden;

    private String waitingMessage;

    PropertyChangeSupport support;

    public WaitingViewModel(Game gameModel, Team teamModel, Character characterModel) {
        this.gameModel = gameModel;
        this.teamModel = teamModel;
        this.characterModel = characterModel;

        this.support = new PropertyChangeSupport(this);

        // add listener to ready event
        this.characterModel.addPropertyChangeListener(SocketRequestType.READY, this::changeStartButtonEnabledProperty);
        this.characterModel.addPropertyChangeListener(SocketRequestType.GAME_RUNNING, this::changeRedirectToGameProperty);

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
        this.waitingMessage = "Please wait, player " +
                (teamModel.getOwnCharacterType() == CharacterType.LEFT ? "left" : "right") +
                ". The second freak will show up soon.";
        this.labelPlayerGreeting = new SimpleStringProperty(waitingMessage);
        // start button is disabled in the beginning
        this.startButtonDisabled = new SimpleBooleanProperty(true);

        // as soon as we are in the waiting view, tell the socket we are here
        this.characterModel.init();
    }

    private void changeStartButtonEnabledProperty(PropertyChangeEvent event) {
        boolean readyStatus = (boolean) event.getNewValue();

        String message = readyStatus ? "Ready to go! The game begins as soon as one player clicks the button." : waitingMessage;
        Platform.runLater(() ->this.labelPlayerGreeting.setValue(message));

        // when status is ready (= both players of a team connected) enable start button
        // when ready is true, disabled should be false and the other way round
        Platform.runLater(() ->this.startButtonDisabled.setValue(!readyStatus));
    }

    private void changeRedirectToGameProperty(PropertyChangeEvent event) {
        if((boolean) event.getNewValue()) {
            // notify view like this, since the redirect is not an element
            this.support.firePropertyChange("running", null, true);
        }
    }

    public void startGame() {
        this.characterModel.startGame();
    }

    public void quitWaiting() {
        this.characterModel.stop();
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

    public BooleanProperty getStartButtonDisabledProperty() {
        return this.startButtonDisabled;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
