package puf.frisbee.frontend.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

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
        return email;
    }

    @Override
    public String getPassword() { return password; }

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
//                    .uri(new URI("https://puf-frisbee-backend.herokuapp.com/api/players/register"))
                    .uri(new URI("http://localhost:8080/api/players/register"))
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
//                    .uri(new URI("https://puf-frisbee-backend.herokuapp.com/api/players/login"))
                    .uri(new URI("http://localhost:8080/api/players/login"))
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
            this.name = name;

            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(new URI("https://puf-frisbee-backend.herokuapp.com/api/players/register"))
                    .uri(new URI("http://localhost:8080/api/players/update-player-name/" + this.email))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(this.name))
                    .build();

            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

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
