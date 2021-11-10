package puf.frisbee.frontend.model;

public class Team {
    private String name;
    private int level;
    private int score;

    public Team(String name, int level, int score) {
        this.name = name;
        this.level = level;
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }
}
