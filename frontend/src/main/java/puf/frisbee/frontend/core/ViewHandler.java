package puf.frisbee.frontend.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import puf.frisbee.frontend.view.*;

public class ViewHandler {
    /**
     * The instance of the stage.
     */
    private final Stage stage;
    /**
     * The instance of the view model factory.
     */
    private final ViewModelFactory viewModelFactory;

    /**
     * Constructs the view handler and sets the view model factory instance.
     *
     * @param viewModelFactory the view model instance
     */
    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.stage = new Stage();
        this.viewModelFactory = viewModelFactory;
    }

    /**
     * Opens the start view and shows the stage.
     */
    public void start() {
        openStartView();
        this.stage.show();
    }

    /**
     * Closes the stage.
     */
    public void end() {
        this.stage.close();
    }

    /**
     * Loads the view for the game.
     */
    public void openGameView() {
        FXMLLoader gameViewLoader = new FXMLLoader();
        gameViewLoader.setLocation(getClass().getResource("/puf/frisbee"
                + "/frontend/view/GameView.fxml"));
        try {
            Parent root = gameViewLoader.load();
            GameView gameView = gameViewLoader.getController();
            gameView.init(viewModelFactory.getGameViewModel(), this);
            this.stage.setTitle("FRIZZBEE FREAKZ | GAME");
            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            this.stage.setScene(scene);
            root.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the view for the waiting screen.
     */
    public void openWaitingView() {
        FXMLLoader waitingViewLoader = new FXMLLoader();
        waitingViewLoader.setLocation(getClass().getResource("/puf/frisbee"
                + "/frontend/view/WaitingView.fxml"));
        try {
            Parent root = waitingViewLoader.load();
            WaitingView waitingView = waitingViewLoader.getController();
            waitingView.init(viewModelFactory.getWaitingViewModel(), this);
            this.stage.setTitle("FRIZZBEE FREAKZ | WAITING");
            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            this.stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the view for the start screen.
     */
    public void openStartView() {
        FXMLLoader startViewLoader = new FXMLLoader();
        startViewLoader.setLocation(getClass().getResource("/puf/frisbee"
                + "/frontend/view/StartView.fxml"));
        try {
            Parent root = startViewLoader.load();
            StartView startView = startViewLoader.getController();
            startView.init(viewModelFactory.getStartViewModel(), this);
            this.stage.setTitle("FRIZZBEE FREAKZ | START");
            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            this.stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the view for the registration and login screen.
     */
    public void openRegistrationLoginView() {
        FXMLLoader registrationLoginLoader = new FXMLLoader();
        registrationLoginLoader.setLocation(getClass().getResource("/puf"
                + "/frisbee/frontend/view/RegistrationLoginView.fxml"));
        try {
            Parent root = registrationLoginLoader.load();
            RegistrationLoginView registrationLoginView =
                    registrationLoginLoader.getController();
            registrationLoginView.init(
                    viewModelFactory.getRegistrationLoginViewModel(), this);
            this.stage.setTitle("FRIZZBEE FREAKZ | LOGIN | REGISTRATION");
            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            this.stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the view for the profile screen.
     */
    public void openProfileView() {
        FXMLLoader profileLoader = new FXMLLoader();
        profileLoader.setLocation(getClass().getResource("/puf/frisbee"
                + "/frontend/view/ProfileView.fxml"));
        try {
            Parent root = profileLoader.load();
            ProfileView profileView = profileLoader.getController();
            profileView.init(viewModelFactory.getProfileViewModel(), this);
            this.stage.setTitle("FRIZZBEE FREAKZ | PROFILE");
            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            this.stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the view for the team screen.
     */
    public void openTeamView() {
        FXMLLoader teamViewLoader = new FXMLLoader();
        teamViewLoader.setLocation(getClass().getResource("/puf/frisbee"
                + "/frontend/view/TeamView.fxml"));
        try {
            Parent root = teamViewLoader.load();
            TeamView teamView = teamViewLoader.getController();
            teamView.init(viewModelFactory.getTeamViewModel(), this);
            this.stage.setTitle("FRIZZBEE FREAKZ | TEAM");
            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            this.stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
