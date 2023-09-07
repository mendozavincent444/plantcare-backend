package com.plantcare.serverapplication.farmmanagement.task;

public interface TaskService {

    void addTasks(TaskOperationDto taskRequestDto, int containerId, int farmId);
}
