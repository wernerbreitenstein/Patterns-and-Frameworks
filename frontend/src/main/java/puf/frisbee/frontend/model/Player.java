package puf.frisbee.frontend.model;

/**
 * Contains all player data.
 */
public interface Player {
    /**
     * Returns the name of the player.
     *
     * @return the player name
     */
    String getPlayerName();

    /**
     * Returns true if the player is logged in.
     *
     * @return true if player is logged in
     */
    boolean isLoggedIn();

    /**
     * Sets the log in status of the player to true or false.
     *
     * @param status
     */
    void setLoginStatus(boolean status);
}
