package com.api.taskManagement.data.repository;

import com.api.taskManagement.data.models.Task;
import com.api.taskManagement.data.models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDueDateAndStatus(LocalDateTime dueDate, TaskStatus status);

    List<Task> findByDueDate(LocalDateTime dueDate);

    List<Task> findByStatus(TaskStatus status);
}
