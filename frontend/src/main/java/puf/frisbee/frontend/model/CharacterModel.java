package puf.frisbee.frontend.model;

import puf.frisbee.frontend.network.SocketClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CharacterModel implements Character {
    SocketClient socketClient;
    Team teamModel;
    private PropertyChangeSupport support;

    public CharacterModel(SocketClient socketClient, Team teamModel) {
        this.socketClient = socketClient;
        this.teamModel = teamModel;
        // add listener to socket income changes
        socketClient.addPropertyChangeListener(this::getOtherCharacterMovement);

        // create own support to notify models
        support = new PropertyChangeSupport(this);
    }

    // tell the server we are connected with our team
    @Override
    public void init() {
        this.socketClient.sendInitToServer(this.teamModel.getName());
    }

    // send message to socket as soon as own position is moved, so the other client knows
    @Override
    public void moveOwnCharacter(MovementDirection direction) {
        socketClient.sendMovementToServer(direction);
    }

    // TODO: add jump
    // get the movement of other character from server socket
    private void getOtherCharacterMovement(PropertyChangeEvent event) {
        String directionString = (String) event.getNewValue();

        // TODO: remove mapping once we have shared object
        if (directionString.equals("left") || directionString.equals("right")) {
            MovementDirection direction = directionString.equals("left") ? MovementDirection.LEFT : MovementDirection.RIGHT;
            support.firePropertyChange("MOVE", null, direction);
        }
    }

    // function to add listener to changes in this model, needed e.g. for game view model
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
