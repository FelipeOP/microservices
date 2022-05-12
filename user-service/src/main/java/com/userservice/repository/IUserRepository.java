package com.userservice.repository;

import com.userservice.entity.User;

import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, Long> {
}
