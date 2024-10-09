package com.arman.journalapp.controller;

import com.arman.journalapp.entity.JournalEntry;
import com.arman.journalapp.entity.User;
import com.arman.journalapp.service.JournalEntryService;
import com.arman.journalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalEntryController {
    private final JournalEntryService journalEntryService;
    private final UserService userService;


    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if (journalEntries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry journalEntry) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        JournalEntry savedJournalEntry =journalEntryService.saveJournalEntry(journalEntry, userName);

        return new ResponseEntity<>(savedJournalEntry, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id){
        JournalEntry journalEntry = journalEntryService.getJournalEntryByIdAndUserName(id);
        if(journalEntry == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(journalEntry);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        journalEntryService.deleteJournalEntryById(id, userName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newJournalEntry) {

        JournalEntry old = journalEntryService.getJournalEntryById(id);

        if(old != null){
            String content = newJournalEntry.getContent() != null && !newJournalEntry.getContent().isEmpty() ?
                    newJournalEntry.getContent() : old.getContent();

            String title = !newJournalEntry.getTitle().isEmpty() ?
                    newJournalEntry.getTitle() : old.getTitle();

            old.setContent(content);
            old.setTitle(title);
            old.setDate(LocalDateTime.now());

            old = journalEntryService.saveJournalEntry(old);
        }

        return old == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(old);
    }
}
