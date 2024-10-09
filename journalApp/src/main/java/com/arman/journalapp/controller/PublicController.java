package com.arman.journalapp.controller;

import com.arman.journalapp.entity.User;
import com.arman.journalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;


    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        user = userService.saveNewUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
