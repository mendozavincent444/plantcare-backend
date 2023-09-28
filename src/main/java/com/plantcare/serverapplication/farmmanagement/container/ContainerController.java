package com.plantcare.serverapplication.farmmanagement.container;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<String> deleteContainerListById(@RequestBody DeleteContainersDto deleteContainersDto, @PathVariable int farmId) {

        this.containerService.deleteContainerListById(deleteContainersDto, farmId);

        return new ResponseEntity<>("All items deleted.", HttpStatus.OK);
    }

    @PutMapping("/{containerId}")
    public ResponseEntity<ContainerDto> updateContainer(@RequestBody ContainerDto containerDto, int containerId) {
        ContainerDto updatedContainer = this.containerService.updateContainer(containerDto, containerId);

        return new ResponseEntity<>(updatedContainer, HttpStatus.OK);
    }


}
