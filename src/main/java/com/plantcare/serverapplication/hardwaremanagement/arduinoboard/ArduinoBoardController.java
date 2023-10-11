package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.processing.Messager;
import java.util.List;

@RestController
@RequestMapping("api/v1/farms/{farmId}/arduinoboards")
public class ArduinoBoardController {
    private final ArduinoBoardService arduinoBoardService;

    public ArduinoBoardController(ArduinoBoardService arduinoBoardService) {
        this.arduinoBoardService = arduinoBoardService;
    }

    @PostMapping
    public ResponseEntity<ArduinoBoardDto> addArduinoBoard(@RequestBody ArduinoBoardDto arduinoBoardDto, @PathVariable int farmId) {

        return ResponseEntity.ok(this.arduinoBoardService.addArduinoBoard(arduinoBoardDto, farmId));
    }

    @GetMapping("/{arduinoBoardId}")
    public ResponseEntity<ArduinoBoardDto> getArduinoBoardById(int farmId, int arduinoBoardId) {

        return ResponseEntity.ok(this.arduinoBoardService.getArduinoBoardById(farmId, arduinoBoardId));
    }

    @GetMapping
    public ResponseEntity<List<ArduinoBoardDto>> getAllArduinoBoardsByFarmId(@PathVariable int farmId) {

        List<ArduinoBoardDto> arduinoBoards = this.arduinoBoardService.getAllArduinoBoardsByFarmId(farmId);

        return ResponseEntity.ok(arduinoBoards);
    }

    @DeleteMapping("/{arduinoBoardId}")
    public ResponseEntity<MessageResponseDto> deleteArduinoBoardById(@PathVariable int farmId, @PathVariable int arduinoBoardId) {

        this.arduinoBoardService.deleteArduinoBoardById(farmId, arduinoBoardId);

        return ResponseEntity.ok(new MessageResponseDto("Arduino board deleted successfully."));
    }
}
