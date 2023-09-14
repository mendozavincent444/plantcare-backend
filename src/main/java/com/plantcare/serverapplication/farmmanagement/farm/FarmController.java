package com.plantcare.serverapplication.farmmanagement.farm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResponseEntity<FarmDto> addFarm(@RequestBody FarmDto farmDto) {

        return ResponseEntity.ok(this.farmService.addFarm(farmDto));
    }
}
