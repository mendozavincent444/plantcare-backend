package com.plantcare.serverapplication.hardwaremanagement.sensor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/farms/{farmId}/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public ResponseEntity<List<SensorDto>> getAllSensorsByFarmId(@PathVariable int farmId) {
        List<SensorDto> sensors = this.sensorService.getAllSensorsByFarmId(farmId);

        return ResponseEntity.ok(sensors);
    }
}
