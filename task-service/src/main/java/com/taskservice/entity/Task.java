package com.taskservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    private String description;
    private boolean isDone = false;

    private Long userId;

    public Task(TaskDTO task) {
        id = task.getId();
        title = task.getTitle();
        description = task.getDescription();
        isDone = task.isDone();
        userId = task.getUserId();
    }
}
