package puf.frisbee.backend;

public class Team {
    private String teamName;
    private int teamLevel;
    private int teamScore;

    public Team(String teamName, int teamLevel, int teamScore) {
        this.teamName = teamName;
        this.teamLevel = teamLevel;
        this.teamScore = teamScore;
    }

    public String getName() {
        return this.teamName;
    }

    public int getLevel() {
        return this.teamLevel;
    }

    public int getScore() {
        return this.teamScore;
    }
}
