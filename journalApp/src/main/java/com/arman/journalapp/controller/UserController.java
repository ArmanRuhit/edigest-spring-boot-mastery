package com.arman.journalapp.controller;

import com.arman.journalapp.entity.User;
import com.arman.journalapp.entity.User;
import com.arman.journalapp.service.UserService;
import com.arman.journalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping
    public ResponseEntity<User> createJournal(@RequestBody User user){
        userService.saveUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId id){
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

//    @DeleteMapping("/id/{id}")
//    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId id){
//        userService.deleteUserById(id);
//        return ResponseEntity.notFound().build();
//    }

    @PutMapping("/id/{id}")
    public ResponseEntity<User> updateUserById(@RequestBody User newUser){

//        User old = userService.getUserById(id);
        User oldUser = userService.findByUsername(newUser.getUsername());
        if(oldUser != null){
            oldUser.setPassword(newUser.getPassword());
            oldUser = userService.saveUser(oldUser);
        }

        return oldUser == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(oldUser);
    }
}
