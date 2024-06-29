package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.List;

public class previous_entries extends Fragment {

    private RecyclerView recyclerView;
    private JournalEntryAdapter adapter;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.previous_entries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewPreviousEntries);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter with an empty list to prevent NullPointerException
        adapter = new JournalEntryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // Then load the data
        loadJournalEntries();
    }


    private void loadJournalEntries() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = auth.getCurrentUser().getUid();
            db.collection("users").document(userId).collection("entries")
                    .orderBy("entryDate", Query.Direction.DESCENDING) // Correct field name here
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<JournalEntry> entries = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                JournalEntry entry = document.toObject(JournalEntry.class);
                                if (entry.getEntryDate() != null) {  // Updated to use getEntryDate
                                    entries.add(entry);
                                } else {
                                    Log.e("Data Error", "Date is null for entry with ID: " + document.getId());
                                }
                            }
                            if (!entries.isEmpty()) {
                                adapter.updateData(entries);
                            } else {
                                Log.d("Data Load", "No entries loaded");
                                Toast.makeText(getContext(), "No entries found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("Firestore Error", "Error getting documents: ", task.getException());
                            Toast.makeText(getContext(), "Error getting documents: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_LONG).show();
        }
    }

}
