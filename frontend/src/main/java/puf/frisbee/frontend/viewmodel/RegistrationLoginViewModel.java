package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.*;
import puf.frisbee.frontend.model.Player;
import puf.frisbee.frontend.model.Team;

import java.util.regex.Pattern;

public class RegistrationLoginViewModel {
    /**
     * The player model instance.
     */
    private final Player playerModel;

    /**
     * The team model instance.
     */
    private final Team teamModel;

    /**
     * The value of the login error label.
     */
    private final StringProperty loginErrorLabel;

    /**
     * The value of the registration error label.
     */
    private final StringProperty registrationErrorLabel;

    /**
     * Regex pattern of valid email.
     */
    private final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

    /**
     * Constructs the registration login view model and sets the player model
     * instance and team model instance.
     * Also initializes the error labels.
     *
     * @param playerModel player model instance
     * @param teamModel   team model instance
     */
    public RegistrationLoginViewModel(Player playerModel, Team teamModel) {
        this.playerModel = playerModel;
        this.teamModel = teamModel;

        this.loginErrorLabel = new SimpleStringProperty();
        this.registrationErrorLabel = new SimpleStringProperty();
    }

    /**
     * Validates the given email and password and triggers a login in the
     * player model if the validation was successful.
     * Loads the team for the given player.
     *
     * @param email    email of the player
     * @param password password of the player
     * @return true if the login was successful otherwise false
     */
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

    /**
     * Validates the given name, email and password and triggers a registration
     * in the player model if the validation was successful.
     *
     * @param name     name of the player
     * @param email    email of the player
     * @param password password of the player
     * @return true if the registration was successful otherwise false
     */
    public boolean register(String name, String email, String password) {
        if (name.length() < 1 || email.length() < 1 || password.length() < 1) {
            this.registrationErrorLabel.setValue(
                    "Name, Email and Password are required.");
            return false;
        }

        if (!isEmailValid(email)) {
            this.registrationErrorLabel.setValue("Email is not valid.");
            return false;
        }

        boolean registrationSuccessful = this.playerModel.register(name, email,
                password);

        if (registrationSuccessful) {
            this.playerModel.setLoginStatus(true);
        } else {
            this.registrationErrorLabel.setValue(
                    "Something went wrong. Please try again.");
        }

        return registrationSuccessful;
    }

    /**
     * Validates a given email against the email regex.
     *
     * @param email email of the player
     * @return true if valid otherwise false
     */
    private boolean isEmailValid(String email) {
        return this.VALID_EMAIL_REGEX.matcher(email).find();
    }

    /**
     * Method for the binding of the login error label value with an element in
     * the view.
     *
     * @return error label value
     */
    public StringProperty getLoginErrorLabelProperty() {
        return this.loginErrorLabel;
    }

    /**
     * Method for the binding of the registration error label value with an
     * element in the view.
     *
     * @return error label value
     */
    public StringProperty getRegistrationErrorLabelProperty() {
        return this.registrationErrorLabel;
    }
}
