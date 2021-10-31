package puf.frisbee.frontend.core;

import puf.frisbee.frontend.model.LevelModel;
import puf.frisbee.frontend.model.WaitingModel;

public class ModelFactory {
    private LevelModel levelModel;
    private WaitingModel waitingModel;

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

    public WaitingModel getWaitingModel() {
        if (waitingModel == null) {
            waitingModel = new WaitingModel();
        }

        return waitingModel;
    }
}
