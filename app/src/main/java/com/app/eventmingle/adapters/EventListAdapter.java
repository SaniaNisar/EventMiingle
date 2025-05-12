package com.app.eventmingle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.eventmingle.R;
import com.app.eventmingle.models.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    private List<Event> eventList;
    private DatabaseReference eventsRef;

    public EventListAdapter(List<Event> eventList) {
        this.eventList = eventList;
        this.eventsRef = FirebaseDatabase.getInstance().getReference("events");
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
       // holder.textViewEventName.setText(event.getTitle());
        holder.textViewEventDetails.setText(event.getDescription());

        holder.buttonEdit.setOnClickListener(v -> {
            // Open the Edit Event Fragment
            // Pass event data to fragment for editing
        });

        holder.buttonDelete.setOnClickListener(v -> {
            // Delete the event from Firebase
          //  eventsRef.child(event.getTitle()).removeValue();
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEventName, textViewEventDetails;
        Button buttonEdit, buttonDelete;

        public EventViewHolder(View itemView) {
            super(itemView);
            textViewEventName = itemView.findViewById(R.id.textViewEventName);
            textViewEventDetails = itemView.findViewById(R.id.textViewEventDetails);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
