package puf.frisbee.frontend.core;

import puf.frisbee.frontend.viewmodel.LevelViewModel;

public class ViewModelFactory {
    private ModelFactory modelFactory;

    public ViewModelFactory(ModelFactory modelFactory) {
        // TODO: use dependency injection later on
        this.modelFactory = modelFactory;
    }

    /**
     * Creates and returns an instance of LevelViewModel.
     * @return a new instance of LevelViewModel
     */
    public LevelViewModel getLevelViewModel() {
        return new LevelViewModel(modelFactory.getLevelModel());
    }
}
