package puf.frisbee.frontend.model;

import puf.frisbee.frontend.network.SocketClient;
import puf.frisbee.frontend.network.SocketRequestType;

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
        // add listener to socket income changes for movement
        socketClient.addPropertyChangeListener(SocketRequestType.MOVE, this::getOtherCharacterMovement);
        // add listener to init status
        socketClient.addPropertyChangeListener(SocketRequestType.READY, this::getReadyStatus);
        // add listener to game status
        socketClient.addPropertyChangeListener(SocketRequestType.GAME_RUNNING, this::getGameStatus);

        // create own support to notify models
        support = new PropertyChangeSupport(this);
    }

    // tell the server we are connected with our team
    @Override
    public void init() {
        this.socketClient.sendInitToServer(this.teamModel.getName());
    }

    // tell the other client the game has started
    @Override
    public void startGame() {
        this.socketClient.sendStartGameToServer();
    }

    // listen to ready status changes and notify listener, if ready is true
    private void getReadyStatus(PropertyChangeEvent event) {
        if(event.getNewValue().equals("true")) {
            support.firePropertyChange(SocketRequestType.READY.name(), null, true);
        }
    }

    // listen to current game status from server and notify own listeners
    private void getGameStatus(PropertyChangeEvent event) {
        boolean gameStatus = event.getNewValue().equals("true");
        support.firePropertyChange(SocketRequestType.GAME_RUNNING.name(), null, gameStatus);
    }

    // send message to socket as soon as own position is moved, so the other client knows
    @Override
    public void moveOwnCharacter(MovementDirection direction) {
        socketClient.sendMovementToServer(direction);
    }

    // get the movement of other character from server socket
    private void getOtherCharacterMovement(PropertyChangeEvent event) {
        String directionString = (String) event.getNewValue();

        // map received direction strint to enum
        // jump as default
        MovementDirection direction = MovementDirection.UP;
        if (directionString.equals(MovementDirection.RIGHT.name())) {
            direction = MovementDirection.RIGHT;
        }
        if (directionString.equals(MovementDirection.LEFT.name())) {
            direction = MovementDirection.LEFT;
        }

        // notify other subscribers like GameViewModel
        support.firePropertyChange(SocketRequestType.MOVE.name(), null, direction);
    }

    // function to add listener to changes in this model, filtered by type, needed e.g. for GameViewModel
    @Override
    public void addPropertyChangeListener(SocketRequestType type, PropertyChangeListener listener) {
        support.addPropertyChangeListener(type.name(), listener);
    }
}
