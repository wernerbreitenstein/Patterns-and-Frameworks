package puf.frisbee.frontend.model;

import puf.frisbee.frontend.core.Constants;

public class TeamModel implements Team {
	private String teamName;
	private int teamLives;
	private int teamLevel;
	private int teamScore;

	public TeamModel(String teamName, int teamLevel, int teamScore) {
		this.teamName = teamName;
		this.teamLives = Constants.TEAM_LIVES;
		this.teamLevel = teamLevel;
		this.teamScore = teamScore;
	}

	@Override
	public String getTeamName() {
		return teamName;
	}

	@Override
	public int getTeamLives() {
		return this.teamLives;
	}

	@Override
	public void setTeamLives(int lives) {
		this.teamLives = lives;
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
