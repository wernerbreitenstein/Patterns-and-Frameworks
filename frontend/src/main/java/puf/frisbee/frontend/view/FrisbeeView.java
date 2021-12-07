package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import puf.frisbee.frontend.viewmodel.GameViewModel;

public class FrisbeeView {
    @FXML
    private ImageView frisbee;

    private GameViewModel gameViewModel;

    public void init(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
        this.frisbee.layoutXProperty().bind(gameViewModel.getCharacterLeftXPositionProperty());
        this.frisbee.layoutYProperty().bind(gameViewModel.getCharacterLeftYPositionProperty());
    }

    @FXML
    private void handleFrisbeeClicked(MouseEvent event) {
        this.gameViewModel.incrementScore();
        this.gameViewModel.removeLife();
    }
}
