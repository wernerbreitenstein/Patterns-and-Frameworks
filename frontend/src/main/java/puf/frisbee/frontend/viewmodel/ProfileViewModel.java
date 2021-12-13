package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import puf.frisbee.frontend.model.Player;

public class ProfileViewModel {
    private final Player playerModel;

    private String profileNameLabel;
    private String profileEmailLabel;
    private StringProperty profileErrorLabel;

    public ProfileViewModel(Player playerModel) {
        this.playerModel = playerModel;

        this.profileNameLabel = playerModel.getName();
        this.profileEmailLabel = playerModel.getEmail();
        this.profileErrorLabel = new SimpleStringProperty();
    }

    public String getProfileNameLabel() {
        return this.profileNameLabel;
    }

    public String getProfileEmailLabel() {
        return this.profileEmailLabel;
    }

    public StringProperty getProfileErrorlLabelProperty() {
        return this.profileErrorLabel;
    }

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

        // only overwrite password, if a new one was submitted
        if (password.length() > 0) {
            boolean updatePasswordSuccessful = this.playerModel.updatePassword(name);
            if (!updatePasswordSuccessful) {
                this.profileErrorLabel.setValue("Password could not be updated.");
                return false;
            }
        }

        return true;
    }
}
