package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.RegistrationLoginViewModel;

public class RegistrationLoginView {
    @FXML
    TextField loginEmail;

    @FXML
    TextField loginPassword;

    @FXML
    TextField registerName;

    @FXML
    TextField registerEmail;

    @FXML
    TextField registerPassword;

    RegistrationLoginViewModel registrationLoginViewModel;
    ViewHandler viewHandler;

    public void init(RegistrationLoginViewModel registrationLoginViewModel, ViewHandler viewHandler) {
        this.registrationLoginViewModel = registrationLoginViewModel;
        this.viewHandler = viewHandler;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        boolean loginSuccessful = registrationLoginViewModel.login(loginEmail.getText(), loginPassword.getText());

        if (loginSuccessful) {
            viewHandler.openStartView();
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        boolean registerSuccessful = registrationLoginViewModel.register(registerName.getText(), registerEmail.getText(), registerPassword.getText());

        if (registerSuccessful) {
            viewHandler.openStartView();
        }
    }
}
