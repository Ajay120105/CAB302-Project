package org.example.grade_predictor.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.grade_predictor.config.OllamaConfig;
import org.example.grade_predictor.dto.OllamaRequestDTO;
import org.example.grade_predictor.dto.OllamaResponseDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class OllamaClient {
    private final OllamaConfig config;

    private final String apiGenerate;

    public OllamaClient(OllamaConfig config) {
        this.config = config;
        this.apiGenerate = config.getHost() + "/api/generate";
    }

    public OllamaResponseDTO sendRequest(OllamaRequestDTO request) throws IOException {
        //Ollama may throw 404 for not indicating a model
        request.setModel(this.config.getModel());

        URL address = URI.create(this.apiGenerate).toURL();
        HttpURLConnection connection = (HttpURLConnection) address.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        //create request body using Gson
        Gson gson = new Gson();
        String requestBody = gson.toJson(request);
        connection.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));

        String results = "";
        if (connection.getResponseCode() == 200) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuilder fullResponse = new StringBuilder();
                System.out.println("Ollama Response:");

                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        try {
                            JsonObject jsonObject = JsonParser.parseString(line).getAsJsonObject();
                            if (jsonObject.has("response")) {
                                String responsePart = jsonObject.get("response").getAsString();
                                System.out.print(responsePart);
                                fullResponse.append(responsePart);
                            }
                        } catch (Exception e) {
                            System.err.println("Error parsing JSON: " + e.getMessage());
                        }
                    }
                }
                results = fullResponse.toString();
            }
        } else {
            System.err.println("Error: " + connection.getResponseCode() + " " + connection.getResponseMessage() + " " + connection.getURL());
        }

        connection.disconnect();

        return new OllamaResponseDTO(results);
    }
}
