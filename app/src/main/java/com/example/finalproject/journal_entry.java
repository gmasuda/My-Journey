package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class journal_entry extends Fragment {

    private EditText editTextJournalEntry;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();
        return inflater.inflate(R.layout.journal_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextJournalEntry = view.findViewById(R.id.editTextJournalEntry);
        Button enterButton = view.findViewById(R.id.button);

        enterButton.setOnClickListener(v -> {
            String journalText = editTextJournalEntry.getText().toString();
            if (!journalText.isEmpty()) {
                saveJournalEntry(journalText);
            } else {
                Toast.makeText(getContext(), "Please enter some text", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveJournalEntry(String journalText) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Map<String, Object> entry = new HashMap<>();
            entry.put("content", journalText);  // Adjust field name based on your Firestore
            entry.put("entryDate", new Date()); // Adjust field name based on your Firestore

            FirebaseFirestore.getInstance().collection("users").document(userId)
                    .collection("entries").add(entry)
                    .addOnSuccessListener(documentReference -> Toast.makeText(getContext(), "Entry saved successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error saving entry", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
