//// com/app/eventmingle/fragments/BudgetListFragment.java
//package com.app.eventmingle.fragments;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.app.eventmingle.R;
//import com.app.eventmingle.adapters.BudgetAdapter;
//import com.app.eventmingle.models.BudgetItem;
//import com.google.firebase.database.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BudgetFragmentList extends Fragment {
//    private static final String ARG_EVENT_ID = "eventId";
//    private String eventId;
//
//    private RecyclerView rv;
//    private BudgetAdapter adapter;
//    private final List<BudgetItem> budgets = new ArrayList<>();
//
//    public static BudgetFragmentList newInstance(String eventId) {
//        BudgetFragmentList f = new BudgetFragmentList();
//        Bundle args = new Bundle();
//        args.putString(ARG_EVENT_ID, eventId);
//        f.setArguments(args);
//        return f;
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inf,
//                             ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inf.inflate(R.layout.fragment_budget_list, container, false);
//        rv = v.findViewById(R.id.rvBudget);
//        int outer = dpToPx(getContext(), 8);
//        rv.setClipToPadding(false);
//        rv.setPadding(0, outer, 0, outer);
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new BudgetAdapter(budgets);
//        rv.setAdapter(adapter);
//
//        if (getArguments() != null) {
//            eventId = getArguments().getString(ARG_EVENT_ID);
//            loadBudgetsForEvent(eventId);
//        }
//        return v;
//    }
//
//    private void loadBudgetsForEvent(String eventId) {
//        DatabaseReference budgetsRef = FirebaseDatabase
//                .getInstance()
//                .getReference("budgets");
//        budgetsRef
//                .orderByChild("eventId")
//                .equalTo(eventId)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snap) {
//                        budgets.clear();
//                        for (DataSnapshot ch : snap.getChildren()) {
//                            BudgetItem b = ch.getValue(BudgetItem.class);
//                            if (b != null) {
//                                // optionally pick up the key if needed:
//                                b.setBudgetItemId(ch.getKey());
//                                budgets.add(b);
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError err) {
//                        Toast.makeText(getContext(),
//                                "Failed to load budgets: " + err.getMessage(),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }
//    // Utility to convert dp → px
//    private int dpToPx(Context context, int dp) {
//        return Math.round(TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                dp,
//                getResources().getDisplayMetrics()
//        ));
//    }
//}

package com.app.eventmingle.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eventmingle.R;
import com.app.eventmingle.adapters.BudgetAdapter;
import com.app.eventmingle.models.BudgetItem;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetFragmentList extends Fragment {
    private static final String ARG_EVENT_ID = "eventId";

    private String eventId;
    private RecyclerView rv;
    private BudgetAdapter adapter;
    private final List<BudgetItem> budgets = new ArrayList<>();

    /**
     * Factory to create with an eventId argument
     */
    public static BudgetFragmentList newInstance(String eventId) {
        BudgetFragmentList frag = new BudgetFragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_budget_list, container, false);

        // RecyclerView + adapter
        rv = view.findViewById(R.id.rvBudget);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BudgetAdapter(budgets, this::showEditBudgetDialog);
        rv.setAdapter(adapter);

        // Grab eventId and load data
        if (getArguments() != null) {
            eventId = getArguments().getString(ARG_EVENT_ID);
            loadBudgetsForEvent(eventId);
        }

        return view;
    }

    private void showEditBudgetDialog(BudgetItem b) {
        View dlgView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_edit_budget, null);
        EditText etMax = dlgView.findViewById(R.id.etDialogMaxBudget);
        EditText remAmt = dlgView.findViewById(R.id.etDialogRemBudget);
        EditText etItems = dlgView.findViewById(R.id.etDialogItemized);

        // Prefill existing values
        etMax.setText("Max Budget: " + String.valueOf(b.getMaxBudget()));
        remAmt.setText("Remaining Amount: " + String.valueOf(b.getRemainingBudget()));
        Map<String, Double> m = b.getItemizedExpenses();
        if (m != null && !m.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Double> e : m.entrySet()) {
                sb.append(e.getKey())
                        .append(":")
                        .append(e.getValue())
                        .append("\n");
            }
            etItems.setText(sb.toString().trim());
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Edit Budget")
                .setView(dlgView)
                .setPositiveButton("Save", (dialog, which) -> {
                    // Parse & validate maxBudget
                    long newMax;
                    try {
                        newMax = Long.parseLong(etMax.getText().toString().trim());
                    } catch (NumberFormatException ex) {
                        Toast.makeText(requireContext(),
                                "Invalid max budget", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Parse item lines "name:amount"
                    Map<String, Double> newItems = new HashMap<>();
                    for (String line : etItems.getText().toString()
                            .trim()
                            .split("\\r?\\n")) {
                        String[] parts = line.split(":");
                        if (parts.length == 2) {
                            try {
                                newItems.put(
                                        parts[0].trim(),
                                        Double.parseDouble(parts[1].trim())
                                );
                            } catch (NumberFormatException ignore) {
                            }
                        }
                    }

                    // Recompute remaining
                    double sum = 0;
                    for (double v : newItems.values()) sum += v;
                    long newRem = newMax - (long) sum;

// Push updates
                    DatabaseReference db = FirebaseDatabase.getInstance()
                            .getReference("budgets")
                            .child(b.getBudgetItemId());

                    Map<String, Object> ups = new HashMap<>();
                    ups.put("maxBudget",        newMax);
                    ups.put("remainingBudget",  newRem);
                    ups.put("itemizedExpenses", newItems);

                    db.updateChildren(ups)
                            .addOnSuccessListener(__ -> {
                                b.setMaxBudget(newMax);
                                b.setRemainingBudget(newRem);
                                b.setItemizedExpenses(newItems);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(requireContext(),
                                        "Budget updated", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(requireContext(),
                                            "Update failed: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show()
                            );

                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    /**
     * Pops up a dialog to edit maxBudget & itemizedExpenses,
     * then pushes updates back to RTDB and refreshes the list.
     */
    private void loadBudgetsForEvent(String eventId) {
        DatabaseReference budgetsRef = FirebaseDatabase
                .getInstance()
                .getReference("budgets");
        budgetsRef
                .orderByChild("eventId")
                .equalTo(eventId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snap) {
                        budgets.clear();
                        for (DataSnapshot ch : snap.getChildren()) {
                            BudgetItem b = ch.getValue(BudgetItem.class);
                            if (b != null) {
                                // optionally pick up the key if needed:
                                b.setBudgetItemId(ch.getKey());
                                budgets.add(b);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError err) {
                        Toast.makeText(getContext(),
                                "Failed to load budgets: " + err.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
    /**
     * Helper to convert dp → pixels
     */
    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        ));
    }
}
