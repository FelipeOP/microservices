package com.userservice.controller;

import com.userservice.entity.User;
import com.userservice.entity.UserDTO;
import com.userservice.models.Task;
import com.userservice.service.IUserService;

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
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping()
    public ResponseEntity<Iterable<User>> getAll() {
        var users = userService.getAll();
        return users.spliterator().estimateSize() > 0 ? ResponseEntity.ok(users) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        if (id == 0 || id == null)
            return ResponseEntity.badRequest().build();

        var foundUser = userService.getById(id);
        return foundUser != null ? ResponseEntity.ok(foundUser) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<Iterable<Task>> getUserTasks(@PathVariable Long id) {
        if (id == 0 || id == null)
            return ResponseEntity.badRequest().build();
        var foundUser = userService.getById(id);
        if (foundUser == null)
            return ResponseEntity.notFound().build();

        var userTasks = userService.getUserTasks(id);
        return userTasks != null ? ResponseEntity.ok(userTasks) : ResponseEntity.status(503).build();
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody UserDTO user) {
        if (user == null || user.getId() == null || user.getId() == 0)
            return ResponseEntity.badRequest().build();

        User persistenceUser = new User(user);
        userService.create(persistenceUser);
        log.info("User created successfully");
        return ResponseEntity.ok(persistenceUser);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody UserDTO user) {
        if (user == null || user.getId() == null || user.getId() == 0)
            return ResponseEntity.badRequest().build();
        var foundUser = userService.getById(user.getId());
        if (foundUser == null)
            return ResponseEntity.notFound().build();

        User persistenceUser = new User(user);
        userService.update(persistenceUser);
        log.info("User updated successfully");
        return ResponseEntity.ok(persistenceUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) {
        if (id == 0 || id == null)
            return ResponseEntity.badRequest().build();
        var foundUser = userService.getById(id);
        if (foundUser == null)
            return ResponseEntity.notFound().build();

        userService.delete(id);
        log.info("User {} deleted successfully", id);
        return ResponseEntity.ok("User " + id + " deleted successfully");
    }

}
