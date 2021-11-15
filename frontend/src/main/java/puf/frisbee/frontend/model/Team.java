package puf.frisbee.frontend.model;

public interface Team {
	/**
	 * Returns the current team name that a team identifies.
	 * 
	 * @return current team name as string
	 */
	String getTeam();
	
	/**
	 * Returns the current level the team has achieved.
	 * 
	 * @return current level as integer
	 */
	int getLevel();

	/**
	 * Returns the current score the team has achieved.
	 * 
	 * @return current score as integer
	 */
	int getScore();
}
