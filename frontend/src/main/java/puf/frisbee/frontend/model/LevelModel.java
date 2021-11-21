package puf.frisbee.frontend.model;

import puf.frisbee.frontend.core.Constants;

public class LevelModel implements Level {
	// TODO: get this from the server
	private int currentLevel = 1;

	private double levelBoundaryLeft = 80;
	private double levelBoundaryRight = 80;
	private double levelGroundHeight = 150;

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
		return Constants.SCENE_HEIGHT - this.levelGroundHeight - Constants.CHARACTER_HEIGHT;
	}

	@Override
	public double getInitialCharacterLeftXPosition() {
		return this.levelBoundaryLeft;
	}

	@Override
	public double getInitialCharacterRightXPosition() {
		return Constants.SCENE_WIDTH - this.levelBoundaryRight - Constants.CHARACTER_WIDTH;
	}
}
