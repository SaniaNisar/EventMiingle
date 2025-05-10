package com.app.eventmingle.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.app.eventmingle.R;
import com.app.eventmingle.fragments.BudgetFragment;
import com.app.eventmingle.fragments.EventsFragment;
import com.app.eventmingle.fragments.GuestsFragment;
import com.app.eventmingle.fragments.TimelineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabAdd;

    public static final String PREFS_NAME = "user_prefs";
    public static final String KEY_THEME = "dark_mode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(Color.TRANSPARENT); // Make status bar transparent

        // Get the stored theme preference
//        SharedPreferences sharedPreferences = getSharedPreferences(ProfileFragment.PREFS_NAME, MODE_PRIVATE);
//        boolean isDarkMode = sharedPreferences.getBoolean(ProfileFragment.KEY_THEME, false);
//
//        // Apply the theme on app start (before onCreate finishes)
//        if (isDarkMode) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }

        // Set content view
        setContentView(R.layout.activity_main);

        // Ensure background color is updated to match the current theme
       // updateBackgroundColor(isDarkMode);


        // Hides the default ActionBar programmatically
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fabAdd = findViewById(R.id.fab_add);

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


        // Handle "+" floating button click
        fabAdd.setOnClickListener(v -> {
            // Option 1: Navigate to add screen as Activity
            //startActivity(new Intent(this, AddTaskActivity.class));

            // Option 2: Show add fragment
            // loadFragment(new AddTaskFragment());
        });
    }

    private void updateBackgroundColor(boolean isDarkMode) {
        View rootView = findViewById(android.R.id.content);
        if (isDarkMode) {
            rootView.setBackgroundColor(getResources().getColor(R.color.black)); // Dark background
        } else {
            rootView.setBackgroundColor(getResources().getColor(R.color.white)); // Light background
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}


//        Button logoutBtn = findViewById(R.id.btnLogout); // Ensure you have a button with this ID

//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
//                startActivity(intent);
//                finish(); // Finish MainActivity
//            }
//        });
