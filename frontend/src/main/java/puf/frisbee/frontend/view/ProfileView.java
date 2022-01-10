package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.ProfileViewModel;

public class ProfileView {
    @FXML
    private TopPanelView topPanelController;

    @FXML
    private BottomPanelView bottomPanelController;

    @FXML
    private TextField profileName;

    @FXML
    private TextField profileEmail;

    @FXML
    private TextField profilePassword;

    @FXML
    private Label profileError;

    private ProfileViewModel profileViewModel;
    private ViewHandler viewHandler;

    public void init(ProfileViewModel profileViewModel, ViewHandler viewHandler) {
        this.profileViewModel = profileViewModel;
        this.viewHandler = viewHandler;

        this.topPanelController.init(profileViewModel, viewHandler);
        this.bottomPanelController.init(viewHandler);

        this.profileName.setText(this.profileViewModel.getProfileNameLabel());
        this.profileEmail.setText(this.profileViewModel.getProfileEmailLabel());

        this.profileError.textProperty().bind(this.profileViewModel.getProfileErrorlLabelProperty());
    }

    @FXML
    private void handleButtonCancelClicked(ActionEvent event) {
        this.viewHandler.openStartView();
    }

    @FXML
    private void handleButtonSaveClicked(ActionEvent event) {
        boolean changesSavedSuccessful = this.profileViewModel.saveChanges(this.profileName.getText(), this.profilePassword.getText());

        if (changesSavedSuccessful) {
            this.viewHandler.openStartView();
        }
    }
}
