package puf.frisbee.frontend.viewmodel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import puf.frisbee.frontend.model.LevelModel;

public class LevelViewModel {
	private LevelModel levelModel;
	private StringProperty labelCountdown;
	private StringProperty labelLevelSuccess;
	private StringProperty buttonLevelContinueText;
	private int second;
	private BooleanProperty showLevelSuccessDialog;


	public LevelViewModel(LevelModel levelModel) {
		this.levelModel = levelModel;
		this.labelCountdown = new SimpleStringProperty();
		this.labelLevelSuccess = new SimpleStringProperty();
		this.buttonLevelContinueText = new SimpleStringProperty();
		this.showLevelSuccessDialog = new SimpleBooleanProperty(false);
		this.startCountdown();
	}

	private void startCountdown() {
		this.second = levelModel.getCountdown();
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

	public int getLevel() {
		return this.levelModel.getCurrentLevel();
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

	public BooleanProperty getLevelSuccessDialogOpenProperty() {
		return this.showLevelSuccessDialog;
	}
}