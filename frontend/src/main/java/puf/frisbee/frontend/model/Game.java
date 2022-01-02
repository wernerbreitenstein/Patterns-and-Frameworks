package puf.frisbee.frontend.model;

/**
 * Contains all game specific settings.
 */
public interface Game {
    /**
     * Returns the game initial countdown.
     *
     * @return initial countdown in seconds
     */
    int getInitialCountdown();

    /**
     * Returns the current game countdown.
     *
     * @return current countdown in seconds
     */
    int getCurrentCountdown();

    /**
     * Sets the current game countdown.
     */
    void setCurrentCountdown(int second);

    /**
     * Returns the game gravity.
     *
     * @return gravity
     */
    int getGravity();

    /**
     * Returns the character movement speed.
     *
     * @return speed
     */
    int getCharacterSpeed();
}
