package com.plantcare.serverapplication.farmmanagement.task;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/byFarm/{farmId}/byContainer/{containerId}")
    public ResponseEntity<String> addTasks(
            @RequestBody TaskOperationDto taskOperationsDto,
            @PathVariable int containerId,
            @PathVariable int farmId
    ) {
        this.taskService.addTasks(taskOperationsDto, containerId, farmId);

        return new ResponseEntity<>("Tasks successfully added.", HttpStatus.CREATED);
    }

    @DeleteMapping("/byFarm/{farmId}/byContainer/{containerId}")
    public ResponseEntity<String> deleteTasks(
            @RequestBody DeleteTasksDto deleteTasksDto,
            @PathVariable int containerId,
            @PathVariable int farmId
    ) {
        this.taskService.deleteTasks(deleteTasksDto, containerId, farmId);

        return new ResponseEntity<>("Tasks successfully deleted.", HttpStatus.OK);
    }
    @GetMapping("/byFarm/{farmId}")
    public ResponseEntity<List<TaskDto>> getAllTasksByFarmId(int farmId) {

        List<TaskDto> tasks = this.taskService.getTasksByFarmId(farmId);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/byContainer/{containerId}")
    public ResponseEntity<List<TaskDto>> getAllTasksByContainerId(int containerId) {

        List<TaskDto> tasks = this.taskService.getTasksByContainerId(containerId);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("{taskId}/containers/{containerId}")
    public ResponseEntity<TaskDto> updateTask(
            @RequestBody TaskDto taskDto,
            @PathVariable int taskId,
            @PathVariable int containerId
    ) {
        TaskDto updatedTask = this.taskService.updateTask(taskDto, taskId, containerId);

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
}
