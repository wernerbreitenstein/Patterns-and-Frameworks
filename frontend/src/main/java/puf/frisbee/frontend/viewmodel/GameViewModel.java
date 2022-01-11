package puf.frisbee.frontend.viewmodel;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.util.Duration;
import puf.frisbee.frontend.core.Constants;
import puf.frisbee.frontend.model.*;
import puf.frisbee.frontend.utils.Calculations;

import java.util.ArrayList;

public class GameViewModel {
	private Game gameModel;
	private Level levelModel;
	private Team teamModel;
	private Timeline timeline;
	private int second;
	private int remainingLives;
	private double counter;

	private BooleanProperty showLevel01BackgroundImage;
	private BooleanProperty showLevel02BackgroundImage;
	private BooleanProperty showLevel03BackgroundImage;

	private DoubleProperty characterLeftXPosition;
	private DoubleProperty characterLeftYPosition;
	private DoubleProperty characterRightXPosition;
	private DoubleProperty characterRightYPosition;

	private DoubleProperty frisbeeXPosition;
	private DoubleProperty frisbeeYPosition;

	// TODO: only flags for one character will be needed once two characters can not play on one computer anymore
	private boolean isCharacterLeftMovingLeft;
	private boolean isCharacterLeftMovingRight;
	private boolean isCharacterRightMovingLeft;
	private boolean isCharacterRightMovingRight;

	private boolean isFrisbeeMoving;
	private boolean isCharacterLeftThrowing;
	private boolean isHighestFrisbeePointReached;
	private double frisbeeSpeedX;
	private double frisbeeSpeedY;

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

	public GameViewModel(Game gameModel, Level levelModel, Team teamModel) {
		this.gameModel = gameModel;
		this.levelModel = levelModel;
		this.teamModel = teamModel;
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

		this.characterLeftXPosition = new SimpleDoubleProperty();
		this.characterLeftYPosition = new SimpleDoubleProperty();
		this.characterRightXPosition = new SimpleDoubleProperty();
		this.characterRightYPosition = new SimpleDoubleProperty();

		this.frisbeeXPosition = new SimpleDoubleProperty();
		this.frisbeeYPosition = new SimpleDoubleProperty();

		// set initial character positions
		this.characterLeftYPosition.setValue(levelModel.getInitialCharacterYPosition());
		this.characterRightYPosition.setValue(levelModel.getInitialCharacterYPosition());
		this.characterLeftXPosition.setValue(levelModel.getInitialCharacterLeftXPosition());
		this.characterRightXPosition.setValue(levelModel.getInitialCharacterRightXPosition());
		this.isCharacterLeftMovingLeft = false;
		this.isCharacterLeftMovingRight = false;
		this.isCharacterRightMovingLeft = false;
		this.isCharacterRightMovingRight = false;

		this.isFrisbeeMoving = false;
		// for now always the left character starts with the frisbee, this can alternate in the levels later on
		this.isCharacterLeftThrowing = true;
		// helper for the frisbee y position
		this.isHighestFrisbeePointReached = false;

		// set initial frisbee position
		this.frisbeeXPosition.setValue(levelModel.getInitialCharacterLeftXPosition() + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X - Constants.FRISBEE_RADIUS);
		this.frisbeeYPosition.setValue(levelModel.getInitialCharacterYPosition() + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y - Constants.FRISBEE_RADIUS);

		this.startCountdown();
		this.startAnimation();
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

	private void startAnimation() {
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// moving is not possible once the level is over
				if (showQuitConfirmDialog.getValue() || showGameOverDialog.getValue() || showLevelSuccessDialog.getValue()) return;

				int characterSpeed = gameModel.getCharacterSpeed();
				int gravity = gameModel.getGravity();

				// only the character that is not throwing is allowed to move
				if (isCharacterLeftMovingLeft && !hasCharacterLeftTheFrisbee() && haveCharactersEnoughDistance())
					characterLeftXPosition.setValue(characterLeftXPosition.getValue() - characterSpeed);
				if (isCharacterLeftMovingRight && !hasCharacterLeftTheFrisbee() && haveCharactersEnoughDistance())
					characterLeftXPosition.setValue(characterLeftXPosition.getValue() + characterSpeed);
				if (isCharacterRightMovingLeft && !hasCharacterRightTheFrisbee() && haveCharactersEnoughDistance())
					characterRightXPosition.setValue(characterRightXPosition.getValue() - characterSpeed);
				if (isCharacterRightMovingRight && !hasCharacterRightTheFrisbee() && haveCharactersEnoughDistance())
					characterRightXPosition.setValue(characterRightXPosition.getValue() + characterSpeed);

				// jumps are detected if character is not on its initial position
				if (characterLeftYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
					characterLeftYPosition.setValue(characterLeftYPosition.getValue() + gravity);
				} else if(isCharacterLeftThrowing && !isFrisbeeMoving) {
					// set frisbee position to character who is next when not jumping (anymore)
					setFrisbeePositionToLeftCharacter();
				}
				if (characterRightYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
					characterRightYPosition.setValue(characterRightYPosition.getValue() + gravity);
				} else if(!isCharacterLeftThrowing && !isFrisbeeMoving) {
					// set frisbee position to character who is next when not jumping (anymore)
					setFrisbeePositionToRightCharacter();
				}

				// frisbee
				if (isFrisbeeMoving) {
					frisbeeMove();
				}
			}
		};
		timer.start();
	}

	public void throwFrisbee() {
		// reset curve helper for new frisbee throw
		this.isHighestFrisbeePointReached = false;
		// set frisbee speed random
		this.frisbeeSpeedX = Math.random() * 8 + 2;
		this.frisbeeSpeedY = Math.random() * 2 + 0.2;
		// throw frisbee
		this.isFrisbeeMoving = true;
		this.counter = 0;
	}

	private void frisbeeMove() {
		double frisbeeXDirection = isCharacterLeftThrowing ? 1 : -1;
		double frisbeeYDirection;
		double t;

		double upperLimit = Constants.SCENE_HEIGHT - Constants.CHARACTER_HEIGHT - this.levelModel.getGroundHeight() - this.levelModel.getJumpHeight() - 300;
		if (this.frisbeeYPosition.getValue() <=  upperLimit) {
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

		double frisbeeX = this.frisbeeXPosition.getValue() + (this.frisbeeSpeedX + 60 / t) * frisbeeXDirection;
		double frisbeeY = this.frisbeeYPosition.getValue() + this.frisbeeSpeedY + ((60 * t) / (t * t) - (t / 30)) * frisbeeYDirection;

		this.frisbeeXPosition.setValue(frisbeeX);
		this.frisbeeYPosition.setValue(frisbeeY);

		// check only right character collision if left character is throwing
		if (isCharacterLeftThrowing) {
			// catching zone is relative to character position
			double characterRightCatchingZoneLeftX = characterRightXPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_X;
			double characterRightCatchingZoneLeftY = characterRightYPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_Y;
			double characterRightCatchingZoneRightX = characterRightXPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_X;
			double characterRightCatchingZoneRightY = characterRightYPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_Y;

			// frisbee is an image view, for the center we need to substract half of the size
			boolean collisionWithCharacterRightCatchingZoneLeft = Calculations.circlesIntersect(
					frisbeeX + Constants.FRISBEE_RADIUS, frisbeeY + Constants.FRISBEE_RADIUS, Constants.FRISBEE_RADIUS,
					characterRightCatchingZoneLeftX, characterRightCatchingZoneLeftY, Constants.CHARACTER_RIGHT_CATCHING_RADIUS);
			boolean collisionWithCharacterRightCatchingZoneRight = Calculations.circlesIntersect(
					frisbeeX + Constants.FRISBEE_RADIUS, frisbeeY + Constants.FRISBEE_RADIUS, Constants.FRISBEE_RADIUS,
					characterRightCatchingZoneRightX, characterRightCatchingZoneRightY, Constants.CHARACTER_RIGHT_CATCHING_RADIUS);

			if (collisionWithCharacterRightCatchingZoneLeft || collisionWithCharacterRightCatchingZoneRight) {
				incrementScore();
				this.isFrisbeeMoving = false;
				this.isCharacterLeftThrowing = false;
				return;
			}
		} else {
			// check only left character collision if right character is throwing
			// catching zone is relative to character position
			double characterLeftCatchingZoneLeftX = characterLeftXPosition.getValue() + Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_X;
			double characterLeftCatchingZoneLeftY = characterLeftYPosition.getValue() + Constants.CHARACTER_LEFT_CATCHING_ZONE_LEFT_Y;
			double characterLeftCatchingZoneRightX = characterLeftXPosition.getValue() + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X;
			double characterLeftCatchingZoneRightY = characterLeftYPosition.getValue() + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y;

			boolean collisionWithCharacterLeftCatchingZoneLeft = Calculations.circlesIntersect(
					frisbeeX + Constants.FRISBEE_RADIUS, frisbeeY + Constants.FRISBEE_RADIUS, Constants.FRISBEE_RADIUS,
					characterLeftCatchingZoneLeftX, characterLeftCatchingZoneLeftY, Constants.CHARACTER_LEFT_CATCHING_RADIUS);
			boolean collisionWithCharacterLeftCatchingZoneRight = Calculations.circlesIntersect(
					frisbeeX + Constants.FRISBEE_RADIUS, frisbeeY + Constants.FRISBEE_RADIUS, Constants.FRISBEE_RADIUS,
					characterLeftCatchingZoneRightX, characterLeftCatchingZoneRightY, Constants.CHARACTER_LEFT_CATCHING_RADIUS);

			if (collisionWithCharacterLeftCatchingZoneLeft || collisionWithCharacterLeftCatchingZoneRight) {
				incrementScore();
				this.isFrisbeeMoving = false;
				this.isCharacterLeftThrowing = true;
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
			this.isCharacterLeftThrowing = !this.isCharacterLeftThrowing;
		}
	}

	private void setFrisbeeTimeout() {
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			System.out.println("Timeout exception");
		}
	}

	private boolean hasCharacterLeftTheFrisbee() {
		return this.isCharacterLeftThrowing && !this.isFrisbeeMoving;
	}

	private boolean hasCharacterRightTheFrisbee() {
		return !this.isCharacterLeftThrowing && !this.isFrisbeeMoving;
	}

	private void setFrisbeePositionToLeftCharacter() {
		this.frisbeeXPosition.setValue(this.characterLeftXPosition.getValue() + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_X - Constants.FRISBEE_RADIUS);
		this.frisbeeYPosition.setValue(this.characterLeftYPosition.getValue() + Constants.CHARACTER_LEFT_CATCHING_ZONE_RIGHT_Y - Constants.FRISBEE_RADIUS);
	}

	private void setFrisbeePositionToRightCharacter() {
		this.frisbeeXPosition.setValue(this.characterRightXPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_X - Constants.FRISBEE_RADIUS);
		this.frisbeeYPosition.setValue(this.characterRightYPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_Y - Constants.FRISBEE_RADIUS);
	}

	// TODO: checks only needed for one character once two characters can not play on one computer anymore
	private boolean isLeftBorderReachedByCharacterLeft() {
		return this.characterLeftXPosition.getValue() <= this.levelModel.getSceneBoundaryLeft();
	}
	private boolean isLeftBorderReachedByCharacterRight() {
		return this.characterRightXPosition.getValue() <= this.levelModel.getSceneBoundaryLeft();
	}
	private boolean isRightBorderReachedByCharacterLeft() {
		return this.characterLeftXPosition.getValue() >= this.levelModel.getSceneBoundaryRight();
	}
	private boolean isRightBorderReachedByCharacterRight() {
		return this.characterRightXPosition.getValue() >= this.levelModel.getSceneBoundaryRight();
	}
	// return true if characters have at least a distance of 350
	private boolean haveCharactersEnoughDistance() {
		return Math.abs(this.characterRightXPosition.getValue() - this.characterLeftXPosition.getValue()) > 350;
	}

	// TODO: character parameter will not be needed anymore once two characters can not play on one computer anymore
	public void moveCharacterLeft(String character) {
		if (character.equals("left")) {
			this.isCharacterLeftMovingLeft = !this.isLeftBorderReachedByCharacterLeft();
		}
		if (character.equals("right")) {
			this.isCharacterRightMovingLeft = !this.isLeftBorderReachedByCharacterRight();
		}
	}

	// TODO: character parameter will not be needed anymore once two characters can not play on one computer anymore
	public void moveCharacterRight(String character) {
		if (character.equals("left")) {
			this.isCharacterLeftMovingRight = !isRightBorderReachedByCharacterLeft();
		}
		if (character.equals("right")) {
			this.isCharacterRightMovingRight = !isRightBorderReachedByCharacterRight();
		}
	}

	// TODO: character parameter will not be needed anymore once two characters can not play on one computer anymore
	public void stopCharacterMoveLeft(String character) {
		if (character.equals("left")) {
			this.isCharacterLeftMovingLeft = false;
		} else {
			this.isCharacterRightMovingLeft = false;
		}
	}

	// TODO: character parameter will not be needed anymore once two characters can not play on one computer anymore
	public void stopCharacterMoveRight(String character) {
		if (character.equals("left")) {
			this.isCharacterLeftMovingRight = false;
		} else {
			this.isCharacterRightMovingRight = false;
		}
	}

	private boolean isCharacterLeftAllowedToJump() {
		return this.characterLeftYPosition.getValue() == levelModel.getInitialCharacterYPosition()
				&& !this.isCharacterLeftMovingLeft
				&& !this.isCharacterLeftMovingRight
				// no jump if the character still needs to throw the frisbee
				&& !this.isCharacterLeftThrowing
				&& !showQuitConfirmDialog.getValue()
				&& !showGameOverDialog.getValue()
				&& !showLevelSuccessDialog.getValue();
	}

	private boolean isCharacterRightAllowedToJump() {
		return this.characterRightYPosition.getValue() == levelModel.getInitialCharacterYPosition()
				&& !this.isCharacterRightMovingLeft
				&& !this.isCharacterRightMovingRight
				// no jump if the character still needs to throw the frisbee
				&& !this.hasCharacterRightTheFrisbee()
				&& !showQuitConfirmDialog.getValue()
				&& !showGameOverDialog.getValue()
				&& !showLevelSuccessDialog.getValue();
	}

	// TODO: character parameter will not be needed anymore once two characters can not play on one computer anymore
	public void jumpCharacter(String character) {
		if (character.equals("left") && isCharacterLeftAllowedToJump()) {
			this.characterLeftYPosition.setValue(this.characterLeftYPosition.getValue() - this.levelModel.getJumpHeight());
		}
		if (character.equals("right") && isCharacterRightAllowedToJump()) {
			this.characterRightYPosition.setValue(this.characterRightYPosition.getValue() - this.levelModel.getJumpHeight());
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
		return this.characterLeftXPosition;
	}
	
	public DoubleProperty getCharacterLeftYPositionProperty() {
		return this.characterLeftYPosition;
	}
	
	public DoubleProperty getCharacterRightXPositionProperty() {
		return this.characterRightXPosition;
	}
	
	public DoubleProperty getCharacterRightYPositionProperty() {
		return this.characterRightYPosition;
	}

	public DoubleProperty getFrisbeeXPositionProperty() {
		return this.frisbeeXPosition;
	}

	public DoubleProperty getFrisbeeYPositionProperty() {
		return this.frisbeeYPosition;
	}
}