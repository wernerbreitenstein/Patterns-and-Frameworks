package puf.frisbee.frontend.model;

import puf.frisbee.frontend.network.SocketRequestType;

import java.beans.PropertyChangeListener;

public interface Character {
    /**
     * Sends a message to the server to initialize a socket session for the team of this character.
     */
    void init();
    /**
     * Send a message to socket as soon as own position is moved, so the other client knows.
     * @param direction in which the character is moved
     */
    void moveOwnCharacter(MovementDirection direction);

    /**
     * Add listener that can be used to notify subscribers when e.g. a character should be moved.
     * @param type type of event that should be listened to
     * @param listener listener that should be added to Character
     */
    void addPropertyChangeListener(SocketRequestType type, PropertyChangeListener listener);
}
