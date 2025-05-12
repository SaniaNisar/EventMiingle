package com.app.eventmingle.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.app.eventmingle.models.Guest;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

public class GuestsFragment extends Fragment {

    private EditText etGuestEmail;
    SharedPreferences sharedPreferences;
    private Button btnInviteGuest;
    private String eventId; // Automatically fetched from latest created event

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etGuestEmail = view.findViewById(R.id.et_guest_email);
        btnInviteGuest = view.findViewById(R.id.btn_invite_guest);
        sharedPreferences = requireActivity().getSharedPreferences("EventPrefs", Context.MODE_PRIVATE);

        // Automatically get the latest created event by this user
        fetchLastCreatedEventId();

        btnInviteGuest.setOnClickListener(v -> inviteGuest());
    }

    /*private void fetchLastCreatedEventId() {
        FirebaseUtils.getEventsRef().orderByChild("createdAt")
                .limitToLast(1)
                .get().addOnSuccessListener(snapshot -> {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String hostId = child.child("hostId").getValue(String.class);
                        if (hostId != null && hostId.equals(FirebaseUtils.getCurrentUserId())) {
                            eventId = child.getKey();
                        }
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error fetching event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
     */

    private void fetchLastCreatedEventId() {
        eventId = sharedPreferences.getString("eventId", null);

        if (eventId != null) {
            // Event found in SharedPreferences, you can use eventId
            // Optional: You can also display event details if needed
            Toast.makeText(getContext(), "Event loaded: " + eventId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "No event found. Please create one first.", Toast.LENGTH_SHORT).show();
        }
    }


    /*private void inviteGuest() {
        String email = etGuestEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etGuestEmail.setError("Email required");
            return;
        }

        if (TextUtils.isEmpty(eventId)) {
            Toast.makeText(getContext(), "No event found. Please create one first.", Toast.LENGTH_SHORT).show();
            return;
        }

        String hostId = FirebaseUtils.getCurrentUserId();
        String guestId = UUID.randomUUID().toString();

        Guest guest = new Guest(guestId, hostId, eventId, email);

        DatabaseReference ref = FirebaseUtils.getGuestsRef().child(guestId);
        ref.setValue(guest).addOnSuccessListener(unused -> {
            Toast.makeText(getContext(), "Invitation sent to " + email, Toast.LENGTH_SHORT).show();
            etGuestEmail.setText("");
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Failed to invite: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
     */
    private void inviteGuest() {
        String email = etGuestEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etGuestEmail.setError("Email required");
            return;
        }

        if (TextUtils.isEmpty(eventId)) {
            Toast.makeText(getContext(), "No event found. Please create one first.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save guest in Firebase
        String hostId = FirebaseUtils.getCurrentUserId();
        String guestId = UUID.randomUUID().toString();

        Guest guest = new Guest(guestId, hostId, eventId, email);

        DatabaseReference ref = FirebaseUtils.getGuestsRef().child(guestId);
        ref.setValue(guest).addOnSuccessListener(unused -> {
            sendEmailInvite(email);
            Toast.makeText(getContext(), "Invitation saved & email intent sent", Toast.LENGTH_SHORT).show();
            etGuestEmail.setText("");
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Failed to invite: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void sendEmailInvite(String recipientEmail) {
        String hostName = sharedPreferences.getString("hostName", "Your host");
        String eventTitle = sharedPreferences.getString("eventName", "Our Event");
        String description = sharedPreferences.getString("eventDescription", "You're invited!");
        String venue = sharedPreferences.getString("eventVenue", "To be announced");
        String dateTime = sharedPreferences.getString("eventDateTime", "Check your calendar!");

        String subject = "Invitation to " + eventTitle;
        String message = "Dear Guest,\n\n"
                + "Greetings from " + hostName + "!\n\n"
                + "You are warmly invited to our upcoming event:\n\n"
                + "🎉 *" + eventTitle + "*\n"
                + "📍 Venue: " + venue + "\n"
                + "📅 Date & Time: " + dateTime + "\n\n"
                + "📝 Description:\n" + description + "\n\n"
                + "We would be honored by your presence.\n\n"
                + "Best regards,\n" + hostName;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + recipientEmail));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No email app found to send invitation.", Toast.LENGTH_SHORT).show();
        }
    }
}
