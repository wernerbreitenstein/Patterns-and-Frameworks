package puf.frisbee.frontend.network;

import io.github.cdimascio.dotenv.Dotenv;
import puf.frisbee.frontend.model.Request;

import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
// TODO: check if we need to use beans or if there is something else
import java.beans.PropertyChangeSupport;

public class SocketClient {

    private Socket socket;
    private ObjectOutputStream outToServer;

    private PropertyChangeSupport support;

    public SocketClient(){
        support = new PropertyChangeSupport(this);

        Dotenv dotenv = Dotenv.load();
        String socketIP = dotenv.get("SOCKET_IP");
        int socketPort = Integer.parseInt((dotenv.get("SOCKET_PORT")));

        try {
            this.socket = new Socket(socketIP, socketPort);
            outToServer = new ObjectOutputStream(socket.getOutputStream());

            // start connection in new thread to not block anything
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
            while(true) {
                // TODO: we need a shared object between client and server, like the request object
                String request = (String) inFromServer.readObject();
                support.firePropertyChange("MOVE", null, request);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void sendMovementToServer(String direction){
        // TODO: use enums
        Request req = new Request("MOVE", direction);
        sendToServer(req);
    }

    private void sendToServer(Request req) {
        try {
            // TODO: we need a shared object between client and server, like the request object
            outToServer.writeObject(req.value);
        } catch (IOException e) {
            support.firePropertyChange("ERROR", null, "Connection lost, restart program");
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }


    // TODO: stop connection somewhere?
    public void stopConnection() {
        try {
            //inFromServer.close();
            outToServer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}