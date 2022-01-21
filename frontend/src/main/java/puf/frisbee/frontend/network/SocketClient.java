package puf.frisbee.frontend.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import puf.frisbee.frontend.model.FrisbeeParameter;
import puf.frisbee.frontend.model.MovementDirection;

import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
// TODO: check if we need to use beans or if there is something else
import java.beans.PropertyChangeSupport;

public class SocketClient {
    private Socket socket;
    private ObjectOutputStream outToServer;
    private PropertyChangeSupport support;
    Thread thread;
    private boolean threadIsRunning;

    public SocketClient(){
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

    private void listenToServer() {
        try {
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

            // listen for requests from the server, notify listeners when request came in
            while(threadIsRunning) {
                String receivedJsonString = (String) inFromServer.readObject();
                ObjectMapper objectMapper = new ObjectMapper();
                SocketRequest response = objectMapper.readValue(receivedJsonString, new TypeReference<>() {
                });

                // default is we are just passing the value through
                Object propertyValue = response.getValue();

                // for some types, we need to convert
                switch(response.getRequestType()) {
                    case MOVE -> propertyValue = mapMovementStringToDirection(response.getValue());
                    case THROW -> propertyValue = FrisbeeParameter.stringToObject(response.getValue());
                }

                // add request type as name so the character model knows how to react on what
                support.firePropertyChange(response.getRequestType().name(), null, propertyValue);
            }

            inFromServer.close();
        } catch (IOException | ClassNotFoundException e) {
            this.stopConnection();
            support.firePropertyChange(SocketRequestType.ERROR.name(), null, "Connection lost.");
        }
    }

    private MovementDirection mapMovementStringToDirection(String movement) {
        if(movement.equals(MovementDirection.UP.name())) {
            return MovementDirection.UP;
        }
        if(movement.equals(MovementDirection.LEFT.name())) {
            return MovementDirection.LEFT;
        }
        if(movement.equals(MovementDirection.RIGHT.name())) {
            return MovementDirection.RIGHT;
        }

        return null;
    }

    /**
     * Transfers the team name to the server.
     * @param teamName name of the team
     */
    public void sendInitToServer(String teamName) {
        SocketRequest request = new SocketRequest(SocketRequestType.INIT, teamName);
        sendToServer(request);
    }

    /**
     * Sends DISCONNECT true to the server.
     */
    public void sendDisconnectToServer() {
        SocketRequest request = new SocketRequest(SocketRequestType.DISCONNECT, "true");
        sendToServer(request);
    }

    /**
     * Sends GAME_RUNNING true to the server.
     */
    public void sendStartGameToServer() {
        SocketRequest request = new SocketRequest(SocketRequestType.GAME_RUNNING, "true");
        sendToServer(request);
    }

    /**
     * Transfers character movement to the server.
     * @param direction the direction the character moves to
     */
    public void sendMovementToServer(MovementDirection direction){
        SocketRequest request = new SocketRequest(SocketRequestType.MOVE, direction.name());
        sendToServer(request);
    }

    /**
     * Transfers paramaters for a frisbee throw to the server.
     * @param frisbeeParameter the needed parameters to calculate a throw
     */
    public void sendFrisbeeThrowToServer(FrisbeeParameter frisbeeParameter){
        SocketRequest request = new SocketRequest(SocketRequestType.THROW, frisbeeParameter.toString());
        sendToServer(request);
    }

    private void sendToServer(SocketRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(request);
            outToServer.writeObject(jsonString);
        } catch (Exception e) {
            this.stopConnection();
            support.firePropertyChange(SocketRequestType.ERROR.name(), null, "Connection lost.");
        }
    }

    /**
     * Method to subscribe listeners to socket events
     * @param type request type that should be listend to
     * @param listener the function that should be executed on changes
     */
    public void addPropertyChangeListener(SocketRequestType type, PropertyChangeListener listener) {
        support.addPropertyChangeListener(type.name(), listener);
    }
}