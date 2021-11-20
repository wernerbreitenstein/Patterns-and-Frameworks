package puf.frisbee.frontend.model;

public class LevelModel implements Level {
	// TODO: get this from the server
	private int currentLevel = 1;

	@Override
	public int getCurrentLevel() {
		return this.currentLevel;
	}

	@Override
	public void updateCurrentLevel() {
		currentLevel++;
	}
}
