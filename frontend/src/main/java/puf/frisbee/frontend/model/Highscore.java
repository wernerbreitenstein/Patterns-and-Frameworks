package puf.frisbee.frontend.model;

import java.util.ArrayList;

/**
 * Contains all highscore specific data.
 */
public interface Highscore {
    /**
     * Returns all teams for the highscore list.
     * @return Team Objects
     */
    ArrayList<Team> getTeams();
}
