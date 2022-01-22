package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.model.Team;
import puf.frisbee.frontend.viewmodel.StartViewModel;

public class StartView {
    /**
     * The top panel element in fxml.
     */
    @FXML
    private TopPanelView topPanelController;

    /**
     * The bottom panel element in fxml.
     */
    @FXML
    private BottomPanelView bottomPanelController;

    /**
     * The highscore table in fxml.
     */
    @FXML
    private TableView<Team> highscoreTable;

    /**
     * The highscore table column "score" in fxml.
     */
    @FXML
    private TableColumn<Team, Integer> highscoreScore;

    /**
     * The button for join/create team in fxml.
     */
    @FXML
    private Button buttonJoinCreateTeam;

    /**
     * The start button in fxml.
     */
    @FXML
    private Button buttonStart;

    /**
     * The start view model instance.
     */
    private StartViewModel startViewModel;

    /**
     * The view handler instance.
     */
    private ViewHandler viewHandler;

    /**
     * Init method. Sets the start view model instance and the view handler
     * instance.
     * Also initializes the top panel and bottom panel, and all the bindings of
     * the fxml elements with the start view model.
     * Fills and sorts the highscore table by score.
     *
     * @param startViewModel start view model instance
     * @param viewHandler    view handler instance
     */
    public void init(StartViewModel startViewModel, ViewHandler viewHandler) {
        this.startViewModel = startViewModel;
        this.viewHandler = viewHandler;

        this.buttonJoinCreateTeam.visibleProperty().bind(
                this.startViewModel.getShowJoinCreateTeamButtonProperty());
        this.buttonStart.visibleProperty().bind(
                this.startViewModel.getShowStartButtonProperty());

        this.topPanelController.init(startViewModel, viewHandler);
        this.bottomPanelController.init(viewHandler);

        this.highscoreTable.setItems(startViewModel.getHighscoreTableItems());
        this.highscoreScore.setSortType(TableColumn.SortType.DESCENDING);
        this.highscoreTable.getSortOrder().add(highscoreScore);
        this.highscoreTable.sort();
    }

    /**
     * This method is called when the refresh button is clicked.
     * It gets the newest highscore data, fills the table and sorts it by score.
     *
     * @param event action event
     */
    @FXML
    private void handleRefreshButtonClicked(ActionEvent event) {
        this.startViewModel.refreshData();
        this.highscoreTable.sort();
    }

    /**
     * This method is called when the create/join team button is clicked.
     * It redirects to the team view.
     *
     * @param event action event
     */
    @FXML
    private void handleJoinCreateTeamButtonClicked(ActionEvent event) {
        this.viewHandler.openTeamView();
    }

    /**
     * This method is called, when the start button is clicked.
     * It resets the game timer and redirects to the waiting view.
     *
     * @param event action event
     */
    @FXML
    private void handleStartButtonClicked(ActionEvent event) {
        this.startViewModel.resetCountdown();
        this.viewHandler.openWaitingView();
    }
}
