package com.taskservice.service;

import com.taskservice.entity.Task;

public interface ITaskService {

    public Iterable<Task> getAll();

    public Iterable<Task> getUserTasks(Long id);

    public void create(Task task);

    public void update(Task task);

    public void delete(Long id);

    public Task getById(Long id);

}
