package com.plantcare.serverapplication.farmmanagement.task;

import com.plantcare.serverapplication.farmmanagement.container.Container;
import com.plantcare.serverapplication.farmmanagement.container.ContainerRepository;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.farmmanagement.plant.Plant;
import com.plantcare.serverapplication.farmmanagement.plant.PlantRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    public void addTasks(TaskOperationDto taskRequestDto, int containerId, int farmId) {

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

        this.containerRepository.save(container);
        this.farmRepository.save(farm);
    }

    @Override
    public void deleteTasks(DeleteTasksDto deleteTasksDto, int containerId, int farmId) {
        List<Integer> taskIds = deleteTasksDto.getTaskIds();

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        Container container = this.containerRepository.findById(containerId).orElseThrow();

        taskIds.forEach((taskId) -> {

            Task task = this.taskRepository.findById(taskId).orElseThrow();

            farm.getTasks().remove(task);
            container.getTasks().remove(task);
        });

        this.taskRepository.deleteAllById(taskIds);
    }
}
