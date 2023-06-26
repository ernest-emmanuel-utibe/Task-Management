package com.api.taskManagement.services;

import com.api.taskManagement.data.dto.request.TaskRequest;
import com.api.taskManagement.data.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest taskRequest);
    List<TaskResponse> getAllTasks();
    TaskResponse getTask(Long taskId);
    TaskResponse updateTask(Long taskId, TaskRequest taskRequest);
    boolean deleteTask(Long taskId);
}
