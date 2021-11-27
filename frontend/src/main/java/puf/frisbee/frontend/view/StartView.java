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
    Label labelGreeting;

    @FXML
    Button buttonLoginRegister;

    @FXML
    Button buttonLogout;

    @FXML
    Button buttonStart;

    @FXML
    TableView<Team> highscoreTable;

    @FXML
    TableColumn<Team, Integer> highscoreScore;

    private StartViewModel startViewModel;
    private ViewHandler viewHandler;

    public void init(StartViewModel startViewModel, ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
        this.startViewModel = startViewModel;

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
    private void handleButtonQuitGameClicked(ActionEvent event) {
        this.viewHandler.end();
    }
    
    
}
