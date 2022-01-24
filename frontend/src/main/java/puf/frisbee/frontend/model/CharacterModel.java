package puf.frisbee.frontend.model;

import puf.frisbee.frontend.network.GameRunningStatus;
import puf.frisbee.frontend.network.SocketClient;
import puf.frisbee.frontend.network.SocketRequestType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CharacterModel implements Character {
    /**
     * The instance of the socket client needed for socket communication.
     */
    private final SocketClient socketClient;
    /**
     * The instance of the team model.
     */
    private final Team teamModel;
    /**
     * Manages the listeners to changes in the character model due to socket
     * connection.
     */
    private final PropertyChangeSupport support;

    /**
     * Constructs the character model and sets the socket client and team
     * model instance.
     *
     * @param socketClient the socket client
     * @param teamModel    the team model
     */
    public CharacterModel(SocketClient socketClient, Team teamModel) {
        this.socketClient = socketClient;
        this.teamModel = teamModel;
        // add listener to init status
        socketClient.addPropertyChangeListener(SocketRequestType.READY,
                this::forwardNotificationAsBoolean);
        // add listener to game status
        socketClient.addPropertyChangeListener(SocketRequestType.GAME_RUNNING,
                this::forwardNotification);
        // add listener to socket income changes for movement
        socketClient.addPropertyChangeListener(SocketRequestType.MOVE,
                this::forwardNotification);
        // add listener to socket income changes for frisbee throw
        socketClient.addPropertyChangeListener(SocketRequestType.THROW,
                this::forwardNotification);

        // create own support to notify models
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void init() {
        // start socket connection and tell the server we are connected with our
        // team
        this.socketClient.start();
        this.socketClient.sendInitToServer(this.teamModel.getName());
    }

    @Override
    public void stop() {
        this.socketClient.sendDisconnectToServer();
        this.socketClient.stopConnection();
    }

    @Override
    public void startGame() {
        this.socketClient.sendGameRunningStatusToServer(GameRunningStatus.START);
    }

    @Override
    public void pauseGame() {
        this.socketClient.sendGameRunningStatusToServer(GameRunningStatus.PAUSE);
    }

    @Override
    public void continueGame() {
        this.socketClient.sendGameRunningStatusToServer(GameRunningStatus.CONTINUE);
    }

    @Override
    public void resumeGame() {
        this.socketClient.sendGameRunningStatusToServer(GameRunningStatus.RESUME);
    }

    @Override
    public void moveOwnCharacter(MovementDirection direction) {
        socketClient.sendMovementToServer(direction);
    }

    @Override
    public void throwFrisbee(FrisbeeParameter parameter) {
        socketClient.sendFrisbeeThrowToServer(parameter);
    }

    /**
     * Forwards notifications as boolean from the socket to all listeners.
     *
     * @param event the event the listener is subsribed to
     */
    private void forwardNotificationAsBoolean(PropertyChangeEvent event) {
        boolean status = event.getNewValue().equals("true");
        support.firePropertyChange(event.getPropertyName(), null, status);
    }

    /**
     * Forwards notifications from the socket to all listeners.
     *
     * @param event the event the listener is subsribed to
     */
    private void forwardNotification(PropertyChangeEvent event) {
        // notify other subscribers like GameViewModel
        support.firePropertyChange(event.getPropertyName(), null,
                event.getNewValue());
    }

    @Override
    public void addPropertyChangeListener(SocketRequestType type,
                                          PropertyChangeListener listener) {
        // function to add listener to changes in this model, filtered by type,
        // needed e.g. for GameViewModel
        support.addPropertyChangeListener(type.name(), listener);
    }
}
