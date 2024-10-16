package com.arman.journalapp.repository;

import com.arman.journalapp.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
            .withReuse(true);

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Order(1)
    @Test
    void checkConnection(){
        assertThat(mongoDBContainer.isCreated()).isTrue();
        assertThat(mongoDBContainer.isRunning()).isTrue();
    }

    @Order(2)
    @Test
    void createUser() {
        //given
        User user = User.builder()
                .username("arman")
                .password("123")
                .build();

        //when
        User savedUser = userRepository.save(user);

        //then
        assertNotNull(savedUser);
    }

    @Order(3)
    @Test
    void shouldFindUserByUsername() {
        User user = userRepository.findByUsername("arman");
        assertNotNull(user);
    }

    @Order(4)
    @Test
    void shouldNotFindUserByUsername() {
        User user = userRepository.findByUsername("ruhit");
        assertNull(user);
    }

    @Order(5)
    @Test
    void deleteByUsername() {
        User user = userRepository.findByUsername("arman");
        userRepository.deleteByUsername(user.getUsername());
        User deletedUser = userRepository.findByUsername("arman");
        assertNull(deletedUser);
    }
}