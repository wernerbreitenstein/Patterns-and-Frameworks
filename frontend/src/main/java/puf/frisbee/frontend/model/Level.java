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
	 * Sets the the current level that a team can play.
	 */
	void setCurrentLevel(int level);

	/**
	 * Increments the current level to the next level.
	 */
	void incrementCurrentLevel();

	/**
	 * Returns the initial character y position for the level.
	 *
	 * @return position
	 */
	double getInitialCharacterYPosition();

	/**
	 * Returns the initial character x position for the left character in the level.
	 *
	 * @return position
	 */
	double getInitialCharacterLeftXPosition();

	/**
	 * Returns the initial character x position for the right character in the level.
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

	/**
	 * Returns the jump height possible for a character in a level.
	 *
	 * @return jump height
	 */
	double getJumpHeight();

	/**
	 * Returns the initial frisbee x position for the level.
	 *
	 * @return position
	 */
	double getInitialFrisbeeXPosition();

	/**
	 * Returns the initial frisbee y position for the level.
	 *
	 * @return position
	 */
	double getInitialFrisbeeYPosition();
}
