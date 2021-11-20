package puf.frisbee.frontend.model;

import puf.frisbee.frontend.core.Constants;

public class LevelModel implements Level {
	// TODO: get this from the server
	private int currentLevel = 1;

	private final double sceneBoundaryLeft = 80;
	private final double sceneBoundaryRight = 80;
	private final double groundHeight = 150;

	@Override
	public int getCurrentLevel() {
		return this.currentLevel;
	}

	@Override
	public void updateCurrentLevel() {
		currentLevel++;
	}

	@Override
	public double getInitialCharacterYPosition() {
		return Constants.SCENE_HEIGHT - this.groundHeight - Constants.CHARACTER_HEIGHT;
	}

	@Override
	public double getInitialCharacterLeftXPosition() {
		return this.sceneBoundaryLeft + 10;
	}

	@Override
	public double getInitialCharacterRightXPosition() {
		return Constants.SCENE_WIDTH - this.sceneBoundaryRight - Constants.CHARACTER_WIDTH - 10;
	}

	@Override
	public double getSceneBoundaryLeft() {
		return this.sceneBoundaryLeft;
	};

	@Override
	public double getSceneBoundaryRight() {
		return Constants.SCENE_WIDTH - this.sceneBoundaryRight - Constants.CHARACTER_WIDTH;
	};
}
