package com.plantcare.serverapplication.farmmanagement.task;

import com.plantcare.serverapplication.shared.HarvestTasksDto;
import com.plantcare.serverapplication.shared.HarvestLogDto;

import java.util.List;

public interface TaskService {

    List<TaskDto> addTasks(TaskOperationDto taskRequestDto, int farmId, int containerId);

    void deleteTasks(DeleteTasksDto deleteTasksDto, int containerId, int farmId);

    List<TaskDto> getAllTasksFromAllContainers(int farmId);

    List<TaskDto> getTasksByContainerId(int containerId);

    TaskDto updateTask(TaskDto taskDto, int taskId, int containerId);

    List<HarvestLogDto> harvestTasksByTaskIds(HarvestTasksDto harvestTasksDto, int farmId, int containerId);
}
