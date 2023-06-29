package com.api.taskManagement.services;

import com.api.taskManagement.data.dto.request.TaskRequest;
import com.api.taskManagement.data.dto.response.TaskResponse;
import com.api.taskManagement.data.models.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest);

    List<TaskResponse> getAllTasks();
    TaskResponse getTaskById(Long taskId);

    TaskResponse updateTask(Long taskId, TaskRequest taskRequest);

    void deleteTask(Long taskId);

    List<TaskResponse> filterTasks(LocalDateTime dueDate, TaskStatus status);
}
