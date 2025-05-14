package com.app.eventmingle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.app.eventmingle.R;
import com.app.eventmingle.models.Vendor;
import java.util.List;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VH> {

    /** If non-null, adapter will show the “Add to Event” button and call this on tap. */
    public interface OnAddClickListener {
        void onAdd(Vendor vendor);
    }

    private final List<Vendor> vendors;
    @Nullable
    private final OnAddClickListener addListener;

    /**
     * @param vendors list of Vendor to display
     * @param addListener if non-null, shows the Add button and calls back on tap;
     *                    if null, hides the button altogether
     */
    public VendorAdapter(List<Vendor> vendors,
                         @Nullable OnAddClickListener addListener) {
        this.vendors = vendors;
        this.addListener = addListener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendor_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Vendor v = vendors.get(pos);
        h.vendorName.setText(v.getName());
        h.vendorServiceType.setText(v.getServiceType());
        h.vendorAddress.setText(v.getAddress());
        h.vendorContact.setText(v.getContactNumber());
        h.vendorEmail.setText(v.getEmail());
        h.vendorDesc.setText(v.getDescription());


        if (addListener != null) {
            h.btnAddToEvent.setVisibility(View.VISIBLE);
            h.btnAddToEvent.setOnClickListener(ignored -> addListener.onAdd(v));
        } else {
            h.btnAddToEvent.setVisibility(View.GONE);
        }
    }

    @Override public int getItemCount() { return vendors.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView vendorName, vendorServiceType, vendorAddress, vendorContact, vendorEmail, vendorDesc;
        Button btnAddToEvent;
        VH(View itemView) {
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
