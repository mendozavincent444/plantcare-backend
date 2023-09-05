package com.plantcare.serverapplication.farmmanagement.container;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.farmmanagement.plant.Plant;
import com.plantcare.serverapplication.farmmanagement.plant.PlantRepository;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoard;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@Service
public class ContainerServiceImpl implements ContainerService {
    private final ContainerRepository containerRepository;
    private final ArduinoBoardRepository arduinoBoardRepository;
    private final PlantRepository plantRepository;
    private final FarmRepository farmRepository;
    private final ModelMapper modelMapper;

    public ContainerServiceImpl(
            ContainerRepository containerRepository,
            ArduinoBoardRepository arduinoBoardRepository,
            PlantRepository plantRepository,
            FarmRepository farmRepository,
            ModelMapper modelMapper
    ) {
        this.containerRepository = containerRepository;
        this.arduinoBoardRepository = arduinoBoardRepository;
        this.plantRepository = plantRepository;
        this.farmRepository = farmRepository;
        this.modelMapper = modelMapper;
    }

    // fix add farm get by id later
    @Override
    public ContainerDto addContainer(ContainerDto containerDto) {

        ArduinoBoard arduinoBoard = this.arduinoBoardRepository.findById(containerDto.getArduinoBoardId()).orElseThrow();

        Plant plant = this.plantRepository.findById(containerDto.getPlantId()).orElseThrow();

        Farm farm = this.farmRepository.findById(containerDto.getFarmId()).orElseThrow();

        Container newContainer = Container
                .builder()
                .name(containerDto.getName())
                .arduinoBoard(arduinoBoard)
                .plant(plant)
                .farm(farm)
                .build();

        Container savedContainer = this.containerRepository.save(newContainer);

        return this.mapToDto(savedContainer);
    }

    private ContainerDto mapToDto(Container container) {
        return this.modelMapper.map(container, ContainerDto.class);
    }

    private Container mapToEntity(ContainerDto containerDto) {
        return this.modelMapper.map(containerDto, Container.class);
    }
}
