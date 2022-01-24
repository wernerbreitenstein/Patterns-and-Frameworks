package puf.frisbee.frontend.network;

public enum GameRunningStatus {
    /**
     * Start the game.
     */
    START,
    /**
     * Pause the game.
     */
    PAUSE,
    /**
     * Resume after pause.
     */
    RESUME,
    /**
     * Continue, e.g. after a level is done.
     */
    CONTINUE,
}
