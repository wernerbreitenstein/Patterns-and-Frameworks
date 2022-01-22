package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import puf.frisbee.frontend.model.Player;

public class ProfileViewModel {
    /**
     * The instance of the player model.
     */
    private final Player playerModel;

    /**
     * The value of the player name label.
     */
    private final String profileNameLabel;

    /**
     * The value of the player email label.
     */
    private final String profileEmailLabel;

    /**
     * The value of the profile error label.
     */
    private final StringProperty profileErrorLabel;

    /**
     * Constructs the profile view model and sets the player model instance.
     * Also sets the values of name, email and error label.
     *
     * @param playerModel player model instance
     */
    public ProfileViewModel(Player playerModel) {
        this.playerModel = playerModel;

        this.profileNameLabel = playerModel.getName();
        this.profileEmailLabel = playerModel.getEmail();
        this.profileErrorLabel = new SimpleStringProperty();
    }

    /**
     * Returns the player name as it should be displayed in the profile.
     *
     * @return the player name
     */
    public String getProfileNameLabel() {
        return this.profileNameLabel;
    }

    /**
     * Returns the player email as it should be displayed in the profile.
     *
     * @return the player email
     */
    public String getProfileEmailLabel() {
        return this.profileEmailLabel;
    }

    /**
     * Method for the binding of the error label value with an element in the
     * view.
     *
     * @return error label value
     */
    public StringProperty getProfileErrorlLabelProperty() {
        return this.profileErrorLabel;
    }

    /**
     * Saves changes in the profile to the database.
     * Validates the given name and password and triggers the update
     * functions in the player model if the validation was successful.
     *
     * @param name name of the player
     * @param password password of the player
     * @return true if the saving was successful otherwise false
     */
    public boolean saveChanges(String name, String password) {
        if (name.length() < 1) {
            this.profileErrorLabel.setValue("Name is required.");
            return false;
        }

        if (!name.equals(this.playerModel.getName())) {
            boolean updateNameSuccessful = this.playerModel.updateName(name);
            if (!updateNameSuccessful) {
                this.profileErrorLabel.setValue("Name could not be updated.");
                return false;
            }
        }

        // only overwrite password if a new one was submitted
        if (password.length() > 0) {
            boolean updatePasswordSuccessful = this.playerModel.updatePassword(
                    name);
            if (!updatePasswordSuccessful) {
                this.profileErrorLabel.setValue(
                        "Password could not be updated.");
                return false;
            }
        }

        return true;
    }
}
