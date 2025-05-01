package org.example.grade_predictor.model.client;

import org.example.grade_predictor.client.OllamaClient;
import org.example.grade_predictor.config.OllamaConfig;
import org.example.grade_predictor.dto.OllamaRequest;
import org.example.grade_predictor.dto.OllamaResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class OllamaClientTest {
    private OllamaClient client;

    @BeforeEach
    void setUp() {
        OllamaConfig config = new OllamaConfig();
        client = new OllamaClient(config);
    }

    @Test
    void testStrawberry() {
        OllamaRequest request = new OllamaRequest("how many 'r's are there in strawberry?");

        OllamaResponse response = null;
        try {
            response = client.sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.getResponse());
    }
}
