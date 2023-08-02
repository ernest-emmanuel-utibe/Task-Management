package com.api.taskManagement.services.impl;

import com.api.taskManagement.data.dto.request.TaskRequest;
import com.api.taskManagement.data.dto.response.TaskResponse;
import com.api.taskManagement.data.models.Task;
import com.api.taskManagement.data.models.TaskStatus;
import com.api.taskManagement.data.repository.TaskRepository;
import com.api.taskManagement.exception.TaskNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {
    @InjectMocks
    private TaskServiceImpl taskService;
    @Mock
    private TaskRepository taskRepository;


    @Test
    public void createTask_ValidRequest_ReturnsTaskResponse() {
        // Prepare test data
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Test Task");
        taskRequest.setTitle("Task Title");
        taskRequest.setDescription("Task Description");
        taskRequest.setDueDate(LocalDateTime.now());

        Task task = new Task();
        task.setId(1L);
        task.setName(taskRequest.getName());
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setCompleted(false);

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        
        TaskResponse taskResponse = taskService.createTask(taskRequest);

        // Assert the result
        assertEquals(task.getId(), taskResponse.getId());
        assertEquals(task.getName(), taskResponse.getName());
        assertEquals(task.getTitle(), taskResponse.getTitle());
        assertEquals(task.getDescription(), taskResponse.getDescription());
        assertEquals(task.getDueDate(), taskResponse.getDueDate());
        assertEquals(task.isCompleted(), taskResponse.isCompleted());
    }


    @Test
    public void updateTask_ExistingTask_ReturnsUpdatedTaskResponse() {
        // Prepare test data
        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("Updated Task Title");
        taskRequest.setDescription("Updated Task Description");
        taskRequest.setDueDate(LocalDateTime.now());

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setTitle("Original Task Title");
        existingTask.setDescription("Original Task Description");
        existingTask.setDueDate(LocalDateTime.now());
        existingTask.setStatus(TaskStatus.IN_PROGRESS);
        existingTask.setCompleted(false);

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(existingTask);

        // Execute the method
        TaskResponse taskResponse = taskService.updateTask(taskId, taskRequest);

        // Assert the result
        assertEquals(existingTask.getId(), taskResponse.getId());
        assertEquals(taskRequest.getTitle(), taskResponse.getTitle());
        assertEquals(taskRequest.getDescription(), taskResponse.getDescription());
        assertEquals(taskRequest.getDueDate(), taskResponse.getDueDate());
        assertEquals(existingTask.isCompleted(), taskResponse.isCompleted());
    }

    @Test
    public void getAllTasks_ValidData_ReturnsListOfTaskResponses() {
        // Prepare test data
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");
        task1.setTitle("Title 1");
        task1.setDescription("Description 1");
        task1.setDueDate(LocalDateTime.now());
        task1.setStatus(TaskStatus.IN_PROGRESS);
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");
        task2.setTitle("Title 2");
        task2.setDescription("Description 2");
        task2.setDueDate(LocalDateTime.now());
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setCompleted(true);

        tasks.add(task1);
        tasks.add(task2);

        Mockito.when(taskRepository.findAll()).thenReturn(tasks);

        // Execute the method
        List<TaskResponse> taskResponses = taskService.getAllTasks();

        // Assert the result
        assertEquals(tasks.size(), taskResponses.size());
        assertEquals(task1.getId(), taskResponses.get(0).getId());
        assertEquals(task1.getName(), taskResponses.get(0).getName());
        assertEquals(task1.getTitle(), taskResponses.get(0).getTitle());
        assertEquals(task1.getDescription(), taskResponses.get(0).getDescription());
        assertEquals(task1.getDueDate(), taskResponses.get(0).getDueDate());
        assertEquals(task1.isCompleted(), taskResponses.get(0).isCompleted());
        assertEquals(task2.getId(), taskResponses.get(1).getId());
        assertEquals(task2.getName(), taskResponses.get(1).getName());
        assertEquals(task2.getTitle(), taskResponses.get(1).getTitle());
        assertEquals(task2.getDescription(), taskResponses.get(1).getDescription());
        assertEquals(task2.getDueDate(), taskResponses.get(1).getDueDate());
        assertEquals(task2.isCompleted(), taskResponses.get(1).isCompleted());
    }

    @Test
    public void getTaskById_ExistingTaskId_ReturnsTaskResponse() {
        // Prepare test data
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setName("Test Task");
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setDueDate(LocalDateTime.now());
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setCompleted(false);

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Execute the method
        TaskResponse taskResponse = taskService.getTaskById(taskId);

        // Assert the result
        assertEquals(task.getId(), taskResponse.getId());
        assertEquals(task.getName(), taskResponse.getName());
        assertEquals(task.getTitle(), taskResponse.getTitle());
        assertEquals(task.getDescription(), taskResponse.getDescription());
        assertEquals(task.getDueDate(), taskResponse.getDueDate());
        assertEquals(task.isCompleted(), taskResponse.isCompleted());
    }

    @Test(expected = TaskNotFoundException.class)
    public void getTaskById_NonExistingTaskId_ThrowsTaskNotFoundException() {
        // Prepare test data
        Long taskId = 1L;

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Execute the method
        taskService.getTaskById(taskId);
    }

    @Test
    public void deleteTask_ExistingTaskId_DeletesTask() {
        // Prepare test data
        Long taskId = 1L;

        Mockito.when(taskRepository.existsById(taskId)).thenReturn(true);

        // Execute the method
        taskService.deleteTask(taskId);

        // Verify the method call
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(taskId);
    }


    @Test(expected = TaskNotFoundException.class)
    public void deleteTask_NonExistingTaskId_ThrowsTaskNotFoundException() {
        // Prepare test data
        Long taskId = 1L;

        Mockito.when(taskRepository.existsById(taskId)).thenReturn(false);

        // Execute the method
        taskService.deleteTask(taskId);
    }

        @Test
        public void filterTasks_FilterByDueDateAndStatus_ReturnsFilteredTaskResponses() {
            // Prepare test data
            LocalDateTime dueDate = LocalDateTime.now();
            TaskStatus status = TaskStatus.IN_PROGRESS;

            List<Task> filteredTasks = new ArrayList<>();
            Task task1 = new Task();
            task1.setId(1L);
            task1.setName("Task 1");
            task1.setTitle("Title 1");
            task1.setDescription("Description 1");
            task1.setDueDate(dueDate);
            task1.setStatus(status);
            task1.setCompleted(false);

            Task task2 = new Task();
            task2.setId(2L);
            task2.setName("Task 2");
            task2.setTitle("Title 2");
            task2.setDescription("Description 2");
            task2.setDueDate(dueDate);
            task2.setStatus(status);
            task2.setCompleted(true);

            filteredTasks.add(task1);
            filteredTasks.add(task2);

            Mockito.when(taskRepository.findByDueDateAndStatus(dueDate, status)).thenReturn(filteredTasks);

            // Execute the method
            List<TaskResponse> taskResponses = taskService.filterTasks(dueDate, status);

            // Assert the result
            assertEquals(filteredTasks.size(), taskResponses.size());
            assertEquals(task1.getId(), taskResponses.get(0).getId());
            assertEquals(task1.getName(), taskResponses.get(0).getName());
            assertEquals(task1.getTitle(), taskResponses.get(0).getTitle());
            assertEquals(task1.getDescription(), taskResponses.get(0).getDescription());
            assertEquals(task1.getDueDate(), taskResponses.get(0).getDueDate());
            assertEquals(task1.isCompleted(), taskResponses.get(0).isCompleted());
            assertEquals(task2.getId(), taskResponses.get(1).getId());
            assertEquals(task2.getName(), taskResponses.get(1).getName());
            assertEquals(task2.getTitle(), taskResponses.get(1).getTitle());
            assertEquals(task2.getDescription(), taskResponses.get(1).getDescription());
            assertEquals(task2.getDueDate(), taskResponses.get(1).getDueDate());
            assertEquals(task2.isCompleted(), taskResponses.get(1).isCompleted());
        }

    @Test
    public void filterTasks_FilterByDueDate_ReturnsFilteredTaskResponses() {
        // Prepare test data
        LocalDateTime dueDate = LocalDateTime.now();
        TaskStatus status = null;

        List<Task> filteredTasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");
        task1.setTitle("Title 1");
        task1.setDescription("Description 1");
        task1.setDueDate(dueDate);
        task1.setStatus(TaskStatus.IN_PROGRESS);
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");
        task2.setTitle("Title 2");
        task2.setDescription("Description 2");
        task2.setDueDate(dueDate);
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setCompleted(true);

        filteredTasks.add(task1);
        filteredTasks.add(task2);

        Mockito.when(taskRepository.findByDueDate(dueDate)).thenReturn(filteredTasks);

        // Execute the method
        List<TaskResponse> taskResponses = taskService.filterTasks(dueDate, status);

        // Assert the result
        assertEquals(filteredTasks.size(), taskResponses.size());
        assertEquals(task1.getId(), taskResponses.get(0).getId());
        assertEquals(task1.getName(), taskResponses.get(0).getName());
        assertEquals(task1.getTitle(), taskResponses.get(0).getTitle());
        assertEquals(task1.getDescription(), taskResponses.get(0).getDescription());
        assertEquals(task1.getDueDate(), taskResponses.get(0).getDueDate());
        assertEquals(task1.isCompleted(), taskResponses.get(0).isCompleted());
        assertEquals(task2.getId(), taskResponses.get(1).getId());
        assertEquals(task2.getName(), taskResponses.get(1).getName());
        assertEquals(task2.getTitle(), taskResponses.get(1).getTitle());
        assertEquals(task2.getDescription(), taskResponses.get(1).getDescription());
        assertEquals(task2.getDueDate(), taskResponses.get(1).getDueDate());
        assertEquals(task2.isCompleted(), taskResponses.get(1).isCompleted());
    }


    @Test
    public void filterTasks_FilterByStatus_ReturnsFilteredTaskResponses() {
        // Prepare test data
        LocalDateTime dueDate = null;
        TaskStatus status = TaskStatus.IN_PROGRESS;

        List<Task> filteredTasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");
        task1.setTitle("Title 1");
        task1.setDescription("Description 1");
        task1.setDueDate(LocalDateTime.now());
        task1.setStatus(status);
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");
        task2.setTitle("Title 2");
        task2.setDescription("Description 2");
        task2.setDueDate(LocalDateTime.now());
        task2.setStatus(status);
        task2.setCompleted(true);

        filteredTasks.add(task1);
        filteredTasks.add(task2);

        Mockito.when(taskRepository.findByStatus(status)).thenReturn(filteredTasks);

        // Execute the method
        List<TaskResponse> taskResponses = taskService.filterTasks(dueDate, status);

        // Assert the result
        assertEquals(filteredTasks.size(), taskResponses.size());
        assertEquals(task1.getId(), taskResponses.get(0).getId());
        assertEquals(task1.getName(), taskResponses.get(0).getName());
        assertEquals(task1.getTitle(), taskResponses.get(0).getTitle());
        assertEquals(task1.getDescription(), taskResponses.get(0).getDescription());
        assertEquals(task1.getDueDate(), taskResponses.get(0).getDueDate());
        assertEquals(task1.isCompleted(), taskResponses.get(0).isCompleted());
        assertEquals(task2.getId(), taskResponses.get(1).getId());
        assertEquals(task2.getName(), taskResponses.get(1).getName());
        assertEquals(task2.getTitle(), taskResponses.get(1).getTitle());
        assertEquals(task2.getDescription(), taskResponses.get(1).getDescription());
        assertEquals(task2.getDueDate(), taskResponses.get(1).getDueDate());
        assertEquals(task2.isCompleted(), taskResponses.get(1).isCompleted());
    }


    @Test
        public void filterTasks_NoFilter_ReturnsAllTaskResponses() {
            // Prepare test data
            LocalDateTime dueDate = null;
            TaskStatus status = null;

            List<Task> filteredTasks = new ArrayList<>();
            Task task1 = new Task();
            task1.setId(1L);
            task1.setName("Task 1");
            task1.setTitle("Title 1");
            task1.setDescription("Description 1");
            task1.setDueDate(LocalDateTime.now());
            task1.setStatus(TaskStatus.IN_PROGRESS);
            task1.setCompleted(false);

            Task task2 = new Task();
            task2.setId(2L);
            task2.setName("Task 2");
            task2.setTitle("Title 2");
            task2.setDescription("Description 2");
            task2.setDueDate(LocalDateTime.now());
            task2.setStatus(TaskStatus.COMPLETED);
            task2.setCompleted(true);

            filteredTasks.add(task1);
            filteredTasks.add(task2);

            Mockito.when(taskRepository.findAll()).thenReturn(filteredTasks);

            // Execute the method
            List<TaskResponse> taskResponses = taskService.filterTasks(dueDate, status);

            // Assert the result
            assertEquals(filteredTasks.size(), taskResponses.size());
            assertEquals(task1.getId(), taskResponses.get(0).getId());
            assertEquals(task1.getName(), taskResponses.get(0).getName());
            assertEquals(task1.getTitle(), taskResponses.get(0).getTitle());
            assertEquals(task1.getDescription(), taskResponses.get(0).getDescription());
            assertEquals(task1.getDueDate(), taskResponses.get(0).getDueDate());
            assertEquals(task1.isCompleted(), taskResponses.get(0).isCompleted());
            assertEquals(task2.getId(), taskResponses.get(1).getId());
            assertEquals(task2.getName(), taskResponses.get(1).getName());
            assertEquals(task2.getTitle(), taskResponses.get(1).getTitle());
            assertEquals(task2.getDescription(), taskResponses.get(1).getDescription());
            assertEquals(task2.getDueDate(), taskResponses.get(1).getDueDate());
            assertEquals(task2.isCompleted(), taskResponses.get(1).isCompleted());
        }

        private TaskResponse mapTaskToTaskResponse(Task task) {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setId(task.getId());
            taskResponse.setName(task.getName());
            taskResponse.setTitle(task.getTitle());
            taskResponse.setDescription(task.getDescription());
            taskResponse.setDueDate(task.getDueDate());
            taskResponse.setCompleted(task.isCompleted());
            return taskResponse;
        }

        private List<TaskResponse> mapTasksToTaskResponses(List<Task> tasks) {
            List<TaskResponse> taskResponses = new ArrayList<>();
            for (Task task : tasks) {
                taskResponses.add(mapTaskToTaskResponse(task));
            }
            return taskResponses;
        }
}
