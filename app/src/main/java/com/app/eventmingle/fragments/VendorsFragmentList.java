//// com/app/eventmingle/fragments/EventVendorsFragment.java
//package com.app.eventmingle.fragments;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//import androidx.annotation.*;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.*;
//import com.app.eventmingle.R;
//import com.app.eventmingle.adapters.VendorAdapter;
//import com.app.eventmingle.models.Vendor;
//import com.app.eventmingle.utils.FirebaseUtils;
//import com.google.firebase.database.*;
//import java.util.*;
//
//public class VendorsFragmentList extends Fragment {
//    private static final String ARG_EVENT_ID = "eventId";
//    private String eventId;
//    private RecyclerView rv;
//    private VendorAdapter adapter;
//    private final List<Vendor> list = new ArrayList<>();
//
//    public static VendorsFragmentList newInstance(String eventId) {
//        VendorsFragmentList f = new VendorsFragmentList();
//        Bundle b = new Bundle();
//        b.putString(ARG_EVENT_ID, eventId);
//        f.setArguments(b);
//        return f;
//    }
//
//    @Nullable @Override
//    public View onCreateView(@NonNull LayoutInflater inf,
//                             ViewGroup c, Bundle s) {
//        View v = inf.inflate(R.layout.fragment_vendors_list, c, false);
//        rv = v.findViewById(R.id.rvVendors);
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
//        // pass null → hides the Add button
//        adapter = new VendorAdapter(list, null);
//        rv.setAdapter(adapter);
//
//        if (getArguments()!=null) {
//            eventId = getArguments().getString(ARG_EVENT_ID);
//        }
//        loadEventVendors();
//        return v;
//    }
//
//    private void loadEventVendors() {
//        FirebaseUtils.getEventsRef()
//                .child(eventId)
//                .child("vendors")
//                .addListenerForSingleValueEvent(new ValueEventListener(){
//                    @Override public void onDataChange(@NonNull DataSnapshot snap) {
//                        list.clear();
//                        for (DataSnapshot c : snap.getChildren()) {
//                            Vendor v = c.getValue(Vendor.class);
//                            if (v!=null) {
//                                v.setVendorId(c.getKey());
//                                list.add(v);
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                    @Override public void onCancelled(@NonNull DatabaseError e) {
//                        Toast.makeText(getContext(),
//                                "Load failed: "+e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}

package com.app.eventmingle.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
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

        // pass in our edit/delete lambdas
        adapter = new VendorAdapter(list,null,
                this::deleteVendor
        );
        rv.setAdapter(adapter);

        if (getArguments() != null) {
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
                                "Load failed: "+e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteVendor(Vendor vendor) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete vendor?")
                .setMessage("Remove \"" + vendor.getName() + "\" from this event?")
                .setPositiveButton("Yes", (d, which) -> {
                    FirebaseUtils.getEventsRef()
                            .child(eventId)
                            .child("vendors")
                            .child(vendor.getVendorId())
                            .removeValue()
                            .addOnSuccessListener(u -> {
                                list.remove(vendor);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(),
                                        "Vendor removed", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(err ->
                                    Toast.makeText(getContext(),
                                            "Delete failed: "+err.getMessage(),
                                            Toast.LENGTH_LONG).show()
                            );
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showEditDialog(Vendor vendor) {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_edit_budget, null);
        EditText etName   = dialogView.findViewById(R.id.etVendorName);
        EditText etDesc   = dialogView.findViewById(R.id.etVendorDescription);
        EditText etContact= dialogView.findViewById(R.id.etVendorContact);
        EditText etAddress= dialogView.findViewById(R.id.etVendorAddress);
        EditText etEmail  = dialogView.findViewById(R.id.etVendorEmail);
        EditText etType   = dialogView.findViewById(R.id.etVendorServiceType);

        // prefill
        etName.setText(vendor.getName());
        etDesc.setText(vendor.getDescription());
        etContact.setText(vendor.getContactNumber());
        etAddress.setText(vendor.getAddress());
        etEmail.setText(vendor.getEmail());
        etType.setText(vendor.getServiceType());

        new AlertDialog.Builder(getContext())
                .setTitle("Edit Vendor")
                .setView(dialogView)
                .setPositiveButton("Save", (d, w) -> {
                    vendor.setName(etName.getText().toString().trim());
                    vendor.setDescription(etDesc.getText().toString().trim());
                    vendor.setContactNumber(etContact.getText().toString().trim());
                    vendor.setAddress(etAddress.getText().toString().trim());
                    vendor.setEmail(etEmail.getText().toString().trim());
                    vendor.setServiceType(etType.getText().toString().trim());

                    FirebaseUtils.getEventsRef()
                            .child(eventId)
                            .child("vendors")
                            .child(vendor.getVendorId())
                            .setValue(vendor)
                            .addOnSuccessListener(u -> {
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(),
                                        "Vendor updated", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(err ->
                                    Toast.makeText(getContext(),
                                            "Update failed: "+err.getMessage(),
                                            Toast.LENGTH_LONG).show()
                            );
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
