package com.arman.journalapp.controller;

import com.arman.journalapp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntryMap = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntryMap.values());
    }

    @PostMapping
    public boolean createJournal(@RequestBody JournalEntry journalEntry){
        journalEntryMap.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable long id){
        return journalEntryMap.get(id);
    }

    @DeleteMapping("/id/{id}")
    public JournalEntry deleteJournalEntryById(@PathVariable long id){
        return journalEntryMap.remove(id);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable long id, @RequestBody JournalEntry journalEntry){
        return journalEntryMap.put(id, journalEntry);
    }
}
