package puf.frisbee.frontend;

import io.github.cdimascio.dotenv.Dotenv;
import puf.frisbee.frontend.core.FrisbeeWebSocketClient;

import java.net.URI;

public class StartFrisbeeApp {

	public static void main(String[] args) {
//		Application.launch(FrisbeeApp.class);


        Dotenv dotenv = Dotenv.load();
        String baseUrl = dotenv.get("BACKEND_BASE_URL");

        try {
            FrisbeeWebSocketClient wsc = new FrisbeeWebSocketClient(new URI(baseUrl));
            wsc.connect();
//            wsc.onMessage("Test");

        }catch (Exception e){
            e.printStackTrace();
        }
	}
}