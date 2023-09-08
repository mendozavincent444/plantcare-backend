package com.plantcare.serverapplication.farmmanagement.container;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.farmmanagement.plant.Plant;
import com.plantcare.serverapplication.farmmanagement.plant.PlantRepository;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoard;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoardRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        farm.getContainers().add(newContainer);

        newContainer = this.containerRepository.saveAndFlush(newContainer);

        return convertToDto(newContainer);
    }

    @Override
    public List<ContainerDto> getAllContainersByFarmId(int farmId) {
        List<Container> containers = this.containerRepository.findAllByFarmId(farmId);


        return containers.stream().map((container) -> convertToDto(container)).collect(Collectors.toList());
    }

    @Override
    public void deleteContainerListById(DeleteContainersDto deleteContainersDto, int farmId) {

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        List<Integer> containerIds = deleteContainersDto.getContainerIds();

        containerIds.forEach((containerId) -> {

            Container container = this.containerRepository.findById(containerId).orElseThrow();

            farm.getContainers().remove(container);
        });

        this.containerRepository.deleteAllById(containerIds);
    }

    @Override
    public ContainerDto updateContainer(ContainerDto containerDto, int containerId) {

        Container container = this.containerRepository.findById(containerId).orElseThrow();

        ArduinoBoard arduinoBoard = this.arduinoBoardRepository.findById(containerDto.getArduinoBoardId()).orElseThrow();

        Plant plant = this.plantRepository.findById(containerDto.getPlantId()).orElseThrow();

        container.setName(containerDto.getName());
        container.setArduinoBoard(arduinoBoard);
        container.setPlant(plant);

        Container updatedContainer = this.containerRepository.save(container);

        return this.mapToDto(updatedContainer);
    }

    private ContainerDto mapToDto(Container container) {
        return this.modelMapper.map(container, ContainerDto.class);
    }

    private ContainerDto convertToDto(Container container) {
        return ContainerDto
                .builder()
                .id(container.getId())
                .name(container.getName())
                .arduinoBoardId(container.getArduinoBoard().getId())
                .plantId(container.getPlant().getId())
                .farmId(container.getFarm().getId())
                .build();
    }

}
