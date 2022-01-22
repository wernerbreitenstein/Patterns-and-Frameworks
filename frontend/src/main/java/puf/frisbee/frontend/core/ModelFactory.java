package puf.frisbee.frontend.core;

import puf.frisbee.frontend.model.*;
import puf.frisbee.frontend.model.Character;
import puf.frisbee.frontend.network.SocketClientFactory;

public class ModelFactory {
    /**
     * The instance of the socket client factory.
     */
    private SocketClientFactory socketClientFactory;

    /**
     * The instance of the game model.
     */
    private Game gameModel;
    /**
     * The instance of the level model.
     */
    private Level levelModel;
    /**
     * The instance of the team model.
     */
    private Team teamModel;
    /**
     * The instance of the highscore model.
     */
    private Highscore highscoreModel;
    /**
     * The instance of the player model.
     */
    private Player playerModel;
    /**
     * The instance of the character model.
     */
    private Character characterModel;

    /**
     * Constructs the model factory and sets the socket client factory.
     *
     * @param socketClientFactory socket client facttory
     */
    public ModelFactory(SocketClientFactory socketClientFactory) {
        this.socketClientFactory = socketClientFactory;
    }

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
     * Creates a HighscoreModel instance if it does not exist yet and returns
     * it.
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

    /**
     * Creates a CharacterModel instance if it does not exist yet and returns
     * it.
     *
     * @return instance of CharacterModel
     */
    public Character getCharacterModel() {
        if (characterModel == null) {
            characterModel = new CharacterModel(
                    socketClientFactory.getSocketClient(),
                    this.getTeamModel());
        }

        return characterModel;
    }
}
