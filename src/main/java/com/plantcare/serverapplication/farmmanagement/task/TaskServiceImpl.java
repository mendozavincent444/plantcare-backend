package com.plantcare.serverapplication.farmmanagement.task;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.farmmanagement.container.Container;
import com.plantcare.serverapplication.farmmanagement.container.ContainerRepository;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.farmmanagement.harvestlog.HarvestLog;
import com.plantcare.serverapplication.farmmanagement.harvestlog.HarvestLogRepository;
import com.plantcare.serverapplication.farmmanagement.plant.Plant;
import com.plantcare.serverapplication.farmmanagement.plant.PlantRepository;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.HarvestLogDto;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PlantRepository plantRepository;
    private final ContainerRepository containerRepository;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final HarvestLogRepository harvestLogRepository;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            PlantRepository plantRepository,
            ContainerRepository containerRepository,
            FarmRepository farmRepository,
            UserRepository userRepository,
            HarvestLogRepository harvestLogRepository
    ) {
        this.taskRepository = taskRepository;
        this.plantRepository = plantRepository;
        this.containerRepository = containerRepository;
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.harvestLogRepository = harvestLogRepository;
    }

    @Override
    public List<TaskDto> addTasks(TaskDto taskDto, int farmId, int containerId) {

        // fix - if farm is accessible and container has all the tasks
        User currentUser = this.getCurrentUser();

        int numberOfTasks = taskDto.getNumberOfTasks();

        Container container = this.containerRepository.findById(containerId)
                .orElseThrow(() -> new ResourceNotFoundException("Container", "id", containerId));

        // fix - check if farm is accessible by user
        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        int plantId = taskDto.getPlantId();

        Plant plant = this.plantRepository.findById(plantId)
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", plantId));

        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < numberOfTasks; i++) {
            tasks.add(
                    Task.builder()
                            .datePlanted(taskDto.getDatePlanted())
                            .harvestDate(taskDto.getHarvestDate())
                            .farmer(currentUser)
                            .status(TaskStatus.GROWING)
                            .plant(plant)
                            .container(container)
                            .build()
            );
        }

        container.getTasks().addAll(tasks);

        List<Task> savedTasks = this.taskRepository.saveAllAndFlush(tasks);

        return savedTasks.stream().map(task -> this.convertToDto(task)).collect(Collectors.toList());
    }

    @Override
    public void deleteTasks(TaskIdsDto taskIdsDto, int farmId) {
        // fix - if farm is accessible and container has all the tasks
        List<Integer> taskIds = taskIdsDto.getTaskIds();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        List<Task> tasks = this.taskRepository.findAllById(taskIds);

        for (Task task: tasks) {
            Container container = task.getContainer();
            container.getTasks().remove(task);
        }

        this.taskRepository.deleteAllById(taskIds);
    }

    @Override
    public List<TaskDto> getAllTasksFromAllContainers(int farmId) {
        // fix - if farm is accessible and container has all the tasks

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        List<Task> allTasks = new ArrayList<>();

        farm.getContainers().forEach((container -> allTasks.addAll(container.getTasks())));

        return allTasks.stream().map((task) -> convertToDto(task)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByContainerId(int farmId, int containerId) {
        // fix - if farm is accessible and container has all the tasks
        List<Task> tasks = this.taskRepository.findAllByContainerId(containerId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "container id", containerId));

        return tasks.stream().map((task) -> convertToDto(task)).collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto, int taskToUpdateId, int containerId) {

        // fix - if farm is accessible and container has all the tasks
        Task task = this.taskRepository.findById(taskToUpdateId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskToUpdateId));

        Container newContainer = this.containerRepository.findById(taskDto.getContainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Container", "id", taskDto.getContainerId()));

        Plant plant = this.plantRepository.findById(taskDto.getPlantId())
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", taskDto.getPlantId()));

        task.setPlant(plant);


        if (task.getContainer() != newContainer) {

            Container oldContainer = this.containerRepository.findById(containerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Container", "id", containerId));

            oldContainer.getTasks().remove(task);

            task.setContainer(newContainer);
            newContainer.getTasks().add(task);
        }

        this.taskRepository.save(task);

        return convertToDto(task);
    }

    @Override
    public List<HarvestLogDto> harvestTasksByTaskIds(TaskIdsDto taskIdsDto, int farmId, int containerId) {

        // fix - if farm is accessible and container has all the tasks

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        List<Integer> taskIdsToHarvest = taskIdsDto.getTaskIds();

        List<Task> tasksToHarvest = this.taskRepository.findAllById(taskIdsToHarvest);

        List<HarvestLog> harvestLogList = tasksToHarvest.stream().map((task) -> {

            return HarvestLog
                    .builder()
                    .harvestedDate(new Date())
                    .task(task)
                    .farmer(task.getFarmer())
                    .farm(farm)
                    .build();
        }).toList();

        List<HarvestLog> savedHarvestLogs = this.harvestLogRepository.saveAll(harvestLogList);

        return savedHarvestLogs.stream().map((harvestLog -> {
            return HarvestLogDto
                    .builder()
                    .id(harvestLog.getId())
                    .harvestedDate(harvestLog.getHarvestedDate())
                    .plantName(harvestLog.getTask().getPlant().getName())
                    .farmerLastName(harvestLog.getFarmer().getLastName())
                    .build();
        })).toList();
    }

    private TaskDto convertToDto(Task task) {
        return TaskDto
                .builder()
                .id(task.getId())
                .datePlanted(task.getDatePlanted())
                .harvestDate(task.getHarvestDate())
                .status(task.getStatus().name())
                .plantId(task.getPlant().getId())
                .plantName(task.getPlant().getName())
                .containerId(task.getContainer().getId())
                .build();
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
