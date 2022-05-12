package com.taskservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long id;

    private String title;
    private String description;
    private boolean isDone = false;

    private Long userId;

}
