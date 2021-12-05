package puf.frisbee.frontend.viewmodel;

import puf.frisbee.frontend.model.Player;

public class RegistrationLoginViewModel {
    private final Player playerModel;

    public RegistrationLoginViewModel(Player playerModel) {
        this.playerModel = playerModel;
    }

    public boolean login(String email, String password) {
        // TODO: validation of text fields with messages
        if (email.length() < 1 || password.length() < 1) {
            return false;
        }

        boolean loginSuccessful = this.playerModel.login(email, password);

        if (loginSuccessful) {
            this.playerModel.setLoginStatus(true);
        }

        return loginSuccessful;
    }

    public boolean register(String name, String email, String password) {
        // TODO: validation of text fields with messages
        if (name.length() < 1 || email.length() < 1 || password.length() < 1) {
            return false;
        }

        boolean registrationSuccessful = this.playerModel.register(name, email, password);

        if (registrationSuccessful) {
            this.playerModel.setLoginStatus(true);
        }

        return registrationSuccessful;
    }
}
