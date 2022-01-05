package puf.frisbee.frontend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Contains all team data.
 */
@JsonDeserialize(as = TeamModel.class)
public interface Team {
	// TODO: update interface
	/**
	 * Returns the current team name that a team identifies.
	 * 
	 * @return current team name as string
	 */
	String getName();

	/**
	 * Returns the current number of lives a team has got.
	 * 
	 * @return current number of lives as integer
	 */
	int getLives();

	/**
	 * Sets the current number of lives a team has got.
	 */
	void setLives(int lives);

	/**
	 * Returns the current level the team has achieved.
	 * 
	 * @return current level as integer
	 */
	int getLevel();
	
	/**
	 * Sets the current level the team has achieved.
	 */
	void setLevel(int level);
	
	/**
	 * Returns the current score the team has achieved.
	 * 
	 * @return current score as integer
	 */
	int getScore();
	
	/**
	 * Sets the current score the team has achieved.
	 */
	void setScore(int score);


	/**
	 * Assignes player to existing team
	 *
	 * @param player player to be added to team
	 * @param teamName of the team to be joined
	 * @return true if joining was successful
	 */
	boolean joinTeam(Player player, String teamName);

	/**
	 * Creates a new team
	 * @param teamName of the team to be created
	 *
	 * @return true if creating was successful
	 */
	boolean createTeam(String teamName);
}
