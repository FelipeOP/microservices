package com.taskservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import com.taskservice.entity.Task;
import com.taskservice.repository.ITaskRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TaskTest {

    @Autowired
    private ITaskRepository taskRepository;

    @Test
    @Rollback(false)
    public void testSaveTask() {
        Task task = new Task(11L, "Microservices", "CRUD users\nCRUD tasks", true, 1L);
        var newTask = taskRepository.save(task);
        assertNotNull(newTask);
    }

    @Test
    @Rollback(false)
    public void testSaveMultipleTask() {
        Task task2 = new Task(12L, "Unit test", "Use JUnit", false, 1L);
        Task task3 = new Task(13L, "Documentaton", "Complete documentation", false, 1L);
        var size = taskRepository.saveAll(List.of(task2, task3)).spliterator().estimateSize();
        assertEquals(2, size);
    }

    @Test
    public void testFindTaskById() {
        Task task = taskRepository.findById(11L).orElse(null);
            assertNotNull(task);
    }

    @Test
    public void testFindTaskByIdWhenIsNotFound() {
        Task task = taskRepository.findById(0L).orElse(null);
        assertNull(task);
    }

    @Test
    @Rollback(false)
    public void testUpdateTask() {
        String actual = taskRepository.findById(12L).orElse(null).getDescription();
        Task newTaskInformation = new Task(12L, "Unit tests", "Use JUnit5", true, 1L);
        String updatedTask = taskRepository.save(newTaskInformation).getDescription();
        assertNotEquals(actual, updatedTask);
    }

    @Test
    public void testDeleteTask() {
        taskRepository.deleteById(11L);
        assertNull(taskRepository.findById(11L).orElse(null));
    }

    @Test
    public void testDeleteTaskWhenNotExists() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> {
                    taskRepository.deleteById(0L);
                });
    }

    @Test
    public void testGetAllUserTasks() {
        var size = taskRepository.findByUserId(1L).spliterator().estimateSize();
        assertEquals(3, size);
    }

    @Test
    public void testGetTaskAndUserDontHave() {
        var userTasks = taskRepository.findByUserId(4L);
        assertIterableEquals(Collections.emptyList(), userTasks);
    }

}
