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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

public class home_page extends Fragment {

    private FirebaseAuth mAuth;
    private EditText emailField;  // Declare the EditText for email
    private EditText passwordField;  // Adjust visibility to class level if needed elsewhere

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize fields
        emailField = view.findViewById(R.id.emailField);  // Make sure this ID matches your XML
        passwordField = view.findViewById(R.id.passwordText);  // Ensure this ID is correct
        Button loginButton = view.findViewById(R.id.button_navigate_entry_page);
        Button signUpButton = view.findViewById(R.id.button_navigate_sign_up_page);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Navigate to entry page
                                    NavController navController = Navigation.findNavController(getActivity(), R.id.button_navigate_entry_page); // Adjust according to your nav host ID
                                    navController.navigate(R.id.action_home_page_to_entry_page);
                                    Toast.makeText(getContext(), "Login successful", Toast.LENGTH_LONG).show();
                                } else {
                                    // Display a message to the user.
                                    if (isAdded()) {
                                        Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    if (isAdded()) {
                        Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.button_navigate_sign_up_page);  // Adjust according to your nav host ID
                navController.navigate(R.id.action_home_page_to_sign_up_page);
            }
        });
    }
}
