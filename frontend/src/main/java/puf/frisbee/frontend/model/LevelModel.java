package puf.frisbee.frontend.model;

import puf.frisbee.frontend.core.Constants;

public class LevelModel implements Level {
	// TODO: get this from the server
	private int currentLevel = 1;

	private double levelBoundaryLeft = 20;
	private double levelBoundaryRight = 20;
	private double levelGroundHeight = 100;

	@Override
	public int getCurrentLevel() {
		return this.currentLevel;
	}

	@Override
	public void updateCurrentLevel() {
		currentLevel++;
	}

	@Override
	public double getCharacterYPosition() {
		return Constants.SCENE_HEIGHT - this.levelGroundHeight - Constants.CHARACTER_HEIGHT;
	}

	@Override
	public double getCharacterLeftXPosition() {
		return this.levelBoundaryLeft;
	}

	@Override
	public double getCharacterRightXPosition() {
		return Constants.SCENE_WIDTH - this.levelBoundaryRight - Constants.CHARACTER_WIDTH;
	}
}
