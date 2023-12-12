package com.plantcare.serverapplication.firebase;

import com.plantcare.serverapplication.shared.ArduinoBoardData;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class FirebaseRestClient {

    private static final String baseUrl = "https://plantcare-4b96e-default-rtdb.asia-southeast1.firebasedatabase.app/farm/";
    private static final String urlSuffix = ".json";
    private final RestTemplate restTemplate;

    public FirebaseRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void addArduinoBoardToFirebaseDb(int farmId, int arduinoBoardId) {

        ArduinoBoardData arduinoBoardData = this.initializeArduinoBoardData();

        this.restTemplate.postForObject(baseUrl + farmId + "/arduinoBoard" + arduinoBoardId + urlSuffix,
                new HttpEntity<>(arduinoBoardData),
                String.class
        );
    }

    public void deleteArduinoBoardFromFirebaseDb(int farmId, int arduinoBoardId) {

        ArduinoBoardData arduinoBoardData = this.initializeArduinoBoardData();

        this.restTemplate.delete(baseUrl + farmId + "/arduinoBoard" + arduinoBoardId + urlSuffix);
    }


    private ArduinoBoardData initializeArduinoBoardData() {

        return ArduinoBoardData
                .builder()
                .currentHumidity(80.0)
                .currentTds(500.0)
                .currentTemperature(30.0)
                .currentpH(7.0)
                .maxTDS(800.0)
                .maxpH(8.0)
                .minTDS(400.0)
                .minpH(5.0)
                .build();
    }
}
