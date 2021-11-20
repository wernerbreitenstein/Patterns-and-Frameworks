package puf.frisbee.frontend.viewmodel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.util.Duration;
import puf.frisbee.frontend.model.*;

public class GameViewModel {
	private Level levelModel;
	private Game gameModel;

	private StringProperty labelLevel;
	private StringProperty labelCountdown;
	private StringProperty labelLevelSuccess;
	private StringProperty buttonLevelContinueText;
	private BooleanProperty showLevelSuccessDialog;
	private TeamModel teamModel;
	private StringProperty labelTeamName;
	private DoubleProperty characterLeftXPosition;

	private int second;

	public GameViewModel(Game gameModel, Level levelModel) {
		this.gameModel = gameModel;
		this.levelModel = levelModel;

		this.labelLevel = new SimpleStringProperty();
		this.labelCountdown = new SimpleStringProperty();
		this.labelLevelSuccess = new SimpleStringProperty();
		this.buttonLevelContinueText = new SimpleStringProperty();
		this.showLevelSuccessDialog = new SimpleBooleanProperty(false);
		this.teamModel = new TeamModel("Bonnie & Clyde", 5, 47);
		this.labelTeamName = new SimpleStringProperty();
		this.characterLeftXPosition = new SimpleDoubleProperty();

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

	public int getLevel() {
		return this.levelModel.getCurrentLevel();
	}
	
	public StringProperty getTeamProperty() {
		this.labelTeamName.setValue(this.teamModel.getTeamName());
		return this.labelTeamName;
	}
	
	public StringProperty getLevelProperty() {
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

	public DoubleProperty getCharacterLeftXPositionProperty() {
		return this.characterLeftXPosition;
	}

	public BooleanProperty getLevelSuccessDialogOpenProperty() {
		return this.showLevelSuccessDialog;
	}
}