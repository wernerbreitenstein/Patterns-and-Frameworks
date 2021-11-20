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
	 * Returns the current level the team has achieved.
	 * 
	 * @return current level as integer
	 */
	int getTeamLevel();

	/**
	 * Returns the current score the team has achieved.
	 * 
	 * @return current score as integer
	 */
	int getTeamScore();
}
