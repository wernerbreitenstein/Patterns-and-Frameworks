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

        try {
            this.teamModel.joinTeam(playerModel, teamName);
        } catch (IllegalArgumentException e) {
            this.joinTeamErrorLabel.setValue(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean createTeam(String teamName) {
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

        return true;
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
