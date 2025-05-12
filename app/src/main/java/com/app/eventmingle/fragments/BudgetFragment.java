package com.app.eventmingle.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.eventmingle.R;
import com.app.eventmingle.models.BudgetItem;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BudgetFragment extends Fragment {

    private EditText etMaxBudget, etItemName, etItemPrice;
    private Button btnIncrement, btnDecrement, btnAddItem, btnSubmitBudget;
    private TextView tvRemaining;
    private TableLayout tableItems;

    private double maxBudget = 0;
    private double remainingBudget = 0;
    private Map<String, Double> itemMap = new HashMap<>();
    private String eventId, hostId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etMaxBudget = view.findViewById(R.id.et_max_budget);
        etItemName = view.findViewById(R.id.et_item_name);
        etItemPrice = view.findViewById(R.id.et_item_price);
        tvRemaining = view.findViewById(R.id.tv_remaining);
        btnIncrement = view.findViewById(R.id.btn_increment);
        btnDecrement = view.findViewById(R.id.btn_decrement);
        btnAddItem = view.findViewById(R.id.btn_add_item);
        btnSubmitBudget = view.findViewById(R.id.btn_submit_budget);
        tableItems = view.findViewById(R.id.table_items);

        SharedPreferences prefs = requireContext().getSharedPreferences("EventPrefs", Context.MODE_PRIVATE);
        eventId = prefs.getString("eventId", null);
        hostId = prefs.getString("hostId", null);

        btnIncrement.setOnClickListener(v -> adjustBudget(+1000));
        btnDecrement.setOnClickListener(v -> adjustBudget(-1000));
        btnAddItem.setOnClickListener(v -> addItemToTable());
        btnSubmitBudget.setOnClickListener(v -> submitBudget());

        // Listen for changes in item price to disable add button if over budget
        etItemPrice.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                checkAddButtonAvailability();
            }
        });
    }

    private void adjustBudget(int delta) {
        double current = getDouble(etMaxBudget.getText().toString());
        current += delta;
        if (current < 0) current = 0;
        maxBudget = current;
        etMaxBudget.setText(String.valueOf(maxBudget));
        updateRemaining();
    }

    private void addItemToTable() {
        String item = etItemName.getText().toString().trim();
        double price = getDouble(etItemPrice.getText().toString().trim());

        if (TextUtils.isEmpty(item) || price <= 0) {
            Toast.makeText(getContext(), "Enter valid item and price", Toast.LENGTH_SHORT).show();
            return;
        }

        if (price > remainingBudget) {
            Toast.makeText(getContext(), "Not enough budget remaining!", Toast.LENGTH_SHORT).show();
            return;
        }

        itemMap.put(item, price);

        TableRow row = new TableRow(getContext());
        TextView tv1 = new TextView(getContext());
        TextView tv2 = new TextView(getContext());
        tv1.setText(item);
        tv2.setText("Rs. " + price);
        row.addView(tv1);
        row.addView(tv2);
        tableItems.addView(row);

        etItemName.setText("");
        etItemPrice.setText("");

        updateRemaining();
    }

    private void updateRemaining() {
        double total = 0;
        for (double val : itemMap.values()) {
            total += val;
        }
        remainingBudget = maxBudget - total;
        tvRemaining.setText("Remaining Budget: Rs. " + remainingBudget);
        checkAddButtonAvailability();
    }

    private void checkAddButtonAvailability() {
        double price = getDouble(etItemPrice.getText().toString().trim());
        btnAddItem.setEnabled(price > 0 && price <= remainingBudget);
    }

    private void submitBudget() {
        if (eventId == null || hostId == null) {
            Toast.makeText(getContext(), "Event not loaded", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalExpenses = itemMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double remaining = maxBudget - totalExpenses;

        String budgetId = UUID.randomUUID().toString();

        BudgetItem budgetItem = new BudgetItem(budgetId, eventId, hostId, maxBudget);
        budgetItem.setItemizedExpenses(itemMap);
        budgetItem.setRemainingBudget(remaining);

        DatabaseReference budgetRef = FirebaseUtils.getBudgetsRef().child(budgetId);
        budgetRef.setValue(budgetItem).addOnSuccessListener(unused -> {
            FirebaseUtils.getEventsRef().child(eventId).child("budgetItemId").setValue(budgetId);
            Toast.makeText(getContext(), "Budget saved and linked to event", Toast.LENGTH_SHORT).show();
            clearForm();
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    private double getDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private void clearForm() {
        etMaxBudget.setText("0");
        itemMap.clear();
        tableItems.removeAllViews();
        tvRemaining.setText("Remaining Budget: 0");
        maxBudget = 0;
        remainingBudget = 0;
    }
}
