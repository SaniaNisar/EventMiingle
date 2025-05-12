package com.app.eventmingle.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import com.app.eventmingle.R;
import com.app.eventmingle.models.Event;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.firebase.database.*;

public class EventInfoFragment extends Fragment {
    private static final String ARG_ID = "eventId";
    private String eventId;

    public static EventInfoFragment newInstance(String eventId) {
        Bundle args = new Bundle();
        args.putString(ARG_ID, eventId);
        EventInfoFragment f = new EventInfoFragment();
        f.setArguments(args);
        return f;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf,
                             @Nullable ViewGroup parent,
                             @Nullable Bundle saved) {
        View v = inf.inflate(R.layout.fragment_event_info, parent, false);
        eventId = getArguments().getString(ARG_ID);
        TextView tvName = v.findViewById(R.id.tvEventName);
        TextView tvDesc = v.findViewById(R.id.tvEventDesc);
        TextView tvDate = v.findViewById(R.id.tvEventDate);
        TextView tvVenue= v.findViewById(R.id.tvEventVenue);
        TextView tvTheme= v.findViewById(R.id.tvEventTheme);
        TextView tvCat  = v.findViewById(R.id.tvEventCategory);

        DatabaseReference ref = FirebaseUtils.getEventsRef().child(eventId);
        ref.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override public void onDataChange(@NonNull DataSnapshot snap) {
                Event e = snap.getValue(Event.class);
                if (e==null) return;
                tvName.setText(e.getEventName());
                tvDesc.setText(e.getDescription());
                tvDate.setText(e.getDateTime());
                tvVenue.setText(e.getVenue());
                tvTheme.setText(e.getTheme());
                tvCat.setText(e.getCategory());
            }
            @Override public void onCancelled(@NonNull DatabaseError err){}
        });
        return v;
    }
}
