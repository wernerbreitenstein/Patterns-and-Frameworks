package puf.frisbee.frontend.model;

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
	 * Returns the countdown for a level in seconds.
	 * 
	 * @return countdown in seconds
	 */
	int getCountdown();
}
