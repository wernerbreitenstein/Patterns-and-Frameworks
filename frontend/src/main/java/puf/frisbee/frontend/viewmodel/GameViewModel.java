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

	private BooleanProperty showLevelSuccessDialog;
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
		this.remainingLives = teamModel.getTeamLives();
		this.teamLivesHidden = new ArrayList<>(5);
		for (int i = 0; i < 5; i++) {
			BooleanProperty hidden = new SimpleBooleanProperty(i >= this.remainingLives);
			this.teamLivesHidden.add(hidden);
		}
		
		this.labelLevel = new SimpleStringProperty();
		this.labelCountdown = new SimpleStringProperty();
		this.labelLevelSuccess = new SimpleStringProperty();
		this.buttonLevelContinueText = new SimpleStringProperty();
		this.showLevelSuccessDialog = new SimpleBooleanProperty(false);
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

		// set initial frisbee position (later on this should probably fit to any specific character)
		this.frisbeeXPosition.setValue(levelModel.getInitialFrisbeeXPosition());
		this.frisbeeYPosition.setValue(levelModel.getInitialFrisbeeYPosition());

		this.startCountdown();
		this.startAnimation();
	}

	private void setTeamLivesHidden() {
		for (int i = 0; i < 5; i++) {
			this.teamLivesHidden.get(i).setValue(i >= this.remainingLives);
		}
	}

	private void startCountdown() {
		this.second = gameModel.getCountdown();
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
				showLevelSuccessDialog.setValue(true);
			}
			
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private void startAnimation() {
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long l) {
				// moving is not possible once the level is over
				if (showQuitConfirmDialog.getValue() || showGameOverDialog.getValue() || showLevelSuccessDialog.getValue()) return;

				int characterSpeed = gameModel.getCharacterSpeed();
				int gravity = gameModel.getGravity();

				// only the character that is not throwing is allowed to move
				if (isCharacterLeftMovingLeft && !isCharacterLeftThrowing) characterLeftXPosition.setValue(characterLeftXPosition.getValue() - characterSpeed);
				if (isCharacterLeftMovingRight && !isCharacterLeftThrowing) characterLeftXPosition.setValue(characterLeftXPosition.getValue() + characterSpeed);
				if (isCharacterRightMovingLeft && isCharacterLeftThrowing) characterRightXPosition.setValue(characterRightXPosition.getValue() - characterSpeed);
				if (isCharacterRightMovingRight && isCharacterLeftThrowing) characterRightXPosition.setValue(characterRightXPosition.getValue() + characterSpeed);

				// jumps are detected if character is not on its initial position
				if (characterLeftYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
					characterLeftYPosition.setValue(characterLeftYPosition.getValue() + gravity);
				}
				if (characterRightYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
					characterRightYPosition.setValue(characterRightYPosition.getValue() + gravity);
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
		this.isFrisbeeMoving = true;

//		Timeline timelineFrisbee = new Timeline();
//        timelineFrisbee.setCycleCount(1);
//        KeyValue xKV = new KeyValue(this.frisbeeXPosition, 1000);
//        KeyValue yKV = new KeyValue(this.frisbeeYPosition, 100, new Interpolator() {
//            @Override
//            protected double curve(double t) {
//				// The subtrahend 't/2.2' at the end defines the difference of vertical start and end position
//				// and needs to be adjusted according to the individual character's and level floor's height.
//				double currentYPosition = (-4 * (t - 0.5) * (t - 0.5) + 1) - t/2.2;
//				double endYPosition = ((-4 * (t - 0.5) * (t - 0.5) + 1) - 1/2.2) + Constants.FRISBEE_RADIUS;
//				if (currentYPosition == 0) {
//					removeLife();
//				}
//                return (-4 * (t - 0.5) * (t - 0.5) + 1) - t/2.2;
//            }
//        });
//
//		// To modify the frisbee's velocity simply reduce or increase the duration parameter manually.
//        KeyFrame xKF = new KeyFrame(Duration.millis(5000), xKV);
//        KeyFrame yKF = new KeyFrame(Duration.millis(5000), yKV);
//        timelineFrisbee.getKeyFrames().addAll(xKF, yKF);
//        timelineFrisbee.play();
	}

	private void frisbeeMove() {
		// TODO: randomize + parabel
		double frisbeeSpeed = isCharacterLeftThrowing ? 3 : -3;
		this.frisbeeXPosition.setValue(this.frisbeeXPosition.getValue() + frisbeeSpeed);

		// check only right character collision if left character is throwing
		if (isCharacterLeftThrowing) {
			// catching zone is relative to character position
			double characterRightCatchingZoneLeftX = characterRightXPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_X;
			double characterRightCatchingZoneLeftY = characterRightYPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_LEFT_Y;
			double characterRightCatchingZoneRightX = characterRightXPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_X;
			double characterRightCatchingZoneRightY = characterRightYPosition.getValue() + Constants.CHARACTER_RIGHT_CATCHING_ZONE_RIGHT_Y;

			boolean collisionWithCharacterRightCatchingZoneLeft = Calculations.circlesIntersect(
					frisbeeXPosition.getValue(), frisbeeYPosition.getValue(), Constants.FRISBEE_RADIUS,
					characterRightCatchingZoneLeftX, characterRightCatchingZoneLeftY, Constants.CHARACTER_RIGHT_CATCHING_RADIUS);
			boolean collisionWithCharacterRightCatchingZoneRight = Calculations.circlesIntersect(
					frisbeeXPosition.getValue(), frisbeeYPosition.getValue(), Constants.FRISBEE_RADIUS,
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
					characterLeftCatchingZoneLeftX, characterLeftCatchingZoneLeftY, Constants.CHARACTER_LEFT_CATCHING_RADIUS,
					frisbeeXPosition.getValue(), frisbeeYPosition.getValue(), Constants.FRISBEE_RADIUS);
			boolean collisionWithCharacterLeftCatchingZoneRight = Calculations.circlesIntersect(
					characterLeftCatchingZoneRightX, characterLeftCatchingZoneRightY, Constants.CHARACTER_LEFT_CATCHING_RADIUS,
					frisbeeXPosition.getValue(), frisbeeYPosition.getValue(), Constants.FRISBEE_RADIUS);

			if (collisionWithCharacterLeftCatchingZoneLeft || collisionWithCharacterLeftCatchingZoneRight) {
				incrementScore();
				this.isFrisbeeMoving = false;
				this.isCharacterLeftThrowing = true;
				return;
			}
		}

		// TODO: check all boundaries
		if(frisbeeYPosition.getValue() + Constants.FRISBEE_RADIUS == 0) {
			this.isFrisbeeMoving = false;
			return;
		}
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
				&& !this.isCharacterLeftThrowing
				&& !showQuitConfirmDialog.getValue()
				&& !showGameOverDialog.getValue()
				&& !showLevelSuccessDialog.getValue();
	}

	private boolean isCharacterRightAllowedToJump() {
		return this.characterRightYPosition.getValue() == levelModel.getInitialCharacterYPosition()
				&& !this.isCharacterRightMovingLeft
				&& !this.isCharacterRightMovingRight
				&& this.isCharacterLeftThrowing
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
		this.labelTeamName.setValue(this.teamModel.getTeamName());
		return this.labelTeamName;
	}
	
	public StringProperty getLabelLevelProperty() {
		this.labelLevel.setValue(String.valueOf(this.levelModel.getCurrentLevel()));
		return this.labelLevel;
	}
	
	public StringProperty getLabelCountdownProperty() {
		return this.labelCountdown;
	}
	
	public IntegerProperty getLabelScoreProperty() {
		this.labelScore.setValue(this.teamModel.getTeamScore());
		return this.labelScore;
	}

	public void incrementScore() {
		this.labelScore.setValue(this.labelScore.getValue() + 1);
	}

	public void removeLife() {
		this.remainingLives --;
		this.setTeamLivesHidden();

		if (this.remainingLives == 0) {
			this.showGameOverDialog.setValue(true);
		}
	}

	public BooleanProperty getTeamLivesHiddenProperty(int lifeIndex) {
		return this.teamLivesHidden.get(lifeIndex);
	}

	public StringProperty getLabelLevelSuccessProperty() {
		this.labelLevelSuccess.setValue("Level " + this.levelModel.getCurrentLevel() + " geschafft!");
		return this.labelLevelSuccess;
	}

	public StringProperty getButtonLevelContinueTextProperty() {
		int nextLevel = this.levelModel.getCurrentLevel() + 1;
		this.buttonLevelContinueText.setValue("Weiter zu Level " + nextLevel);
		return this.buttonLevelContinueText;
	}

	public BooleanProperty getLevelSuccessDialogProperty() {
		return this.showLevelSuccessDialog;
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
			this.showQuitConfirmDialog.setValue(true);
		}
	}
	
	public void hideQuitConfirmDialog() {
		this.timeline.playFrom(this.timeline.getCurrentTime());
		this.showQuitConfirmDialog.setValue(false);
	}

	public void continueGameOver() {
		// TODO: save current lives, score and level of team to backend later on
		this.teamModel.setTeamLevel(1);
		this.teamModel.setTeamScore(0);
		this.teamModel.setTeamLives(5);
		this.levelModel.setCurrentLevel(1);
	}

	public void continueGame() {
		this.levelModel.incrementCurrentLevel();
		// TODO: save current lives, score and level of team to backend later on
		this.teamModel.setTeamLevel(this.levelModel.getCurrentLevel());
		this.teamModel.setTeamScore(this.labelScore.getValue());
		this.teamModel.setTeamLives(this.remainingLives);
	}
	
	public void continueGameAfterQuit() {		
		this.hideQuitConfirmDialog();
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
		// frisbee is an image view, so we need to substract the radius to get the left upper corner for view position
		//this.frisbeeXPosition.setValue(this.frisbeeXPosition.getValue() - Constants.FRISBEE_RADIUS);
		return this.frisbeeXPosition;
	}

	public DoubleProperty getFrisbeeYPositionProperty() {
		// frisbee is an image view, so we need to substract the radius to get the left upper corner for view position
		//this.frisbeeYPosition.setValue(this.frisbeeYPosition.getValue() - Constants.FRISBEE_RADIUS);
		return this.frisbeeYPosition;
	}
}