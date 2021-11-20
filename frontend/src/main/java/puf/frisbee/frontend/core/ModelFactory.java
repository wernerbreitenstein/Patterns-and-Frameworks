package puf.frisbee.frontend.core;

import puf.frisbee.frontend.model.*;

public class ModelFactory {
	private Level levelModel;
	private Highscore highscoreModel;
	private Game gameModel;

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
}
