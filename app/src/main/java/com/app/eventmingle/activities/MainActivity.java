package com.app.eventmingle.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.eventmingle.R;
import com.app.eventmingle.fragments.AddEventFragment;
import com.app.eventmingle.fragments.BudgetFragment;
import com.app.eventmingle.fragments.EventsFragment;
import com.app.eventmingle.fragments.GuestsFragment;
import com.app.eventmingle.fragments.TimelineFragment;
import com.app.eventmingle.fragments.VendorsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button btnEventDetails, btnGuestManagement, btnBudgetPlanner, btnVendors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make status bar transparent
        Window window = getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize UI elements
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        btnEventDetails = findViewById(R.id.btn_event_details);
        btnGuestManagement = findViewById(R.id.btn_guest_management);
        btnBudgetPlanner = findViewById(R.id.btn_budget_planner);
        btnVendors = findViewById(R.id.btn_vendors);

        // Load default fragment
        loadFragment(new EventsFragment());

        // Handle bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_events) {
                    selectedFragment = new EventsFragment();
                } else if (itemId == R.id.nav_timeline) {
                    selectedFragment = new TimelineFragment();
                } else if (itemId == R.id.nav_guests) {
                    selectedFragment = new GuestsFragment();
                } else if (itemId == R.id.nav_budget) {
                    selectedFragment = new BudgetFragment();
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }

                return false;
            }
        });

        // Button Click Listeners
        btnEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open AddEventFragment
                loadFragment(new AddEventFragment());
            }
        });

        btnGuestManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open GuestsFragment
                loadFragment(new GuestsFragment());
            }
        });

        btnBudgetPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open BudgetFragment
                loadFragment(new BudgetFragment());
            }
        });

        btnVendors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open VendorsFragment
                loadFragment(new VendorsFragment());
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
