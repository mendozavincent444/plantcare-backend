package com.plantcare.serverapplication.farmmanagement.task;

import com.plantcare.serverapplication.farmmanagement.container.Container;
import com.plantcare.serverapplication.farmmanagement.container.ContainerRepository;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.farmmanagement.plant.Plant;
import com.plantcare.serverapplication.farmmanagement.plant.PlantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PlantRepository plantRepository;
    private final ContainerRepository containerRepository;
    private final FarmRepository farmRepository;
    private final ModelMapper modelMapper;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            PlantRepository plantRepository,
            ContainerRepository containerRepository,
            FarmRepository farmRepository,
            ModelMapper modelMapper
    ) {
        this.taskRepository = taskRepository;
        this.plantRepository = plantRepository;
        this.containerRepository = containerRepository;
        this.farmRepository = farmRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskOperationDto addTasks(TaskOperationDto taskRequestDto, int containerId, int farmId) {

        List<TaskDto> taskDtoList = taskRequestDto.getTaskDtoList();

        Container container = this.containerRepository.findById(containerId).orElseThrow();
        Farm farm = this.farmRepository.findById(farmId).orElseThrow();


        List<Task> tasks = taskDtoList.stream().map((taskDto -> {

            Plant plant = this.plantRepository.findById(taskDto.getPlantId()).orElseThrow();

            return Task
                    .builder()
                    .datePlanted(taskDto.getDatePlanted())
                    .harvestDate(taskDto.getHarvestDate())
                    .status(taskDto.getStatus())
                    .plant(plant)
                    .container(container)
                    .farm(farm)
                    .build();
        })).collect(Collectors.toList());

        tasks.forEach((task -> {
            container.getTasks().add(task);
            farm.getTasks().add(task);
        }));

        List<Task> savedTasks = this.taskRepository.saveAll(tasks);

        List<TaskDto> savedTaskDtos = savedTasks.stream().map((task -> {

            return TaskDto
                    .builder()
                    .id(task.getId())
                    .datePlanted(task.getDatePlanted())
                    .harvestDate(task.getHarvestDate())
                    .status(task.getStatus())
                    .plantId(task.getPlant().getId())
                    .containerId(task.getContainer().getId())
                    .farmId(task.getFarm().getId())
                    .build();
        })).collect(Collectors.toList());

        return new TaskOperationDto(savedTaskDtos);
    }
}
