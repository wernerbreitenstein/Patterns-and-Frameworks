package puf.frisbee.frontend.viewmodel;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.util.Duration;
import puf.frisbee.frontend.core.Constants;
import puf.frisbee.frontend.model.*;
import puf.frisbee.frontend.model.Character;
import puf.frisbee.frontend.network.GameRunningStatus;
import puf.frisbee.frontend.network.SocketRequestType;
import puf.frisbee.frontend.utils.Calculations;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class GameViewModel {
    /**
     * Instance of the game model.
     */
    private final Game gameModel;

    /**
     * Instance of the level model.
     */
    private final Level levelModel;

    /**
     * Instance of the team model.
     */
    private final Team teamModel;

    /**
     * Instance of the character model.
     */
    private final Character characterModel;

    /**
     * Manages the listeners to changes in the waiting view model due to socket
     * connection.
     */
    private final PropertyChangeSupport support;

    /**
     * The timeline of the countdown.
     */
    private Timeline timeline;

    /**
     * The current second the countdown is at.
     */
    private int second;

    /**
     * The remaining lives in the current game.
     */
    private int remainingLives;

    /**
     * The hidden team lives as an observable array list.
     */
    private ArrayList<BooleanProperty> teamLivesHidden;

    /**
     * The character type of the own character. Left or right.
     */
    private CharacterType ownCharacter;

    /**
     * The value of the own character's x position.
     * Will be updated during the game.
     */
    private DoubleProperty ownCharacterXPosition;

    /**
     * The value of the own character's y position.
     * Will be updated during the game.
     */
    private DoubleProperty ownCharacterYPosition;

    /**
     * The value of the other character's x position.
     * Will be updated during the game.
     */
    private DoubleProperty otherCharacterXPosition;

    /**
     * The value of the other character's y position.
     * Will be updated during the game.
     */
    private DoubleProperty otherCharacterYPosition;

    /**
     * The own character's right hand x position.
     */
    private double ownCharacterCatchingZoneRightX;

    /**
     * The own character's right hand y position.
     */
    private double ownCharacterCatchingZoneRightY;

    /**
     * The own character's left hand x position.
     */
    private double ownCharacterCatchingZoneLeftX;

    /**
     * The own character's left hand y position.
     */
    private double ownCharacterCatchingZoneLeftY;

    /**
     * The other character's right hand x position.
     */
    private double otherCharacterCatchingZoneRightX;

    /**
     * The other character's right hand y position.
     */
    private double otherCharacterCatchingZoneRightY;

    /**
     * The other character's left hand x position.
     */
    private double otherCharacterCatchingZoneLeftX;

    /**
     * The other character's left hand y position.
     */
    private double otherCharacterCatchingZoneLeftY;

    /**
     * Flag that indicates if own character is moving left.
     */
    private boolean isOwnCharacterMovingLeft;

    /**
     * Flag that indicates if own character is moving right.
     */
    private boolean isOwnCharacterMovingRight;

    /**
     * Flag that indicates if own character is throwing the frisbee.
     */
    private boolean isOwnCharacterThrowing;

    /**
     * The value of the other frisbee's x position.
     * Will be updated during the game.
     */
    private DoubleProperty frisbeeXPosition;

    /**
     * The value of the other frisbee's y position.
     * Will be updated during the game.
     */
    private DoubleProperty frisbeeYPosition;

    /**
     * Flag that indicates if the frisbee is moving right now.
     */
    private boolean isFrisbeeMoving;

    /**
     * Helper property for the frisbee movement calculation.
     * Indicates if the highest point, the frisbee is allowed to be, is reached.
     */
    private boolean isHighestFrisbeePointReached;

    /**
     * The frisbee's speed in x direction.
     */
    private double frisbeeSpeedX;

    /**
     * The frisbee's speed in y direction.
     */
    private double frisbeeSpeedY;

    /**
     * The frisbee's throw direction.
     */
    private int frisbeeThrowDirection;

    /**
     * The frisbee's timeline counter, needed for frisbee movement calculation.
     */
    private double frisbeeTimelineCounter;

    /**
     * The value of the flag that indicates if the level success dialog is shown.
     */
    private final BooleanProperty showLevelSuccessDialog;

    /**
     * The value of the flag that indicates if the game success dialog is shown.
     */
    private final BooleanProperty showGameSuccessDialog;

    /**
     * The value of the flag that indicates if the game over dialog is shown.
     */
    private final BooleanProperty showGameOverDialog;

    /**
     * The value of the flag that indicates if the pause dialog is shown.
     */
    private final BooleanProperty showQuitConfirmDialog;

    /**
     * The value of the flag that indicates if the disconnect dialog is shown.
     */
    private final BooleanProperty showDisconnectDialog;

    /**
     * The value of the text displayed on the continue button in the level
     * success dialog.
     */
    private final StringProperty buttonLevelContinueText;

    /**
     * The value of the text displayed as headline in the level
     * success dialog.
     */
    private final StringProperty labelLevelSuccess;

    /**
     * The value of the countdown label in the top panel.
     * Will be updated during the game.
     */
    private final StringProperty labelCountdown;

    /**
     * The value of the team name label in the top panel.
     */
    private final StringProperty labelTeamName;

    /**
     * The value of the level label in the top panel.
     */
    private final StringProperty labelLevel;

    /**
     * The value of the score label in the top panel.
     * Will be updated during the game.
     */
    private final IntegerProperty labelScore;

    /**
     * Constructs the game model view and sets all needed model instances.
     * Initializes all values needed for bindings and all flags needed for
     * the game. Also subscribes to the character model events to be able
     * to react on changes triggered by the socket connection.
     * Starts the countdown and animation timer.
     *
     * @param gameModel      game model instance
     * @param levelModel     level model instance
     * @param teamModel      team model instance
     * @param characterModel character model instance
     */
    public GameViewModel(Game gameModel, Level levelModel, Team teamModel,
                         Character characterModel) {
        this.gameModel = gameModel;
        this.levelModel = levelModel;
        this.teamModel = teamModel;
        this.characterModel = characterModel;

        // own property change support to trigger redirects in the view
        this.support = new PropertyChangeSupport(this);

        // these are called when character model triggers change
        characterModel.addPropertyChangeListener(SocketRequestType.MOVE,
                this::executeOtherCharacterMovement);
        characterModel.addPropertyChangeListener(SocketRequestType.THROW,
                this::executeOtherCharacterFrisbeeThrow);
        characterModel.addPropertyChangeListener(SocketRequestType.GAME_RUNNING,
                this::updateOtherCharacterGameRunningStatus);
        characterModel.addPropertyChangeListener(SocketRequestType.READY,
                this::updateOtherCharacterOnDisconnect);

        // initialize lives, character positions and frisbee position
        // (including flags)
        initializeLives();
        initializeCharacters();
        initializeFrisbee();

        // initially hide all dialogs
        this.showLevelSuccessDialog = new SimpleBooleanProperty(false);
        this.showGameSuccessDialog = new SimpleBooleanProperty(false);
        this.showGameOverDialog = new SimpleBooleanProperty(false);
        this.showQuitConfirmDialog = new SimpleBooleanProperty(false);
        this.showDisconnectDialog = new SimpleBooleanProperty(false);

        this.buttonLevelContinueText = new SimpleStringProperty(
                "Yes, take me to level " + (this.teamModel.getLevel()
                        + 1) + ".");
        this.labelLevelSuccess = new SimpleStringProperty(
                "Hey, you finished level " + this.teamModel.getLevel()
                        + " … " + "go ahead?");

        this.labelCountdown = new SimpleStringProperty();
        this.labelTeamName = new SimpleStringProperty(this.teamModel.getName());
        this.labelLevel = new SimpleStringProperty(
                String.valueOf(this.teamModel.getLevel()));
        this.labelScore = new SimpleIntegerProperty(this.teamModel.getScore());

        this.startCountdown();
        this.startAnimation();
    }

    /**
     * Sets initial positions and catching zones for own and other character,
     * left or right depends on the own character type.
     * Also sets all related flags.
     */
    private void initializeCharacters() {
        this.ownCharacter = teamModel.getOwnCharacterType();
        this.ownCharacterXPosition = new SimpleDoubleProperty(
                this.ownCharacter == CharacterType.LEFT
                        ? levelModel.getInitialCharacterLeftXPosition()
                        : levelModel.getInitialCharacterRightXPosition());
        this.ownCharacterCatchingZoneLeftX =
                this.ownCharacter == CharacterType.LEFT
                        ? Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_X
                        : Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_X;
        this.ownCharacterCatchingZoneLeftY =
                this.ownCharacter == CharacterType.LEFT
                        ? Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_Y
                        : Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_Y;
        this.ownCharacterCatchingZoneRightX =
                this.ownCharacter == CharacterType.LEFT
                        ? Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X
                        : Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_X;
        this.ownCharacterCatchingZoneRightY =
                this.ownCharacter == CharacterType.LEFT
                        ? Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y
                        : Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_Y;

        this.otherCharacterXPosition = new SimpleDoubleProperty(
                this.ownCharacter == CharacterType.LEFT
                        ? levelModel.getInitialCharacterRightXPosition()
                        : levelModel.getInitialCharacterLeftXPosition());
        this.otherCharacterCatchingZoneLeftX =
                this.ownCharacter == CharacterType.LEFT
                        ? Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_X
                        : Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_X;
        this.otherCharacterCatchingZoneLeftY =
                this.ownCharacter == CharacterType.LEFT
                        ? Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_Y
                        : Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_Y;
        this.otherCharacterCatchingZoneRightX =
                this.ownCharacter == CharacterType.LEFT
                        ? Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_X
                        : Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X;
        this.otherCharacterCatchingZoneRightY =
                this.ownCharacter == CharacterType.LEFT
                        ? Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_Y
                        : Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y;

        // y position is the same for both characters
        this.ownCharacterYPosition = new SimpleDoubleProperty(
                levelModel.getInitialCharacterYPosition());
        this.otherCharacterYPosition = new SimpleDoubleProperty(
                levelModel.getInitialCharacterYPosition());

        // nobody is moving in the beginning
        this.isOwnCharacterMovingLeft = false;
        this.isOwnCharacterMovingRight = false;
    }

    /**
     * Sets initial frisbee position and all related flags.
     */
    private void initializeFrisbee() {
        this.isFrisbeeMoving = false;
        // for now always the left character starts with the frisbee, this
        // can alternate in the levels later on
        this.isOwnCharacterThrowing = ownCharacter == CharacterType.LEFT;
        this.frisbeeXPosition = new SimpleDoubleProperty(
                levelModel.getInitialCharacterLeftXPosition()
                        + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X
                        - Constants.FRISBEE_RADIUS
        );
        this.frisbeeYPosition = new SimpleDoubleProperty(
                levelModel.getInitialCharacterYPosition()
                        + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y
                        - Constants.FRISBEE_RADIUS
        );
        // helper for the frisbee y position
        this.isHighestFrisbeePointReached = false;
    }

    /**
     * Sets the properties for the initial lives.
     */
    private void initializeLives() {
        this.remainingLives = teamModel.getLives();
        this.teamLivesHidden = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            BooleanProperty hidden = new SimpleBooleanProperty(
                    i >= this.remainingLives);
            this.teamLivesHidden.add(hidden);
        }
    }

    /**
     * This method is the heart of the game.
     * Starts the animation timer, detects (own) character movement and
     * executes the frisbee's throw.
     */
    private void startAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // moving is not possible once the level is over
                if (showQuitConfirmDialog.getValue() || showGameOverDialog.getValue() || showLevelSuccessDialog.getValue())
                    return;

                int characterSpeed = gameModel.getCharacterSpeed();
                int gravity = gameModel.getGravity();

                // only when character is not throwing and has enough
                // distance to other character, he is allowed to move
                if (isOwnCharacterMovingLeft && !hasOwnCharacterTheFrisbee()) {
                    ownCharacterXPosition.setValue(
                            ownCharacterXPosition.getValue() - characterSpeed);
                    characterModel.moveOwnCharacter(MovementDirection.LEFT);
                }
                if (isOwnCharacterMovingRight && !hasOwnCharacterTheFrisbee()) {
                    ownCharacterXPosition.setValue(
                            ownCharacterXPosition.getValue() + characterSpeed);
                    characterModel.moveOwnCharacter(MovementDirection.RIGHT);
                }

                // jumps are detected if character is not on its initial
                // position
                if (ownCharacterYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
                    ownCharacterYPosition.setValue(
                            ownCharacterYPosition.getValue() + gravity);
                } else if (isOwnCharacterThrowing && !isFrisbeeMoving) {
                    // set frisbee position to own character, when frisbee is
                    // not moving and when not jumping (anymore)
                    setFrisbeePositionToOwnCharacter();
                }
                if (otherCharacterYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
                    otherCharacterYPosition.setValue(
                            otherCharacterYPosition.getValue() + gravity);
                } else if (!isOwnCharacterThrowing && !isFrisbeeMoving) {
                    // set frisbee position to other character, when frisbee
                    // is not moving and when not jumping (anymore)
                    setFrisbeePositionToOtherCharacter();
                }

                // frisbee
                if (isFrisbeeMoving) {
                    // parameters are set when throwing
                    frisbeeMove();
                }
            }
        };
        timer.start();
    }

    /**
     * Starts the countdown in the top panel, counts down 1 each second.
     * On game over the timeline stops.
     * When the countdown reaches 0, either the level success or the game
     * success dialog visibility flag is set to true.
     */
    private void startCountdown() {
        this.second = gameModel.getCurrentCountdown();
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), actionEvent -> {
                    labelCountdown.setValue(Integer.toString(second));
                    second--;

                    if (this.showGameOverDialog.getValue()) {
                        timeline.stop();
                    }

                    if (!this.showGameOverDialog.getValue() && (second < 0)) {
                        timeline.stop();
                        labelCountdown.setValue("Time over");
                        showLevelSuccessDialog.setValue(
                                this.teamModel.getLevel() < gameModel.getMaximumLevel());
                        showGameSuccessDialog.setValue(
                                this.teamModel.getLevel() >= gameModel.getMaximumLevel());
                    }

                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Updates the team model with current data.
     * Pauses the timer in the top panel and sets the showQuitConfirmDialog
     * visibility to true.
     */
    private void pauseCountdown() {
        // save all values to the models, because on resume the view
        // model is re-initialized
        this.gameModel.setCurrentCountdown(this.second);
        this.teamModel.setScore(this.labelScore.getValue());
        this.teamModel.setLives(this.remainingLives);

        if (!this.showGameOverDialog.getValue()) {
            this.timeline.pause();
            this.showQuitConfirmDialog.setValue(true);
        }
    }

    /**
     * This method is called when the other character moves.
     * Moves the other character depending on the values received from the
     * socket connection.
     *
     * @param event property change event
     */
    private void executeOtherCharacterMovement(PropertyChangeEvent event) {
        MovementDirection movementDirection =
                (MovementDirection) event.getNewValue();
        if (movementDirection == MovementDirection.LEFT) {
            // update in javafx thread
            Platform.runLater(() -> otherCharacterXPosition.setValue(
                    otherCharacterXPosition.getValue() - gameModel.getCharacterSpeed()));
        }

        if (movementDirection == MovementDirection.RIGHT) {
            // update in javafx thread
            Platform.runLater(() -> otherCharacterXPosition.setValue(
                    otherCharacterXPosition.getValue() + gameModel.getCharacterSpeed()));
        }

        if (movementDirection == MovementDirection.UP) {
            // update in javafx thread
            Platform.runLater(() -> this.otherCharacterYPosition.setValue(
                    this.otherCharacterYPosition.getValue() - this.levelModel.getJumpHeight()));
        }
    }

    /**
     * This method is called when the other character throws.
     * Sets the frisbee's throw parameter as received from the socket
     * connection and triggers a frisbee's throw from the other character.
     *
     * @param event property change event
     */
    private void executeOtherCharacterFrisbeeThrow(PropertyChangeEvent event) {
        this.resetThrowParameter();

        try {
            // we get the parameter from the socket and calculate the curve here
            FrisbeeParameter parameter = (FrisbeeParameter) event.getNewValue();
            this.frisbeeSpeedX = parameter.getFrisbeeSpeedX();
            this.frisbeeSpeedY = parameter.getFrisbeeSpeedY();
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
        this.frisbeeTimelineCounter = 0;
        // throw direction is always the opposite of the own character throw
        // direction
        this.frisbeeThrowDirection = this.ownCharacter == CharacterType.LEFT
                ? -1 : 1;
        // this triggers the animation timer frisbee moving
        Platform.runLater(() -> isFrisbeeMoving = true);
    }

    /**
     * This method is called when the other character changes the game
     * running status, e.g. by continuing the level or stopping the game.
     * It either opens or closes dialogs or notifies the view to continue the
     * game, so the game of both players is synchronized.
     *
     * @param event property change event
     */
    private void updateOtherCharacterGameRunningStatus(
            PropertyChangeEvent event) {
        GameRunningStatus gameRunningStatus = (GameRunningStatus) event.getNewValue();

        if (gameRunningStatus == GameRunningStatus.PAUSE) {
            Platform.runLater(this::pauseCountdown);
        }

        if (gameRunningStatus == GameRunningStatus.RESUME) {
            // trigger a redirect to notify view like this, since the
            // redirect is not an element on the view
            Platform.runLater(() -> {
                this.support.firePropertyChange("running", null, true);
            });
        }

        if (gameRunningStatus == GameRunningStatus.CONTINUE) {
            // reload team data from backend to be sure to have the newest data
            // to display
            this.teamModel.reloadTeamData();
            // reset countdown
            this.gameModel.setCurrentCountdown(
                    this.gameModel.getInitialCountdown());
            // trigger a redirect to notify view like this, since the
            // redirect is not an element on the view
            Platform.runLater(() -> {
                this.showLevelSuccessDialog.setValue(false);
                this.support.firePropertyChange("running", null, true);
            });
        }
    }

    /**
     * This method is called when the other player disconnects from the game.
     * It opens a disconnect dialog.
     *
     * @param event property change event
     */
    private void updateOtherCharacterOnDisconnect(PropertyChangeEvent event) {
        boolean ready = (boolean) event.getNewValue();
        if (!ready && !this.showDisconnectDialog.getValue()) {
            // we got not ready from the server, which means, the other
            // character probably disconnected show a dialog (if not already
            // there) and close others
            Platform.runLater(() -> {
                showGameSuccessDialog.setValue(false);
                showLevelSuccessDialog.setValue(false);
                showQuitConfirmDialog.setValue(false);
                showDisconnectDialog.setValue(true);
            });
        }
    }

    /**
     * The frisbee movement calculation. Should be called and executed in the
     * animation timer.
     */
    private void frisbeeMove() {
        double frisbeeYDirection;
        double t;
        double upperLimit =
                Constants.SCENE_HEIGHT
                        - Constants.CHARACTER_HEIGHT
                        - this.levelModel.getGroundHeight()
                        - this.levelModel.getJumpHeight() - 300;

        if (this.frisbeeYPosition.getValue() <= upperLimit) {
            this.isHighestFrisbeePointReached = true;
        }

        // positive is down, negative is up
        if (isHighestFrisbeePointReached) {
            frisbeeYDirection = 1;
            t = --frisbeeTimelineCounter;
        } else {
            frisbeeYDirection = -1;
            t = ++frisbeeTimelineCounter;
        }

        double frisbeeX =
                this.frisbeeXPosition.getValue() + (this.frisbeeSpeedX + 60 / t) * this.frisbeeThrowDirection;
        double frisbeeY =
                this.frisbeeYPosition.getValue() + this.frisbeeSpeedY + ((60 * t) / (t * t) - (t / 30)) * frisbeeYDirection;

        this.frisbeeXPosition.setValue(frisbeeX);
        this.frisbeeYPosition.setValue(frisbeeY);

        // check only other character collision if own character is throwing
        if (isOwnCharacterThrowing) {
            // frisbee is an image view, for the center we need to substract
            // half of the size
            boolean collisionWithCharacterRightCatchingZoneLeft =
                    Calculations.circlesIntersect(
                            frisbeeX + Constants.FRISBEE_RADIUS,
                            frisbeeY + Constants.FRISBEE_RADIUS,
                            Constants.FRISBEE_RADIUS,
                            // catching zone is relative to character position
                            this.otherCharacterXPosition.getValue() + this.otherCharacterCatchingZoneLeftX,
                            this.otherCharacterYPosition.getValue() + this.otherCharacterCatchingZoneLeftY,
                            Constants.CHARACTER_CATCHING_RADIUS);
            boolean collisionWithCharacterRightCatchingZoneRight =
                    Calculations.circlesIntersect(
                            frisbeeX + Constants.FRISBEE_RADIUS,
                            frisbeeY + Constants.FRISBEE_RADIUS,
                            Constants.FRISBEE_RADIUS,
                            // catching zone is relative to character position
                            this.otherCharacterXPosition.getValue() + otherCharacterCatchingZoneRightX,
                            this.otherCharacterYPosition.getValue() + otherCharacterCatchingZoneRightY,
                            Constants.CHARACTER_CATCHING_RADIUS);

            if (collisionWithCharacterRightCatchingZoneLeft || collisionWithCharacterRightCatchingZoneRight) {
                incrementScore();
                this.isFrisbeeMoving = false;
                this.isOwnCharacterThrowing = false;
                return;
            }
        } else {
            // check only own character collision if other character is throwing
            // catching zone is relative to character position
            boolean collisionWithCharacterLeftCatchingZoneLeft =
                    Calculations.circlesIntersect(
                            frisbeeX + Constants.FRISBEE_RADIUS,
                            frisbeeY + Constants.FRISBEE_RADIUS,
                            Constants.FRISBEE_RADIUS,
                            // catching zone is relative to character position
                            this.ownCharacterXPosition.getValue() + this.ownCharacterCatchingZoneLeftX,
                            this.ownCharacterYPosition.getValue() + this.ownCharacterCatchingZoneLeftY,
                            Constants.CHARACTER_CATCHING_RADIUS);
            boolean collisionWithCharacterLeftCatchingZoneRight =
                    Calculations.circlesIntersect(
                            frisbeeX + Constants.FRISBEE_RADIUS,
                            frisbeeY + Constants.FRISBEE_RADIUS,
                            Constants.FRISBEE_RADIUS,
                            // catching zone is relative to character position
                            this.ownCharacterXPosition.getValue() + this.ownCharacterCatchingZoneRightX,
                            this.ownCharacterYPosition.getValue() + this.ownCharacterCatchingZoneRightY,
                            Constants.CHARACTER_CATCHING_RADIUS);

            if (collisionWithCharacterLeftCatchingZoneLeft || collisionWithCharacterLeftCatchingZoneRight) {
                incrementScore();
                this.isFrisbeeMoving = false;
                this.isOwnCharacterThrowing = true;
                return;
            }
        }

        // check all boundaries of scene
        if (frisbeeX >= Constants.SCENE_WIDTH ||
                frisbeeX <= 0 - Constants.FRISBEE_RADIUS * 2 ||
                frisbeeY + Constants.FRISBEE_RADIUS * 2 >= Constants.SCENE_HEIGHT - levelModel.getGroundHeight()) {
            this.isFrisbeeMoving = false;
            this.removeLife();

            // add timeout so frisbee is not imediately available
            setFrisbeeTimeout();

            // set frisbee throwing turn to player who did not catch the frisbee
            this.isOwnCharacterThrowing = !this.isOwnCharacterThrowing;
        }
    }

    /**
     * Increments the score on the score label. Does not save anything, this
     * will be done only when the level is finished.
     */
    private void incrementScore() {
        this.labelScore.setValue(this.labelScore.getValue() + 1);
    }

    /**
     * Removes a life from the remaining lives and updates the observable
     * array list, so the changes are reflected in the view.
     * If the last life is lost, the game over dialog is shown.
     */
    private void removeLife() {
        this.remainingLives--;
        for (int i = 0; i < 5; i++) {
            this.teamLivesHidden.get(i).setValue(i >= this.remainingLives);
        }

        if (this.remainingLives == 0) {
            this.showGameOverDialog.setValue(true);
        }
    }

    /**
     * Helper function to reset the throw parameter for a frisbee's throw.
     */
    private void resetThrowParameter() {
        // reset curve helper for new frisbee throw
        this.isHighestFrisbeePointReached = false;
        this.frisbeeTimelineCounter = 0;
    }

    /**
     * Sets a timeout. Can be used after a frisbee's throw was done.
     */
    private void setFrisbeeTimeout() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Timeout exception");
        }
    }

    /**
     * Helper function to check if own character is holding the frisbee.
     *
     * @return true if character holds the frisbee, false if not
     */
    private boolean hasOwnCharacterTheFrisbee() {
        return this.isOwnCharacterThrowing && !this.isFrisbeeMoving;
    }

    /**
     * Updates the frisbee's position to the right hand of the own character.
     */
    private void setFrisbeePositionToOwnCharacter() {
        this.frisbeeXPosition.setValue(
                this.ownCharacterXPosition.getValue() + ownCharacterCatchingZoneRightX - Constants.FRISBEE_RADIUS);
        this.frisbeeYPosition.setValue(
                this.ownCharacterYPosition.getValue() + ownCharacterCatchingZoneRightY - Constants.FRISBEE_RADIUS);
    }

    /**
     * Updates the frisbee's position to the right hand of the other character.
     */
    private void setFrisbeePositionToOtherCharacter() {
        this.frisbeeXPosition.setValue(
                this.otherCharacterXPosition.getValue() + otherCharacterCatchingZoneRightX - Constants.FRISBEE_RADIUS);
        this.frisbeeYPosition.setValue(
                this.otherCharacterYPosition.getValue() + otherCharacterCatchingZoneRightY - Constants.FRISBEE_RADIUS);
    }

    /**
     * Helper function to check, if the own character has reached the left
     * scene border.
     *
     * @return true if left border is reached, false if not
     */
    private boolean isLeftBorderReachedByCharacter() {
        return this.ownCharacterXPosition.getValue() <= this.levelModel.getSceneBoundaryLeft();
    }

    /**
     * Helper function to check, if the own character has reached the right
     * scene border.
     *
     * @return true if right border is reached, false if not
     */
    private boolean isRightBorderReachedByCharacter() {
        return this.ownCharacterXPosition.getValue() >= this.levelModel.getSceneBoundaryRight();
    }

    /**
     * Helper function to check, if own character is allowed to jump.
     *
     * @return true if character is allowed to jump, false if not
     */
    private boolean isCharacterAllowedToJump() {
        return this.ownCharacterYPosition.getValue() == levelModel.getInitialCharacterYPosition()
                && !this.isOwnCharacterMovingLeft
                && !this.isOwnCharacterMovingRight
                // no jump if the character still needs to throw the frisbee
                && !this.isOwnCharacterThrowing
                && !showQuitConfirmDialog.getValue()
                && !showGameOverDialog.getValue()
                && !showLevelSuccessDialog.getValue();
    }

    /**
     * This method is called when the own character throws the frisbee.
     * It sets the frisbee's throw parameter and triggers the throw in the
     * animation timer.
     */
    public void throwFrisbee() {
        // if the own character does not have the frisbee then do nothing
        if (!isOwnCharacterThrowing) {
            return;
        }
        // reset curve helper for new frisbee throw
        this.resetThrowParameter();
        // set frisbee speed random
        this.frisbeeSpeedX = Math.random() * 8 + 2;
        this.frisbeeSpeedY = Math.random() * 2 + 0.2;
        // send to socket
        this.characterModel.throwFrisbee(
                new FrisbeeParameter(frisbeeSpeedX, frisbeeSpeedY));

        this.frisbeeThrowDirection = this.ownCharacter == CharacterType.LEFT
                ? 1 : -1;
        // throw frisbee
        this.isFrisbeeMoving = true;
    }

    /**
     * This method is called when the player presses the arrow key left.
     * It sets the flag for the own left movement if the character is
     * allowed to move left.
     */
    public void moveCharacterLeft() {
        this.isOwnCharacterMovingLeft =
                !this.isLeftBorderReachedByCharacter() &&
                        (this.ownCharacterXPosition.getValue() - otherCharacterXPosition.getValue() > 350 ||
                                // this check is needed so the character can
                                // still move right, when not allowed to move
                                // left anymore
                                this.ownCharacterXPosition.getValue() - otherCharacterXPosition.getValue() < 0);
    }

    /**
     * This method is called when the player presses the arrow key right.
     * It sets the flag for the own right movement if the character is
     * allowed to move right.
     */
    public void moveCharacterRight() {
        this.isOwnCharacterMovingRight =
                !this.isRightBorderReachedByCharacter() &&
                        (this.otherCharacterXPosition.getValue() - ownCharacterXPosition.getValue() > 350 ||
                                // this check is needed so the character can
                                // still move left, when not allowed to move
                                // right anymore
                                this.otherCharacterXPosition.getValue() - ownCharacterXPosition.getValue() < 0);
    }

    /**
     * This method is called when the player releases the arrow left key.
     * It sets the flag for the own left movement to false and triggers a stop.
     */
    public void stopCharacterMoveLeft() {
        this.isOwnCharacterMovingLeft = false;
    }

    /**
     * This method is called when the player releases the arrow right key.
     * It sets the flag for the own right movement to false and triggers a stop.
     */
    public void stopCharacterMoveRight() {
        this.isOwnCharacterMovingRight = false;
    }

    /**
     * This method is called when the player presses the arrow up key.
     * It triggers a jump if the character is allowed to jump.
     */
    public void jumpCharacter() {
        if (isCharacterAllowedToJump()) {
            this.ownCharacterYPosition.setValue(
                    this.ownCharacterYPosition.getValue() - this.levelModel.getJumpHeight());
            this.characterModel.moveOwnCharacter(MovementDirection.UP);
        }
    }

    /**
     * Triggers a disconnect so the other client can react.
     */
    public void disconnect() {
        this.characterModel.stop();
    }

    /**
     * Triggers the "Pause" Dialog and sends GAME_RUNNING=false to the other
     * client.
     */
    public void showQuitConfirmDialog() {
        // notify other client to also pause
        this.characterModel.pauseGame();
        // trigger all actions
        this.pauseCountdown();
    }

    /**
     * Continues the game after level success and sends a message to the other
     * client.
     */
    public void continueGame() {
        // notify other client to also continue
        this.characterModel.continueGame();
    }

    /**
     * Continues the game after pause and sends a message to the other
     * client.
     */
    public void resumeGame() {
        this.characterModel.resumeGame();
    }

    /**
     * Save the game data from the team model to the backend.
     *
     * @return true if saving was successful
     */
    private boolean saveGame() {
        // reset countdown
        this.gameModel.setCurrentCountdown(
                this.gameModel.getInitialCountdown());
        // save to backend
        return this.teamModel.saveTeamData();
    }

    /**
     * This method is called when a level has been finished successfully.
     * It increases the level and triggers a save to the backend.
     *
     * @return true if saving was successful
     */
    public boolean saveAfterLevelSucceeded() {
        // increase level after successful level
        this.teamModel.setLevel(this.teamModel.getLevel() + 1);
        this.teamModel.setScore(this.labelScore.getValue());
        this.teamModel.setLives(this.remainingLives);
        this.teamModel.setActive(true);
        return this.saveGame();
    }

    /**
     * This method is called when a game has been finished successfully.
     * It triggers a save to the backend and sets the team inactive.
     */
    public void saveAfterGameSucceeded() {
        // skip updating the level, we are at the end
        this.teamModel.setScore(this.labelScore.getValue());
        this.teamModel.setLives(this.remainingLives);
        // no more games for this team after finishing the last level
        this.teamModel.setActive(false);
        this.saveGame();
    }

    /**
     * This method is called when a game is over due to lost lives.
     * It triggers a save to the backend and sets the team inactive.
     */
    public void saveAfterGameOver() {
        // skip updating the level, we died in the current level
        this.teamModel.setScore(this.labelScore.getValue());
        this.teamModel.setLives(this.remainingLives);
        // no more games for this team because of game over
        this.teamModel.setActive(false);
        this.saveGame();
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
     * Method for the binding of the left character's x position value with an
     * element in the view. Can be either own character's position or other
     * character's position.
     *
     * @return left character's x position value
     */
    public DoubleProperty getCharacterLeftXPositionProperty() {
        // return own character position if own character is left, otherwise
        // other character position
        return this.ownCharacter == CharacterType.LEFT
                ? this.ownCharacterXPosition : this.otherCharacterXPosition;
    }

    /**
     * Method for the binding of the left character's y position value with an
     * element in the view. Can be either own character's position or other
     * character's position.
     *
     * @return left character's y position value
     */
    public DoubleProperty getCharacterLeftYPositionProperty() {
        // return own character position if own character is left, otherwise
        // other character position
        return this.ownCharacter == CharacterType.LEFT
                ? this.ownCharacterYPosition : this.otherCharacterYPosition;
    }

    /**
     * Method for the binding of the right character's x position value with an
     * element in the view. Can be either own character's position or other
     * character's position.
     *
     * @return right character's x position value
     */
    public DoubleProperty getCharacterRightXPositionProperty() {
        // return own character position, if own character is right,
        // otherwise other character position
        return this.ownCharacter == CharacterType.RIGHT
                ? this.ownCharacterXPosition : this.otherCharacterXPosition;
    }

    /**
     * Method for the binding of the right character's y position value with an
     * element in the view. Can be either own character's position or other
     * character's position.
     *
     * @return right character's y position value
     */
    public DoubleProperty getCharacterRightYPositionProperty() {
        // return own character position if own character is right,—
        // otherwise other character position
        return this.ownCharacter == CharacterType.RIGHT
                ? this.ownCharacterYPosition : this.otherCharacterYPosition;
    }

    /**
     * Method for the binding of the frisbee's x position value with an
     * element in the view.
     *
     * @return frisbee's x position value
     */
    public DoubleProperty getFrisbeeXPositionProperty() {
        return this.frisbeeXPosition;
    }

    /**
     * Method for the binding of the frisbee's y position value with an
     * element in the view.
     *
     * @return frisbee's y position value
     */
    public DoubleProperty getFrisbeeYPositionProperty() {
        return this.frisbeeYPosition;
    }

    /**
     * Method for the binding of the visibility of the level success dialog
     * with an element in the view.
     *
     * @return the level success dialog visibility flag
     */
    public BooleanProperty getLevelSuccessDialogProperty() {
        return this.showLevelSuccessDialog;
    }

    /**
     * Method for the binding of the visibility of the game success dialog
     * with an element in the view.
     *
     * @return the game success dialog visibility flag
     */
    public BooleanProperty getGameSuccessDialogProperty() {
        return this.showGameSuccessDialog;
    }

    /**
     * Method for the binding of the visibility of the game over dialog
     * with an element in the view.
     *
     * @return the game over dialog visibility flag
     */
    public BooleanProperty getGameOverDialogProperty() {
        return this.showGameOverDialog;
    }

    /**
     * Method for the binding of the visibility of the pause (= quit confirm)
     * dialog with an element in the view.
     *
     * @return the pause dialog visibility flag
     */
    public BooleanProperty getQuitConfirmDialogProperty() {
        return this.showQuitConfirmDialog;
    }

    /**
     * Method for the binding of the visibility of the disconnect dialog
     * with an element in the view.
     *
     * @return the disconnect dialog visibility flag
     */
    public BooleanProperty getDisconnectDialogProperty() {
        return this.showDisconnectDialog;
    }

    /**
     * Method for the binding of the level continue button's text value with an
     * element in the view.
     *
     * @return level continue button text value
     */
    public StringProperty getButtonLevelContinueTextProperty() {
        return this.buttonLevelContinueText;
    }

    /**
     * Method for the binding of the level success label's value with an
     * element in the view.
     *
     * @return level success label value
     */
    public StringProperty getLabelLevelSuccessProperty() {
        return this.labelLevelSuccess;
    }

    /**
     * Method for the binding of the countdown label's value with an
     * element in the view.
     *
     * @return countdown label value
     */
    public StringProperty getLabelCountdownProperty() {
        return this.labelCountdown;
    }

    /**
     * Method for the binding of the team label's value with an
     * element in the view.
     *
     * @return team label value
     */
    public StringProperty getLabelTeamProperty() {
        return this.labelTeamName;
    }

    /**
     * Method for the binding of the level label's value with an
     * element in the view.
     *
     * @return level label value
     */
    public StringProperty getLabelLevelProperty() {
        return this.labelLevel;
    }

    /**
     * Method for the binding of the score label's value with an
     * element in the view.
     *
     * @return score label value
     */
    public IntegerProperty getLabelScoreProperty() {
        return this.labelScore;
    }

    /**
     * Method to subscribe listeners to game view model events.
     *
     * @param listener the function that should be executed on changes
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
