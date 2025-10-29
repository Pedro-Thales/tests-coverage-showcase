package com.pedrovisk.service;

import com.pedrovisk.model.Status;
import com.pedrovisk.model.UserEntity;
import com.pedrovisk.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserRepository userRepository;
    UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }


    @Test
    void testFindById_Success() {
        var userEntity = new UserEntity(1L, "user", "pass", Status.ACTIVE);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));

        var result = userService.findById(1L);
        assertNotNull(result);

    }

    @Test
    void testFindById_Failure() {

        assertThrows(Exception.class, () -> userService.findById(1L));

    }

    @Test
    void testFindByUsername_Success() {
        var userEntity = new UserEntity(1L, "user", "pass", Status.ACTIVE);

        when(userRepository.findByUsername("user"))
                .thenReturn(java.util.Optional.of(userEntity));

        var result = userService.findByUsername("user");
        assertNotNull(result);

    }

    @Test
    void testFindByUsername_Failure() {

        assertThrows(Exception.class, () -> userService.findByUsername("user"));

    }


    @Test
    void testFindByUsernameAndIsActive_Success() {
        var userEntity = new UserEntity(1L, "user", "pass", Status.ACTIVE);

        when(userRepository.findByUsername("user"))
                .thenReturn(java.util.Optional.of(userEntity));

        var result = userService.findByUsernameAndIsActive("user");
        assertNotNull(result);

    }

    @Test
    void testFindByUsernameAndIsActive_Inactive() {

        var userEntity = new UserEntity(1L, "user", "pass", Status.INACTIVE);
        when(userRepository.findByUsername("user"))
                .thenReturn(java.util.Optional.of(userEntity));

        assertThrows(RuntimeException.class, () -> userService.findByUsernameAndIsActive("user"));

    }




}