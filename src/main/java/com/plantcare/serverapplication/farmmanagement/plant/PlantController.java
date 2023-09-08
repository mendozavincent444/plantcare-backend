package com.plantcare.serverapplication.farmmanagement.plant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/plants")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping("{id}")
    public ResponseEntity<PlantDto> getPlantById(@PathVariable("id") int plantId) {

        return ResponseEntity.ok(this.plantService.getPlantById(plantId));
    }
    @GetMapping("/byFarm/{farmId}")
    public ResponseEntity<List<PlantDto>> getAllPlantsByFarmId(@PathVariable int farmId) {
        List<PlantDto> plants = this.plantService.getAllPlantsByFarmId(farmId);

        return new ResponseEntity<>(plants, HttpStatus.OK);
    }
}
