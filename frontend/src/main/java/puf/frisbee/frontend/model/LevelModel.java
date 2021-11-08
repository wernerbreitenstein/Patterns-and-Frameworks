package puf.frisbee.frontend.model;

public class LevelModel implements Level {
	// TODO: get this from the server
	private int countdownInSeconds = 10;
	private int currentLevel = 1;

	public int getCountdown() {
		return this.countdownInSeconds;
	}

	public int getCurrentLevel() {
		return this.currentLevel;
	}

	public void updateCurrentLevel() {
		currentLevel++;
	}
}
