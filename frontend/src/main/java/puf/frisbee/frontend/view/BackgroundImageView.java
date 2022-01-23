package puf.frisbee.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import puf.frisbee.frontend.viewmodel.BackgroundImageViewModel;

public class BackgroundImageView {
    /**
     * The imageview instance corresponding the specified FXML-element.
     */
    @FXML
    private ImageView backgroundImage;

    /**
     * Init method used in other views, sets background image view model and
     * background image.
     *
     * @param backgroundImageViewModel the background image view model instance
     */
    public void init(BackgroundImageViewModel backgroundImageViewModel) {
        this.backgroundImage.imageProperty().bind(
                backgroundImageViewModel.getBackgroundImageProperty());
    }
}
