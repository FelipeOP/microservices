package com.userservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.userservice.entity.User;
import com.userservice.repository.IUserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    @Rollback(false)
    public void testSaveUser() {
        User user = new User(1L, "Felipe", "Salamanca", "user@email.com");
        var newUser = userRepository.save(user);
        assertNotNull(newUser);
    }

    @Test
    @Rollback(false)
    public void testSaveMultipleUsers() {
        User user2 = new User(2L, "Luis", "Lopez", "user@email.com");
        User user3 = new User(3L, "Juan", "Perez", "user@email.com");
        User user4 = new User(4L, "Laura", "Vivas", "user@email.com");
        var size = userRepository.saveAll(List.of(user2, user3, user4)).spliterator().estimateSize();
        assertEquals(3, size);
    }

    @Test
    public void testFindUserById() {
        User user = userRepository.findById(1L).orElse(null);
        assertNotNull(user);
    }

    @Test
    public void testFindUserByIdWhenIsNotFound() {
        User user = userRepository.findById(0L).orElse(null);
        assertNull(user);
    }

    @Test
    @Rollback(false)
    public void testUpdateUser() {
        String actual = userRepository.findById(1L).orElse(null).getEmail();
        User newUserInformation = new User(1L, "Felipe", "Salamanca", "feljpe000@gmail.com");
        String updatedUser = userRepository.save(newUserInformation).getEmail();
        assertNotEquals(actual, updatedUser);
    }

    @Test
    public void testDeleteUser() {
        userRepository.deleteById(4L);
        assertNull(userRepository.findById(4L).orElse(null));
    }

    @Test
    public void testDeleteUserWhenNotExists() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> {
                    userRepository.deleteById(0L);
                });
    }
}
