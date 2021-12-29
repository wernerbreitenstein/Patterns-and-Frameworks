package puf.frisbee.frontend.model;

public class GameModel implements Game {
    private final int initialCountdown = 10;
    private int currentCountdown;
    private final int gravity = 1;
    private final int characterSpeed = 1;


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
