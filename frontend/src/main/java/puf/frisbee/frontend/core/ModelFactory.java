package puf.frisbee.frontend.core;

import puf.frisbee.frontend.model.LevelModel;

public class ModelFactory {
    private LevelModel levelModel;

    /**
     * Creates a LevelModel instance if it does not exist yet and returns it.
     * @return instance of LevelModel
     */
    public LevelModel getLevelModel() {
        if (levelModel == null) {
            levelModel = new LevelModel();
        }

        return levelModel;
    }
}
