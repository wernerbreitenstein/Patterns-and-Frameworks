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
    private boolean threadIsRunning;

    public SocketClient(){
        support = new PropertyChangeSupport(this);
        this.start();
    }

    // TODO: call this e.g. in waiting view, because here we want to start the connection?
    public void start() {
        Dotenv dotenv = Dotenv.load();
        String socketIP = dotenv.get("SOCKET_IP");
        int socketPort = Integer.parseInt((dotenv.get("SOCKET_PORT")));
        try {
            this.socket = new Socket(socketIP, socketPort);
            outToServer = new ObjectOutputStream(socket.getOutputStream());

            // start connection in new thread to not block anything
            this.threadIsRunning = true;
            Thread thread = new Thread(this::listenToServer);
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
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
            System.out.println("Thread stopped.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            support.firePropertyChange(SocketRequestType.ERROR.name(), null, "Connection lost, restart program");
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

    public void sendInitToServer(String teamName) {
        SocketRequest request = new SocketRequest(SocketRequestType.INIT, teamName);
        sendToServer(request);
    }

    public void sendStartGameToServer() {
        SocketRequest request = new SocketRequest(SocketRequestType.GAME_RUNNING, "true");
        sendToServer(request);
    }

    public void sendMovementToServer(MovementDirection direction){
        SocketRequest request = new SocketRequest(SocketRequestType.MOVE, direction.name());
        sendToServer(request);
    }

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
            e.printStackTrace();
            support.firePropertyChange(SocketRequestType.ERROR.name(), null, "Connection lost, restart program");
        }
    }

    // add listener for defined types
    public void addPropertyChangeListener(SocketRequestType type, PropertyChangeListener listener) {
        support.addPropertyChangeListener(type.name(), listener);
    }


    // TODO: stop thread somewhere? probably when leaving the game view
    public void stopConnection() {
        try {
            threadIsRunning = false;
            outToServer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}