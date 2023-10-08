package com.plantcare.serverapplication.hardwaremanagement.pump;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/farms/{farmId}/pumps")
public class PumpController {
    private final PumpService pumpService;

    public PumpController(PumpService pumpService) {
        this.pumpService = pumpService;
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
}
