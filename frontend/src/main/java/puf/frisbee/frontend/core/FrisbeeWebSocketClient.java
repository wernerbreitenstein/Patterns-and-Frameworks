package puf.frisbee.frontend.core;

import java.net.URI;
import java.util.Map;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;


public class FrisbeeWebSocketClient extends WebSocketClient {

    public FrisbeeWebSocketClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public FrisbeeWebSocketClient(URI serverURI) {
        super(serverURI);
    }

    public FrisbeeWebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, (Draft) httpHeaders);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, please let me in.");
        System.out.println("opened connection");
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The close codes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

}