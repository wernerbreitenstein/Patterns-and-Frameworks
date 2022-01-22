package puf.frisbee.frontend;

import javafx.application.Application;
import javafx.stage.Stage;
import puf.frisbee.frontend.core.ModelFactory;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.core.ViewModelFactory;
import puf.frisbee.frontend.network.SocketClientFactory;

public class FrisbeeApp extends Application {
    @Override
    public void start(Stage stage) {
        SocketClientFactory socketClientFactory = new SocketClientFactory();
        ModelFactory modelFactory = new ModelFactory(socketClientFactory);
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);

        viewHandler.start();
    }
}
