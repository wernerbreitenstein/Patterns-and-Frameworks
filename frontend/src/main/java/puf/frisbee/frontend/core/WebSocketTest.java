package puf.frisbee.frontend.core;

import io.github.cdimascio.dotenv.Dotenv;

public class WebSocketTest {

    public void start() {

        try {
            WebSocketClient wsc = new WebSocketClient();

            // initialize base url for requests
            Dotenv dotenv = Dotenv.load();
            String baseUrl = dotenv.get("BACKEND_BASE_URL");
            wsc.connect(baseUrl);
            System.out.println("Connection established");
            Thread.sleep(1000);
            wsc.disconnect();
            System.out.println("Disconnected");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
