package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class BottomPanelView {
    @FXML
    private Label labelQuickTip;

	private ViewHandler viewHandler;
    
    public void init(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
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
