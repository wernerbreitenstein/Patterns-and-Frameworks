package puf.frisbee.frontend.viewmodel;


import puf.frisbee.frontend.model.LevelModel;

public class WaitingViewModel {

    private LevelModel levelModel;

    public WaitingViewModel(LevelModel levelModel) {
        this.levelModel = levelModel;
    }

    public int getLevel() {
        return this.levelModel.getCurrentLevel();
    }
}