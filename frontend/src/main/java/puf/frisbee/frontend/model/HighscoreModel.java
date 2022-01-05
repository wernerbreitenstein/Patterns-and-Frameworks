package puf.frisbee.frontend.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

public class HighscoreModel implements Highscore {
    String baseUrl;

    public HighscoreModel() {
        // initialize base url for requests
        Dotenv dotenv = Dotenv.load();
        this.baseUrl = dotenv.get("BACKEND_BASE_URL");
    }

    @Override
    public ArrayList<Team> getHighscoreData() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.baseUrl + "/teams"))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                // create team objects on the fly and return them
                return objectMapper.readValue(response.body(), new TypeReference<>() {
                });
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // if something goes wrong, return empty list
        return new ArrayList<>();
    }
}
