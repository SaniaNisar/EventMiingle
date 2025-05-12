package com.app.eventmingle.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.eventmingle.R;
import com.app.eventmingle.adapters.GuestAdapter;
import com.app.eventmingle.models.User;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GuestListFragment extends Fragment {
    private static final String ARG_EVENT_ID = "eventId";
    private String eventId;

    private RecyclerView rvGuests;
    private GuestAdapter adapter;
    private final List<User> guests = new ArrayList<>();

    public static GuestListFragment newInstance(String eventId) {
        GuestListFragment f = new GuestListFragment();
        Bundle b = new Bundle();
        b.putString(ARG_EVENT_ID, eventId);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventId = getArguments().getString(ARG_EVENT_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guest_list, container, false);

        rvGuests = v.findViewById(R.id.rvGuests);
        rvGuests.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GuestAdapter(guests);
        rvGuests.setAdapter(adapter);

        loadGuests();
        return v;
    }

    private void loadGuests() {
        // point directly at events/{eventId}/guests
        DatabaseReference guestsRef = FirebaseUtils
                .getEventsRef()
                .child(eventId)
                .child("guests");

        guestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {
                guests.clear();

                // if you stored guests as an array of UIDs
                for (DataSnapshot child : snap.getChildren()) {
                    String uid = child.getValue(String.class);
                    if (uid == null) continue;

                    // now fetch full user profile
                    FirebaseUtils.getUsersRef()
                            .child(uid)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override public void onDataChange(@NonNull DataSnapshot uSnap) {
                                    User u = uSnap.getValue(User.class);
                                    if (u != null) {
                                        guests.add(u);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                @Override public void onCancelled(@NonNull DatabaseError e) { /*…*/ }
                            });
                }
            }

            @Override public void onCancelled(@NonNull DatabaseError error) { /*…*/ }
        });
    }
}
