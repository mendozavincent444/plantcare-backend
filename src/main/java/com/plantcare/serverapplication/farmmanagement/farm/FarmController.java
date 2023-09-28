package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.shared.UserDto;
import com.sun.mail.iap.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("api/v1/farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping("/{farmId}")
    public ResponseEntity<FarmDto> getFarmById(@PathVariable int farmId) {

        return ResponseEntity.ok(this.farmService.getFarmById(farmId));
    }

    @PostMapping
    public ResponseEntity<FarmDto> addFarm(@RequestBody FarmDto farmDto) {

        return new ResponseEntity<>(this.farmService.addFarm(farmDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FarmDto>> getAllFarmsByAdmin() {

        return ResponseEntity.ok(this.farmService.getAllFarms());
    }

    @DeleteMapping("/{farmId}")
    public ResponseEntity<String> deleteFarmById(@PathVariable int farmId) {

        this.farmService.deleteFarmById(farmId);

        return ResponseEntity.ok("Farm deleted successfully.");
    }
    @PutMapping("/{farmId}")
    public ResponseEntity<FarmDto> updateFarm(@RequestBody FarmDto farmDto, @PathVariable int farmId) {

        FarmDto updatedFarm = this.farmService.updateFarm(farmDto, farmId);

        return ResponseEntity.ok(updatedFarm);
    }

    @GetMapping("/{farmId}/farmers")
    public ResponseEntity<List<UserDto>> getAllFarmersByFarmId(@PathVariable int farmId) {
        List<UserDto> farmers = this.farmService.getAllFarmersByFarmId(farmId);

        return ResponseEntity.ok(farmers);
    }

    @DeleteMapping("/{farmId}/farmers/{farmerId}")
    public ResponseEntity<String> removeFarmerByFarm(@PathVariable int farmId, @PathVariable int farmerId) {
        this.farmService.removeFarmerByFarm(farmId, farmerId);

        return ResponseEntity.ok("Farmer/s successfully removed from farm.");
    }

    @PutMapping("/{farmId}/change-owner/{newOwnerId}")
    public ResponseEntity<String> changeFarmOwnership(@PathVariable int farmId, @PathVariable int newOwnerId) {

        this.farmService.changeFarmOwnership(farmId, newOwnerId);

        return ResponseEntity.ok("Farm ownership changed successfully.");
    }

}
