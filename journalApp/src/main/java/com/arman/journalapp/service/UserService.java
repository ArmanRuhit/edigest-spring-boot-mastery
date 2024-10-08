package com.arman.journalapp.service;

import com.arman.journalapp.entity.JournalEntry;
import com.arman.journalapp.entity.User;
import com.arman.journalapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
