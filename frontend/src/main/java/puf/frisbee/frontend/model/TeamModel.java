package puf.frisbee.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;

public class TeamModel implements Team {
	private final String baseUrl;
	@JsonIgnore
	private boolean teamIsSet;
	@JsonIgnore
	private CharacterType ownCharacterType;

	private int id;
	private String name;
	private Player playerLeft;
	private Player playerRight;
	private int lives;
	private int level;
	private int score;
	private boolean active;

	public TeamModel() {
		// initialize base url for requests
		Dotenv dotenv = Dotenv.load();
		this.baseUrl = dotenv.get("BACKEND_BASE_URL");
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
	public String getBackgroundImageForLevel(int level) { return "/puf/frisbee/frontend/images/level" + level + "_bg.png"; }

	@Override
	public boolean getActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean isTeamSet() {
		return this.teamIsSet;
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
				return true;
			}

			if (response.statusCode() == 400) {
				throw new IllegalArgumentException(response.body().toString());
			}
		} catch(IllegalArgumentException e) {
			this.teamIsSet = false;
			// forward because we need the message for the error label
			throw e;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

		this.teamIsSet = false;
		return false;
	}

	@Override
	public boolean joinTeam(Player player, String teamName) {
		// join team in backend
		try {
			String requestBody = "{\"teamName\":\"" + teamName + "\",\"playerEmail\":\"" + player.getEmail()  +"\"}";

			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(this.baseUrl + "/teams/join"))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();

			HttpResponse<String> response = HttpClient
					.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 201) {
				ObjectMapper objectMapper = new ObjectMapper();
				Team joinedTeam = objectMapper.readValue(response.body(), new TypeReference<>() {});
				setTeamData(joinedTeam);

				// also set own character type for the game later, depending on the position in the team
				this.ownCharacterType = joinedTeam.getPlayerLeft().equals(player) ? CharacterType.LEFT : CharacterType.RIGHT;

				return true;
			}

			if (response.statusCode() == 400) {
				throw new IllegalArgumentException(response.body().toString());
			}
		} catch(IllegalArgumentException e) {
			this.teamIsSet = false;
			// forward because we need the message for the error label
			throw e;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

		this.teamIsSet = false;
		return false;
	}

	public boolean getTeamForPlayer(Player player) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(this.baseUrl + "/teams/player/" + player.getEmail() + "/active"))
					.GET()
					.build();

			HttpResponse<String> response = HttpClient
					.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				ObjectMapper objectMapper = new ObjectMapper();
				ArrayList<Team> teams = objectMapper.readValue(response.body(), new TypeReference<>() {});
				// set data of first found team for this player after sorting alphabetically
				teams.sort(Comparator.comparing(Team::getName));
				setTeamData(teams.get(0));

				// also set own character type for the game later, depending on the position in the team
				this.ownCharacterType = teams.get(0).getPlayerLeft().equals(player) ? CharacterType.LEFT : CharacterType.RIGHT;

				return true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		this.teamIsSet = false;
		return false;
	}

	@Override
	public boolean saveTeamData() {
		try {
			String requestBody = "{\"name\":\"" + this.name
					+ "\",\"level\":" + this.level
					+ ", \"score\":" + this.score
					+ ", \"lives\":" + this.lives
					+ ", \"active\":" + this.active  + "}";

			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(this.baseUrl + "/teams/update"))
					.header("Content-Type", "application/json")
					.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();

			HttpResponse<String> response = HttpClient
					.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 201) {
				ObjectMapper objectMapper = new ObjectMapper();
				Team updatedTeam = objectMapper.readValue(response.body(), new TypeReference<>() {});
				// set data to be sure to have the right data
				setTeamData(updatedTeam);
				return true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	@JsonIgnore
	@Override
	public void resetTeamData() {
		this.id = 0;
		this.name = "";
		this.playerLeft = null;
		this.playerRight = null;
		this.lives = 0;
		this.score = 0;
		this.level = 0;
		this.active = false;

		this.teamIsSet = false;
	}

	@JsonIgnore
	@Override
	public void reloadTeamData() {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(this.baseUrl + "/teams/" + this.name))
					.GET()
					.build();

			HttpResponse<String> response = HttpClient
					.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				ObjectMapper objectMapper = new ObjectMapper();
				Team team = objectMapper.readValue(response.body(), new TypeReference<>() {});
				setTeamData(team);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@JsonIgnore
	private void setTeamData(Team team) {
		this.id = team.getId();
		this.name = team.getName();
		this.playerLeft = team.getPlayerLeft();
		this.playerRight = team.getPlayerRight();
		this.lives = team.getLives();
		this.score = team.getScore();
		this.level = team.getLevel();
		this.active = team.getActive();

		this.teamIsSet = true;
	}

	@JsonIgnore
	@Override
	public CharacterType getOwnCharacterType() {
		return this.ownCharacterType;
	}
}
