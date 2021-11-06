package puf.frisbee.frontend.core;

import puf.frisbee.frontend.viewmodel.LevelViewModel;
import puf.frisbee.frontend.viewmodel.WaitingViewModel;

public class ViewModelFactory {
    private ModelFactory modelFactory;

    public ViewModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    /**
     * Creates and returns an instance of LevelViewModel.
     * @return a new instance of LevelViewModel
     */
    public LevelViewModel getLevelViewModel() {
        return new LevelViewModel(modelFactory.getLevelModel());
    }

    public WaitingViewModel getWaitingViewModel() { return new WaitingViewModel(); }
}
