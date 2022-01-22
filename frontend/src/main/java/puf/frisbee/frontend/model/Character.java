package puf.frisbee.frontend.model;

import puf.frisbee.frontend.network.SocketRequestType;

import java.beans.PropertyChangeListener;

public interface Character {
    /**
     * Starts socket connection and sends a message to the server to
     * initialize a socket session for the team of this character.
     */
    void init();

    /**
     * Sends a message to the server, that the game has stopped and closes
     * the socket connection.
     */
    void stop();

    /**
     * Sends a message to the server to notify the other client to start the
     * game.
     */
    void startGame();

    /**
     * Sends a message to the server to notify the other client to stop the
     * game.
     */
    void stopGame();

    /**
     * Send a message to socket as soon as own position is moved, so the
     * other client knows.
     *
     * @param direction in which the character is moved
     */
    void moveOwnCharacter(MovementDirection direction);

    /**
     * Send a message with the frisbee parameter to socket, so the other
     * client can calculate the curve itself.
     *
     * @param parameter parameter needed for the frisbee curve calculation
     */
    void throwFrisbee(FrisbeeParameter parameter);

    /**
     * Add listener that can be used to notify subscribers when e.g. a
     * character should be moved.
     *
     * @param type     type of event that should be listened to
     * @param listener listener that should be added to Character
     */
    void addPropertyChangeListener(SocketRequestType type,
                                   PropertyChangeListener listener);
}
