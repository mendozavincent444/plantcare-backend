package com.plantcare.serverapplication.farmmanagement.task;

public interface TaskService {

    TaskOperationDto addTasks(TaskOperationDto taskRequestDto, int containerId, int farmId);
}
