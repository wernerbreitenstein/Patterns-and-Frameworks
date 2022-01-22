package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.WaitingViewModel;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class BottomPanelView {
    /**
     * The waiting view model instance.
     */
    private WaitingViewModel waitingViewModel;
    /**
     * The game view model instance.
     */
    private GameViewModel gameViewModel;
    /**
     * The view handler instance.
     */
    private ViewHandler viewHandler;

    /**
     * Init method used in waiting view, sets waiting view model and view
     * handler.
     *
     * @param waitingViewModel the waiting view model instance
     * @param viewHandler      the view handler instance
     */
    public void init(WaitingViewModel waitingViewModel,
                     ViewHandler viewHandler) {
        this.waitingViewModel = waitingViewModel;
        this.viewHandler = viewHandler;
    }

    /**
     * Init method used in game view, sets waiting view model and view
     * handler.
     *
     * @param gameViewModel the game view model instance
     * @param viewHandler   the view handler instance
     */
    public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
        this.gameViewModel = gameViewModel;
        this.viewHandler = viewHandler;
    }

    /**
     * Default init method, used in views without any special bottom panel
     * handling.
     *
     * @param viewHandler the view handler instance
     */
    public void init(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }

    /**
     * Handles click on quit button, depending on where it is called.
     * For the game view, a pause dialog is shown.
     * For the waiting view, the user is redirected to the start view.
     * For all other views, the application is closed.
     *
     * @param event the mouse event
     */
    @FXML
    private void handleQuitGameClicked(MouseEvent event) {
        if (gameViewModel != null) {
            this.gameViewModel.showQuitConfirmDialog();
        } else if (waitingViewModel != null) {
            this.waitingViewModel.quitWaiting();
            this.viewHandler.openStartView();
        } else {
            this.viewHandler.end();
        }
    }
}
