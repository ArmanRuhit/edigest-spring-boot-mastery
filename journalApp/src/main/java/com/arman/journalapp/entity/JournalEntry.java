package com.arman.journalapp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JournalEntry {

    private long id;
    private String title;
    private String content;
}
