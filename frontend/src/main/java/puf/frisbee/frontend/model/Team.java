package puf.frisbee.frontend.model;

/**
 * Contains all team data.
 */
public interface Team {
	/**
	 * Returns the current team name that a team identifies.
	 * 
	 * @return current team name as string
	 */
	String getTeamName();

	/**
	 * Sets the team name.
	 */
	void setTeamName(String teamName);

	/**
	 * Returns the current number of lives a team has got.
	 * 
	 * @return current number of lives as integer
	 */
	int getTeamLives();

	/**
	 * Sets the current number of lives a team has got.
	 */
	void setTeamLives(int lives);

	/**
	 * Returns the current level the team has achieved.
	 * 
	 * @return current level as integer
	 */
	int getTeamLevel();
	
	/**
	 * Sets the current level the team has achieved.
	 */
	void setTeamLevel(int level);
	
	/**
	 * Returns the current score the team has achieved.
	 * 
	 * @return current score as integer
	 */
	int getTeamScore();
	
	/**
	 * Sets the current score the team has achieved.
	 */
	void setTeamScore(int score);

	/**
	 * @return first player in team
	 */
	String getPlayer1();

	/**
	 * @return second player in team
	 */
	String getPlayer2();

	/**
	 * Assignes player to existing team until team is full
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
