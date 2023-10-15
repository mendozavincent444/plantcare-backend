package com.plantcare.serverapplication.farmmanagement.task;

import com.plantcare.serverapplication.shared.HarvestLogDto;

import java.util.List;

public interface TaskService {

    List<TaskDto> addTasks(TaskDto taskDto, int farmId, int containerId);

    void deleteTasks(TaskIdsDto taskIdsDto, int containerId, int farmId);

    List<TaskDto> getAllTasksFromAllContainers(int farmId);

    List<TaskDto> getTasksByContainerId(int farmId, int containerId);

    TaskDto updateTask(TaskDto taskDto, int taskId, int containerId);

    List<HarvestLogDto> harvestTasksByTaskIds(TaskIdsDto taskIdsDto, int farmId, int containerId);
}
