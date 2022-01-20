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
        // add listener to init status
        socketClient.addPropertyChangeListener(SocketRequestType.READY, this::forwardNotificationAsBoolean);
        // add listener to game status
        socketClient.addPropertyChangeListener(SocketRequestType.GAME_RUNNING, this::forwardNotificationAsBoolean);
        // add listener to socket income changes for movement
        socketClient.addPropertyChangeListener(SocketRequestType.MOVE, this::forwardNotification);
        // add listener to socket income changes for frisbee throw
        socketClient.addPropertyChangeListener(SocketRequestType.THROW, this::forwardNotification);

        // create own support to notify models
        support = new PropertyChangeSupport(this);
    }

    // start socket connection and tell the server we are connected with our team
    @Override
    public void init() {
        this.socketClient.start();
        this.socketClient.sendInitToServer(this.teamModel.getName());
    }

    @Override
    public void stop() {
        this.socketClient.sendDisconnectToServer();
        this.socketClient.stopConnection();
    }

    // tell the other client the game has started
    @Override
    public void startGame() {
        this.socketClient.sendGameRunningToServer(true);
    }

    // tell the other client the game has stopped
    @Override
    public void stopGame() {
        this.socketClient.sendGameRunningToServer(false);
    }

    // listen to ready and current game status changes and notify listener, if ready is true
    private void forwardNotificationAsBoolean(PropertyChangeEvent event) {
        boolean status = event.getNewValue().equals("true");
        support.firePropertyChange(event.getPropertyName(), null, status);
    }

    // send message to socket as soon as own position is moved, so the other client knows
    @Override
    public void moveOwnCharacter(MovementDirection direction) {
        socketClient.sendMovementToServer(direction);
    }

    @Override
    public void throwFrisbee(FrisbeeParameter parameter) {
        socketClient.sendFrisbeeThrowToServer(parameter);
    }

    // get the movement or frisbee throw of other character from server socket
    private void forwardNotification(PropertyChangeEvent event) {
        // notify other subscribers like GameViewModel
        support.firePropertyChange(event.getPropertyName(), null, event.getNewValue());
    }

    // function to add listener to changes in this model, filtered by type, needed e.g. for GameViewModel
    @Override
    public void addPropertyChangeListener(SocketRequestType type, PropertyChangeListener listener) {
        support.addPropertyChangeListener(type.name(), listener);
    }
}
