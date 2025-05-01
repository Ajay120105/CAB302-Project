package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: put prompt in args");
            return;
        }

        String prompt = "";
        //combine all args into prompt
        for (String arg : args) {
            prompt += " " + arg;
        }

        String model = "deepseek-r1:14b-qwen-distill-q4_K_M";

        try {
            sendPromptToOllama(prompt, model);
        } catch (IOException e) {
            System.err.println("Error communicating with Ollama: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void sendPromptToOllama(String prompt, String model) throws IOException {
        URL url = URI.create(OLLAMA_API_URL).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        //cannot write to a URLConnection if doOutput=false - call setDoOutput(true)
        connection.setDoOutput(true);

        //create request body using Gson
        Gson gson = new Gson();
        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("model", model);
        requestJson.addProperty("prompt", prompt);
        requestJson.addProperty("stream", true);
        
        String requestBody = gson.toJson(requestJson);
        
        connection.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));

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
            }
        } else {
            System.err.println("Error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
        }

        connection.disconnect();
    }
}