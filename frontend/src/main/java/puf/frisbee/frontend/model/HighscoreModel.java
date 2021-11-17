package puf.frisbee.frontend.model;

import java.util.ArrayList;
import java.util.Random;

public class HighscoreModel {
    // TODO: get all data from backend
    public ArrayList<TeamModel> getTeams() {
        ArrayList<TeamModel> teams = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String name = "Team #" + random.nextInt(10);
            TeamModel team = new TeamModel(name, random.nextInt(15), random.nextInt(200));
            teams.add(team);
        }

        return teams;
    }
}
