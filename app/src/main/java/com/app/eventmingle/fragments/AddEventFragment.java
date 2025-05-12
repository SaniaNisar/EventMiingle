package com.app.eventmingle.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.eventmingle.R;
import com.app.eventmingle.models.Event;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class AddEventFragment extends Fragment {

    private EditText etEventName, etDescription, etTheme, etCategory, etVenue;
    private Button btnPickDateTime, btnSubmit;
    private String selectedDateTime = "";

    private final Calendar calendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etEventName = view.findViewById(R.id.et_event_name);
        etDescription = view.findViewById(R.id.et_description);
        etTheme = view.findViewById(R.id.et_theme);
        etCategory = view.findViewById(R.id.et_category);
        etVenue = view.findViewById(R.id.et_venue);
        btnPickDateTime = view.findViewById(R.id.btn_date_time);
        btnSubmit = view.findViewById(R.id.btn_submit_event);

        btnPickDateTime.setOnClickListener(v -> pickDateTime());

        btnSubmit.setOnClickListener(v -> submitEvent());
    }

    private void pickDateTime() {
        // First show date picker
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Then show time picker
                    TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                            (view1, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                selectedDateTime = sdf.format(calendar.getTime());

                                btnPickDateTime.setText("Selected: " + selectedDateTime);
                            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                    timePickerDialog.show();
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void submitEvent() {
        String name = etEventName.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();
        String theme = etTheme.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String venue = etVenue.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etEventName.setError("Event name required");
            return;
        }
        if (TextUtils.isEmpty(desc)) {
            etDescription.setError("Description required");
            return;
        }
        if (TextUtils.isEmpty(theme)) {
            etTheme.setError("Theme required");
            return;
        }
        if (TextUtils.isEmpty(category)) {
            etCategory.setError("Category required");
            return;
        }
        if (TextUtils.isEmpty(venue)) {
            etVenue.setError("Venue required");
            return;
        }
        if (TextUtils.isEmpty(selectedDateTime)) {
            Toast.makeText(getContext(), "Please select date & time", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get user info
        String uid = FirebaseUtils.getCurrentUserId();
        FirebaseUtils.getUsersRef().child(uid).get().addOnSuccessListener(snapshot -> {
            String hostName = "";
            if (snapshot.exists()) {
                hostName = snapshot.child("name").getValue(String.class);
            }

            String eventId = UUID.randomUUID().toString();
            Event event = new Event();
            event.setEventId(eventId);
            event.setEventName(name);
            event.setDescription(desc);
            event.setTheme(theme);
            event.setCategory(category);
            event.setVenue(venue);
            event.setDateTime(selectedDateTime);
            event.setHostId(uid);
            event.setHostName(hostName);
            event.setCreatedAt(System.currentTimeMillis());

            DatabaseReference eventRef = FirebaseUtils.getEventsRef().child(eventId);
            eventRef.setValue(event).addOnSuccessListener(unused -> {
                Toast.makeText(getContext(), "Event created!", Toast.LENGTH_SHORT).show();
                clearFields();
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Failed to create event: " + e.getMessage(), Toast.LENGTH_LONG).show();
            });

        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Failed to fetch user info", Toast.LENGTH_LONG).show();
        });
    }

    private void clearFields() {
        etEventName.setText("");
        etDescription.setText("");
        etTheme.setText("");
        etCategory.setText("");
        etVenue.setText("");
        selectedDateTime = "";
        btnPickDateTime.setText("Pick Date and Time");
    }
}
