package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.model.TeamModel;
import puf.frisbee.frontend.viewmodel.HighscoreViewModel;

public class HighscoreView {
    @FXML
    TableView<TeamModel> highscoreTable;

    @FXML
    TableColumn<TeamModel, Integer> highscoreScore;

    private HighscoreViewModel highscoreViewModel;
    private ViewHandler viewHandler;

    public void init(HighscoreViewModel highscoreViewModel, ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
        this.highscoreViewModel = highscoreViewModel;

        this.highscoreTable.setItems(highscoreViewModel.getHighscoreTableItems());
        this.highscoreScore.setSortType(TableColumn.SortType.DESCENDING);
        this.highscoreTable.getSortOrder().add(highscoreScore);
        this.highscoreTable.sort();
    }


    @FXML
    private void handleStartButtonClicked(MouseEvent event) {
        this.viewHandler.openWaitingView();
    }

    @FXML
    private void handleRefreshButtonClicked(MouseEvent event) {
        this.highscoreViewModel.refreshData();
        this.highscoreTable.sort();
    }
}
