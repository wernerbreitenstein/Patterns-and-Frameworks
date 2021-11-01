package puf.frisbee.frontend.model;

// TODO: add interface for class
public class LevelModel {
    private int countdownSeconds;

    public void setCountdown(int countdownSeconds)
    {
        this.countdownSeconds = countdownSeconds;
    }

    public int getCountdown() {
        return this.countdownSeconds;
    }

}
