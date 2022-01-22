package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import puf.frisbee.frontend.model.Player;
import puf.frisbee.frontend.model.Team;


public class TeamViewModel {
    /**
     * The team model instance.
     */
    private final Team teamModel;

    /**
     * The player model instance.
     */
    private final Player playerModel;

    /**
     * The value of the flag that indicates if the team form is shown.
     */
    private final BooleanProperty teamFormVisibility;

    /**
     * The value of the flag that indicates if the ready to go label is shown.
     */
    private final BooleanProperty readyToGoLabel;

    /**
     * The value of the current team name label.
     */
    private final StringProperty currentTeamLabel;

    /**
     * The value of the left player label.
     */
    private final StringProperty playerLeftLabel;

    /**
     * The value of the right player label.
     */
    private final StringProperty playerRightLabel;

    /**
     * The value of the label for join team errors.
     */
    private final StringProperty joinTeamErrorLabel;

    /**
     * The value of the label for create team errors.
     */
    private final StringProperty createTeamErrorLabel;

    /**
     * Constructs the team view model and sets all needed model instances.
     * Initializes all values needed for the bindings with the view.
     * Sets the values for the team labels.
     *
     * @param teamModel   team model instance
     * @param playerModel player model instance
     */
    public TeamViewModel(Team teamModel, Player playerModel) {
        this.teamModel = teamModel;
        this.playerModel = playerModel;

        this.joinTeamErrorLabel = new SimpleStringProperty();
        this.createTeamErrorLabel = new SimpleStringProperty();

        // if no team or no active team exists on load form is visible
        this.teamFormVisibility = new SimpleBooleanProperty(
                !this.teamModel.isTeamSet() || !this.teamModel.getActive()
        );

        // if a team or an active team exists on load headline is visible
        this.readyToGoLabel = new SimpleBooleanProperty(
                this.teamModel.isTeamSet() && this.teamModel.getActive()
        );

        this.currentTeamLabel = new SimpleStringProperty();
        this.playerLeftLabel = new SimpleStringProperty();
        this.playerRightLabel = new SimpleStringProperty();

        // fill team values
        if (!this.teamModel.isTeamSet() || !this.teamModel.getActive()) {
            this.setEmptyTeamValues();
        } else {
            this.refreshTeamLabelValues();
        }
    }

    /**
     * Sets the team labels with values that should be displayed if the player
     * has no team.
     */
    private void setEmptyTeamValues() {
        this.currentTeamLabel.setValue("Right now, you have no active team.");
        this.playerLeftLabel.setValue("");
        this.playerRightLabel.setValue("");
    }

    /**
     * Sets the team labels with the values of the team the player currently
     * is in.
     */
    private void refreshTeamLabelValues() {
        String nameTeam = this.teamModel.getName() != null
                ? this.teamModel.getName() : " – –";
        this.currentTeamLabel.setValue("YOUR CURRENT TEAM: " + nameTeam);
        String namePlayerLeft = this.teamModel.getPlayerLeft() != null
                ? this.teamModel.getPlayerLeft().getName() : " – –";
        this.playerLeftLabel.setValue("PLAYER LEFT: " + namePlayerLeft);
        String namePlayerRight = this.teamModel.getPlayerRight() != null
                ? this.teamModel.getPlayerRight().getName() : " – –";
        this.playerRightLabel.setValue("PLAYER RIGHT: " + namePlayerRight);
    }

    /**
     * Validates the team name and triggers the join method on the team
     * model if the validation was successful.
     * Also sets error and visibility flags.
     *
     * @param teamName name of the team that should be joined
     * @return true if the join was successful otherwise false
     */
    public boolean joinTeam(String teamName) {
        this.joinTeamErrorLabel.setValue("");

        if (teamName.length() < 1) {
            this.joinTeamErrorLabel.setValue("Team name is required.");
            return false;
        }

        try {
            this.teamModel.joinTeam(playerModel, teamName);
            this.currentTeamLabel.setValue(this.teamModel.getName());
        } catch (IllegalArgumentException e) {
            this.joinTeamErrorLabel.setValue(e.getMessage());
            return false;
        }

        // set label values to new team data
        refreshTeamLabelValues();

        // set form display to false, we already have a team now
        this.teamFormVisibility.setValue(false);
        this.readyToGoLabel.setValue(true);
        return true;
    }

    /**
     * Validates the team name and triggers the create method on the team
     * model if the validation was successful.
     * Also sets error and visibility flags and the team labels if the
     * creation was successful.
     *
     * @param teamName name of the team that should be created
     * @return true if the creation was successful otherwise false
     */
    public boolean createTeam(String teamName) {
        this.createTeamErrorLabel.setValue("");

        if (teamName.length() < 1) {
            this.createTeamErrorLabel.setValue("Team name is required.");
            return false;
        }

        try {
            this.teamModel.createTeam(teamName);
        } catch (IllegalArgumentException e) {
            this.createTeamErrorLabel.setValue(e.getMessage());
            return false;
        }

        try {
            this.teamModel.joinTeam(playerModel, teamName);
        } catch (IllegalArgumentException e) {
            this.createTeamErrorLabel.setValue(e.getMessage());
            return false;
        }

        // set label values to new team data
        refreshTeamLabelValues();

        // set form display to false, we already have a team now
        this.teamFormVisibility.setValue(false);
        this.readyToGoLabel.setValue(true);
        return true;
    }

    /**
     * Method for the binding of the current team label value with an element in
     * the view.
     *
     * @return current team label value
     */
    public StringProperty getCurrentTeamLabel() {
        return this.currentTeamLabel;
    }

    /**
     * Method for the binding of the left player label value with an element in
     * the view.
     *
     * @return left player label value
     */
    public StringProperty getPlayerLeftLabel() {
        return this.playerLeftLabel;
    }

    /**
     * Method for the binding of the right player label value with an element in
     * the view.
     *
     * @return right player label value
     */
    public StringProperty getPlayerRightLabel() {
        return this.playerRightLabel;
    }

    /**
     * Method for the binding of the label value of join team errors with an
     * element in the view.
     *
     * @return join team error label value
     */
    public StringProperty getJoinTeamErrorLabelProperty() {
        return this.joinTeamErrorLabel;
    }

    /**
     * Method for the binding of the label value of create team errors with an
     * element in the view.
     *
     * @return create team error label value
     */
    public StringProperty getCreateTeamErrorLabelProperty() {
        return this.createTeamErrorLabel;
    }

    /**
     * Method for the binding of the visibility of the ready to go label
     * with an element in the view.
     *
     * @return the ready to go label visibility flag
     */
    public BooleanProperty getReadyToGoVisibilityProperty() {
        return this.readyToGoLabel;
    }

    /**
     * Method for the binding of the visibility of the team form
     * with an element in the view.
     *
     * @return the team form visibility flag
     */
    public BooleanProperty getTeamFormVisibilityProperty() {
        return this.teamFormVisibility;
    }
}
