package com.example.finalproject;
import java.util.Date;

public class JournalEntry {
    private Date entryDate;  // No renaming needed if this matches Firestore
    private String content;  // No renaming needed if this matches Firestore

    // Constructor
    public JournalEntry() {}

    // Getters and setters to match Firestore field names
    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
