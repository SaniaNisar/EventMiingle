package com.app.eventmingle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.app.eventmingle.R;
import com.app.eventmingle.models.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventFragment extends Fragment {

    private EditText editTextTitle, editTextDescription, editTextDate, editTextTime, editTextVenue;
    private Button buttonSaveEvent;
    private DatabaseReference eventsRef;

    public AddEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_event, container, false);

        editTextTitle = rootView.findViewById(R.id.editTextTitle);
        editTextDescription = rootView.findViewById(R.id.editTextDescription);
        editTextDate = rootView.findViewById(R.id.editTextDate);
        editTextTime = rootView.findViewById(R.id.editTextTime);
        editTextVenue = rootView.findViewById(R.id.editTextVenue);
        buttonSaveEvent = rootView.findViewById(R.id.buttonSaveEvent);

        // Get the current user's ID from Firebase Authentication
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Modify the reference to save events under this user's node
        eventsRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("events");

        buttonSaveEvent.setOnClickListener(v -> saveEvent());

        return rootView;
    }

    private void saveEvent() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = editTextDate.getText().toString();
        String time = editTextTime.getText().toString();
        String venue = editTextVenue.getText().toString();

        if (!title.isEmpty() && !description.isEmpty() && !date.isEmpty() && !time.isEmpty() && !venue.isEmpty()) {
            // Create a new event
            String id = eventsRef.push().getKey();
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setDate(date);
            event.setTime(time);
            event.setVenue(venue);
            event.setCreatedAt(System.currentTimeMillis());

            // Save the event under the current user's events node
            eventsRef.child(id).setValue(event);

            // Close the fragment after saving the event
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
