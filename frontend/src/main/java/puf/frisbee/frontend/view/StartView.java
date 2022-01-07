package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.model.Team;
import puf.frisbee.frontend.viewmodel.StartViewModel;

public class StartView {
	@FXML
	private TopPanelView topPanelController;

	@FXML
	private BottomPanelView bottomPanelController;

	@FXML
	private Label labelQuickTip;

	@FXML
	private TableView<Team> highscoreTable;

	@FXML
	private TableColumn<Team, Integer> highscoreScore;

	@FXML
	private Button buttonJoinCreateTeam;

	@FXML
	private Button buttonStart;

	private StartViewModel startViewModel;
	private ViewHandler viewHandler;

	public void init(StartViewModel startViewModel, ViewHandler viewHandler) {
		this.startViewModel = startViewModel;
		this.viewHandler = viewHandler;

		this.buttonJoinCreateTeam.visibleProperty().bind(this.startViewModel.getShowJoinCreateTeamButtonProperty());
		this.buttonStart.visibleProperty().bind(this.startViewModel.getShowStartButtonProperty());

		this.topPanelController.init(startViewModel, viewHandler);
		this.bottomPanelController.init(viewHandler);

		this.highscoreTable.setItems(startViewModel.getHighscoreTableItems());
		this.highscoreScore.setSortType(TableColumn.SortType.DESCENDING);
		this.highscoreTable.getSortOrder().add(highscoreScore);
		this.highscoreTable.sort();
	}

	@FXML
	private void handleRefreshButtonClicked(ActionEvent event) {
		this.startViewModel.refreshData();
		this.highscoreTable.sort();
	}

	@FXML
	private void handleJoinCreateTeamButtonClicked(ActionEvent event) {
		this.startViewModel.resetCountdown();
		this.viewHandler.openTeamView();
	}

	@FXML
	private void handleStartButtonClicked(ActionEvent event) {
		this.startViewModel.resetCountdown();
		this.viewHandler.openWaitingView();
	}
}
