package puf.frisbee.frontend.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import puf.frisbee.frontend.model.*;
import puf.frisbee.frontend.model.Character;
import puf.frisbee.frontend.network.SocketRequestType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class WaitingViewModel {
    /**
     * The game model instance.
     */
    private final Game gameModel;
    /**
     * The team model instance.
     */
    private final Team teamModel;
    /**
     * The character model instance.
     */
    private final Character characterModel;

    /**
     * The value of the background image.
     */
    private final ObjectProperty<Image> backgroundImage;
    /**
     * The value of the team name label.
     */
    private final StringProperty labelTeamName;
    /**
     * The value of the level label.
     */
    private final StringProperty labelLevel;
    /**
     * The value of the score label.
     */
    private final IntegerProperty labelScore;
    /**
     * The value of the countdown label.
     */
    private final StringProperty labelCountdown;
    /**
     * The value of the player greeting label.
     */
    private final StringProperty labelPlayerGreeting;
    /**
     * The value of flag that indicates, if the start button is shown.
     */
    private final BooleanProperty startButtonDisabled;
    /**
     * The hidden team lives as an observable array list.
     */
    private final ArrayList<BooleanProperty> teamLivesHidden;
    /**
     * The waiting message.
     */
    private final String waitingMessage;
    /**
     * Manages the listeners to changes in the waiting view model due to socket
     * connection.
     */
    private PropertyChangeSupport support;

    /**
     * Constructs the waiting view model and sets the needed model instances.
     * Also initializes all values needed for the bindings.
     * Subscribes to events from the character model and initializes the
     * socket connection.
     *
     * @param gameModel      game model instance
     * @param teamModel      team model instance
     * @param characterModel character model instance
     */
    public WaitingViewModel(Game gameModel, Team teamModel,
                            Character characterModel) {
        this.gameModel = gameModel;
        this.teamModel = teamModel;
        this.characterModel = characterModel;

        this.support = new PropertyChangeSupport(this);

        // add listener to ready event
        this.characterModel.addPropertyChangeListener(SocketRequestType.READY,
                this::changeStartButtonEnabledProperty);
        this.characterModel.addPropertyChangeListener(
                SocketRequestType.GAME_RUNNING,
                this::changeRedirectToGameProperty);

        this.teamLivesHidden = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            BooleanProperty hidden = new SimpleBooleanProperty(
                    i >= this.teamModel.getLives());
            this.teamLivesHidden.add(hidden);
        }

        this.backgroundImage = new SimpleObjectProperty(
                new Image(getClass().getResource(
                        this.teamModel.getBackgroundImageForLevel(
                                this.teamModel.getLevel())).toString()));
        this.labelTeamName = new SimpleStringProperty(this.teamModel.getName());
        this.labelLevel = new SimpleStringProperty(
                String.valueOf(this.teamModel.getLevel()));
        this.labelCountdown = new SimpleStringProperty(
                String.valueOf(this.gameModel.getInitialCountdown()));
        this.labelScore = new SimpleIntegerProperty(this.teamModel.getScore());
        this.waitingMessage = "Please wait, player "
                + (teamModel.getOwnCharacterType() == CharacterType.LEFT
                ? "left" : "right")
                + ". The second freak will show up soon.";
        this.labelPlayerGreeting = new SimpleStringProperty(waitingMessage);
        // start button is disabled in the beginning
        this.startButtonDisabled = new SimpleBooleanProperty(true);

        // as soon as we are in the waiting view, tell the socket we are here
        this.characterModel.init();
    }

    /**
     * This method is called when the ready status in the character model
     * changes.
     * It sets the player greeting value and enables or disables the start
     * button.
     *
     * @param event property change event
     */
    private void changeStartButtonEnabledProperty(PropertyChangeEvent event) {
        boolean readyStatus = (boolean) event.getNewValue();

        String message = readyStatus ? "Ready to go! The game begins as soon "
                + "as one player clicks the button." : waitingMessage;
        Platform.runLater(() -> this.labelPlayerGreeting.setValue(message));

        // when status is ready (= both players of a team connected) enable
        // start button
        // when ready is true, disabled should be false and the other way round
        Platform.runLater(
                () -> this.startButtonDisabled.setValue(!readyStatus));
    }

    /**
     * This method is called when the game running status in the character model
     * is true.
     * It notifies all subscribers of the waiting view model support.
     *
     * @param event property change event
     */
    private void changeRedirectToGameProperty(PropertyChangeEvent event) {
        if ((boolean) event.getNewValue()) {
            // notify view like this, since the redirect is not an element
            this.support.firePropertyChange("running", null, true);
        }
    }

    /**
     * Triggers the start of the game via the socket connection.
     */
    public void startGame() {
        this.characterModel.startGame();
    }

    /**
     * Triggers the stop of the socket connection.
     */
    public void quitWaiting() {
        this.characterModel.stop();
    }

    /**
     * Method for the binding of the background image value with an element in
     * the view.
     *
     * @return background image value
     */
    public ObjectProperty<Image> getBackgroundImageProperty() {
        return this.backgroundImage;
    }

    /**
     * Method for the binding of the team label value with an element in the
     * view.
     *
     * @return team label value
     */
    public StringProperty getLabelTeamProperty() {
        return this.labelTeamName;
    }

    /**
     * Method for the binding of the level label value with an element in the
     * view.
     *
     * @return level label value
     */
    public StringProperty getLabelLevelProperty() {
        return this.labelLevel;
    }

    /**
     * Method for the binding of the countdown label value with an element in
     * the view.
     *
     * @return countdown label value
     */
    public StringProperty getLabelCountdownProperty() {
        return this.labelCountdown;
    }

    /**
     * Method for the binding of the score label value with an element in the
     * view.
     *
     * @return score label value
     */
    public IntegerProperty getLabelScoreProperty() {
        return this.labelScore;
    }

    /**
     * Method for the binding of the visibility of a life with the given index
     * with an element in the view.
     *
     * @param lifeIndex the index of the life
     * @return flag for the visibility of the team life with the given index
     */
    public BooleanProperty getTeamLivesHiddenProperty(int lifeIndex) {
        return this.teamLivesHidden.get(lifeIndex);
    }

    /**
     * Method for the binding of the player greeting label value with an
     * element in the view.
     *
     * @return plauer greeting label value
     */
    public StringProperty getLabelPlayerGreetingProperty() {
        return this.labelPlayerGreeting;
    }

    /**
     * Method for the binding of the visibility of the start button
     * with an element in the view.
     *
     * @return the start button visibility flag
     */
    public BooleanProperty getStartButtonDisabledProperty() {
        return this.startButtonDisabled;
    }

    /**
     * Method to subscribe listeners to waiting view model events.
     *
     * @param listener the function that should be executed on changes
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
