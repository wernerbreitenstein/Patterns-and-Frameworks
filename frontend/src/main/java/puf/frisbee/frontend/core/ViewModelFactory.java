package puf.frisbee.frontend.core;

import puf.frisbee.frontend.viewmodel.HighscoreViewModel;
import puf.frisbee.frontend.viewmodel.LevelViewModel;

public class ViewModelFactory {
	private ModelFactory modelFactory;

	public ViewModelFactory(ModelFactory modelFactory) {
		this.modelFactory = modelFactory;
	}

	/**
	 * Creates and returns an instance of LevelViewModel.
	 * 
	 * @return a new instance of LevelViewModel
	 */
	public LevelViewModel getLevelViewModel() {
		return new LevelViewModel(modelFactory.getLevelModel());
	}

	/**
	 * Creates and returns an instance of HighscoreViewModel.
	 *
	 * @return a new instance of HighscoreViewModel
	 */
	public HighscoreViewModel getHighscoreViewModel() {
		return new HighscoreViewModel(modelFactory.getHighscoreModel());
	}
}
