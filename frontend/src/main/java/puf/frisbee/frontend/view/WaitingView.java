package puf.frisbee.frontend.view;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.LevelViewModel;

public class WaitingView {

	@FXML
	private JFXButton startButton;

	@FXML
	private Text startText;

	@FXML
	private ImageView frisbee;

	private LevelViewModel levelViewModel;
	private ViewHandler viewHandler;

	public void init(LevelViewModel levelViewModel, ViewHandler viewHandler) {
		this.viewHandler = viewHandler;
		this.levelViewModel = levelViewModel;
	}

	@FXML
	private void handleStartButtonClicked(MouseEvent event) {
//        TODO: Animate frisbee
//        TranslateTransition tt = new TranslateTransition(Duration.seconds(2), frisbee);
//        tt.setByX(200);
//        tt.play();
		this.viewHandler.openLevelView(this.levelViewModel.getLevel());
	}
}
