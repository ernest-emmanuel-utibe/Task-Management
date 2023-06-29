package com.api.taskManagement.data.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String name;
    private String title;
    private String description;
    private LocalDateTime dueDate;

}
