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
	double getCharacterYPosition();

	/**
	 * Returns the inital character x position for the left character in the level.
	 *
	 * @return position
	 */
	double getCharacterLeftXPosition();

	/**
	 * Returns the inital character x position for the right character in the level.
	 *
	 * @return position
	 */
	double getCharacterRightXPosition();
}
