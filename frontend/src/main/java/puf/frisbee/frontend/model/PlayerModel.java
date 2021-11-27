package puf.frisbee.frontend.model;

public class PlayerModel implements Player {
    // TODO: get this from server
    private String playerName = "Player 1";
    private boolean isLoggedIn = false;

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public boolean isLoggedIn() {
        return this.isLoggedIn;
    };

    @Override
    public void setLoginStatus(boolean status) {
        this.isLoggedIn = status;
    }
}
