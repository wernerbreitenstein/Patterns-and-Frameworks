package puf.frisbee.frontend.model;

public class TeamModel implements Team {

	private int id;
	private String name;
	private Player playerLeft;
	private Player playerRight;
	private int lives;
	private int level;
	private int score;

	public TeamModel() {
		this.name = "---";
		this.level = 0;
		this.lives = 5;
		this.score = 0;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Player getPlayerLeft() {
		return playerLeft;
	}

	@Override
	public void setPlayerLeft(Player playerLeft) {
		this.playerLeft = playerLeft;
	}

	@Override
	public Player getPlayerRight() {
		return playerRight;
	}

	@Override
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

	@Override
	public boolean createTeam(String teamName) {
		// create team in backend
		this.name = teamName;
		return true;
	}

	@Override
	public boolean joinTeam(Player player, String teamName) {
		// get team data from backend
		// save current player in team at backend
		this.name = teamName;
		return true;
	}

}
