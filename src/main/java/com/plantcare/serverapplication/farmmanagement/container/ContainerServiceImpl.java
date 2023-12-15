package com.plantcare.serverapplication.farmmanagement.container;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.farmmanagement.plant.Plant;
import com.plantcare.serverapplication.farmmanagement.plant.PlantRepository;
import com.plantcare.serverapplication.farmmanagement.plant.PlantService;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoard;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoardRepository;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoardService;
import com.plantcare.serverapplication.notificationmanagement.notification.Notification;
import com.plantcare.serverapplication.notificationmanagement.notification.NotificationRepository;
import com.plantcare.serverapplication.shared.DeviceStatus;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.usermanagement.user.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContainerServiceImpl implements ContainerService {
    private final ContainerRepository containerRepository;
    private final ArduinoBoardRepository arduinoBoardRepository;
    private final PlantRepository plantRepository;
    private final FarmRepository farmRepository;
    private final ArduinoBoardService arduinoBoardService;
    private final PlantService plantService;
    private final NotificationRepository notificationRepository;

    public ContainerServiceImpl(
            ContainerRepository containerRepository,
            ArduinoBoardRepository arduinoBoardRepository,
            PlantRepository plantRepository,
            FarmRepository farmRepository,
            ArduinoBoardService arduinoBoardService,
            PlantService plantService,
            NotificationRepository notificationRepository
    ) {
        this.containerRepository = containerRepository;
        this.arduinoBoardRepository = arduinoBoardRepository;
        this.plantRepository = plantRepository;
        this.farmRepository = farmRepository;
        this.arduinoBoardService = arduinoBoardService;
        this.plantService = plantService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public ContainerDto addContainer(ContainerDto containerDto, int farmId) {

        ArduinoBoard arduinoBoard = this.arduinoBoardRepository.findById(containerDto.getArduinoBoardDto().getId()).orElseThrow();

        Plant plant = this.plantRepository.findById(containerDto.getPlantDto().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", containerDto.getPlantDto().getId()));

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        arduinoBoard.setStatus(DeviceStatus.ACTIVE);

        Container newContainer = Container
                .builder()
                .name(containerDto.getName())
                .arduinoBoard(arduinoBoard)
                .plant(plant)
                .farm(farm)
                .build();

        farm.getContainers().add(newContainer);

        Notification notification = Notification
                .builder()
                .date(new Date())
                .title("Add Container")
                .content(newContainer.getName() + " container added successfully.")
                .isReadNotification(false)
                .build();

        this.generateContainersNotifications(farm, notification);

        newContainer = this.containerRepository.saveAndFlush(newContainer);

        return convertToDto(newContainer);
    }

    @Override
    public List<ContainerDto> getAllContainersByFarmId(int farmId) {
        List<Container> containers = this.containerRepository.findAllByFarmId(farmId).orElseThrow();

        return containers.stream().map((container) -> convertToDto(container)).collect(Collectors.toList());
    }

    @Override
    public void deleteContainerListById(DeleteContainersDto deleteContainersDto, int farmId) {

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        List<Integer> containerIds = deleteContainersDto.getContainerIds();

        List<Container> containers = this.containerRepository.findAllById(containerIds);

        containers.forEach((container -> {
            if (container.getArduinoBoard() != null) {
                container.getArduinoBoard().setStatus(DeviceStatus.INACTIVE);
            }
            container.getTasks().clear();
        }));

        farm.getContainers().removeAll(containers);

        Notification notification = Notification
                .builder()
                .date(new Date())
                .title("Delete Container")
                .content("Deleted " + containers.size() + " container/s.")
                .isReadNotification(false)
                .build();

        this.generateContainersNotifications(farm, notification);

        this.containerRepository.deleteAllById(containerIds);
    }

    @Override
    public ContainerDto updateContainer(ContainerDto containerDto, int farmId, int containerId) {

        Container container = this.containerRepository.findById(containerId)
                .orElseThrow(() -> new ResourceNotFoundException("Container", "id", containerId));

        Farm farm = container.getFarm();

        int newArduinoBoardId = containerDto.getArduinoBoardDto().getId();
        int newPlantId = containerDto.getPlantDto().getId();
        String newContainerName = containerDto.getName();

        // if user updated arduino board
        if (newArduinoBoardId != 0) {
            ArduinoBoard arduinoBoard = this.arduinoBoardRepository.findById(newArduinoBoardId)
                    .orElseThrow(() -> new ResourceNotFoundException("Arduino Board", "id", newArduinoBoardId));

            ArduinoBoard oldArduinoBoard = container.getArduinoBoard();

            if (oldArduinoBoard != arduinoBoard) {
                container.setArduinoBoard(arduinoBoard);

                oldArduinoBoard.setStatus(DeviceStatus.INACTIVE);
                arduinoBoard.setStatus(DeviceStatus.ACTIVE);
            }
        }

        // if user updated plant
        if (newPlantId != 0) {
            Plant plant = this.plantRepository.findById(containerDto.getPlantDto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", containerDto.getPlantDto().getId()));

            container.setPlant(plant);
        }

        if (!newContainerName.isBlank()) {
            container.setName(containerDto.getName());
        }

        Notification notification = Notification
                .builder()
                .date(new Date())
                .title("Update Container")
                .content(container.getName() + " container updated successfully.")
                .isReadNotification(false)
                .build();

        this.generateContainersNotifications(farm, notification);

        Container updatedContainer = this.containerRepository.save(container);

        return this.convertToDto(updatedContainer);
    }

    private void generateContainersNotifications(Farm farm, Notification notification) {
        Notification savedNotification = this.notificationRepository.save(notification);

        List<User> users = farm.getUsers();

        users.forEach(user -> user.getNotifications().add(savedNotification));
    }

    private ContainerDto convertToDto(Container container) {
        return ContainerDto
                .builder()
                .id(container.getId())
                .name(container.getName())
                .arduinoBoardDto(this.arduinoBoardService.convertToDto(container.getArduinoBoard()))
                .plantDto(this.plantService.convertToDto(container.getPlant()))
                .farmId(container.getFarm().getId())
                .build();

    }

}
