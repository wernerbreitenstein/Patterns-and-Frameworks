package puf.frisbee.frontend.core;

import puf.frisbee.frontend.viewmodel.GameViewModel;
import puf.frisbee.frontend.viewmodel.HighscoreViewModel;

public class ViewModelFactory {
	private ModelFactory modelFactory;

	public ViewModelFactory(ModelFactory modelFactory) {
		this.modelFactory = modelFactory;
	}

	/**
	 * Creates and returns an instance of GameViewModel.
	 * 
	 * @return a new instance of GameViewModel
	 */
	public GameViewModel getGameViewModel() {
		return new GameViewModel(modelFactory.getGameModel(), modelFactory.getLevelModel());
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
