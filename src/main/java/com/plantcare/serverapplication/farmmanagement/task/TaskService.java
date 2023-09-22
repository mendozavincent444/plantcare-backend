package com.plantcare.serverapplication.farmmanagement.task;

import java.util.List;

public interface TaskService {

    List<TaskDto> addTasks(TaskOperationDto taskRequestDto, int farmId, int containerId);

    void deleteTasks(DeleteTasksDto deleteTasksDto, int containerId, int farmId);

    List<TaskDto> getAllTasksFromAllContainers(int farmId);

    List<TaskDto> getTasksByContainerId(int containerId);

    TaskDto updateTask(TaskDto taskDto, int taskId, int containerId);
}
