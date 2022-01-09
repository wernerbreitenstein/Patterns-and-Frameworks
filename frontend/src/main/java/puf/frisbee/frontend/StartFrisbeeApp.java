package puf.frisbee.frontend;

import javafx.application.Application;
import puf.frisbee.frontend.core.WebSocketTest;

public class StartFrisbeeApp {

	public static void main(String[] args) {
//		Application.launch(FrisbeeApp.class);


        WebSocketTest wst = new WebSocketTest();
        wst.start();
	}
}