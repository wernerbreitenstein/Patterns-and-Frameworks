package puf.frisbee.frontend.core;

import puf.frisbee.frontend.viewmodel.*;

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
		return new GameViewModel(modelFactory.getGameModel(), modelFactory.getLevelModel(), modelFactory.getTeamModel());
	}

	/**
	 * Creates and returns an instance of StartViewModel.
	 *
	 * @return a new instance of StartViewModel
	 */
	public StartViewModel getStartViewModel() {
		return new StartViewModel(modelFactory.getGameModel(), modelFactory.getLevelModel(), modelFactory.getTeamModel(), modelFactory.getHighscoreModel(), modelFactory.getPlayerModel());
	}

	/**
	 * Creates and returns an instance of RegistrationViewModel.
	 *
	 * @return a new instance of RegistrationViewModel
	 */
	public RegistrationLoginViewModel getRegistrationLoginViewModel() {
		return new RegistrationLoginViewModel(modelFactory.getPlayerModel(), modelFactory.getTeamModel());
	}

	/**
	 * Creates and returns an instance of ProfileViewModel.
	 *
	 * @return a new instance of ProfileViewModel
	 */
	public ProfileViewModel getProfileViewModel() {
		return new ProfileViewModel(modelFactory.getPlayerModel());
	}

	/**
	 * Creates and returns an instance of TeamViewModel.
	 *
	 * @return a new instance of TeamViewModel
	 */
	public TeamViewModel getTeamViewModel() {
		return new TeamViewModel(modelFactory.getTeamModel(), modelFactory.getPlayerModel());
	}

	/**
	 * Creates and returns an instance of WaitingViewModel.
	 *
	 * @return a new instance of WaitingViewModel
	 */
	public WaitingViewModel getWaitingViewModel() {
		return new WaitingViewModel(modelFactory.getGameModel(), modelFactory.getLevelModel(), modelFactory.getTeamModel());
	}
}
