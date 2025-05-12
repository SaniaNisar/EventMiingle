package com.app.eventmingle.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.eventmingle.R;
import com.app.eventmingle.models.User;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone;
    private Button btnSave;

    private DatabaseReference userRef;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_setup);

        // Bind views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);

        // Get user reference from Firebase
        String uid = FirebaseUtils.getCurrentUserId();
        userRef = FirebaseUtils.getUsersRef().child(uid);

        // Load user data
        loadUserProfile();

        // Save profile updates
        btnSave.setOnClickListener(v -> saveUserProfile());
    }

    private void loadUserProfile() {
        if (userRef == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                if (currentUser == null) {
                    Log.e("ProfileActivity", "No user data found");
                    return;
                }

                // Populate fields
                etName.setText(currentUser.getName() != null ? currentUser.getName() : "");
                etEmail.setText(currentUser.getEmail() != null ? currentUser.getEmail() : "");
                etPhone.setText(currentUser.getPhoneNumber() != null ? currentUser.getPhoneNumber() : "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error loading profile: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserProfile() {
        if (currentUser == null) {
            currentUser = new User(); // create new if null
        }

        currentUser.setName(etName.getText().toString().trim());
        currentUser.setEmail(etEmail.getText().toString().trim());
        currentUser.setPhoneNumber(etPhone.getText().toString().trim());

        userRef.setValue(currentUser)
                .addOnSuccessListener(unused -> Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error saving profile: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
