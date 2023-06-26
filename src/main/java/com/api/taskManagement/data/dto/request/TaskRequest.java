package com.api.taskManagement.data.dto.request;

import com.api.taskManagement.data.models.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String title;

    private String description;

    private LocalDateTime dueDate;

//    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
