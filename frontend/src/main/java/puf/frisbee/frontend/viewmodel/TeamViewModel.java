package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import puf.frisbee.frontend.model.Player;
import puf.frisbee.frontend.model.Team;


public class TeamViewModel {
    private Team teamModel;
    private final Player playerModel;

    private String currentTeamLabel;
    private String player1Label = "Bud Spencer";
    private String player2Label = "Terence Hill";

    private StringProperty joinTeamErrorLabel;
    private StringProperty createTeamErrorLabel;


    public TeamViewModel(Team teamModel, Player playerModel) {
        this.teamModel = teamModel;
        this.playerModel = playerModel;

        this.currentTeamLabel = teamModel.getName();

        this.joinTeamErrorLabel = new SimpleStringProperty();
        this.createTeamErrorLabel = new SimpleStringProperty();
    }

    public boolean joinTeam(String teamName) {
        if (teamName.length() < 1) {
            this.joinTeamErrorLabel.setValue("Team name is required.");
            return false;
        }

        boolean joinTeamSuccessful = this.teamModel.joinTeam(this.playerModel, teamName);

        if (joinTeamSuccessful){
            this.currentTeamLabel = this.teamModel.getName();
            return true;
        } else {
            this.joinTeamErrorLabel.setValue("Team couldn't be joined.");
            return false;
        }
    }

    public boolean createTeam(String teamName) {
        if (teamName.length() < 1) {
            this.createTeamErrorLabel.setValue("Team name is required.");
            return false;
        }
        boolean createTeamSuccessful = this.teamModel.createTeam(teamName);
        boolean joinTeamSuccessful = this.teamModel.joinTeam(playerModel, teamName);

        if (createTeamSuccessful && joinTeamSuccessful){
            this.currentTeamLabel = this.teamModel.getName();
            return true;
        } else {
            this.joinTeamErrorLabel.setValue("Team couldn't be created.");
            return false;
        }
    }

    public String getCurrentTeamLabel() { return this.currentTeamLabel;}

    public String getPlayer1Label() {
        return this.player1Label;
    }
    public String getPlayer2Label(){
        return this.player2Label;
    }

    public StringProperty getJoinTeamErrorLabelProperty() {
        return this.joinTeamErrorLabel;
    }

    public StringProperty getCreateTeamErrorLabelProperty() {
        return this.createTeamErrorLabel;
    }
}
