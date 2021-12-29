package puf.frisbee.frontend.viewmodel;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.util.Duration;
import puf.frisbee.frontend.model.*;

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

		// set initial frisbee position (later on this should probably fit to any specific character)
		this.frisbeeXPosition.setValue(levelModel.getInitialFrisbeeXPosition());
		this.frisbeeYPosition.setValue(levelModel.getInitialFrisbeeYPosition());

		this.setTeamData();
		this.startCountdown();
		this.startCharacterAnimation();
	}

	private void setTeamData() {
		if (levelModel.getCurrentLevel() == 0) {this.levelModel.setCurrentLevel(1);}
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
				if (this.levelModel.getCurrentLevel() < 3) {
					showLevelSuccessDialog.setValue(true);
				} else {
					showGameSuccessDialog.setValue(true);
				}
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
				if (showQuitConfirmDialog.getValue() || showGameOverDialog.getValue() || showLevelSuccessDialog.getValue()) return;

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

	public void throwFrisbee() {
		Timeline timelineFrisbee = new Timeline();
        timelineFrisbee.setCycleCount(1);
        KeyValue xKV = new KeyValue(this.frisbeeXPosition, 1000);
        KeyValue yKV = new KeyValue(this.frisbeeYPosition, 100, new Interpolator() {
            @Override
            protected double curve(double t) {
				// The subtrahend 't/2.2' at the end defines the difference of vertical start and end position
				// and needs to be adjusted according to the individual character's and level floor's height.
				double currentYPosition = (-4 * (t - 0.5) * (t - 0.5) + 1) - t/2.2;
				double endYPosition = (-4 * (t - 0.5) * (t - 0.5) + 1) - 1/2.2;
				if (currentYPosition == endYPosition) {
					removeLife();
				}
                return (-4 * (t - 0.5) * (t - 0.5) + 1) - t/2.2;
            }
        });

		// To modify the frisbee's velocity simply reduce or increase the duration parameter manually.
        KeyFrame xKF = new KeyFrame(Duration.millis(2000), xKV);
        KeyFrame yKF = new KeyFrame(Duration.millis(2000), yKV);
        timelineFrisbee.getKeyFrames().addAll(xKF, yKF);
        timelineFrisbee.play();
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
				&& !showQuitConfirmDialog.getValue()
				&& !showGameOverDialog.getValue()
				&& !showLevelSuccessDialog.getValue();
	}

	private boolean isCharacterRightAllowedToJump() {
		return this.characterRightYPosition.getValue() == levelModel.getInitialCharacterYPosition()
				&& !this.isCharacterRightMovingLeft
				&& !this.isCharacterRightMovingRight
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
		this.labelLevelSuccess.setValue("Hey, you finished level " + this.levelModel.getCurrentLevel() + " … go ahead?");
		return this.labelLevelSuccess;
	}

	public StringProperty getButtonLevelContinueTextProperty() {
		int nextLevel = this.levelModel.getCurrentLevel() + 1;
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

	public void continueGameOver() {
		// TODO: save current lives, score and level of team to backend later on
		this.teamModel.setTeamLevel(1);
		this.teamModel.setTeamScore(0);
		this.teamModel.setTeamLives(5);
		this.levelModel.setCurrentLevel(1);
		this.gameModel.setCurrentCountdown(this.gameModel.getInitialCountdown());
	}

	public void continueGame() {
		this.levelModel.incrementCurrentLevel();
		// TODO: save current lives, score and level of team to backend later on
		this.teamModel.setTeamLevel(this.levelModel.getCurrentLevel());
		this.teamModel.setTeamScore(this.labelScore.getValue());
		this.teamModel.setTeamLives(this.remainingLives);
		this.gameModel.setCurrentCountdown(this.gameModel.getInitialCountdown());
	}

	public void pauseGame() {
		// TODO: save current lives, score and level of team to backend later on
		this.levelModel.setCurrentLevel(this.levelModel.getCurrentLevel() + 1);
		this.teamModel.setTeamLevel(this.levelModel.getCurrentLevel());
		this.teamModel.setTeamScore(this.labelScore.getValue());
		this.teamModel.setTeamLives(this.remainingLives);
		this.teamModel.setTeamLives(this.remainingLives);
		this.gameModel.setCurrentCountdown(this.gameModel.getInitialCountdown());
	}
	
	public void continueGameAfterQuit() {
		this.teamModel.setTeamLevel(this.levelModel.getCurrentLevel());
		this.teamModel.setTeamScore(this.labelScore.getValue());
		this.teamModel.setTeamLives(this.remainingLives);
		this.showQuitConfirmDialog.setValue(false);
	}

	public void quitGame() {
		// TODO: save current lives, score and level of team to backend later on
		this.teamModel.setTeamLevel(this.levelModel.getCurrentLevel());
		this.teamModel.setTeamScore(this.labelScore.getValue());
		this.teamModel.setTeamLives(this.remainingLives);
		this.gameModel.setCurrentCountdown(this.gameModel.getInitialCountdown());
	}

	public void quitGameAfterFinish() {
		// TODO: save current lives, score and level of team to backend later on
		this.levelModel.setCurrentLevel(this.levelModel.getCurrentLevel() + 1);
		this.teamModel.setTeamLevel(this.levelModel.getCurrentLevel());
		this.teamModel.setTeamScore(this.labelScore.getValue());
		this.teamModel.setTeamLives(this.remainingLives);
		this.gameModel.setCurrentCountdown(this.gameModel.getInitialCountdown());
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