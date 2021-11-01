package puf.frisbee.frontend;

import javafx.application.Application;
import javafx.stage.Stage;
import puf.frisbee.frontend.core.ModelFactory;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.core.ViewModelFactory;


public class FrisbeeApp extends Application {
    @Override
    public void start(Stage stage) {
        ModelFactory modelFactory = new ModelFactory();
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);

        viewHandler.start();
    }
}