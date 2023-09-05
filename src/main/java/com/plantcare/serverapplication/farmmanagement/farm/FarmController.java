package com.plantcare.serverapplication.farmmanagement.farm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
