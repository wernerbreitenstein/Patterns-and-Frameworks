package puf.frisbee.frontend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Contains all team data.
 */
@JsonDeserialize(as = TeamModel.class)
public interface Team {
	/**
	 * Returns the id of the team.
	 *
	 * @return team id
	 */
	int getId();

	/**
	 * Sets the id of the team.
	 *
	 * @param id id of the team
	 */
	void setId(int id);

	/**
	 * Returns the current team name that a team identifies.
	 * 
	 * @return current team name as string
	 */
	String getName();

	/**
	 * Sets the name of a team.
	 *
	 * @param name the team name
	 */
	void setName(String name);

	/**
	 * Returns the left player.
	 *
	 * @return object of the left player
	 */
	 Player getPlayerLeft();

	/**
	 * Sets the left player.
	 *
	 * @param playerLeft player object
	 */
	 void setPlayerLeft(Player playerLeft);

	/**
	 * Returns the right player.
	 *
	 * @return object of the right player
	 */
	 Player getPlayerRight();

	/**
	 * Sets the right player.
	 *
	 * @param playerRight player object
	 */
	 void setPlayerRight(Player playerRight);

	/**
	 * Returns the current number of lives a team has got.
	 * 
	 * @return current number of lives as integer
	 */
	int getLives();

	/**
	 * Sets the current number of lives a team has got.
	 *
	 * @param lives team lives
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
	 *
	 * @param level team level
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
	 *
	 * @param  score team score
	 */
	void setScore(int score);

	/**
	 * Returns the path to the background image for the current level.
	 *
	 * @param Level the current level
	 *
	 * @return path to background images for the current level
	 */
	String getBackgroundImageForLevel(int level);

	/**
	 * Returns the active status of the team.
	 *
	 * @return true if team is active
	 */
	boolean getActive();

	/**
	 * Returns the active status of the team.
	 *
	 * @param  active true if team is active
	 */
	void setActive(boolean active);

	/**
	 * Returns true if team for a player is loaded and its data is set.
	 *
	 * @return true if team data is set
	 */
	boolean isTeamSet();


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

	/**
	 * Returns the first team of the teams of a player and sets its data.
	 *
	 * @param player player object
	 * @return true if a team for the player was found
	 */
	boolean getTeamForPlayer(Player player);

	/**
	 * Saves team data to backend and sets updated data in team model.
	 *
	 * @return true if saving was successful
	 */
	boolean saveTeamData();

	/**
	 * Resets team data in team model.
	 */
	void resetTeamData();

	/**
	 * Reloads team data in team model with the newest backend data.
	 */
	void reloadTeamData();

	/**
	 * Returns character type of the player.
	 *
	 * @return left or right
	 */
	CharacterType getOwnCharacterType();
}
