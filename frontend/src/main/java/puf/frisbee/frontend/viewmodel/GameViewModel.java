package puf.frisbee.frontend.viewmodel;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.util.Duration;
import puf.frisbee.frontend.core.Constants;
import puf.frisbee.frontend.model.*;
import puf.frisbee.frontend.model.Character;
import puf.frisbee.frontend.network.SocketRequestType;
import puf.frisbee.frontend.utils.Calculations;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class GameViewModel {
	private Game gameModel;
	private Level levelModel;
	private Team teamModel;
	private Character characterModel;

	private Timeline timeline;
	private int second;
	private int remainingLives;
	private double counter;

	private BooleanProperty showLevel01BackgroundImage;
	private BooleanProperty showLevel02BackgroundImage;
	private BooleanProperty showLevel03BackgroundImage;

	private CharacterType ownCharacter;

	private DoubleProperty ownCharacterXPosition;
	private DoubleProperty ownCharacterYPosition;
	private DoubleProperty otherCharacterXPosition;
	private DoubleProperty otherCharacterYPosition;

	private DoubleProperty frisbeeXPosition;
	private DoubleProperty frisbeeYPosition;

	private double ownCharacterCatchingZoneRightX;
	private double ownCharacterCatchingZoneRightY;
	private double ownCharacterCatchingZoneLeftX;
	private double ownCharacterCatchingZoneLeftY;

	private double otherCharacterCatchingZoneRightX;
	private double otherCharacterCatchingZoneRightY;
	private double otherCharacterCatchingZoneLeftX;
	private double otherCharacterCatchingZoneLeftY;


	private boolean isCharacterMovingLeft;
	private boolean isCharacterMovingRight;

	private boolean isFrisbeeMoving;
	private boolean isOwnCharacterThrowing;
	private boolean isHighestFrisbeePointReached;
	private double frisbeeSpeedX;
	private double frisbeeSpeedY;
	private int frisbeeThrowDirection;

	private BooleanProperty showLevelSuccessDialog;
	private BooleanProperty showGameSuccessDialog;
	private BooleanProperty showGameOverDialog;
	private BooleanProperty showQuitConfirmDialog;
	private StringProperty buttonLevelContinueText;
	private StringProperty labelCountdown;
	private StringProperty labelLevel;
	private StringProperty labelLevelSuccess;
	private StringProperty labelTeamName;
	private IntegerProperty labelScore;

	private ArrayList<BooleanProperty> teamLivesHidden;

	public GameViewModel(Game gameModel, Level levelModel, Team teamModel, Character characterModel) {
		this.gameModel = gameModel;
		this.levelModel = levelModel;
		this.teamModel = teamModel;
		this.characterModel = characterModel;
		// executeActionsReceivedFromSocket is called when character model triggers change
		characterModel.addPropertyChangeListener(SocketRequestType.MOVE, this::executeOtherCharacterMovement);

		this.remainingLives = teamModel.getLives();
		this.teamLivesHidden = new ArrayList<>(5);
		for (int i = 0; i < 5; i++) {
			BooleanProperty hidden = new SimpleBooleanProperty(i >= this.remainingLives);
			this.teamLivesHidden.add(hidden);
		}

		this.showLevel01BackgroundImage = new SimpleBooleanProperty(false);
		this.showLevel02BackgroundImage = new SimpleBooleanProperty(false);
		this.showLevel03BackgroundImage = new SimpleBooleanProperty(false);
		
		this.labelLevel = new SimpleStringProperty();
		this.labelCountdown = new SimpleStringProperty();
		this.labelLevelSuccess = new SimpleStringProperty();
		this.buttonLevelContinueText = new SimpleStringProperty();
		this.showLevelSuccessDialog = new SimpleBooleanProperty(false);
		this.showGameSuccessDialog = new SimpleBooleanProperty(false);
		this.showGameOverDialog = new SimpleBooleanProperty(false);
		this.showQuitConfirmDialog = new SimpleBooleanProperty(false);
		this.labelTeamName = new SimpleStringProperty();
		this.labelScore = new SimpleIntegerProperty();

		this.ownCharacter = teamModel.getOwnCharacterType();
		initializeCharacterPositions();

		this.isCharacterMovingLeft = false;
		this.isCharacterMovingRight = false;

		this.isFrisbeeMoving = false;
		// for now always the left character starts with the frisbee, this can alternate in the levels later on
		this.isOwnCharacterThrowing = ownCharacter == CharacterType.LEFT;

		this.frisbeeXPosition = new SimpleDoubleProperty(
				levelModel.getInitialCharacterLeftXPosition() + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X - Constants.FRISBEE_RADIUS
		);
		this.frisbeeYPosition = new SimpleDoubleProperty(
				levelModel.getInitialCharacterYPosition() + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y - Constants.FRISBEE_RADIUS
		);

		// helper for the frisbee y position
		this.isHighestFrisbeePointReached = false;

		// TODO: move this to waiting view in the future
		this.characterModel.init();

		this.startCountdown();
		this.startAnimation();
	}

	// set initial positions and catching zones for players, left or right depends on the own character type
	private void initializeCharacterPositions() {
		this.ownCharacterXPosition = new SimpleDoubleProperty(this.ownCharacter == CharacterType.LEFT ?
				levelModel.getInitialCharacterLeftXPosition() : levelModel.getInitialCharacterRightXPosition());
		this.ownCharacterCatchingZoneLeftX = this.ownCharacter == CharacterType.LEFT ?
				Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_X : Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_X;
		this.ownCharacterCatchingZoneLeftY = this.ownCharacter == CharacterType.LEFT ?
				Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_Y :  Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_Y;
		this.ownCharacterCatchingZoneRightX = this.ownCharacter == CharacterType.LEFT ?
				Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X : Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_X;
		this.ownCharacterCatchingZoneRightY = this.ownCharacter == CharacterType.LEFT ?
				Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y : Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_Y;

		this.otherCharacterXPosition = new SimpleDoubleProperty(this.ownCharacter == CharacterType.LEFT ?
				levelModel.getInitialCharacterRightXPosition() : levelModel.getInitialCharacterLeftXPosition());
		this.otherCharacterCatchingZoneLeftX = this.ownCharacter == CharacterType.LEFT ?
				Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_X : Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_X;
		this.otherCharacterCatchingZoneLeftY = this.ownCharacter == CharacterType.LEFT ?
				Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_Y : Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_Y;
		this.otherCharacterCatchingZoneRightX = this.ownCharacter == CharacterType.LEFT ?
				Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_X : Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X;
		this.otherCharacterCatchingZoneRightY = this.ownCharacter == CharacterType.LEFT ?
				Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_Y : Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y;

		// y position is the same for both characters
		this.ownCharacterYPosition = new SimpleDoubleProperty(levelModel.getInitialCharacterYPosition());
		this.otherCharacterYPosition = new SimpleDoubleProperty(levelModel.getInitialCharacterYPosition());
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

	private void setTeamLivesHidden() {
		for (int i = 0; i < 5; i++) {
			this.teamLivesHidden.get(i).setValue(i >= this.remainingLives);
		}
	}

	private void startCountdown() {
		this.second = gameModel.getCurrentCountdown();
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), actionEvent -> {
			labelCountdown.setValue(Integer.toString(second));
			second--;
			
			if (this.showGameOverDialog.getValue()) {
				timeline.stop();
			}

			if (!this.showGameOverDialog.getValue() && (second < 0)) {
				timeline.stop();
				labelCountdown.setValue("Time over");
				showLevelSuccessDialog.setValue(this.teamModel.getLevel() < 3);
				showGameSuccessDialog.setValue(this.teamModel.getLevel() >= 3);
			}
			
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	// this function is called when a character action came in from the server
	private void executeOtherCharacterMovement(PropertyChangeEvent event) {
		MovementDirection movementDirection = (MovementDirection) event.getNewValue();
		if (movementDirection == MovementDirection.LEFT) {
			// update in javafx thread
			Platform.runLater(() -> otherCharacterXPosition.setValue(otherCharacterXPosition.getValue() - gameModel.getCharacterSpeed()));
		}

		if (movementDirection == MovementDirection.RIGHT) {
			// update in javafx thread
			Platform.runLater(() -> otherCharacterXPosition.setValue(otherCharacterXPosition.getValue() + gameModel.getCharacterSpeed()));
		}

		if (movementDirection == MovementDirection.UP) {
			// update in javafx thread
			Platform.runLater(() -> this.otherCharacterYPosition.setValue(this.otherCharacterYPosition.getValue() - this.levelModel.getJumpHeight()));
		}
	}

	// TODO: remove later and add function code to listener function of character throw property change event
	// use functionality from socketDummyFunctionTriggeredByClick then, which is now triggered by click (and because of that external)
	private void socketFrisbeeDummyFunctionTriggeredByClick() {
		this.resetThrowParameter();
		// TODO: get throw data from socket, for now just improvise
		this.frisbeeSpeedX = Math.random() * 8 + 2;
		this.frisbeeSpeedY = Math.random() * 2 + 0.2;
		this.counter = 0;
		// throw direction is always the opposite of the own character throw direction
		this.frisbeeThrowDirection = this.ownCharacter == CharacterType.LEFT ? -1 : 1;;
		// this triggers the animation timer frisbee moving
		// TODO: check later, if this is really working with sockets or if we need Platform.runLater()
		this.isFrisbeeMoving = true;
	}

	private void startAnimation() {
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// moving is not possible once the level is over
				if (showQuitConfirmDialog.getValue() || showGameOverDialog.getValue() || showLevelSuccessDialog.getValue()) return;

				int characterSpeed = gameModel.getCharacterSpeed();
				int gravity = gameModel.getGravity();

				// only when character is not throwing and has enough distance to other character, he is allowed to move
				if (isCharacterMovingLeft && !hasOwnCharacterTheFrisbee() && haveCharactersEnoughDistance()) {
					ownCharacterXPosition.setValue(ownCharacterXPosition.getValue() - characterSpeed);
					characterModel.moveOwnCharacter(MovementDirection.LEFT);
				}
				if (isCharacterMovingRight && !hasOwnCharacterTheFrisbee() && haveCharactersEnoughDistance()) {
					ownCharacterXPosition.setValue(ownCharacterXPosition.getValue() + characterSpeed);
					characterModel.moveOwnCharacter(MovementDirection.RIGHT);
				}

				// jumps are detected if character is not on its initial position
				if (ownCharacterYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
					ownCharacterYPosition.setValue(ownCharacterYPosition.getValue() + gravity);
				} else if(isOwnCharacterThrowing && !isFrisbeeMoving) {
					// set frisbee position to own character, when frisbee is not moving and when not jumping (anymore)
					setFrisbeePositionToOwnCharacter();
				}
				if (otherCharacterYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
					otherCharacterYPosition.setValue(otherCharacterYPosition.getValue() + gravity);
				} else if(!isOwnCharacterThrowing && !isFrisbeeMoving) {
					// set frisbee position to other character, when frisbee is not moving and when not jumping (anymore)
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

	public void throwFrisbee() {
		// if not the own character has the frisbee, do nothing
		if(!isOwnCharacterThrowing) {
			// TODO: remove this function call once we get the click and frisbee parameter from the socket
			this.socketFrisbeeDummyFunctionTriggeredByClick();
			return;
		}
		// reset curve helper for new frisbee throw
		this.resetThrowParameter();
		// TODO: send this to socket later
		// set frisbee speed random
		this.frisbeeSpeedX = Math.random() * 8 + 2;
		this.frisbeeSpeedY = Math.random() * 2 + 0.2;
		this.frisbeeThrowDirection = this.ownCharacter == CharacterType.LEFT ? 1 : -1;
		// throw frisbee
		this.isFrisbeeMoving = true;
	}

	private void resetThrowParameter() {
		// reset curve helper for new frisbee throw
		this.isHighestFrisbeePointReached = false;
		this.counter = 0;
	}

	private void frisbeeMove() {
		double frisbeeYDirection;
		double t;

		double upperLimit = Constants.SCENE_HEIGHT - Constants.CHARACTER_HEIGHT - this.levelModel.getGroundHeight() - this.levelModel.getJumpHeight() - 300;
		if (this.frisbeeYPosition.getValue() <= upperLimit) {
			this.isHighestFrisbeePointReached = true;
		}

		// positive is down, negative is up
		if (isHighestFrisbeePointReached) {
			frisbeeYDirection = 1;
			t = --counter;
		} else {
			frisbeeYDirection = -1;
			t = ++counter;
		}

		double frisbeeX = this.frisbeeXPosition.getValue() + (this.frisbeeSpeedX + 60 / t) * this.frisbeeThrowDirection;
		double frisbeeY = this.frisbeeYPosition.getValue() + this.frisbeeSpeedY + ((60 * t) / (t * t) - (t / 30)) * frisbeeYDirection;

		this.frisbeeXPosition.setValue(frisbeeX);
		this.frisbeeYPosition.setValue(frisbeeY);

		// check only other character collision if own character is throwing
		if (isOwnCharacterThrowing) {
			// frisbee is an image view, for the center we need to substract half of the size
			boolean collisionWithCharacterRightCatchingZoneLeft = Calculations.circlesIntersect(
					frisbeeX + Constants.FRISBEE_RADIUS,
					frisbeeY + Constants.FRISBEE_RADIUS,
					Constants.FRISBEE_RADIUS,
					// catching zone is relative to character position
					this.otherCharacterXPosition.getValue() + this.otherCharacterCatchingZoneLeftX,
					this.otherCharacterYPosition.getValue() + this.otherCharacterCatchingZoneLeftY,
					Constants.CHARACTER_CATCHING_RADIUS);
			boolean collisionWithCharacterRightCatchingZoneRight = Calculations.circlesIntersect(
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
			boolean collisionWithCharacterLeftCatchingZoneLeft = Calculations.circlesIntersect(
					frisbeeX + Constants.FRISBEE_RADIUS,
					frisbeeY + Constants.FRISBEE_RADIUS,
					Constants.FRISBEE_RADIUS,
					// catching zone is relative to character position
					this.ownCharacterXPosition.getValue() + this.ownCharacterCatchingZoneLeftX,
					this.ownCharacterYPosition.getValue() + this.ownCharacterCatchingZoneLeftY,
					Constants.CHARACTER_CATCHING_RADIUS);
			boolean collisionWithCharacterLeftCatchingZoneRight = Calculations.circlesIntersect(
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
		if(frisbeeX >= Constants.SCENE_WIDTH ||
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

	private void setFrisbeeTimeout() {
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			System.out.println("Timeout exception");
		}
	}

	private boolean hasOwnCharacterTheFrisbee() {
		return this.isOwnCharacterThrowing && !this.isFrisbeeMoving;
	}

	private void setFrisbeePositionToOwnCharacter() {
		this.frisbeeXPosition.setValue(this.ownCharacterXPosition.getValue() + ownCharacterCatchingZoneRightX - Constants.FRISBEE_RADIUS);
		this.frisbeeYPosition.setValue(this.ownCharacterYPosition.getValue() + ownCharacterCatchingZoneRightY - Constants.FRISBEE_RADIUS);
	}

	private void setFrisbeePositionToOtherCharacter() {
		this.frisbeeXPosition.setValue(this.otherCharacterXPosition.getValue() + otherCharacterCatchingZoneRightX - Constants.FRISBEE_RADIUS);
		this.frisbeeYPosition.setValue(this.otherCharacterYPosition.getValue() + otherCharacterCatchingZoneRightY - Constants.FRISBEE_RADIUS);
	}

	// returns false if character reaches left border
	private boolean isLeftBorderReachedByCharacter() {
		return this.ownCharacterXPosition.getValue() <= this.levelModel.getSceneBoundaryLeft();
	}

	// returns false if character reaches right border
	private boolean isRightBorderReachedByCharacter() {
		return this.ownCharacterXPosition.getValue() >= this.levelModel.getSceneBoundaryRight();
	}

	// return true if characters have at least a distance of 350
	private boolean haveCharactersEnoughDistance() {
		return Math.abs(this.otherCharacterXPosition.getValue() - this.ownCharacterXPosition.getValue()) > 350;
	}

	// true as long as the left border is not reached
	public void moveCharacterLeft() {
		this.isCharacterMovingLeft = !this.isLeftBorderReachedByCharacter();
	}

	// true as long as the right border is not reached
	public void moveCharacterRight() {
		this.isCharacterMovingRight = !isRightBorderReachedByCharacter();
	}

	// set the moving variable to false, so it is recognized by the animation timer
	public void stopCharacterMoveLeft() {
		this.isCharacterMovingLeft = false;
	}

	// set the moving variable to false, so it is recognized by the animation timer
	public void stopCharacterMoveRight() {
		this.isCharacterMovingRight = false;
	}

	// conditions when a character is allowed to jump, since the jump is not handled in the animation timer
	private boolean isCharacterAllowedToJump() {
		return this.ownCharacterYPosition.getValue() == levelModel.getInitialCharacterYPosition()
				&& !this.isCharacterMovingLeft
				&& !this.isCharacterMovingRight
				// no jump if the character still needs to throw the frisbee
				&& !this.isOwnCharacterThrowing
				&& !showQuitConfirmDialog.getValue()
				&& !showGameOverDialog.getValue()
				&& !showLevelSuccessDialog.getValue();
	}

	// jump only if character is allowed to - depends on open dialogs, if the character holds the frisbee, ...
	public void jumpCharacter() {
		if (isCharacterAllowedToJump()) {
			this.ownCharacterYPosition.setValue(this.ownCharacterYPosition.getValue() - this.levelModel.getJumpHeight());
			this.characterModel.moveOwnCharacter(MovementDirection.UP);
		}
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
		return this.labelCountdown;
	}
	
	public IntegerProperty getLabelScoreProperty() {
		this.labelScore.setValue(this.teamModel.getScore());
		return this.labelScore;
	}

	public void incrementScore() {
		this.labelScore.setValue(this.labelScore.getValue() + 1);
	}

	public void removeLife() {
		this.remainingLives--;
		this.setTeamLivesHidden();

		if (this.remainingLives == 0) {
			this.showGameOverDialog.setValue(true);
		}
	}

	public BooleanProperty getTeamLivesHiddenProperty(int lifeIndex) {
		return this.teamLivesHidden.get(lifeIndex);
	}

	public StringProperty getLabelLevelSuccessProperty() {
		this.labelLevelSuccess.setValue("Hey, you finished level " + this.teamModel.getLevel() + " … go ahead?");
		return this.labelLevelSuccess;
	}

	public StringProperty getButtonLevelContinueTextProperty() {
		int nextLevel = this.teamModel.getLevel() + 1;
		this.buttonLevelContinueText.setValue("Yes, take me to level " + nextLevel + ".");
		return this.buttonLevelContinueText;
	}

	public BooleanProperty getLevelSuccessDialogProperty() {
		return this.showLevelSuccessDialog;
	}

	public BooleanProperty getGameSuccessDialogProperty() {
		return this.showGameSuccessDialog;
	}
	
	public BooleanProperty getGameOverDialogProperty() {
		return this.showGameOverDialog;
	}
	
	public BooleanProperty getQuitConfirmDialogProperty() {
		return this.showQuitConfirmDialog;
	}
	
	public void showQuitConfirmDialog() {
		if (!this.showGameOverDialog.getValue()) {
			this.timeline.pause();
			this.gameModel.setCurrentCountdown(this.second);
			this.showQuitConfirmDialog.setValue(true);
		}
	}

	public void saveGame() {
		// save to backend
		this.teamModel.saveTeamData();
		// reset countdown
		this.gameModel.setCurrentCountdown(this.gameModel.getInitialCountdown());
	}

	public void saveAfterLevelSucceeded() {
		// increase level after successful level
		this.teamModel.setLevel(this.teamModel.getLevel() + 1);
		this.teamModel.setScore(this.labelScore.getValue());
		this.teamModel.setLives(this.remainingLives);
		this.teamModel.setActive(true);
		this.saveGame();
	}

	public void saveAfterGameSucceeded() {
		// skip updating the level, we are at the end
		this.teamModel.setScore(this.labelScore.getValue());
		this.teamModel.setLives(this.remainingLives);
		// no more games for this team after finishing the last level
		this.teamModel.setActive(false);
		this.saveGame();
	}

	public void saveAfterGameOver() {
		// skip updating the level, we died in the current level
		this.teamModel.setScore(this.labelScore.getValue());
		this.teamModel.setLives(this.remainingLives);
		// no more games for this team because of game over
		this.teamModel.setActive(false);
		this.saveGame();
	}
	
	public void continueGame() {
		this.teamModel.setLevel(this.teamModel.getLevel());
		this.teamModel.setScore(this.labelScore.getValue());
		this.teamModel.setLives(this.remainingLives);
		this.showQuitConfirmDialog.setValue(false);
	}

	public DoubleProperty getCharacterLeftXPositionProperty() {
		// return own character position, if own character is left, otherwise other character position
		return this.ownCharacter == CharacterType.LEFT ? this.ownCharacterXPosition : this.otherCharacterXPosition;
	}
	
	public DoubleProperty getCharacterLeftYPositionProperty() {
		// return own character position, if own character is left, otherwise other character position
		return this.ownCharacter == CharacterType.LEFT ? this.ownCharacterYPosition : this.otherCharacterYPosition;
	}
	
	public DoubleProperty getCharacterRightXPositionProperty() {
		// return own character position, if own character is right, otherwise other character position
		return this.ownCharacter == CharacterType.RIGHT ? this.ownCharacterXPosition : this.otherCharacterXPosition;
	}
	
	public DoubleProperty getCharacterRightYPositionProperty() {
		// return own character position, if own character is right, otherwise other character position
		return this.ownCharacter == CharacterType.RIGHT ? this.ownCharacterYPosition : this.otherCharacterYPosition;
	}

	public DoubleProperty getFrisbeeXPositionProperty() {
		return this.frisbeeXPosition;
	}

	public DoubleProperty getFrisbeeYPositionProperty() {
		return this.frisbeeYPosition;
	}
}