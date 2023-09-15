package com.plantcare.serverapplication.farmmanagement.farm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping("{id}")
    public ResponseEntity<FarmDto> getFarmById(@PathVariable("id") int farmId) {

        return ResponseEntity.ok(this.farmService.getFarmById(farmId));
    }

    @PostMapping
    public ResponseEntity<FarmDto> addFarm(@RequestBody FarmDto farmDto) {

        return ResponseEntity.ok(this.farmService.addFarm(farmDto));
    }

    @GetMapping
    public ResponseEntity<List<FarmDto>> getAllFarmsByAdmin() {

        return ResponseEntity.ok(this.farmService.getAllFarms());
    }

    @PostMapping("{farmId}")
    public ResponseEntity<String> deleteFarmById(@PathVariable int farmId) {

        this.farmService.deleteFarmById(farmId);

        return ResponseEntity.ok("Farm deleted successfully.");
    }
}
