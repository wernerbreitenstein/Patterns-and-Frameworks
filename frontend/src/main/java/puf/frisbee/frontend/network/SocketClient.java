package puf.frisbee.frontend.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import puf.frisbee.frontend.model.FrisbeeParameter;
import puf.frisbee.frontend.model.MovementDirection;

import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
import java.beans.PropertyChangeSupport;

public class SocketClient {
    /**
     * The socket instance.
     */
    private Socket socket;
    /**
     * The object output stream instance.
     */
    private ObjectOutputStream outToServer;
    /**
     * Manages the listeners to changes in the socket client due to socket
     * connection.
     */
    private final PropertyChangeSupport support;
    /**
     * The thread instance.
     */
    private Thread thread;
    /**
     * Flag for the thread to indicate if its running or not.
     */
    private boolean threadIsRunning;

    /**
     * Constructs the socket client and initializes the support for managing
     * listeners.
     */
    public SocketClient() {
        support = new PropertyChangeSupport(this);
    }

    /**
     * Starts a socket connection with the server.
     */
    public void start() {
        Dotenv dotenv = Dotenv.load();
        String socketIP = dotenv.get("SOCKET_IP");
        int socketPort = Integer.parseInt((dotenv.get("SOCKET_PORT")));
        try {
            this.socket = new Socket(socketIP, socketPort);
            outToServer = new ObjectOutputStream(socket.getOutputStream());

            // start connection in new thread to not block anything
            this.threadIsRunning = true;
            thread = new Thread(this::listenToServer);
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            this.stopConnection();
            System.out.println("Connection stopped.");
        }
    }

    /**
     * Stop a socket connection with the server.
     */
    public void stopConnection() {
        try {
            threadIsRunning = false;
            outToServer.close();
            socket.close();
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The thread that is listening to socket messages.
     */
    private void listenToServer() {
        try {
            ObjectInputStream inFromServer = new ObjectInputStream(
                    socket.getInputStream());

            // listen for requests from the server, notify listeners when
            // request came in
            while (threadIsRunning) {
                String receivedJsonString = (String) inFromServer.readObject();
                ObjectMapper objectMapper = new ObjectMapper();
                SocketRequest response = objectMapper.readValue(
                        receivedJsonString, new TypeReference<>() {
                        });

                Object propertyValue;

                // for some types, we need to convert
                switch (response.getRequestType()) {
                    case MOVE -> propertyValue = mapMovementStringToDirection(
                            response.getValue());
                    case THROW -> propertyValue =
                            FrisbeeParameter.stringToObject(
                                    response.getValue());
                    case GAME_RUNNING -> propertyValue =
                            mapGameRunningStringToGameRunningStatus(
                            response.getValue());
                    // default is we are just passing the value through
                    default -> propertyValue = response.getValue();
                }

                // add request type as name so the character model knows how
                // to react on what
                support.firePropertyChange(response.getRequestType().name(),
                        null, propertyValue);
            }

            // close input stream if not running anymore
            inFromServer.close();
        } catch (IOException | ClassNotFoundException e) {
            this.stopConnection();
            support.firePropertyChange(SocketRequestType.READY.name(), null,
                    false);
        }
    }

    /**
     * Helper method to convert movement strings from the socket to enums.
     *
     * @param movement movement as string
     * @return movement as enum
     */
    private MovementDirection mapMovementStringToDirection(String movement) {
        if (movement.equals(MovementDirection.UP.name())) {
            return MovementDirection.UP;
        }
        if (movement.equals(MovementDirection.LEFT.name())) {
            return MovementDirection.LEFT;
        }
        if (movement.equals(MovementDirection.RIGHT.name())) {
            return MovementDirection.RIGHT;
        }

        return null;
    }

    /**
     * Helper method to convert game running strings from the socket to enums.
     *
     * @param gameRunning status as string
     * @return status as enum
     */
    private GameRunningStatus mapGameRunningStringToGameRunningStatus(
            String gameRunning) {
        if (gameRunning.equals(GameRunningStatus.START.name())) {
            return GameRunningStatus.START;
        }
        if (gameRunning.equals(GameRunningStatus.PAUSE.name())) {
            return GameRunningStatus.PAUSE;
        }
        if (gameRunning.equals(GameRunningStatus.RESUME.name())) {
            return GameRunningStatus.RESUME;
        }
        if (gameRunning.equals(GameRunningStatus.CONTINUE.name())) {
            return GameRunningStatus.CONTINUE;
        }

        return null;
    }

    /**
     * Transfers the team name to the server.
     *
     * @param teamName name of the team
     */
    public void sendInitToServer(String teamName) {
        SocketRequest request = new SocketRequest(SocketRequestType.INIT,
                teamName);
        sendToServer(request);
    }

    /**
     * Sends DISCONNECT true to the server.
     */
    public void sendDisconnectToServer() {
        SocketRequest request = new SocketRequest(SocketRequestType.DISCONNECT,
                "true");
        sendToServer(request);
    }

    /**
     * Sends GAME_RUNNING with a value to the server.
     *
     * @param value the value of game running
     */
    public void sendGameRunningStatusToServer(GameRunningStatus value) {
        SocketRequest request = new SocketRequest(
                SocketRequestType.GAME_RUNNING, value.name());
        sendToServer(request);
    }

    /**
     * Transfers character movement to the server.
     *
     * @param direction the direction the character moves to
     */
    public void sendMovementToServer(MovementDirection direction) {
        SocketRequest request = new SocketRequest(SocketRequestType.MOVE,
                direction.name());
        sendToServer(request);
    }

    /**
     * Transfers paramaters for a frisbee throw to the server.
     *
     * @param frisbeeParameter the needed parameters to calculate a throw
     */
    public void sendFrisbeeThrowToServer(FrisbeeParameter frisbeeParameter) {
        SocketRequest request = new SocketRequest(SocketRequestType.THROW,
                frisbeeParameter.toString());
        sendToServer(request);
    }

    private void sendToServer(SocketRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(request);
            outToServer.writeObject(jsonString);
        } catch (Exception e) {
            this.stopConnection();
            support.firePropertyChange(SocketRequestType.READY.name(), null,
                    false);
        }
    }

    /**
     * Method to subscribe listeners to socket events.
     *
     * @param type     request type that should be listend to
     * @param listener the function that should be executed on changes
     */
    public void addPropertyChangeListener(SocketRequestType type,
                                          PropertyChangeListener listener) {
        support.addPropertyChangeListener(type.name(), listener);
    }
}
