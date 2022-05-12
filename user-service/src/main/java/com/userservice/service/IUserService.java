package com.userservice.service;

import com.userservice.entity.User;

public interface IUserService {

    public Iterable<User> getAll();

    public void create(User user);

    public void update(User user);

    public void delete(Long id);

    public User getById(Long id);
}
