package com.plantcare.serverapplication.farmmanagement.plant;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/farms/{farmId}/plants")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping
    public ResponseEntity<PlantDto> addPlant(@RequestBody PlantDto plantDto, @PathVariable int farmId) {

        PlantDto savedPlant = this.plantService.addPlant(plantDto, farmId);

        return new ResponseEntity<>(savedPlant, HttpStatus.OK);
    }
    @GetMapping("/{plantId}")
    public ResponseEntity<PlantDto> getPlantById(@PathVariable int plantId, @PathVariable int farmId) {

        return ResponseEntity.ok(this.plantService.getPlantById(farmId, plantId));
    }
    @GetMapping
    public ResponseEntity<List<PlantDto>> getAllPlantsByFarmId(@PathVariable int farmId) {
        List<PlantDto> plants = this.plantService.getAllPlantsByFarmId(farmId);

        return new ResponseEntity<>(plants, HttpStatus.OK);
    }
    @DeleteMapping("/{plantId}")
    public ResponseEntity<MessageResponseDto> deletePlantById(@PathVariable int plantId, @PathVariable int farmId) {
        this.plantService.deletePlantById(farmId, plantId);

        return new ResponseEntity<>(new MessageResponseDto("Plant successfully deleted."), HttpStatus.OK);
    }

    @PutMapping("/{plantId}")
    public ResponseEntity<PlantDto> updatePlant(
            @RequestBody PlantDto plantDto,
            @PathVariable int plantId,
            @PathVariable int farmId
    ) {

        PlantDto updatedPlant = this.plantService.updatePlant(plantDto, farmId, plantId);

        return new ResponseEntity<>(updatedPlant, HttpStatus.OK);
    }
}
