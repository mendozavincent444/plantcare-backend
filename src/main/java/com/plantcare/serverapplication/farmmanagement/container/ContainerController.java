package com.plantcare.serverapplication.farmmanagement.container;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/containers")
public class ContainerController {

    private final ContainerService containerService;

    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    public ResponseEntity<ContainerDto> addContainer(@RequestBody ContainerDto containerDto) {

        ContainerDto savedContainer = this.containerService.addContainer(containerDto);

        return ResponseEntity.ok(savedContainer);
    }
}
