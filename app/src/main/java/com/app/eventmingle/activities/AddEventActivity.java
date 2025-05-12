package com.app.eventmingle.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.eventmingle.R;
import com.app.eventmingle.adapters.EventListAdapter;
import com.app.eventmingle.fragments.AddEventFragment;
import com.app.eventmingle.models.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventListAdapter eventListAdapter;
    private List<Event> eventList;
    private DatabaseReference eventsRef;
    private Button buttonAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        eventsRef = FirebaseDatabase.getInstance().getReference("events");

        // Load events from Firebase
        loadEvents();

        buttonAddEvent = findViewById(R.id.buttonAddEvent);
        buttonAddEvent.setOnClickListener(v -> {
            // Open AddEventFragment to enter event details
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddEventFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void loadEvents() {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    eventList.add(event);
                }
                eventListAdapter = new EventListAdapter(eventList);
                recyclerView.setAdapter(eventListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AddEventActivity.this, "Failed to load events", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
