package com.api.taskManagement.services.impl;

import com.api.taskManagement.data.dto.request.TaskRequest;
import com.api.taskManagement.data.dto.response.TaskResponse;
import com.api.taskManagement.data.models.Task;
import com.api.taskManagement.data.models.TaskStatus;
import com.api.taskManagement.data.repository.TaskRepository;
import com.api.taskManagement.exception.TaskNotFoundException;
import com.api.taskManagement.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setCompleted(false);

        Task savedTask = taskRepository.save(task);

        TaskResponse taskResponses = new TaskResponse();
        taskResponses.setId(savedTask.getId());
        taskResponses.setName(savedTask.getName());
        taskResponses.setTitle(savedTask.getTitle());
        taskResponses.setDescription(savedTask.getDescription());
        taskResponses.setDueDate(savedTask.getDueDate());
        taskResponses.setCompleted(savedTask.isCompleted());

        return taskResponses;
    }

    @Override
    public TaskResponse updateTask(Long taskId, TaskRequest taskRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());

        Task updatedTask = taskRepository.save(task);

        return mapTaskToTaskResponse(updatedTask);
    }


    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return mapTasksToTaskResponses(tasks);
    }

    @Override
    public TaskResponse getTaskById(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        return optionalTask.map(this::mapTaskToTaskResponse)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));
    }

    @Override
    public void deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new TaskNotFoundException("Task not found with ID: " + taskId);
        }
    }

    private TaskResponse mapTaskToTaskResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setDueDate(task.getDueDate());
        taskResponse.setCompleted(task.isCompleted());
        return taskResponse;
    }

    private List<TaskResponse> mapTasksToTaskResponses(List<Task> tasks) {
        return tasks.stream()
                .map(this::mapTaskToTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> filterTasks(LocalDateTime dueDate, TaskStatus status) {
        List<Task> filteredTasks;

        if (dueDate != null && status != null) {
            filteredTasks = taskRepository.findByDueDateAndStatus(dueDate, status);
        } else if (dueDate != null) {
            filteredTasks = taskRepository.findByDueDate(dueDate);
        } else if (status != null) {
            filteredTasks = taskRepository.findByStatus(status);
        } else {
            filteredTasks = taskRepository.findAll();
        }

        return mapTasksToTaskResponses(filteredTasks);
    }
}
