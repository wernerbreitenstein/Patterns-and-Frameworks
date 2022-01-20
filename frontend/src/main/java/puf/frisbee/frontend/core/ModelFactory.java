package puf.frisbee.frontend.core;

import puf.frisbee.frontend.model.*;

public class ModelFactory {

	private Game gameModel;
	private Level levelModel;
	private Team teamModel;
	private Highscore highscoreModel;
	private Player playerModel;

	/**
	 * Creates a GameModel instance if it does not exist yet and returns it.
	 *
	 * @return instance of GameModel
	 */
	public Game getGameModel() {
		if (gameModel == null) {
			gameModel = new GameModel();
		}
		return gameModel;
	}

	/**
	 * Creates a LevelModel instance if it does not exist yet and returns it.
	 * 
	 * @return instance of LevelModel
	 */
	public Level getLevelModel() {
		if (levelModel == null) {
			levelModel = new LevelModel();
		}
		return levelModel;
	}

	/**
	 * Creates a TeamModel instance if it does not exist yet and returns it.
	 *
	 * @return instance of TeamModel
	 */
	public Team getTeamModel() {
		if (teamModel == null) {
			teamModel = new TeamModel();
		}
		return teamModel;
	}

	/**
	 * Creates a HighscoreModel instance if it does not exist yet and returns it.
	 *
	 * @return instance of HighscoreModel
	 */
	public Highscore getHighscoreModel() {
		if (highscoreModel == null) {
			highscoreModel = new HighscoreModel();
		}
		return highscoreModel;
	}

	/**
	 * Creates a PlayerModel instance if it does not exist yet and returns it.
	 *
	 * @return instance of PlayerModel
	 */
	public Player getPlayerModel() {
		if (playerModel == null) {
			playerModel = new PlayerModel();
		}

		return playerModel;
	}
}
