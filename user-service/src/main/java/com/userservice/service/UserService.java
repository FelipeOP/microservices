package com.userservice.service;

import com.userservice.entity.User;
import com.userservice.models.Task;
import com.userservice.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Iterable<Task> getUserTasks(Long id) {
        String tasksUri = String.format("http://localhost:8081/api/v1/tasks/user?id=%d", id);
        log.info("searching for {} in {}", id, tasksUri);
        Iterable<Task> foundTasks = null;
        try {
            foundTasks = restTemplate.getForObject(tasksUri, Iterable.class);
        } catch (Exception e) {
            log.info("RestClientException - {}", e.toString());
        }
        return foundTasks;
    }

}
