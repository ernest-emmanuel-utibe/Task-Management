package com.api.taskManagement.controllers;

import com.api.taskManagement.data.dto.request.TaskRequest;
import com.api.taskManagement.data.dto.response.TaskResponse;
import com.api.taskManagement.data.models.TaskStatus;
import com.api.taskManagement.exception.TaskNotFoundException;
import com.api.taskManagement.services.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create_tasks")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse createdTask = taskService.createTask(taskRequest);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @GetMapping("/get_all_tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("taskId") Long taskId) {
        TaskResponse task = taskService.getTaskById(taskId);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable("taskId") Long taskId,
            @RequestBody TaskRequest taskRequest) {
        try {
            TaskResponse updatedTask = taskService.updateTask(taskId, taskRequest);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (TaskNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable("taskId") Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
        } catch (TaskNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter_tasks")
    public ResponseEntity<List<TaskResponse>> filterTasks(
            @RequestParam(value = "dateTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dueDate,
            @RequestParam(value = "status", required = false) TaskStatus status) {
        List<TaskResponse> filteredTasks = taskService.filterTasks(dueDate, status);
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }
}
