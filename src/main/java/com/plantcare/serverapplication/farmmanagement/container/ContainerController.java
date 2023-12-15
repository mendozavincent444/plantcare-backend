package com.plantcare.serverapplication.farmmanagement.container;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/farms/{farmId}/containers")
public class ContainerController {

    private final ContainerService containerService;

    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @PostMapping
    public ResponseEntity<ContainerDto> addContainer(@RequestBody ContainerDto containerDto, @PathVariable int farmId) {

        ContainerDto savedContainerDto = this.containerService.addContainer(containerDto, farmId);

        return new ResponseEntity<>(savedContainerDto, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ContainerDto>> getAllContainersByFarmId(@PathVariable int farmId) {
        List<ContainerDto> containersByFarm = this.containerService.getAllContainersByFarmId(farmId);

        return new ResponseEntity<>(containersByFarm, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponseDto> deleteContainerListById(@RequestBody DeleteContainersDto deleteContainersDto, @PathVariable int farmId) {

        this.containerService.deleteContainerListById(deleteContainersDto, farmId);

        return new ResponseEntity<>(new MessageResponseDto("All items deleted."), HttpStatus.OK);
    }

    @PatchMapping("/{containerId}/arduino-board/{arduinoBoardId}")
    public ResponseEntity<MessageResponseDto> setMainArduinoBoard(
            @PathVariable int containerId,
            @PathVariable int arduinoBoard
    ) {
        MessageResponseDto messageResponseDto = this.containerService.setMainArduinoBoard(containerId, arduinoBoard);

        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{containerId}")
    public ResponseEntity<ContainerDto> updateContainer(
            @RequestBody ContainerDto containerDto,
            @PathVariable int farmId,
            @PathVariable int containerId
    ) {
        ContainerDto updatedContainer = this.containerService.updateContainer(containerDto, farmId, containerId);

        return new ResponseEntity<>(updatedContainer, HttpStatus.OK);
    }


}
