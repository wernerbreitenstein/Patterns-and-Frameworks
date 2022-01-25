package puf.frisbee.frontend.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.BackgroundImageViewModel;
import puf.frisbee.frontend.viewmodel.GameViewModel;

import java.beans.PropertyChangeEvent;

public class GameView {
    /**
     * Quit confirm dialog element in fxml.
     */
    @FXML
    private StackPane quitConfirmDialog;

    /**
     * Game over dialog element in fxml.
     */
    @FXML
    private StackPane gameOverDialog;

    /**
     * Level success dialog element in fxml.
     */
    @FXML
    private StackPane levelSuccessDialog;

    /**
     * Game success dialog element in fxml.
     */
    @FXML
    private StackPane gameSuccessDialog;

    /**
     * Disconnect dialog element in fxml.
     */
    @FXML
    private StackPane disconnectDialog;

    /**
     * Label element in fxml to display the successful level message.
     */
    @FXML
    private Label labelLevelSuccess;

    /**
     * Button element in fxml to continue the game in the level success dialog.
     */
    @FXML
    private Button buttonLevelContinue;

    /**
     * The background image element in fxml.
     */
    @FXML
    private BackgroundImageView backgroundImageController;

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
     * The character left element in fxml.
     */
    @FXML
    private CharacterLeftView characterLeftController;

    /**
     * The character right element in fxml.
     */
    @FXML
    private CharacterRightView characterRightController;

    /**
     * The frisbee element in fxml.
     */
    @FXML
    private FrisbeeView frisbeeController;

    /**
     * The game view model instance.
     */
    private GameViewModel gameViewModel;

    /**
     * The view handler instance.
     */
    private ViewHandler viewHandler;

    /**
     * Init method, sets game view model instance and view handler instance.
     * Also initializes the controller of all imported views and the bindings
     * of all fxml elements with the game view model.
     * Subscribes as listener to the game view model to trigger redirects.
     *
     * @param gameViewModel game view model instance
     * @param backgroundImageViewModel background image view model instance
     * @param viewHandler   view handler instance
     */
    public void init(GameViewModel gameViewModel,
                     BackgroundImageViewModel backgroundImageViewModel,
                     ViewHandler viewHandler) {
        this.gameViewModel = gameViewModel;
        this.viewHandler = viewHandler;

        this.backgroundImageController.init(backgroundImageViewModel);

        this.topPanelController.init(gameViewModel, viewHandler);
        this.bottomPanelController.init(gameViewModel, viewHandler);
        this.characterLeftController.init(gameViewModel);
        this.characterRightController.init(gameViewModel);
        this.frisbeeController.init(gameViewModel);

        this.labelLevelSuccess.textProperty().bind(
                this.gameViewModel.getLabelLevelSuccessProperty());
        this.buttonLevelContinue.textProperty().bind(
                this.gameViewModel.getButtonLevelContinueTextProperty());
        this.levelSuccessDialog.visibleProperty().bind(
                this.gameViewModel.getLevelSuccessDialogProperty());
        this.gameOverDialog.visibleProperty().bind(
                this.gameViewModel.getGameOverDialogProperty());
        this.quitConfirmDialog.visibleProperty().bind(
                this.gameViewModel.getQuitConfirmDialogProperty());
        this.gameSuccessDialog.visibleProperty().bind(
                this.gameViewModel.getGameSuccessDialogProperty());
        this.disconnectDialog.visibleProperty().bind(
                this.gameViewModel.getDisconnectDialogProperty());

        // subscribe also to changes of the "game running status", triggered
        // when true (e.g. the other character continues the next level)
        this.gameViewModel.addPropertyChangeListener(this::redirectToGame);
    }

    /**
     * Triggers a redirect to the game view when the event, this method is
     * subscribed to, is triggered.
     *
     * @param event property change events
     */
    private void redirectToGame(PropertyChangeEvent event) {
        // add execution to javafx application
        Platform.runLater(() -> this.viewHandler.openGameView());
    }

    /**
     * This method is executed when a key is pressed in the game view.
     * Triggers the character movements in the game view model.
     *
     * @param event key event
     */
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT -> this.gameViewModel.moveCharacterLeft();
            case RIGHT -> this.gameViewModel.moveCharacterRight();
            case UP -> this.gameViewModel.jumpCharacter();
        }
    }

    /**
     * This method is executed when a pressed key is released in the game view.
     * Stops the character movements in the game model view.
     *
     * @param event key event
     */
    @FXML
    private void handleKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT -> this.gameViewModel.stopCharacterMoveLeft();
            case RIGHT -> this.gameViewModel.stopCharacterMoveRight();
        }
    }

    /**
     * This method is triggered, when continue is clicked in the level
     * success dialog.
     * Triggers all needed level success continue actions in the game view
     * model and reopens the game view.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonLevelSuccessContinue(ActionEvent event) {
        boolean saveSuccessful = this.gameViewModel.saveAfterLevelSucceeded();
        // wait for save before doing anything else, so the other player can
        // sync
        if (saveSuccessful) {
            this.gameViewModel.continueGame();
            this.viewHandler.openGameView();
        }
    }

    /**
     * This method is triggered, when quit is clicked in the level
     * success dialog.
     * Triggers all needed level success quit actions in the game view
     * model and redirects to the start view.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonLevelSuccessQuit(ActionEvent event) {
        this.gameViewModel.saveAfterLevelSucceeded();
        this.gameViewModel.disconnect();
        this.viewHandler.openStartView();
    }

    /**
     * This method is triggered, when confirm is clicked in the game success
     * dialog.
     * Saves the game and redirects to the start view.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonGameSuccessQuit(ActionEvent event) {
        // the game ends here
        this.gameViewModel.saveAfterGameSucceeded();
        this.viewHandler.openStartView();
    }

    /**
     * This method is triggered, when confirm is clicked in the game over
     * dialog.
     * Saves the game and redirects to the start view.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonGameOverQuit(ActionEvent event) {
        // the game ends here
        this.gameViewModel.saveAfterGameOver();
        this.viewHandler.openStartView();
    }

    /**
     * This method is triggered, when continue is clicked in the pause
     * dialog.
     * Triggers the corresponding method in the game view model and reopens
     * the game view.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonQuitGameContinue(ActionEvent event) {
        this.gameViewModel.resumeGame();
        this.viewHandler.openGameView();
    }

    /**
     * This method is triggered, when quit is clicked in the pause
     * dialog.
     * Disconnects the character and redirects to the start view.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonQuitGame(ActionEvent event) {
        this.gameViewModel.disconnect();
        this.viewHandler.openStartView();
    }
}
