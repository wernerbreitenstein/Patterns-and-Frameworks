package puf.frisbee.frontend.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.model.Team;
import puf.frisbee.frontend.viewmodel.HighscoreViewModel;

public class HighscoreView {
    @FXML
    TableView<Team> highscoreTable;

    private HighscoreViewModel highscoreViewModel;
    private ViewHandler viewHandler;

    public void init(HighscoreViewModel highscoreViewModel, ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
        this.highscoreViewModel = highscoreViewModel;
        this.highscoreTable.setItems(highscoreViewModel.getHighscoreTableItems());
    }


    @FXML
    private void handleStartButtonClicked(MouseEvent event) {
        this.viewHandler.openWaitingView();
    }

    @FXML
    private void handleRefreshButtonClicked(MouseEvent event) {
        this.highscoreViewModel.refreshData();
    }
}
