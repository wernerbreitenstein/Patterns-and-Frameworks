package puf.frisbee.frontend.model;

public class TeamModel implements Team {
	private String teamName;
	private int teamLives = 5;
	private int teamLevel;
	private int teamScore = 0;
	private Player player1;
	private Player player2;

	public TeamModel(String teamName, int teamLevel, int teamScore) {
		this.teamName = teamName;
		this.teamLevel = teamLevel;
		this.teamScore = teamScore;
	}

	@Override
	public String getTeamName() {
		return this.teamName;
	}

	@Override
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
		return this.teamLevel;
	}
	
	@Override
	public void setTeamLevel(int level) {
		this.teamLevel = level;
	}

	@Override
	public int getTeamScore() {
		return this.teamScore;
	}

	@Override
	public void setTeamScore(int score) {
		this.teamScore = score;
	}


	public boolean joinTeam(Player player2, String teamName){
		this.player2 = player2;
		return true;
	}

	public boolean createTeam(Player player1, String teamName){
		this.player1 = player1;
		this.teamName = teamName;
		return true;
	}
}
