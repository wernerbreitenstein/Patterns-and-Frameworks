package puf.frisbee.frontend.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;


public class PlayerModel implements Player {
    private String baseUrl;
    private String name;
    private String email;
    private String password;
    private boolean isLoggedIn = false;

    public PlayerModel() {
        // initialize base url for requests
        Dotenv dotenv = Dotenv.load();
        this.baseUrl = dotenv.get("BACKEND_BASE_URL");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isLoggedIn() { return this.isLoggedIn; }

    @Override
    public void setLoginStatus(boolean status) {
        this.isLoggedIn = status;
    }

    @Override
    public boolean register(String name, String email, String password){
        try {
            this.name = name;
            this.email = email;
            this.password = password;
            this.isLoggedIn = true;

            ObjectMapper objectMapper = new ObjectMapper();
            String playerJson = objectMapper.writeValueAsString(this);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.baseUrl + "/players/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(playerJson))
                    .build();

            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 201;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean login(String email, String password) {

        try {
            String loginCredentials = "{\"email\":\"" + email + "\",\"password\":\"" + password  +"\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.baseUrl + "/players/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(loginCredentials))
                    .build();

            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200){

                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> map = objectMapper.readValue(response.body(), Map.class);

                this.name = map.get("name");
                this.email = map.get("email");
                this.isLoggedIn = true;
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateName(String name) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.baseUrl + "/players/update-player-name/" + this.email))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(name))
                    .build();

            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                this.name = name;
            }

            return response.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updatePassword(String password) {
        this.password = password;
        return false;
    }
}
