package com.app.eventmingle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eventmingle.R;
import com.app.eventmingle.models.Vendor;

import java.util.List;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VendorViewHolder> {

    public interface OnVendorClickListener {
        void onAddToEventClick(Vendor vendor);
    }

    private List<Vendor> vendorList;
    private OnVendorClickListener listener;

    public VendorAdapter(List<Vendor> vendorList, OnVendorClickListener listener) {
        this.vendorList = vendorList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendor_list_item, parent, false);
        return new VendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorViewHolder holder, int position) {
        Vendor vendor = vendorList.get(position);
        holder.vendorName.setText(vendor.getName());
        holder.vendorServiceType.setText(vendor.getServiceType());
        holder.vendorAddress.setText(vendor.getAddress());
        holder.vendorContact.setText(vendor.getContactNumber());
        holder.vendorEmail.setText(vendor.getEmail());
        holder.vendorDesc.setText(vendor.getDescription());

        holder.btnAddToEvent.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToEventClick(vendor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }

    static class VendorViewHolder extends RecyclerView.ViewHolder {
        TextView vendorName, vendorServiceType, vendorAddress, vendorContact, vendorEmail, vendorDesc;
        Button btnAddToEvent;

        public VendorViewHolder(@NonNull View itemView) {
            super(itemView);
            vendorName = itemView.findViewById(R.id.vendorName);
            vendorServiceType = itemView.findViewById(R.id.vendorServiceType);
            vendorAddress = itemView.findViewById(R.id.vendorAddress);
            vendorContact = itemView.findViewById(R.id.vendorContact);
            vendorEmail = itemView.findViewById(R.id.vendorEmail);
            vendorDesc = itemView.findViewById(R.id.vendorDesc);
            btnAddToEvent = itemView.findViewById(R.id.btnAddToEvent);
        }
    }
}
