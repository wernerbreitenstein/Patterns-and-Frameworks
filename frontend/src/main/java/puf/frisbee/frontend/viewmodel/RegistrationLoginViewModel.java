package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.*;
import puf.frisbee.frontend.model.Player;
import puf.frisbee.frontend.model.Team;

import java.util.regex.Pattern;


public class RegistrationLoginViewModel {
    private final Player playerModel;
    private final Team teamModel;

    private StringProperty loginErrorLabel;
    private StringProperty registrationErrorLabel;

    private final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public RegistrationLoginViewModel(Player playerModel, Team teamModel) {
        this.playerModel = playerModel;
        this.teamModel = teamModel;

        this.loginErrorLabel = new SimpleStringProperty();
        this.registrationErrorLabel = new SimpleStringProperty();
    }

    public boolean login(String email, String password) {
        if (email.length() < 1 || password.length() < 1) {
            this.loginErrorLabel.setValue("Email and Password are required.");
            return false;
        }

        if (!isEmailValid(email)) {
            this.loginErrorLabel.setValue("Email is not valid.");
            return false;
        }

        boolean loginSuccessful = this.playerModel.login(email, password);

        if (loginSuccessful) {
            this.playerModel.setLoginStatus(true);
        } else {
            this.loginErrorLabel.setValue("Email or password is wrong.");
        }

        // load team data for player
        this.teamModel.getTeamForPlayer(this.playerModel);

        return loginSuccessful;
    }

    public boolean register(String name, String email, String password) {
        if (name.length() < 1 || email.length() < 1 || password.length() < 1) {
            this.registrationErrorLabel.setValue("Name, Email and Password are required.");
            return false;
        }

        if (!isEmailValid(email)) {
            this.registrationErrorLabel.setValue("Email is not valid.");
            return false;
        }

        boolean registrationSuccessful = this.playerModel.register(name, email, password);

        if (registrationSuccessful) {
            this.playerModel.setLoginStatus(true);
        } else {
            this.registrationErrorLabel.setValue("Something went wrong. Please try again.");
        }

        return registrationSuccessful;
    }

    private boolean isEmailValid(String email) {
        return this.VALID_EMAIL_REGEX.matcher(email).find();
    }

    public StringProperty getLoginErrorLabelProperty() {
        return this.loginErrorLabel;
    }

    public StringProperty getRegistrationErrorLabelProperty() {
        return this.registrationErrorLabel;
    }
}
