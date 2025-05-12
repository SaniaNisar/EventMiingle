package com.app.eventmingle.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.app.eventmingle.R;
import com.app.eventmingle.adapters.EventPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.viewpager2.widget.ViewPager2;

public class EventDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_ID = "eventId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        String eventId = getIntent().getStringExtra(EXTRA_EVENT_ID);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        EventPagerAdapter adapter = new EventPagerAdapter(this, eventId);
        viewPager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabs, viewPager,
                (tab, pos) -> {
                    switch (pos) {
                        case 0: tab.setText("Info");   break;
                        case 1: tab.setText("Guests"); break;
                        case 2: tab.setText("Budget"); break;
                        case 3: tab.setText("Vendors");break;
                    }
                }
        ).attach();
    }
}
