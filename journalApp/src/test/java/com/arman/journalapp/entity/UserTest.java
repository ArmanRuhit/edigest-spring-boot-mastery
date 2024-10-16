package com.arman.journalapp.entity;

import com.arman.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class UserTest {

    @Test
    void setJournalEntries() {
        User user = new User();
        user.setJournalEntries(new ArrayList<>());
    }
}