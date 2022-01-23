package puf.frisbee.frontend.core;

import puf.frisbee.frontend.viewmodel.*;

public class ViewModelFactory {
    /**
     * The model factory instance.
     */
    private final ModelFactory modelFactory;

    /**
     * Constructs the view model factory and sets the model factory instance.
     *
     * @param modelFactory model factory instance
     */
    public ViewModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    /**
     * Creates and returns an instance of GameViewModel.
     *
     * @return a new instance of GameViewModel
     */
    public GameViewModel getGameViewModel() {
        return new GameViewModel(modelFactory.getGameModel(),
                modelFactory.getLevelModel(), modelFactory.getTeamModel(),
                modelFactory.getCharacterModel());
    }

    /**
     * Creates and returns an instance of StartViewModel.
     *
     * @return a new instance of StartViewModel
     */
    public StartViewModel getStartViewModel() {
        return new StartViewModel(modelFactory.getGameModel(),
                modelFactory.getTeamModel(), modelFactory.getHighscoreModel(),
                modelFactory.getPlayerModel());
    }

    /**
     * Creates and returns an instance of RegistrationLoginViewModel.
     *
     * @return a new instance of RegistrationLoginViewModel
     */
    public RegistrationLoginViewModel getRegistrationLoginViewModel() {
        return new RegistrationLoginViewModel(modelFactory.getPlayerModel(),
                modelFactory.getTeamModel());
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
        return new TeamViewModel(modelFactory.getTeamModel(),
                modelFactory.getPlayerModel());
    }

    /**
     * Creates and returns an instance of WaitingViewModel.
     *
     * @return a new instance of WaitingViewModel
     */
    public WaitingViewModel getWaitingViewModel() {
        return new WaitingViewModel(modelFactory.getGameModel(),
                modelFactory.getTeamModel(), modelFactory.getCharacterModel());
    }

    /**
     * Creates and returns an instance of BackgroundImageViewModel.
     *
     * @return a new instance of BackgroundImageViewModel
     */
    public BackgroundImageViewModel getBackgroundImageViewModel() {
        return new BackgroundImageViewModel(
                modelFactory.getTeamModel());
    }
}
