package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.LevelViewModel;

public class HighscoreView {
    @FXML
    TableView highscoreTable;

    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }


    @FXML
    private void handleStartButtonClicked(MouseEvent event) {
        this.viewHandler.openWaitingView();
    }
}
