package com.plantcare.serverapplication.farmmanagement.container;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/containers")
public class ContainerController {

    private final ContainerService containerService;

    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @PostMapping
    public ResponseEntity<ContainerDto> addContainer(@RequestBody ContainerDto containerDto) {

        ContainerDto savedContainer = this.containerService.addContainer(containerDto);

        return new ResponseEntity<>(savedContainer, HttpStatus.CREATED);
    }
    @GetMapping("/byFarm/{id}")
    public ResponseEntity<List<ContainerDto>> getAllContainersByFarmId(@PathVariable("id") int farmId) {
        List<ContainerDto> containersByFarm = this.containerService.getAllContainersByFarmId(farmId);

        return new ResponseEntity<>(containersByFarm, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteContainerListById(@RequestBody DeleteContainersDto deleteContainersDto, @PathVariable("id") int farmId) {

        this.containerService.deleteContainerListById(deleteContainersDto, farmId);

        return new ResponseEntity<>("All items deleted.", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ContainerDto> updateContainer(@RequestBody ContainerDto containerDto, @PathVariable("id") int containerId) {
        ContainerDto updatedContainer = this.containerService.updateContainer(containerDto, containerId);

        return new ResponseEntity<>(updatedContainer, HttpStatus.OK);
    }


}
