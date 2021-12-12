package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.ProfileViewModel;

public class ProfileView {
    @FXML
    TextField profileName;

    @FXML
    TextField profileEmail;

    @FXML
    TextField profilePassword;

    @FXML
    Label profileError;

    ProfileViewModel profileViewModel;
    ViewHandler viewHandler;

    public void init(ProfileViewModel profileViewModel, ViewHandler viewHandler) {
        this.profileViewModel = profileViewModel;
        this.viewHandler = viewHandler;

        this.profileName.setText(this.profileViewModel.getProfileNameLabel());
        this.profileEmail.setText(this.profileViewModel.getProfileEmailLabel());

        this.profileError.textProperty().bind(this.profileViewModel.getProfileErrorlLabelProperty());
    }

    @FXML
    private void handleSave(ActionEvent event) {
        boolean changesSavedSuccessful = this.profileViewModel.saveChanges(this.profileName.getText(), this.profilePassword.getText());

        if (changesSavedSuccessful) {
            this.viewHandler.openStartView();
        }
    }
}