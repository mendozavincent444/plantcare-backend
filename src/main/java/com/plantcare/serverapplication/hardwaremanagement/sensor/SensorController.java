package com.plantcare.serverapplication.hardwaremanagement.sensor;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{sensorId}")
    public ResponseEntity<SensorDto> getSensorById(@PathVariable int farmId, @PathVariable int sensorId) {
        SensorDto sensor = this.sensorService.getSensorById(farmId, sensorId);

        return ResponseEntity.ok(sensor);
    }

    @DeleteMapping("/{sensorId}")
    public ResponseEntity<MessageResponseDto> deleteSensorById(@PathVariable int farmId, @PathVariable int sensorId) {
        this.sensorService.deleteSensorById(farmId, sensorId);

        return ResponseEntity.ok(new MessageResponseDto("Sensor deleted successfully."));
    }
}
