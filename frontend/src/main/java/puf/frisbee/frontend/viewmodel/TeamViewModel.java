package puf.frisbee.frontend.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import puf.frisbee.frontend.model.Player;
import puf.frisbee.frontend.model.Team;

public class TeamViewModel {
    private final Team teamModel;
    private final Player playerModel;

    private StringProperty joinTeamErrorLabel;
    private StringProperty createTeamErrorLabel;


    public TeamViewModel(Team teamModel, Player playerModel) {
        this.teamModel = teamModel;
        this.playerModel = playerModel;

        this.joinTeamErrorLabel = new SimpleStringProperty();
        this.createTeamErrorLabel = new SimpleStringProperty();
    }

    public boolean joinTeam(String teamName) {
        if (teamName.length() < 1) {
            this.createTeamErrorLabel.setValue("Team name is required.");
            return false;
        }

        boolean joinTeamSuccessful = this.teamModel.joinTeam(this.playerModel, teamName);

        return joinTeamSuccessful;
    }

    public boolean createTeam(String teamName) {
        if (teamName.length() < 1) {
            this.joinTeamErrorLabel.setValue("Team name is required.");
            return false;
        }

        boolean createTeamSuccessful = this.teamModel.createTeam(this.playerModel, teamName);

        return createTeamSuccessful;
    }


    public StringProperty getJoinTeamErrorLabelProperty() {
        return this.joinTeamErrorLabel;
    }

    public StringProperty getCreateTeamErrorLabelProperty() {
        return this.createTeamErrorLabel;
    }
}
