package com.app.eventmingle.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.app.eventmingle.R;

public class AboutUS extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About Us");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Handle back arrow click
        return true;
    }
}
