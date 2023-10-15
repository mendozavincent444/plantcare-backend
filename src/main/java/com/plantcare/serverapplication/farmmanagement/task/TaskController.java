package com.plantcare.serverapplication.farmmanagement.task;

import com.plantcare.serverapplication.shared.HarvestLogDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/farms/{farmId}")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/containers/{containerId}/tasks")
    public ResponseEntity<List<TaskDto>> addTasks(
            @RequestBody TaskDto taskDto,
            @PathVariable int containerId,
            @PathVariable int farmId
    ) {
        List<TaskDto> savedTasks = this.taskService.addTasks(taskDto, farmId, containerId);

        return new ResponseEntity<>(savedTasks, HttpStatus.CREATED);
    }

    @DeleteMapping("/tasks")
    public ResponseEntity<String> deleteTasks(
            @RequestBody TaskIdsDto taskIdsDto,
            @PathVariable int farmId
    ) {
        this.taskService.deleteTasks(taskIdsDto, farmId);

        return new ResponseEntity<>("Tasks successfully deleted.", HttpStatus.OK);
    }
    @GetMapping("/containers/tasks/all")
    public ResponseEntity<List<TaskDto>> getAllTasksFromAllContainers(@PathVariable int farmId) {

        List<TaskDto> tasks = this.taskService.getAllTasksFromAllContainers(farmId);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/containers/{containerId}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasksByContainerId(@PathVariable int farmId, @PathVariable int containerId) {

        List<TaskDto> tasks = this.taskService.getTasksByContainerId(farmId, containerId);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/containers/{containerId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @RequestBody TaskDto taskDto,
            @PathVariable int taskId,
            @PathVariable int containerId
    ) {
        TaskDto updatedTask = this.taskService.updateTask(taskDto, taskId, containerId);

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @PostMapping("/containers/{containerId}/tasks/harvest")
    public ResponseEntity<List<HarvestLogDto>> harvestTasksById(
            @RequestBody TaskIdsDto taskIdsDto,
            @PathVariable int farmId,
            @PathVariable int containerId
            ) {

        List<HarvestLogDto> harvestLogDtos = this.taskService.harvestTasksByTaskIds(taskIdsDto, farmId, containerId);

        return new ResponseEntity<>(harvestLogDtos, HttpStatus.CREATED);
    }
}
