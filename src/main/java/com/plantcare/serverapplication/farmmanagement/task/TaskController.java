package com.plantcare.serverapplication.farmmanagement.task;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/farms/{farmId}/containers/{containerId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<List<TaskDto>> addTasks(
            @RequestBody TaskOperationDto taskOperationsDto,
            @PathVariable int containerId,
            @PathVariable int farmId
    ) {
        List<TaskDto> savedTasks = this.taskService.addTasks(taskOperationsDto, containerId, farmId);

        return new ResponseEntity<>(savedTasks, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTasks(
            @RequestBody DeleteTasksDto deleteTasksDto,
            @PathVariable int containerId,
            @PathVariable int farmId
    ) {
        this.taskService.deleteTasks(deleteTasksDto, containerId, farmId);

        return new ResponseEntity<>("Tasks successfully deleted.", HttpStatus.OK);
    }
    @GetMapping("/containers/tasks/all")
    public ResponseEntity<List<TaskDto>> getAllTasksFromAllContainers(int farmId) {

        List<TaskDto> tasks = this.taskService.getAllTasksFromAllContainers(farmId);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasksByContainerId(int containerId) {

        List<TaskDto> tasks = this.taskService.getTasksByContainerId(containerId);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @RequestBody TaskDto taskDto,
            @PathVariable int taskId,
            @PathVariable int containerId
    ) {
        TaskDto updatedTask = this.taskService.updateTask(taskDto, taskId, containerId);

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
}
