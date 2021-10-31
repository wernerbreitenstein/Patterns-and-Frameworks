package puf.frisbee.frontend.viewmodel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import puf.frisbee.frontend.model.LevelModel;

public class LevelViewModel {
	private LevelModel levelModel;
	private StringProperty countdown;
	private int second;


	public LevelViewModel(LevelModel levelModel) {
		this.levelModel = levelModel;
		levelModel.setCountdown(30);
		this.countdown = new SimpleStringProperty();
		this.startCountdown();
	}

	private void startCountdown() {
		this.second = levelModel.getCountdown();
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				countdown.setValue(Integer.toString(second));
				second--;

				if (second < 0) {
					timeline.stop();
					countdown.setValue("Time over");
					// TODO: set flag for modal if countdown is 0
				}
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public StringProperty countdownProperty() {
		return this.countdown;
	}
}