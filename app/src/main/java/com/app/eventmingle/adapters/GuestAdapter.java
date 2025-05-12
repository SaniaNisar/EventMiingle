package com.app.eventmingle.adapters;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.eventmingle.R;
import com.app.eventmingle.models.User;
import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.VH> {
    private final List<User> list;
    public GuestAdapter(List<User> list){ this.list=list; }
    public static class VH extends RecyclerView.ViewHolder {
        TextView name, email;
        public VH(View v){
            super(v);
            name = v.findViewById(R.id.tvGuestName);
            email= v.findViewById(R.id.tvGuestEmail);
        }
    }
    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int t){
        View v= LayoutInflater.from(p.getContext())
                .inflate(R.layout.guest_item,p,false);
        return new VH(v);
    }
    @Override public void onBindViewHolder(@NonNull VH h, int pos){
        User u=list.get(pos);
        h.name.setText(u.getName());
        h.email.setText(u.getEmail());
    }
    @Override public int getItemCount(){ return list.size(); }
}
