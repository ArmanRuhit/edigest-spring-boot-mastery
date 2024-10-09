package com.arman.journalapp.service;

import com.arman.journalapp.entity.User;
import com.arman.journalapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("ROLE_USER"));
        return userRepository.save(user);
    }

    public User saveNewAdminUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("ROLE_USER", "ROLE_ADMIN"));
        return userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUserName(String username) {
        userRepository.deleteByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
