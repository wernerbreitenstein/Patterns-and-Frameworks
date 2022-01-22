package puf.frisbee.frontend.model;

public class GameModel implements Game {
    /**
     * The maximum level for the game.
     */
    private final int maximumLevel = 3;
    /**
     * The initial time for each level.
     */
    private final int initialCountdown = 30;
    /**
     * The actual time the level has left.
     */
    private int currentCountdown;
    /**
     * The gravity parameter for the game.
     */
    private final int gravity = 1;
    /**
     * The character's speed parameter for the game.
     */
    private final int characterSpeed = 3;

    @Override
    public int getMaximumLevel() {
        return this.maximumLevel;
    }

    @Override
    public int getInitialCountdown() {
        return initialCountdown;
    }

    @Override
    public int getCurrentCountdown() {
        return this.currentCountdown;
    }

    @Override
    public void setCurrentCountdown(int second) {
        this.currentCountdown = second;
    }

    @Override
    public int getGravity() {
        return this.gravity;
    }

    @Override
    public int getCharacterSpeed() {
        return this.characterSpeed;
    }
}
