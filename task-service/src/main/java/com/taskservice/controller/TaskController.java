package com.taskservice.controller;

import com.taskservice.entity.Task;
import com.taskservice.entity.TaskDTO;
import com.taskservice.service.ITaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/tasks")
public class TaskController  {

    @Autowired
    private ITaskService taskService;

    @GetMapping()
    public ResponseEntity<Iterable<Task>> getAll() {
        var tasks = taskService.getAll();
        return tasks.spliterator().estimateSize() > 0 ? ResponseEntity.ok(tasks) : ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<Iterable<Task>> getUserTasks(@RequestParam(value = "id") Long id) {
        var tasks = taskService.getUserTasks(id);
        return tasks.spliterator().estimateSize() > 0 ? ResponseEntity.ok(tasks)
                : ResponseEntity.noContent().build();
    }

    @GetMapping("/complete")
    public ResponseEntity<Task> complete(@RequestParam(value = "id") Long id) {
        if (id == 0 || id == null)
            return ResponseEntity.badRequest().build();

        var task = taskService.complete(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        if (id == 0 || id == null)
            return ResponseEntity.badRequest().build();

        var task = taskService.getById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Task> create(@RequestBody TaskDTO task) {
        if (task == null || task.getId() == 0 || task.getId() == null)
            return ResponseEntity.badRequest().build();

        Task userTask = new Task(task);
        taskService.create(userTask);
        log.info("Task created successfully");
        return ResponseEntity.ok(userTask);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody TaskDTO task) {
        if (task == null || task.getId() == 0 || task.getId() == null)
            return ResponseEntity.badRequest().build();
        var foundTask = taskService.getById(task.getId());
        if (foundTask == null)
            return ResponseEntity.notFound().build();

        Task userTask = new Task(task);
        taskService.update(userTask);
        log.info("Task updated successfully");
        return ResponseEntity.ok(userTask);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) {
        if (id == 0 || id == null)
            return ResponseEntity.badRequest().build();
        var foundTask = taskService.getById(id);
        if (foundTask == null)
            return ResponseEntity.notFound().build();

        taskService.delete(id);
        log.info("Task {} deleted successfully", id);
        return ResponseEntity.ok("Task " + id + " deleted successfully");
    }

}
