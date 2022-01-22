package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.ProfileViewModel;

public class ProfileView {
    /**
     * The top panel element in fxml.
     */
    @FXML
    private TopPanelView topPanelController;

    /**
     * The bottom panel element in fxml.
     */
    @FXML
    private BottomPanelView bottomPanelController;

    /**
     * The text field for name in fxml.
     */
    @FXML
    private TextField profileName;

    /**
     * The text field for email in fxml.
     */
    @FXML
    private TextField profileEmail;

    /**
     * The text field for password in fxml.
     */
    @FXML
    private TextField profilePassword;

    /**
     * The label for displaying error messages in fxml.
     */
    @FXML
    private Label profileError;

    /**
     * The profile view instance.
     */
    private ProfileViewModel profileViewModel;

    /**
     * The view handler instance.
     */
    private ViewHandler viewHandler;

    /**
     * Init methods, sets profile view model and view handler instance.
     * Also initializes the top panel, the bottom panel view and the binding
     * of the text fields and labels with the profile view model.
     *
     * @param profileViewModel the profile view model instance
     * @param viewHandler the view handler instance
     */
    public void init(ProfileViewModel profileViewModel,
                     ViewHandler viewHandler) {
        this.profileViewModel = profileViewModel;
        this.viewHandler = viewHandler;

        this.topPanelController.init(profileViewModel, viewHandler);
        this.bottomPanelController.init(viewHandler);

        this.profileName.setText(this.profileViewModel.getProfileNameLabel());
        this.profileEmail.setText(this.profileViewModel.getProfileEmailLabel());

        this.profileError.textProperty().bind(
                this.profileViewModel.getProfileErrorlLabelProperty());
    }

    /**
     * This method is called when the cancel button in the profile is clicked.
     * It triggers the redirect to the start view.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonCancelClicked(ActionEvent event) {
        this.viewHandler.openStartView();
    }

    /**
     * This method is called when the save button in the profile view is
     * clicked.
     * It triggers the save of the profile data and redirects to the start
     * view, if the save was successful.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonSaveClicked(ActionEvent event) {
        boolean changesSavedSuccessful = this.profileViewModel.saveChanges(
                this.profileName.getText(), this.profilePassword.getText());

        if (changesSavedSuccessful) {
            this.viewHandler.openStartView();
        }
    }
}
