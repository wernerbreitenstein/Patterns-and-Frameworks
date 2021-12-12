package puf.frisbee.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import puf.frisbee.frontend.core.ViewHandler;
import puf.frisbee.frontend.viewmodel.RegistrationLoginViewModel;
import java.io.IOException;
import java.net.URISyntaxException;

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

    @FXML
    Label loginError;

    @FXML
    Label registrationError;

    RegistrationLoginViewModel registrationLoginViewModel;
    ViewHandler viewHandler;

    public void init(RegistrationLoginViewModel registrationLoginViewModel, ViewHandler viewHandler) {
        this.registrationLoginViewModel = registrationLoginViewModel;
        this.viewHandler = viewHandler;

        this.loginError.textProperty().bind(this.registrationLoginViewModel.getLoginErrorLabelProperty());
        this.registrationError.textProperty().bind(this.registrationLoginViewModel.getRegistrationErrorLabelProperty());
    }

    @FXML
    private void handleLogin(ActionEvent event) throws URISyntaxException {
        boolean loginSuccessful = registrationLoginViewModel.login(loginEmail.getText(), loginPassword.getText());

        if (loginSuccessful) {
            viewHandler.openStartView();
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) throws URISyntaxException, IOException, InterruptedException {
        boolean registerSuccessful = registrationLoginViewModel.register(registerName.getText(), registerEmail.getText(), registerPassword.getText());

        if (registerSuccessful) {
            viewHandler.openStartView();
        }
    }
}
