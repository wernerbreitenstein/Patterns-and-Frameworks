package puf.frisbee.frontend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Contains all player data.
 */
@JsonDeserialize(as = PlayerModel.class)
public interface Player {
    /**
     * Returns the id of the player.
     *
     * @return player id
     */
    int getId();

    /**
     * Sets the id of the player.
     *
     * @param id id of the player
     */
    void setId(int id);

    /**
     * Returns the name of the player.
     *
     * @return the player name
     */
    String getName();

    /**
     * Sets the name of a player.
     *
     * @param name the player name
     */
    void setName(String name);

    /**
     * Returns the email of the player.
     *
     * @return the player email
     */
    String getEmail();

    /**
     * Sets the email of a player.
     *
     * @param email the player email
     */
    void setEmail(String email);

    /**
     * Returns the password of the player.
     *
     * @return the player password
     */
    String getPassword();

    /**
     * Sets the password of a player.
     *
     * @param password the player email
     */
    void setPassword(String password);

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

    /**
     * Updates the player name in the database and in the player model.
     *
     * @param name that should be updated
     * @return true if update was successful
     */
    boolean updateName(String name);

    /**
     * Updates the player password in the database and in the player model.
     *
     * @param password that should be updated
     * @return true if update was successful
     */
    boolean updatePassword(String password);
}
