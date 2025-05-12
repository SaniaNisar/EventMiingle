package com.app.eventmingle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.eventmingle.models.BudgetItem;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.VH> {
    private final List<BudgetItem> budgets;
    public BudgetAdapter(List<BudgetItem> budgets) {
        this.budgets = budgets;
    }

    @Override
    public @NonNull VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        BudgetItem b = budgets.get(pos);
        // Title line: Max vs Remaining
        h.title.setText("Max: " + b.getMaxBudget()
                + " | Rem: " + b.getRemainingBudget());
        // Subtitle: count of itemized entries, or blank if none
        if (b.getItemizedExpenses() != null && !b.getItemizedExpenses().isEmpty()) {
            h.subtitle.setText("Items: " + b.getItemizedExpenses().size());
        } else {
            h.subtitle.setText("(no line-items)");
        }
    }

    @Override public int getItemCount() { return budgets.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, subtitle;
        VH(View itemView) {
            super(itemView);
            title    = itemView.findViewById(android.R.id.text1);
            subtitle = itemView.findViewById(android.R.id.text2);
        }
    }
}
