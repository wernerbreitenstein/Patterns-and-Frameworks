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
    String getName();

    /**
     * Returns true if the player is logged in.
     *
     * @return true if player is logged in
     */
    boolean isLoggedIn();

    /**
     * Sets the log in status of the player to true or false.
     *
     * @param status true if player should be logged in
     */
    void setLoginStatus(boolean status);

    /**
     * Registers player in the database and sets data in the player model.
     *
     * @param name name of the player
     * @param email email of the player
     * @param password password of the player
     *
     * @return boolean true if registration was successful
     */
    boolean register(String name, String email, String password);

    /**
     * Logs player in and sets data in the player model.
     *
     * @param email email of the player
     * @param password password of the player
     *
     * @return boolean true if login was successful
     */
    boolean login(String email, String password);
}
