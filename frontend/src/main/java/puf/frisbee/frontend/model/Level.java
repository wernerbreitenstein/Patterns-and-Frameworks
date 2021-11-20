package puf.frisbee.frontend.model;

/**
 * Contains all level specific settings.
 */
public interface Level {
	/**
	 * Returns the current level that a team can play.
	 * 
	 * @return current level as integer
	 */
	int getCurrentLevel();

	/**
	 * Updates the current level to the next level.
	 */
	void updateCurrentLevel();

	/**
	 * Returns the inital character y position for the level.
	 *
	 * @return position
	 */
	double getInitialCharacterYPosition();

	/**
	 * Returns the inital character x position for the left character in the level.
	 *
	 * @return position
	 */
	double getInitialCharacterLeftXPosition();

	/**
	 * Returns the inital character x position for the right character in the level.
	 *
	 * @return position
	 */
	double getInitialCharacterRightXPosition();

	/**
	 * Returns the left scene boundary of a level.
	 *
	 * @return position
	 */
	double getSceneBoundaryLeft();

	/**
	 * Returns the right scene boundary of a level.
	 *
	 * @return position
	 */
	double getSceneBoundaryRight();
}
