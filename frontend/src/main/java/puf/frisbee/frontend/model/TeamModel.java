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

	@Override
	public String getPlayer1() {
		if (this.player1 != null) {
			return this.player1.getName();
		} else {
			return "---";
		}
	}

	@Override
	public String getPlayer2() {
		if (this.player2 != null) {
			return this.player2.getName();
		} else {
			return "---";
		}
	}


	public boolean joinTeam(Player player, String teamName){
		if (teamName == this.teamName){
			if (this.player1 == null && this.player2 == null){
				this.player1 = player;
			} else if (this.player1 != null && this.player2 == null){
				this.player2 = player;
			}else{
				System.out.println("Team already has two members.");
				return false;
			}
		} else{
			System.out.println("Team doesn't exist.");
			return false;
		}
		return true;
	}

	public boolean createTeam(String teamName){
		this.teamName = teamName;
		return true;
	}
}
