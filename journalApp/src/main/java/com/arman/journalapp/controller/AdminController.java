package com.arman.journalapp.controller;

import com.arman.journalapp.entity.User;
import com.arman.journalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        users = users.stream().peek(user -> user.setPassword("")).toList();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> addAdminUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveNewAdminUser(user), HttpStatus.CREATED);
    }
}
