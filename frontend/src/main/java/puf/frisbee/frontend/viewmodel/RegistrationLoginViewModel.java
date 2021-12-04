package puf.frisbee.frontend.viewmodel;

import puf.frisbee.frontend.model.Player;

public class RegistrationLoginViewModel {
    private Player playerModel;

    public RegistrationLoginViewModel(Player playerModel) {
        this.playerModel = playerModel;
    }

    public void login() {
        this.playerModel.setLoginStatus(true);
    }
}
