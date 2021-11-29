package puf.frisbee.frontend.model;

import java.util.ArrayList;
import java.util.Random;

public class HighscoreModel implements Highscore {
    // TODO: get all data from backend
    @Override
    public ArrayList<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            String name = "Team #" + random.nextInt(10);
            Team team = new TeamModel(name, random.nextInt(15), random.nextInt(200));
            teams.add(team);
        }

        return teams;
    }
}
