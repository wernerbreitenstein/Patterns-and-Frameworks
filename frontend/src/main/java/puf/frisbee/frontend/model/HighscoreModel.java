package puf.frisbee.frontend.model;

import java.util.ArrayList;
import java.util.Random;

public class HighscoreModel {
    // TODO: get all data from backend
    public ArrayList<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String name = "Team #" + random.nextInt(10);
            Team team = new Team(name, random.nextInt(15), random.nextInt(200));
            teams.add(team);
        }

        return teams;
    }
}
