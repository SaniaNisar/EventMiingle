// com/app/eventmingle/fragments/BudgetListFragment.java
package com.app.eventmingle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.eventmingle.R;
import com.app.eventmingle.adapters.BudgetAdapter;
import com.app.eventmingle.models.BudgetItem;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetFragmentList extends Fragment {
    private static final String ARG_EVENT_ID = "eventId";
    private String eventId;

    private RecyclerView rv;
    private BudgetAdapter adapter;
    private final List<BudgetItem> budgets = new ArrayList<>();

    public static BudgetFragmentList newInstance(String eventId) {
        BudgetFragmentList f = new BudgetFragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inf,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inf.inflate(R.layout.fragment_budget_list, container, false);
        rv = v.findViewById(R.id.rvBudget);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BudgetAdapter(budgets);
        rv.setAdapter(adapter);

        if (getArguments() != null) {
            eventId = getArguments().getString(ARG_EVENT_ID);
            loadBudgetsForEvent(eventId);
        }
        return v;
    }

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
}
