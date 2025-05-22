package org.example.grade_predictor.model.client;

import org.example.grade_predictor.client.OllamaClient;
import org.example.grade_predictor.config.OllamaConfig;
import org.example.grade_predictor.dto.OllamaRequestDTO;
import org.example.grade_predictor.dto.OllamaResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class OllamaClientTest {
    private OllamaClient client;

    @BeforeEach
    void setUp() {
        OllamaConfig config = new OllamaConfig();
        client = new OllamaClient(config);
    }

    // This test current should not be leading false maybe
    // Due to CI environment
    /*@Test
    void testRequesting_Success() {
        OllamaRequestDTO request = new OllamaRequestDTO("What's the answer of 1+1?");
        request.setStream(true);
        request.setThinking(false);

        OllamaResponseDTO response = null;
        try {
            response = client.sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.getResponse());
    }*/
}
