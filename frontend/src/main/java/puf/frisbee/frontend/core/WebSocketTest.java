package puf.frisbee.frontend.core;

import io.github.cdimascio.dotenv.Dotenv;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketTest {

    public void start() {

        Dotenv dotenv = Dotenv.load();
        String baseUrl = dotenv.get("BACKEND_BASE_URL");

        try {
//            WebSocketClientEndpoint wsc = new WebSocketClientEndpoint();
//
//            wsc.connect(baseUrl);
//            System.out.println("Connection established");
//            Thread.sleep(1000);
//            wsc.disconnect();
//            System.out.println("Disconnected");

            URI uri = new URI(baseUrl);
            ExampleClient wsc = new ExampleClient(uri);
            wsc.connect();
//            wsc.onMessage("Test");

        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
