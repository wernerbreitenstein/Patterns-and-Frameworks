package puf.frisbee.frontend.model;

public class PlayerModel implements Player {
    // TODO: get this from server
    private String name;
    private String email;
    private String password;
    private boolean isLoggedIn = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean isLoggedIn() {
        return this.isLoggedIn;
    };

    @Override
    public void setLoginStatus(boolean status) {
        this.isLoggedIn = status;
    }

    @Override
    public boolean register(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

        return true;
    }

    @Override
    public boolean login(String email, String password) {
        return email.equals(this.email) && password.equals(this.password);
    }

    @Override
    public boolean updateName(String name) {
        this.name = name;
        return true;
    }

    @Override
    public boolean updatePassword(String password) {
        this.password = password;
        return false;
    }
}
