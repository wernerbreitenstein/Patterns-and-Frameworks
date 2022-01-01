package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.RegistrationLoginViewModel;

public class RegistrationLoginView {
    @FXML
    private TopPanelView topPanelController;

    @FXML
    private BottomPanelView bottomPanelController;

    @FXML
    private Label labelQuickTip;

    @FXML
    private TextField loginEmail;

    @FXML
    private TextField loginPassword;

    @FXML
    private TextField registerName;

    @FXML
    private TextField registerEmail;

    @FXML
    private TextField registerPassword;

    @FXML
    private Label loginError;

    @FXML
    private Label registrationError;

    private RegistrationLoginViewModel registrationLoginViewModel;
    private ViewHandler viewHandler;

    public void init(RegistrationLoginViewModel registrationLoginViewModel, ViewHandler viewHandler) {
        this.registrationLoginViewModel = registrationLoginViewModel;
        this.viewHandler = viewHandler;

        this.topPanelController.init(registrationLoginViewModel, viewHandler);
        this.bottomPanelController.init(viewHandler);

        this.loginError.textProperty().bind(this.registrationLoginViewModel.getLoginErrorLabelProperty());
        this.registrationError.textProperty().bind(this.registrationLoginViewModel.getRegistrationErrorLabelProperty());
    }

    @FXML
    private void handleButtonCancelClicked(ActionEvent event) {
        this.viewHandler.openStartView();
    }

    @FXML
    private void handleButtonLoginClicked(ActionEvent event) {
        boolean loginSuccessful = registrationLoginViewModel.login(loginEmail.getText(), loginPassword.getText());

        if (loginSuccessful) {
            viewHandler.openStartView();
        }
    }

    @FXML
    private void handleButtonRegisterClicked(ActionEvent event) {
        boolean registerSuccessful = registrationLoginViewModel.register(registerName.getText(), registerEmail.getText(), registerPassword.getText());

        if (registerSuccessful) {
            viewHandler.openStartView();
        }
    }
}
