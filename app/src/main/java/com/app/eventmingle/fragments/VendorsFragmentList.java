// com/app/eventmingle/fragments/EventVendorsFragment.java
package com.app.eventmingle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import com.app.eventmingle.R;
import com.app.eventmingle.adapters.VendorAdapter;
import com.app.eventmingle.models.Vendor;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.firebase.database.*;
import java.util.*;

public class VendorsFragmentList extends Fragment {
    private static final String ARG_EVENT_ID = "eventId";
    private String eventId;
    private RecyclerView rv;
    private VendorAdapter adapter;
    private final List<Vendor> list = new ArrayList<>();

    public static VendorsFragmentList newInstance(String eventId) {
        VendorsFragmentList f = new VendorsFragmentList();
        Bundle b = new Bundle();
        b.putString(ARG_EVENT_ID, eventId);
        f.setArguments(b);
        return f;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf,
                             ViewGroup c, Bundle s) {
        View v = inf.inflate(R.layout.fragment_vendors_list, c, false);
        rv = v.findViewById(R.id.rvVendors);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        // pass null → hides the Add button
        adapter = new VendorAdapter(list, null);
        rv.setAdapter(adapter);

        if (getArguments()!=null) {
            eventId = getArguments().getString(ARG_EVENT_ID);
        }
        loadEventVendors();
        return v;
    }

    private void loadEventVendors() {
        FirebaseUtils.getEventsRef()
                .child(eventId)
                .child("vendors")
                .addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override public void onDataChange(@NonNull DataSnapshot snap) {
                        list.clear();
                        for (DataSnapshot c : snap.getChildren()) {
                            Vendor v = c.getValue(Vendor.class);
                            if (v!=null) {
                                v.setVendorId(c.getKey());
                                list.add(v);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override public void onCancelled(@NonNull DatabaseError e) {
                        Toast.makeText(getContext(),
                                "Load failed: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
