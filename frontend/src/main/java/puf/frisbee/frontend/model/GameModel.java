package puf.frisbee.frontend.model;

public class GameModel implements Game {
    private final int maximumLevel = 3;
    private final int initialCountdown = 30;
    private int currentCountdown;
    private final int gravity = 1;
    private final int characterSpeed = 3;

    @Override
    public int getMaximumLevel() { return this.maximumLevel; }

    @Override
    public int getInitialCountdown() { return initialCountdown; }

    @Override
    public int getCurrentCountdown() {
        return this.currentCountdown;
    }

    @Override
    public void setCurrentCountdown(int second) { this.currentCountdown = second; }

    @Override
    public int getGravity() {
        return this.gravity;
    }

    @Override
    public int getCharacterSpeed() {
        return this.characterSpeed;
    }
}
