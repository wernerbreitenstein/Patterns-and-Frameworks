package puf.frisbee.frontend.model;

/**
 * Contains all game specific settings.
 */
public interface Game {
    /**
     * Returns the game countdown.
     *
     * @return countdown in seconds
     */
    int getCountdown();

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
