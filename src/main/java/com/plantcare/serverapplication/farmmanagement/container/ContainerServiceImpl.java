package com.plantcare.serverapplication.farmmanagement.container;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@Service
public class ContainerServiceImpl implements ContainerService {
    private final ContainerRepository containerRepository;
    private final ModelMapper modelMapper;

    public ContainerServiceImpl(ContainerRepository containerRepository, ModelMapper modelMapper) {
        this.containerRepository = containerRepository;
        this.modelMapper = modelMapper;
    }

    // fix
    @Override
    public ContainerDto addContainer(ContainerDto containerDto) {

        /*
        // inject arduinoboard repository and plantid repository here

        int arduinoBoardId = containerDto.getArduinoBoardId();
        int plantId = containerDto.getPlantId();

        Container newContainer = Container
                .builder()
                .name(containerDto.getName())
                .arduinoBoard()
                .plant()
                .build();



        Container savedContainer = this.containerRepository.save(newContainer);

        ContainerDto containerDto1 = this.mapToDto(savedContainer);


        return containerDto1;



         */

        return new ContainerDto();
    }

    private ContainerDto mapToDto(Container container) {
        return this.modelMapper.map(container, ContainerDto.class);
    }

    private Container mapToEntity(ContainerDto containerDto) {
        return this.modelMapper.map(containerDto, Container.class);
    }
}
