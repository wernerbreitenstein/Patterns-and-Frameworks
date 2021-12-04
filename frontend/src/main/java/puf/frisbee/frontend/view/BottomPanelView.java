package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class BottomPanelView {
	@FXML
	private Label labelQuickTip;

	private GameViewModel gameViewModel;
	private ViewHandler viewHandler;

	public void init(GameViewModel gameViewModel, ViewHandler viewHandler) {
		this.gameViewModel = gameViewModel;
		this.viewHandler = viewHandler;
	}

	public void init(ViewHandler viewHandler) {
		this.viewHandler = viewHandler;
	}

	@FXML
	private void handleQuitGameEntered(MouseEvent event) {
		this.labelQuickTip.textProperty().setValue("Should I stay or should I go?");
	}

	@FXML
	private void handleQuitGameExited(MouseEvent event) {
		this.labelQuickTip.textProperty().setValue("");
	}

	@FXML
	private void handleQuitGameClicked(MouseEvent event) {
		if (gameViewModel != null) {
			this.gameViewModel.showQuitConfirmDialog();
		} else {
			this.viewHandler.end();
		}
	}
}