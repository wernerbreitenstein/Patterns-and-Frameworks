package puf.frisbee.frontend.model;

public class TeamModel implements Team {
	private String teamName;
	private int teamLevel;
	private int teamScore;

	public TeamModel(String teamName, int teamLevel, int teamScore) {
		this.teamName = teamName;
		this.teamLevel = teamLevel;
		this.teamScore = teamScore;
	}

	@Override
	public String getTeamName() {
		return teamName;
	}

	@Override
	public int getTeamLevel() {
		return teamLevel;
	}

	@Override
	public int getTeamScore() {
		return teamScore;
	}

}
