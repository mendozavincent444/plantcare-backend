package com.plantcare.serverapplication.hardwaremanagement.pump;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/farms/{farmId}/pumps")
public class PumpController {
    private final PumpService pumpService;

    public PumpController(PumpService pumpService) {
        this.pumpService = pumpService;
    }

    @PostMapping
    public ResponseEntity<PumpDto> addPump(@RequestBody PumpDto pumpDto, @PathVariable int farmId) {

        PumpDto addedPump = this.pumpService.addPump(pumpDto, farmId);

        return new ResponseEntity<>(addedPump, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PumpDto>> getAllPumpsByFarmId(@PathVariable int farmId) {

        List<PumpDto> pumps = this.pumpService.getAllPumpsByFarmId(farmId);

        return ResponseEntity.ok(pumps);
    }

    @GetMapping("/{pumpId}")
    public ResponseEntity<PumpDto> getPumpById(@PathVariable int farmId, @PathVariable int pumpId) {
        PumpDto pump = this.pumpService.getPumpById(farmId, pumpId);

        return ResponseEntity.ok(pump);
    }

    @DeleteMapping("/{pumpId}")
    public ResponseEntity<MessageResponseDto> deletePumpById(@PathVariable int farmId, @PathVariable int pumpId) {
        this.pumpService.deletePumpById(farmId, pumpId);

        return ResponseEntity.ok(new MessageResponseDto("Pump deleted successfully."));
    }
}
