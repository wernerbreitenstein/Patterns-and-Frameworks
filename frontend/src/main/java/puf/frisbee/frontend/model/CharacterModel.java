package puf.frisbee.frontend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import puf.frisbee.frontend.network.SocketClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// TODO: create interface
public class CharacterModel {
    SocketClient socketClient;
    private PropertyChangeSupport support;

    public CharacterModel(SocketClient socketClient) {
        this.socketClient = socketClient;
        // add listener to socket income changes
        // TODO: make more generic, right now only for character movement
        socketClient.addPropertyChangeListener(this::getOtherCharacterMovement);

        // create own support to notify models
        support = new PropertyChangeSupport(this);
    }

    // send message to socket as soon as own position is moved, so the other client knows
    // TODO: use enums and stuff
    public void moveOwnCharacter(String direction) {
        socketClient.sendMovementToServer(direction);
    }

    // TODO: use enums and stuff
    public void getOtherCharacterMovement(PropertyChangeEvent event) {
        // TODO: return real values with enums and all, right now we are just running in the
        // TODO: oposite direction of the own character
        String direction = (String) event.getNewValue();

        if (direction.equals("left")) {
            support.firePropertyChange("MOVE", null, "right");
        }

        if (direction.equals("right")) {
            support.firePropertyChange("MOVE", null, "left");
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
