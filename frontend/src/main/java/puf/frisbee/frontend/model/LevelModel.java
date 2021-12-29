package puf.frisbee.frontend.model;

import puf.frisbee.frontend.core.Constants;

public class LevelModel implements Level {	
	// TODO: get this from the server
	private final int maximumLevel = 3;
	private int currentLevel = 0;

	private final double sceneBoundaryLeft = 80;
	private final double sceneBoundaryRight = 80;
	private final double groundHeight = 150;
	private final double jumpHeight = 100;

	@Override
	public int getMaximumLevel() { return this.maximumLevel; }

	@Override
	public int getCurrentLevel() {
		return this.currentLevel;
	}
	
	@Override
	public void setCurrentLevel(int level) {
		this.currentLevel = level;
	}

	@Override
	public void incrementCurrentLevel() {
		this.currentLevel++;
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

	@Override
	public double getJumpHeight() {
		return this.jumpHeight;
	}

	@Override
	//TODO: We could refer to any specific character model (width and height) later on.
	public double getInitialFrisbeeXPosition() {
		return this.sceneBoundaryLeft + 10;
	}

	@Override
	//TODO: We could refer to any specific character model (width and height) later on.
	public double getInitialFrisbeeYPosition() {
		return Constants.SCENE_HEIGHT - this.groundHeight - Constants.CHARACTER_HEIGHT + 10;
	}
}
