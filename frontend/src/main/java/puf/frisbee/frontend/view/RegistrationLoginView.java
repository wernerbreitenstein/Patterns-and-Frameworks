package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.RegistrationLoginViewModel;

public class RegistrationLoginView {
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
     * The text field for email (log in form) in fxml.
     */
    @FXML
    private TextField loginEmail;

    /**
     * The text field for password (log in form) in fxml.
     */
    @FXML
    private TextField loginPassword;

    /**
     * The text field for name (registration form) in fxml.
     */
    @FXML
    private TextField registerName;

    /**
     * The text field for email (registration form) in fxml.
     */
    @FXML
    private TextField registerEmail;

    /**
     * The text field for password (registration form) in fxml.
     */
    @FXML
    private TextField registerPassword;

    /**
     * The label for error messages in the log in form in fxml.
     */
    @FXML
    private Label loginError;

    /**
     * The label for error messages in the registration form in fxml.
     */
    @FXML
    private Label registrationError;

    /**
     * The registration login view model instance.
     */
    private RegistrationLoginViewModel registrationLoginViewModel;

    /**
     * The view handler instance.
     */
    private ViewHandler viewHandler;

    /**
     * Init method, sets the registration login view model instance and the
     * view handler instance.
     * Also initializes the top panel view, the bottom panel view and the
     * binding of the text fields and labels with the login registration view
     * model.
     *
     * @param registrationLoginViewModel registration login view model instance
     * @param viewHandler                view handler instance
     */
    public void init(RegistrationLoginViewModel registrationLoginViewModel,
                     ViewHandler viewHandler) {
        this.registrationLoginViewModel = registrationLoginViewModel;
        this.viewHandler = viewHandler;

        this.topPanelController.init(registrationLoginViewModel, viewHandler);
        this.bottomPanelController.init(viewHandler);

        this.loginError.textProperty().bind(
                this.registrationLoginViewModel.getLoginErrorLabelProperty());
        this.registrationError.textProperty().bind(
                this.registrationLoginViewModel.getRegistrationErrorLabelProperty());
    }

    /**
     * This method is called when the cancel button on login or registration
     * is clicked.
     * It triggers the redirect to the start view.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonCancelClicked(ActionEvent event) {
        this.viewHandler.openStartView();
    }

    /**
     * This method is called when the login button is clicked.
     * It triggers the login functionality of the registration login view
     * model and redirects to the start view, if the login was successful.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonLoginClicked(ActionEvent event) {
        boolean loginSuccessful = registrationLoginViewModel.login(
                loginEmail.getText(), loginPassword.getText());

        if (loginSuccessful) {
            viewHandler.openStartView();
        }
    }

    /**
     * This method is called when the register button is clicked.
     * It triggers the registration functionality of the registration login view
     * model and redirects to the start view, if the login was successful.
     *
     * @param event action event
     */
    @FXML
    private void handleButtonRegisterClicked(ActionEvent event) {
        boolean registerSuccessful = registrationLoginViewModel.register(
                registerName.getText(), registerEmail.getText(),
                registerPassword.getText());

        if (registerSuccessful) {
            viewHandler.openStartView();
        }
    }
}
