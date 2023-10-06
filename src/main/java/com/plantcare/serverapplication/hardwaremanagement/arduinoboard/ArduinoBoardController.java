package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/farms/{farmId}/arduinoboards")
public class ArduinoBoardController {

    private final ArduinoBoardService arduinoBoardService;

    public ArduinoBoardController(ArduinoBoardService arduinoBoardService) {
        this.arduinoBoardService = arduinoBoardService;
    }

    // fix - might not need feature
    @GetMapping("{id}")
    public ResponseEntity<ArduinoBoardDto> getArduinoBoardById(@PathVariable("id") int arduinoBoardId) {

        return ResponseEntity.ok(this.arduinoBoardService.getArduinoBoardById(arduinoBoardId));
    }

    @GetMapping
    public ResponseEntity<List<ArduinoBoardDto>> getAllArduinoBoardsByFarmId(@PathVariable int farmId) {

        List<ArduinoBoardDto> arduinoBoards = this.arduinoBoardService.getAllArduinoBoardsByFarmId(farmId);

        return ResponseEntity.ok(arduinoBoards);
    }
}
