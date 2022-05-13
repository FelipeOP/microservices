package com.taskservice.service;

import com.taskservice.entity.Task;
import com.taskservice.repository.ITaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    @Transactional
    public void create(Task task) {
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void update(Task task) {
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Task> getUserTasks(Long id) {
        return taskRepository.findByUserId(id);
    }

    @Override
    public Iterable<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task complete(Long id) {
        var task = taskRepository.findById(id).orElse(null);
        if (task == null)
            return task;
        task.setDone(!task.isDone());
        return taskRepository.save(task);
    }

}
