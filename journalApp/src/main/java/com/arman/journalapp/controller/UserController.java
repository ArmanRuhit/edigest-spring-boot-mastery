package com.arman.journalapp.controller;

import com.arman.journalapp.entity.User;
import com.arman.journalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> journalEntries = userService.getAllUsers();
        if (journalEntries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
    }



    @GetMapping("/user-name")
    public ResponseEntity<User> getUserByUsername(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(userName);
        if(user == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByUserName(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteUserByUserName(userName);
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User newUser) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User oldUser = userService.findByUsername(username);
        if(oldUser != null){
            oldUser.setPassword(newUser.getPassword());
            oldUser = userService.saveNewUser(oldUser);
        }

        return oldUser == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(oldUser);
    }
}
