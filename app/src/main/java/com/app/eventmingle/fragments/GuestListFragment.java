package com.app.eventmingle.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.eventmingle.R;
import com.app.eventmingle.adapters.GuestAdapter;
import com.app.eventmingle.models.Guest;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class GuestListFragment extends Fragment {
    private static final String ARG_EVENT_ID = "eventId";
    private String eventId;

    private RecyclerView rv;
    private GuestAdapter adapter;
    private final List<Guest> guests = new ArrayList<>();

    public static GuestListFragment newInstance(String eventId) {
        GuestListFragment f = new GuestListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inf,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inf.inflate(R.layout.fragment_guest_list, container, false);
        rv = v.findViewById(R.id.rvGuests);
        int outer = dpToPx(getContext(), 8);
        rv.setClipToPadding(false);
        rv.setPadding(0, outer, 0, outer);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GuestAdapter(guests);
        rv.setAdapter(adapter);

        if (getArguments() != null) {
            eventId = getArguments().getString(ARG_EVENT_ID);
            loadGuestsForEvent(eventId);
        }
        return v;
    }

    private void loadGuestsForEvent(String eventId) {
        DatabaseReference guestsRef = FirebaseDatabase
                .getInstance()
                .getReference("guests");
        // Query the guests node by its child "eventId" == our eventId
        guestsRef.orderByChild("eventId")
                .equalTo(eventId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snap) {
                        guests.clear();
                        for (DataSnapshot child : snap.getChildren()) {
                            Guest g = child.getValue(Guest.class);
                            if (g != null) {
                                // If your guestId isn’t in the payload, you can also do:
                                // g.setGuestId(child.getKey());
                                guests.add(g);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError err) {
                        Toast.makeText(getContext(),
                                "Failed to load guests: " + err.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // Utility to convert dp → px
    private int dpToPx(Context context, int dp) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        ));
    }
}
