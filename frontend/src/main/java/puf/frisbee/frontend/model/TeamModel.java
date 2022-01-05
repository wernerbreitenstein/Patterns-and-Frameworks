package puf.frisbee.frontend.model;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TeamModel implements Team {
	private final String baseUrl;

	private int id;
	private String name;
	private Player playerLeft;
	private Player playerRight;
	private int lives;
	private int level;
	private int score;

	public TeamModel() {
		// initialize base url for requests
		Dotenv dotenv = Dotenv.load();
		this.baseUrl = dotenv.get("BACKEND_BASE_URL");

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
	public boolean createTeam(String teamName) throws IllegalArgumentException {
		// create team in backend
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(this.baseUrl + "/teams/create"))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(teamName))
					.build();

			HttpResponse<String> response = HttpClient
					.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 201) {
				return false;
			}

			if (response.statusCode() == 400) {
				throw new IllegalArgumentException(response.body().toString());
			}
		} catch(IllegalArgumentException e) {
			// forward because we need the message for the error label
			throw e;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return false;
	}

	@Override
	public boolean joinTeam(Player player, String teamName) {
		// get team data from backend
		// save current player in team at backend
		this.name = teamName;
		return true;
	}

}
