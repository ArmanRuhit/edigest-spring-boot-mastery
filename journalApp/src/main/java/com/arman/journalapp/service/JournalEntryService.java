package com.arman.journalapp.service;

import com.arman.journalapp.entity.JournalEntry;
import com.arman.journalapp.entity.User;
import com.arman.journalapp.repository.JournalEntryRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;

    @Transactional
    public JournalEntry saveJournalEntry(JournalEntry journalEntry, String userName) {
        journalEntry.setDate(LocalDateTime.now());
        User user = userService.findByUsername(userName);
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(savedJournalEntry);
        userService.saveUser(user);
        return savedJournalEntry;
    }

    public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        return journalEntryRepository.save(journalEntry);
    }



    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry getJournalEntryById(ObjectId id) {
        return journalEntryRepository.findById(id).orElse(null);
    }

    public JournalEntry getJournalEntryByIdAndUserName(ObjectId id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(userName);
        if(user == null) {
            return null;
        }
        return user.getJournalEntries().stream().filter(journalEntry -> journalEntry.getId().equals(id)).findFirst().orElse(null);
    }

    public void deleteJournalEntryById(ObjectId id, String userName) {
        User user = userService.findByUsername(userName);
        user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
}
