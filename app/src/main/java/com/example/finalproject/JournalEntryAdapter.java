package com.example.finalproject;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class JournalEntryAdapter extends RecyclerView.Adapter<JournalEntryAdapter.ViewHolder> {
    private List<JournalEntry> journalEntries;  // Made non-final to allow modification

    public JournalEntryAdapter(List<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View journalEntryView = layoutInflater.inflate(R.layout.item_journal, parent, false);
        return new ViewHolder(journalEntryView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalEntry journalEntry = journalEntries.get(position);
        if (journalEntry.getEntryDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            holder.textEntryDate.setText(format.format(journalEntry.getEntryDate()));
        } else {
            holder.textEntryDate.setText("No date");
        }
        holder.textContent.setText(journalEntry.getContent() != null ? journalEntry.getContent() : "No content");
    }


    @Override
    public int getItemCount() {
        return journalEntries.size();
    }

    public void updateData(List<JournalEntry> newEntries) {
        if (newEntries != null) { // Check if new entries are not null
            journalEntries.clear();  // Clear the existing data
            journalEntries.addAll(newEntries);  // Add all the new entries
            notifyDataSetChanged();  // Notify the adapter that the data set has changed
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textEntryDate;
        public TextView textContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textEntryDate = itemView.findViewById(R.id.textEntryDate);
            textContent = itemView.findViewById(R.id.textContent);
        }
    }
}
