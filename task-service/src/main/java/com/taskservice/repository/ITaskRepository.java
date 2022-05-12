package com.taskservice.repository;

import com.taskservice.entity.Task;

import org.springframework.data.repository.CrudRepository;

public interface ITaskRepository extends CrudRepository<Task, Long> {

    Iterable<Task> findByUserId(Long id);
}
