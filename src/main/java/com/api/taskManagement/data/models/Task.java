package com.api.taskManagement.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Ernest Emmanuel Utibe
 *
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String title;

    @Column(length = 65450, columnDefinition = "text")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    private TaskStatus status;

    private boolean completed;




//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//    private long program;
//    private String name;
//    private long createdBy;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime createdTime;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime startTime;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime deadline;
//
//    private long modifiedBy;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime modifiedTime;
//    private String status;
//
//    @Column(length = 65450, columnDefinition = "text")
//    private String description;
}
