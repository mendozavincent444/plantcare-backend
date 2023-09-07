package com.plantcare.serverapplication.farmmanagement.task;

import java.util.List;

public interface TaskService {

    void addTasks(TaskOperationDto taskRequestDto, int containerId, int farmId);

    void deleteTasks(DeleteTasksDto deleteTasksDto, int containerId, int farmId);

    List<TaskDto> getTasksByFarmId(int farmId);

    List<TaskDto> getTasksByContainerId(int containerId);

    TaskDto updateTask(TaskDto taskDto, int taskId, int containerId);
}
