package puf.frisbee.frontend.model;

public class GameModel implements Game {
    private final int countdownInSeconds = 10;
    private final int gravity = 1;
    private final int characterSpeed = 1;


    @Override
    public int getCountdown() {
        return this.countdownInSeconds;
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
