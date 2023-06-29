package com.api.taskManagement.data.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private String name;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private boolean completed;
}
