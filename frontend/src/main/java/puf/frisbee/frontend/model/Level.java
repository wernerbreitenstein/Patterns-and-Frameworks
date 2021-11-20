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
}
