package com.plantcare.serverapplication.farmmanagement.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/byFarm/{farmId}/byContainer/{containerId}")
    public ResponseEntity<TaskOperationDto> addTasks(
            @RequestBody TaskOperationDto taskOperationsDto,
            @PathVariable int containerId,
            @PathVariable int farmId
    ) {
        TaskOperationDto savedTaskOperationDto = this.taskService.addTasks(taskOperationsDto, containerId, farmId);

        return new ResponseEntity<>(savedTaskOperationDto, HttpStatus.CREATED);
    }
}
