package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import puf.frisbee.frontend.model.Team;

public class BackgroundImageViewModel {

    /**
     * The value of the background image.
     */
    private final ObjectProperty<Image> backgroundImage;

    /**
     * Constructs the background image view model and sets the needed model
     * instances.
     * Also initializes all values needed for the bindings.
     *
     * @param teamModel      team model instance
     */
    public BackgroundImageViewModel(Team teamModel) {
        this.backgroundImage = new SimpleObjectProperty(
                new Image(getClass().getResource(
                        teamModel.getBackgroundImageForLevel(
                                teamModel.getLevel())).toString()));
    }

    /**
     * Method for the binding of the background image value with an element in
     * the view.
     *
     * @return background image value
     */
    public ObjectProperty<Image> getBackgroundImageProperty() {
        return this.backgroundImage;
    }
}
