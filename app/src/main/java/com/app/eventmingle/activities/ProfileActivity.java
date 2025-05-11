package com.app.eventmingle.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eventmingle.R;
import com.app.eventmingle.adapters.EventAdapter;
import com.app.eventmingle.models.Event;
import com.app.eventmingle.models.User;
import com.app.eventmingle.utils.FirebaseUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.*;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 101;

    private EditText etName, etEmail, etPhone;
    private ImageView profileImage;
    private Button btnSave;
    private RecyclerView recyclerViewEvents;

    private Uri imageUri;
    private StorageReference storageRef;
    private DatabaseReference userRef;
    private User currentUser;

    private List<Event> eventList = new ArrayList<>();
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
//        profileImage = findViewById(R.id.profileImage);
        btnSave = findViewById(R.id.btnSave);
        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);

        String uid = FirebaseUtils.getCurrentUserId();
        userRef = FirebaseUtils.getUsersRef().child(uid);
        storageRef = FirebaseStorage.getInstance().getReference("profile_images");

//        profileImage.setOnClickListener(v -> openImagePicker());
        btnSave.setOnClickListener(v -> uploadAndSaveProfile());

        setupRecyclerView();
        loadUserProfile();
    }

    private void setupRecyclerView() {
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(eventList);
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri); // preview immediately
        }
    }

    private void uploadAndSaveProfile() {
        if (currentUser == null) return;

        currentUser.setName(etName.getText().toString().trim());
        currentUser.setEmail(etEmail.getText().toString().trim());
        currentUser.setPhoneNumber(etPhone.getText().toString().trim());

        if (imageUri != null) {
            String uid = FirebaseUtils.getCurrentUserId();
            StorageReference fileRef = storageRef.child(uid + ".jpg");

            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        currentUser.setProfileImageUrl(uri.toString());
                        saveUserProfile();
                    }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        } else {
            saveUserProfile();
        }
    }

    private void saveUserProfile() {
        userRef.setValue(currentUser)
                .addOnSuccessListener(unused -> Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void loadUserProfile() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                if (currentUser == null) return;

                etName.setText(currentUser.getName());
                etEmail.setText(currentUser.getEmail());
                etPhone.setText(currentUser.getPhoneNumber());

                if (currentUser.getProfileImageUrl() != null && !currentUser.getProfileImageUrl().isEmpty()) {
                    Glide.with(ProfileActivity.this)
                            .load(currentUser.getProfileImageUrl())
                            .placeholder(R.drawable.ic_person_24)
                            .into(profileImage);
                }

                // Load created events
                if (currentUser.getCreatedEvents() != null) {
                    for (String eventId : currentUser.getCreatedEvents().keySet()) {
                        FirebaseUtils.getEventsRef().child(eventId)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Event event = snapshot.getValue(Event.class);
                                        if (event != null) {
                                            eventList.add(event);
                                            eventAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
