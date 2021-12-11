package puf.frisbee.frontend.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PlayerModel implements Player {

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
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
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
    public boolean register(String name, String email, String password) throws URISyntaxException, IOException, InterruptedException {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isLoggedIn = true;


        ObjectMapper objectMapper = new ObjectMapper();
        String playerJson = objectMapper.writeValueAsString(this);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://puf-frisbee-backend.herokuapp.com/api/players/register"))
//                .uri(new URI("http://localhost:8080/api/players/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(playerJson))
                .build();

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());


        System.out.println(response.statusCode());
        System.out.println(response.body());


        return response.statusCode() == 201 ? true : false;
    }

    @Override
    public boolean login(String email, String password) {
        return email.equals(this.email) && password.equals(this.password);
    }
}
