package com.app.eventmingle.adapters;

import android.content.Context;
import android.util.TypedValue;
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
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Guest guest = guests.get(position);
        holder.title.setText(guest.getEmail());
        holder.subtitle.setText("Invitation: Sent");

        // 1) convert 8dp into px:
        int margin = dpToPx(holder.itemView.getContext(), 8);

        // 2) apply margin: left, top, right, bottom
        ViewGroup.MarginLayoutParams lp =
                (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        lp.setMargins(margin, margin, margin, 0);
        holder.itemView.setLayoutParams(lp);

        // 3) apply padding inside the “card”
        holder.itemView.setPadding(margin, margin, margin, margin);

        // 4) set your card-shape background
        holder.itemView.setBackgroundResource(R.drawable.card_background);
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

    private int dpToPx(Context ctx, float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());
    }
}
