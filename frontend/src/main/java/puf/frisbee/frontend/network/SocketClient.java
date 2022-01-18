package puf.frisbee.frontend.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
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
                // TODO: think about property name and maybe only subscribe listener to needed changes
                support.firePropertyChange(response.getRequestType().name(), null, response.getValue());
            }

            inFromServer.close();
            System.out.println("Thread stopped.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendInitToServer(String teamName) {
        SocketRequest request = new SocketRequest(SocketRequestType.INIT, teamName);
        sendToServer(request);
    }

    public void sendMovementToServer(MovementDirection direction){
        String directionString = direction == MovementDirection.LEFT ? "left" : "right";
        SocketRequest request = new SocketRequest(SocketRequestType.MOVE, directionString);
        sendToServer(request);
    }

    private void sendToServer(SocketRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(request);
            outToServer.writeObject(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            support.firePropertyChange("ERROR", null, "Connection lost, restart program");
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
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