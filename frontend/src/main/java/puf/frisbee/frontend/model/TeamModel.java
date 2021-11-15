package puf.frisbee.frontend.model;

public class TeamModel implements Team {
	private String team;
	private int level;
	private int score;

	public TeamModel(String team, int level, int score) {
		this.team = team;
		this.level = level;
		this.score = score;
	}

	@Override
	public String getTeam() {
		return team;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public int getScore() {
		return score;
	}

}
