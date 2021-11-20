package puf.frisbee.frontend.viewmodel;

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

	private BooleanProperty showLevelSuccessDialog;
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
		this.labelTeamName = new SimpleStringProperty();
		this.labelScore = new SimpleIntegerProperty();

		this.teamModel = new TeamModel("Bonnie & Clyde", 5, 47);
		// start with latest saved score for team
		this.labelScore.setValue(teamModel.getTeamScore());

		// set initial character positions
		this.characterLeftYPosition.setValue(levelModel.getCharacterYPosition());
		this.characterRightYPosition.setValue(levelModel.getCharacterYPosition());
		this.characterLeftXPosition.setValue(levelModel.getCharacterLeftXPosition());
		this.characterRightXPosition.setValue(levelModel.getCharacterRightXPosition());

		this.startCountdown();
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

	public void continueLevel() {
		this.levelModel.updateCurrentLevel();
		this.showLevelSuccessDialog.setValue(false);
	}

	public void setCharacterLeftXPosition(double position) {
		this.characterLeftXPosition.setValue(position);
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

	public BooleanProperty getLevelSuccessDialogOpenProperty() {
		return this.showLevelSuccessDialog;
	}
}