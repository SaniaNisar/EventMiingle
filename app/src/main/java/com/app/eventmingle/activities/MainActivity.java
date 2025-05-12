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

        // Load default fragment
        //loadFragment(new EventsFragment());

        // Handle bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_events) {
                    selectedFragment = new AddEventFragment();
                } else if (itemId == R.id.nav_timeline) {
                    selectedFragment = new VendorsFragment();
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

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
