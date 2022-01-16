package puf.frisbee.frontend.model;

import java.beans.PropertyChangeListener;

public interface Character {
    /**
     * Send a message to socket as soon as own position is moved, so the other client knows
     * @param direction in which the character is moved
     */
    void moveOwnCharacter(MovementDirection direction);

    /**
     * Add listener that can be used to notify subscribers when e.g. a character should be moved
     * @param listener listener that should be added to Character
     */
    void addPropertyChangeListener(PropertyChangeListener listener);
}
