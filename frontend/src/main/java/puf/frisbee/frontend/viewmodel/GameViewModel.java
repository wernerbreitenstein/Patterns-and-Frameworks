package puf.frisbee.frontend.viewmodel;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.util.Duration;
import puf.frisbee.frontend.model.*;

public class GameViewModel {
	private Level levelModel;
	private Game gameModel;

	private DoubleProperty characterLeftXPosition;
	private DoubleProperty characterLeftYPosition;
	private DoubleProperty characterRightXPosition;
	private DoubleProperty characterRightYPosition;
	// TODO: only flags for one character will be needed once two characters can not play on one computer anymore
	private boolean isCharacterLeftMovingLeft;
	private boolean isCharacterLeftMovingRight;
	private boolean isCharacterRightMovingLeft;
	private boolean isCharacterRightMovingRight;

	private BooleanProperty showLevelSuccessDialog;
	private BooleanProperty showGameOverDialog;
	private StringProperty buttonLevelContinueText;
	private StringProperty labelCountdown;
	private StringProperty labelLevel;
	private StringProperty labelLevelSuccess;
	private StringProperty labelTeamName;
	private IntegerProperty labelScore;

	private TeamModel teamModel;
	private int second;

	public GameViewModel(Game gameModel, Level levelModel) {
		this.gameModel = gameModel;
		this.levelModel = levelModel;

		this.characterLeftXPosition = new SimpleDoubleProperty();
		this.characterLeftYPosition = new SimpleDoubleProperty();
		this.characterRightXPosition = new SimpleDoubleProperty();
		this.characterRightYPosition = new SimpleDoubleProperty();

		this.labelLevel = new SimpleStringProperty();
		this.labelCountdown = new SimpleStringProperty();
		this.labelLevelSuccess = new SimpleStringProperty();
		this.buttonLevelContinueText = new SimpleStringProperty();
		this.showLevelSuccessDialog = new SimpleBooleanProperty(false);
		this.showGameOverDialog = new SimpleBooleanProperty(true);
		this.labelTeamName = new SimpleStringProperty();
		this.labelScore = new SimpleIntegerProperty();

		this.teamModel = new TeamModel("Bonnie & Clyde", 5, 47);
		// start with latest saved score for team
		this.labelScore.setValue(teamModel.getTeamScore());

		// set initial character positions
		this.characterLeftYPosition.setValue(levelModel.getInitialCharacterYPosition());
		this.characterRightYPosition.setValue(levelModel.getInitialCharacterYPosition());
		this.characterLeftXPosition.setValue(levelModel.getInitialCharacterLeftXPosition());
		this.characterRightXPosition.setValue(levelModel.getInitialCharacterRightXPosition());
		this.isCharacterLeftMovingLeft = false;
		this.isCharacterLeftMovingRight = false;
		this.isCharacterRightMovingLeft = false;
		this.isCharacterRightMovingRight = false;

		this.startCountdown();
		this.startCharacterAnimation();
	}

	private void startCountdown() {
		this.second = gameModel.getCountdown();
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), actionEvent -> {
			labelCountdown.setValue(Integer.toString(second));
			second--;

			if (second < 0) {
				timeline.stop();
				labelCountdown.setValue("Time over");
				showLevelSuccessDialog.setValue(true);
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private void startCharacterAnimation() {
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long l) {
				// moving is not possible once the level is over
				if (showLevelSuccessDialog.getValue()) return;

				int characterSpeed = gameModel.getCharacterSpeed();
				int gravity = gameModel.getGravity();

				if (isCharacterLeftMovingLeft) characterLeftXPosition.setValue(characterLeftXPosition.getValue() - characterSpeed);
				if (isCharacterLeftMovingRight) characterLeftXPosition.setValue(characterLeftXPosition.getValue() + characterSpeed);
				if (isCharacterRightMovingLeft) characterRightXPosition.setValue(characterRightXPosition.getValue() - characterSpeed);
				if (isCharacterRightMovingRight) characterRightXPosition.setValue(characterRightXPosition.getValue() + characterSpeed);

				// jumps are detected if character is not on its initial position
				if (characterLeftYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
					characterLeftYPosition.setValue(characterLeftYPosition.getValue() + gravity);
				}
				if (characterRightYPosition.getValue() < levelModel.getInitialCharacterYPosition()) {
					characterRightYPosition.setValue(characterRightYPosition.getValue() + gravity);
				}
			}
		};

		timer.start();
	}

	public void continueLevel() {
		this.levelModel.updateCurrentLevel();
		this.showLevelSuccessDialog.setValue(false);
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
				&& !this.isCharacterLeftMovingRight;
	}

	private boolean isCharacterRightAllowedToJump() {
		return this.characterRightYPosition.getValue() == levelModel.getInitialCharacterYPosition()
				&& !this.isCharacterRightMovingLeft
				&& !this.isCharacterRightMovingRight;
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


	public void addScore(int score) {
		this.labelScore.setValue(this.labelScore.getValue() + score);
	}

	public int getLevel() {
		return this.levelModel.getCurrentLevel();
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

	public StringProperty getLabelLevelSuccessProperty() {
		this.labelLevelSuccess.setValue("Level " + this.levelModel.getCurrentLevel() + " geschafft!");
		return this.labelLevelSuccess;
	}

	public StringProperty getButtonLevelContinueTextProperty() {
		int nextLevel = this.levelModel.getCurrentLevel() + 1;
		this.buttonLevelContinueText.setValue("Weiter zu Level " + nextLevel);
		return this.buttonLevelContinueText;
	}

	public IntegerProperty getLabelScoreProperty() {
		return this.labelScore;
	}

	public BooleanProperty getLevelSuccessDialogOpenProperty() {
		return this.showLevelSuccessDialog;
	}
	
	public BooleanProperty getGameOverDialogOpenProperty() {
		return this.showGameOverDialog;
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
}