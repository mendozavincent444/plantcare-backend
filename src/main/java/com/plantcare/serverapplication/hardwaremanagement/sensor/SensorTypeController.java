package com.plantcare.serverapplication.hardwaremanagement.sensor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/sensortypes")
public class SensorTypeController {

    private final SensorTypeService sensorTypeService;

    public SensorTypeController(SensorTypeService sensorTypeService) {
        this.sensorTypeService = sensorTypeService;
    }

    @GetMapping
    public ResponseEntity<List<SensorType>> getAllSensorTypes() {
        List<SensorType> sensorTypes = this.sensorTypeService.getAllSensorTypes();

        return ResponseEntity.ok(sensorTypes);
    }
}
