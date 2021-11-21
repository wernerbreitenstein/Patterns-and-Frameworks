package puf.frisbee.frontend.model;

public class GameModel implements Game {
    private final int countdownInSeconds = 30;

    @Override
    public int getCountdown() {
        return this.countdownInSeconds;
    }
}
