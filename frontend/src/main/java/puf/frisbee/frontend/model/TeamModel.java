package puf.frisbee.frontend.model;

public class TeamModel implements Team {

	private int id;
	private String name;
	private Player playerLeft;
	private Player playerRight;
	private int lives;
	private int level;
	private int score;

	// TODO: use for model factory
	public TeamModel() {

	}

//	public TeamModel(int id, String name, Player playerLeft, Player playerRight, int level, int score, int lives) {
//		this.id = id;
//		this.teamName = name;
//		this.playerLeft = playerLeft;
//		this.playerRight = playerRight;
//		this.teamLevel = level;
//		this.teamScore = score;
//		this.teamLives = lives;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getPlayerLeft() {
		return playerLeft;
	}

	public void setPlayerLeft(Player playerLeft) {
		this.playerLeft = playerLeft;
	}

	public Player getPlayerRight() {
		return playerRight;
	}

	public void setPlayerRight(Player playerRight) {
		this.playerRight = playerRight;
	}

	@Override
	public int getLives() {
		return this.lives;
	}

	@Override
	public void setLives(int lives) {
		this.lives = lives;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public void setScore(int score) {
		this.score = score;
	}

	public boolean createTeam(String teamName) {
		// create team in backend
		this.name = teamName;
		return true;
	}


	public boolean joinTeam(Player player, String teamName) {
		// get team data from backend
		// save current player in team at backend
		this.name = teamName;
		return true;
	}

}
