package com.app.eventmingle.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.eventmingle.R;
import com.app.eventmingle.activities.EventDetailsActivity;
import com.app.eventmingle.models.DashboardItem;
import com.app.eventmingle.models.Event;

import java.util.List;

public class DashboardItemAdapter extends RecyclerView.Adapter<DashboardItemAdapter.ViewHolder> {

    private List<Event> eventList;

    public DashboardItemAdapter(List<Event> eventList) {
        this.eventList=eventList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvDashboardTitle);
            tvSubtitle = itemView.findViewById(R.id.tvDashboardSubtitle);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dashboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvTitle.setText(event.getEventName());
        holder.tvSubtitle.setText(event.getDescription());

        // ← HERE: launch EventDetailsActivity with the eventId
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), EventDetailsActivity.class);
            i.putExtra(EventDetailsActivity.EXTRA_EVENT_ID, event.getEventId());
            v.getContext().startActivity(i);
        });
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }
}