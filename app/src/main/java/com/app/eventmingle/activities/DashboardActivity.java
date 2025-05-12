package com.app.eventmingle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.eventmingle.R;
import com.app.eventmingle.adapters.DashboardItemAdapter;
import com.app.eventmingle.models.Event;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private DashboardItemAdapter adapter;
    private List<Event> eventList = new ArrayList<>();
    private FloatingActionButton fabAddEvent;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // Hide default title if any
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Drawer setup
        drawerLayout = findViewById(R.id.dashboard);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerViewClasses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DashboardItemAdapter(eventList);
        recyclerView.setAdapter(adapter);

        // Load only the events created by this user
        loadMyCreatedEvents();

        // FAB to add new event
        fabAddEvent = findViewById(R.id.fabAddEvent);
        fabAddEvent.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, MainActivity.class))
        );
    }

    private void loadMyCreatedEvents() {
        String uid = FirebaseUtils.getCurrentUserId();
        DatabaseReference createdRef = FirebaseUtils.getUsersRef()
                .child(uid)
                .child("createdEvents");

        createdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                DatabaseReference eventsRef = FirebaseDatabase
                        .getInstance()
                        .getReference("events");

                for (DataSnapshot child : snapshot.getChildren()) {
                    String eventId = child.getKey();
                    if (eventId == null) continue;
                    eventsRef.child(eventId)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot eventSnap) {
                                    Event event = eventSnap.getValue(Event.class);
                                    if (event != null) {
                                        eventList.add(event);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) { }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this,
                        "Failed to load your events: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutUS.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_add_event) {
            startActivity(new Intent(this, MainActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
