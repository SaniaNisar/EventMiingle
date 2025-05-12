package com.app.eventmingle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.eventmingle.R;
import com.app.eventmingle.models.Guest;
import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.VH> {
    private final List<Guest> guests;
    public GuestAdapter(List<Guest> guests) {
        this.guests = guests;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int i) {
        View v = LayoutInflater.from(p.getContext())
                .inflate(android.R.layout.simple_list_item_2, p, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Guest g = guests.get(pos);
        h.title.setText(g.getEmail());
        h.subtitle.setText("ID: " + g.getGuestId());
    }

    @Override public int getItemCount() { return guests.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, subtitle;
        VH(View itemView) {
            super(itemView);
            title    = itemView.findViewById(android.R.id.text1);
            subtitle = itemView.findViewById(android.R.id.text2);
        }
    }
}
