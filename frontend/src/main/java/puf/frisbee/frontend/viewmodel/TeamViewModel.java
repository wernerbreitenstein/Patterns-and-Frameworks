package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import puf.frisbee.frontend.model.Player;
import puf.frisbee.frontend.model.Team;


public class TeamViewModel {
    private Team teamModel;
    private final Player playerModel;

    private BooleanProperty teamFormVisibility;
    private BooleanProperty readyToGoLabel;
    private StringProperty currentTeamLabel;
    private StringProperty playerLeftLabel;
    private StringProperty playerRightLabel;

    private StringProperty joinTeamErrorLabel;
    private StringProperty createTeamErrorLabel;


    public TeamViewModel(Team teamModel, Player playerModel) {
        this.teamModel = teamModel;
        this.playerModel = playerModel;

        this.joinTeamErrorLabel = new SimpleStringProperty();
        this.createTeamErrorLabel = new SimpleStringProperty();

        // if no team or no active team exists on load, form is visible
        this.teamFormVisibility = new SimpleBooleanProperty(
                !this.teamModel.isTeamSet() || !this.teamModel.getActive()
        );

        // if a team or an active team exists on load, headline is visible
        this.readyToGoLabel = new SimpleBooleanProperty(
                this.teamModel.isTeamSet() || this.teamModel.getActive()
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

    private void setEmptyTeamValues() {
        this.currentTeamLabel.setValue("Right now, you have no active team.");
        this.playerLeftLabel.setValue("");
        this.playerRightLabel.setValue("");
    }

    private void refreshTeamLabelValues() {
        String nameTeam = this.teamModel.getName() != null ? this.teamModel.getName() : " – –";
        this.currentTeamLabel.setValue("YOUR CURRENT TEAM: " + nameTeam);
        String namePlayerLeft = this.teamModel.getPlayerLeft() != null ? this.teamModel.getPlayerLeft().getName() : " – –";
        this.playerLeftLabel.setValue("PLAYER LEFT: " + namePlayerLeft);
        String namePlayerRight = this.teamModel.getPlayerRight() != null ? this.teamModel.getPlayerRight().getName() : " – –";
        this.playerRightLabel.setValue("PLAYER RIGHT: "+ namePlayerRight);
    }

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

    public StringProperty getCurrentTeamLabel() {
        return this.currentTeamLabel;
    }
    public StringProperty getPlayerLeftLabel() {
        return this.playerLeftLabel;
    }
    public StringProperty getPlayerRightLabel(){
        return this.playerRightLabel;
    }
    public StringProperty getJoinTeamErrorLabelProperty() {
        return this.joinTeamErrorLabel;
    }
    public StringProperty getCreateTeamErrorLabelProperty() {
        return this.createTeamErrorLabel;
    }
    public BooleanProperty getReadyToGoVisibilityProperty() {
        return this.readyToGoLabel;
    }
    public BooleanProperty getTeamFormVisibilityProperty() {
        return this.teamFormVisibility;
    }
}
