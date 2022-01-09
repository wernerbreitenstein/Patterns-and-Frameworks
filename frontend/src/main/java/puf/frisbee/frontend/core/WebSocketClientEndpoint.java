package puf.frisbee.frontend.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class WebSocketClientEndpoint {

    protected   WebSocketContainer container;
    protected   Session userSession = null;

    public WebSocketClientEndpoint() {
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(String sServer) {

        try {
            userSession = container.connectToServer(this, new URI(sServer));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String sMsg) throws IOException {
        userSession.getBasicRemote().sendText(sMsg);
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println ("Connected, Session ID = " + session.getId());
        try {
            session.getBasicRemote().sendText("start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println ("Received message:" + message);
            String userInput = bufferRead.readLine();
            return userInput;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while receiving message";
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Connection closed because of " + closeReason + ", Session ID " + session.getId());
    }

    public void disconnect() throws IOException {
        userSession.close();
    }
}