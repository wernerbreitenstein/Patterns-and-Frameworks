package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.model.Team;
import puf.frisbee.frontend.viewmodel.StartViewModel;

public class StartView {
	@FXML
	private Label labelTeamName;

	@FXML
	private Label labelLevel;

	@FXML
	private Label labelScore;

	@FXML
	private Label labelQuickTip;

	@FXML
	private Label labelGreeting;

	@FXML
	private Button buttonLoginRegister;

	@FXML
	private MenuButton buttonSettings;

	@FXML
	private Button buttonStart;

	@FXML
	private TableView<Team> highscoreTable;

	@FXML
	private TableColumn<Team, Integer> highscoreScore;

	@FXML
	private BottomPanelView bottomPanelController;

	private StartViewModel startViewModel;
	private ViewHandler viewHandler;

	public void init(StartViewModel startViewModel, ViewHandler viewHandler) {
		this.startViewModel = startViewModel;
		this.viewHandler = viewHandler;

		this.bottomPanelController.init(viewHandler);

		this.labelGreeting.textProperty().bind(this.startViewModel.getLabelGreetingProperty());
		this.buttonLoginRegister.visibleProperty().bind(this.startViewModel.getShowLoginRegisterButtonProperty());
		this.buttonSettings.visibleProperty().bind(this.startViewModel.getShowSettingsButtonProperty());
		this.buttonStart.visibleProperty().bind(this.startViewModel.getShowStartButtonProperty());

		this.highscoreTable.setItems(startViewModel.getHighscoreTableItems());
		this.highscoreScore.setSortType(TableColumn.SortType.DESCENDING);
		this.highscoreTable.getSortOrder().add(highscoreScore);
		this.highscoreTable.sort();
	}

	@FXML
	private void handleLoginRegisterButtonClicked(ActionEvent event) {
		this.viewHandler.openRegistrationLoginView();
	}

	@FXML
	private void handleLogoutButtonClicked(ActionEvent event) {
		this.startViewModel.logout();
	}

	@FXML
	private void handleProfileButtonClicked(ActionEvent event) {
		this.viewHandler.openProfileView();
	}

	@FXML
	private void handleStartButtonClicked(ActionEvent event) {
		this.viewHandler.openWaitingView();
	}

	@FXML
	private void handleRefreshButtonClicked(ActionEvent event) {
		this.startViewModel.refreshData();
		this.highscoreTable.sort();
	}
}
