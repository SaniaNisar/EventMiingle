package com.app.eventmingle.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.app.eventmingle.fragments.*;

public class EventPagerAdapter extends FragmentStateAdapter {
    private final String eventId;

    public EventPagerAdapter(@NonNull FragmentActivity fa, String eventId) {
        super(fa);
        this.eventId = eventId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return GuestListFragment.newInstance(eventId);
            case 2: return BudgetFragmentList.newInstance(eventId);
//            case 3: return VendorsFragmentList.newInstance(eventId);
            case 0:
            default:
                return EventInfoFragment.newInstance(eventId);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
