package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.UserDto;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{farmId}")
    public ResponseEntity<FarmDto> getFarmById(@PathVariable int farmId) {

        return ResponseEntity.ok(this.farmService.getFarmById(farmId));
    }

    @PostMapping
    public ResponseEntity<FarmDto> addFarm(@RequestBody FarmDto farmDto) {

        return new ResponseEntity<>(this.farmService.addFarm(farmDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FarmDto>> getAllFarms() {

        return ResponseEntity.ok(this.farmService.getAllFarms());
    }

    @DeleteMapping("/{farmId}")
    public ResponseEntity<MessageResponseDto> deleteFarmById(@PathVariable int farmId) {

        this.farmService.deleteFarmById(farmId);

        return ResponseEntity.ok(new MessageResponseDto("Farm deleted successfully."));
    }

    @PutMapping("/{farmId}/new-owner/{newOwnerId}")
    public ResponseEntity<FarmDto> updateFarm(@RequestBody FarmDto farmDto, @PathVariable int farmId, @PathVariable int newOwnerId) {

        FarmDto updatedFarm = this.farmService.updateFarm(farmDto, farmId, newOwnerId);

        return ResponseEntity.ok(updatedFarm);
    }

    @GetMapping("/{farmId}/farmers")
    public ResponseEntity<List<UserDto>> getAllFarmersByFarmId(@PathVariable int farmId) {
        List<UserDto> farmers = this.farmService.getAllFarmersByFarmId(farmId);

        return ResponseEntity.ok(farmers);
    }

    @GetMapping("/{farmId}/all-users")
    public ResponseEntity<List<UserDto>> getAllUsersByFarmId(@PathVariable int farmId) {

        List<UserDto> users = this.farmService.getAllUsersByFarmId(farmId);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{farmId}/admins")
    public ResponseEntity<List<UserDto>> getAllAdminsByFarmId(@PathVariable int farmId) {
        List<UserDto> admins = this.farmService.getAllAdminsByFarmId(farmId);

        return ResponseEntity.ok(admins);
    }

    @DeleteMapping("/{farmId}/farmers/{farmerId}")
    public ResponseEntity<MessageResponseDto> removeFarmerByFarm(@PathVariable int farmId, @PathVariable int farmerId) {
        this.farmService.removeFarmerByFarm(farmId, farmerId);

        return ResponseEntity.ok(new MessageResponseDto("Farmer/s successfully removed from farm."));
    }

    @PatchMapping("/{farmId}/arduino-board/{arduinoBoardId}")
    public ResponseEntity<FarmDto> setMainArduinoBoard(
            @PathVariable int farmId,
            @PathVariable int arduinoBoardId
    ) {
        FarmDto farmDto = this.farmService.setMainArduinoBoard(farmId, arduinoBoardId);

        return new ResponseEntity<>(farmDto, HttpStatus.OK);
    }

    @PatchMapping("/{farmId}")
    public ResponseEntity<FarmDto> removeMainArduinoBoard(@PathVariable int farmId) {
        FarmDto farmDto = this.farmService.removeMainArduinoBoard(farmId);

        return new ResponseEntity<>(farmDto, HttpStatus.OK);
    }
}
