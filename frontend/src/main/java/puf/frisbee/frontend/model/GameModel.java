package puf.frisbee.frontend.model;

public class GameModel implements Game {
    private final int countdownInSeconds = 30;
    private final int gravity = 2;
    private final int characterSpeed = 2;


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
