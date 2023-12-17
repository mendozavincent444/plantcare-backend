package com.plantcare.serverapplication.firebase;

import com.plantcare.serverapplication.shared.ArduinoBoardData;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

@Service
public class FirebaseRestClient {

    private static final String BASE_URL = "https://plantcare-4b96e-default-rtdb.asia-southeast1.firebasedatabase.app/farm/";
    private static final String URL_SUFFIX = ".json";
    private final RestTemplate restTemplate;

    public FirebaseRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void addArduinoBoardToFirebaseDb(int farmId, int arduinoBoardId) {

        ArduinoBoardData arduinoBoardData = this.initializeArduinoBoardData();

        this.restTemplate.put(BASE_URL + farmId + "/arduinoBoard/" + arduinoBoardId + URL_SUFFIX,
                new HttpEntity<>(arduinoBoardData),
                String.class
        );
    }

    public void deleteArduinoBoardFromFirebaseDb(int farmId, int arduinoBoardId) {

        this.restTemplate.delete(BASE_URL + farmId + "/arduinoBoard" + arduinoBoardId + URL_SUFFIX);
    }


    private ArduinoBoardData initializeArduinoBoardData() {

        return ArduinoBoardData
                .builder()
                .currentHumidity(80.0)
                .currentTds(500.0)
                .currentTemperature(30.0)
                .currentpH(7.0)
                .currentWaterLevel(60.0)
                .maxTDS(800.0)
                .maxpH(8.0)
                .minTDS(400.0)
                .minpH(5.0)
                .build();
    }
}
