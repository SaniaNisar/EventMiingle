package com.app.eventmingle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eventmingle.R;
import com.app.eventmingle.models.BudgetItem;
import java.util.List;
import java.util.Map;

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

        // Title line: “Max vs Rem”
        h.title.setText("Maximum Budget: " + b.getMaxBudget()
                + "   |   Remaining: " + b.getRemainingBudget());

        // If there are any itemized entries, list each “name: amount” on its own line:
        Map<String, Double> items = b.getItemizedExpenses();
        if (items != null && !items.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Double> e : items.entrySet()) {
                sb.append(e.getKey())
                        .append(": Rs.")
                        .append(e.getValue())
                        .append("\n");
            }
            // trim trailing newline
            h.subtitle.setText(sb.toString().trim());
        } else {
            h.subtitle.setText("No line-items");
        }

        // apply your card styling / margins / padding as before...
        int margin = dpToPx(h.itemView.getContext(), 8);
        ViewGroup.MarginLayoutParams lp =
                (ViewGroup.MarginLayoutParams) h.itemView.getLayoutParams();
        lp.setMargins(margin, margin, margin, 0);
        h.itemView.setLayoutParams(lp);
        h.itemView.setPadding(margin, margin, margin, margin);
        h.itemView.setBackgroundResource(R.drawable.card_background);
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
    // helper to convert dp→px
    private int dpToPx(Context c, int dp) {
        float d = c.getResources().getDisplayMetrics().density;
        return Math.round(dp * d);
    }

}
