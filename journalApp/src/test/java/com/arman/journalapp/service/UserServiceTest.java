package com.arman.journalapp.service;

import com.arman.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, bCryptPasswordEncoder);
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void saveNewUser() {
//
//    }
//
//    @Test
//    void saveNewAdminUser() {
//    }
//
//    @Test
//    void saveUser() {
//    }

    @Test
    void shouldGetAllUsers() {
        userService.getAllUsers();
        verify(userRepository).findAll();
    }

//    @Test
//    void getUserById() {
//    }
//
//    @Test
//    void getUserByUserName() {
//    }
//
//    @Test
//    void deleteUserById() {
//    }
//
//    @Test
//    void deleteUserByUserName() {
//    }
//
//    @Test
//    void findByUsername() {
//    }
}