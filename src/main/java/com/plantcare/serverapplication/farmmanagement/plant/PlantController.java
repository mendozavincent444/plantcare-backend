package com.plantcare.serverapplication.farmmanagement.plant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/plants")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping("/{plantId}/byFarm/{farmId}")
    public ResponseEntity<PlantDto> getPlantById(@PathVariable("plantId") int plantId, @PathVariable("farmId") int farmId) {

        return ResponseEntity.ok(this.plantService.getPlantById(farmId, plantId));
    }
    @GetMapping("/byFarm/{farmId}")
    public ResponseEntity<List<PlantDto>> getAllPlantsByFarmId(@PathVariable int farmId) {
        List<PlantDto> plants = this.plantService.getAllPlantsByFarmId(farmId);

        return new ResponseEntity<>(plants, HttpStatus.OK);
    }
    @DeleteMapping("/{plantId}/byFarm/{farmId}")
    public ResponseEntity<String> deletePlantById(@PathVariable int plantId, @PathVariable int farmId) {
        this.plantService.deletePlantById(farmId, plantId);

        return new ResponseEntity<>("Plant successfully deleted.", HttpStatus.OK);
    }
}
