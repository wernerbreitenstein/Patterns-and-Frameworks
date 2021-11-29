package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
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
    private Button buttonLogout;

    @FXML
    private Button buttonStart;

    @FXML
    private TableView<Team> highscoreTable;

    @FXML
    private TableColumn<Team, Integer> highscoreScore;

    private StartViewModel startViewModel;
    private ViewHandler viewHandler;

    public void init(StartViewModel startViewModel, ViewHandler viewHandler) {
    	this.startViewModel = startViewModel;
        this.viewHandler = viewHandler;

        this.labelGreeting.textProperty().bind(this.startViewModel.getLabelGreetingProperty());
        this.buttonLoginRegister.visibleProperty().bind(this.startViewModel.getShowLoginRegisterButtonProperty());
        this.buttonLogout.visibleProperty().bind(this.startViewModel.getShowLogoutButtonProperty());
        this.buttonStart.visibleProperty().bind(this.startViewModel.getShowStartButtonProperty());

        this.highscoreTable.setItems(startViewModel.getHighscoreTableItems());
        this.highscoreScore.setSortType(TableColumn.SortType.DESCENDING);
        this.highscoreTable.getSortOrder().add(highscoreScore);
        this.highscoreTable.sort();
    }

    @FXML
    private void handleLoginRegisterButtonClicked(MouseEvent event) {
        // TODO: load login/register view later on
        this.startViewModel.login();
    }

    @FXML
    private void handleLogoutButtonClicked(MouseEvent event) {
        this.startViewModel.logout();
    }

    @FXML
    private void handleStartButtonClicked(MouseEvent event) {
        this.viewHandler.openWaitingView();
    }

    @FXML
    private void handleRefreshButtonClicked(MouseEvent event) {
        this.startViewModel.refreshData();
        this.highscoreTable.sort();
    }
    
    @FXML
    private void handleIconCloseClicked(MouseEvent event) {
        this.viewHandler.end();
    }
    
    @FXML
    private void handleIconCloseEntered(MouseEvent event) {
    	this.labelQuickTip.textProperty().setValue("Quit the game.");
    }
    
    @FXML
    private void handleIconCloseExited(MouseEvent event) {
    	this.labelQuickTip.textProperty().setValue("");
    }    
}
