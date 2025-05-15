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
    public interface OnEditListener { void onEdit(@NonNull BudgetItem b); }

    private final List<BudgetItem> budgets;
    private final OnEditListener   editListener;

    public BudgetAdapter(List<BudgetItem> budgets,
                         OnEditListener editListener) {
        this.budgets = budgets;
        this.editListener = editListener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        BudgetItem b = budgets.get(pos);

        // Title & subtitle as before…
        h.title.setText("Max: " + b.getMaxBudget()
                + " | Rem: " + b.getRemainingBudget());
        Map<String, Double> items = b.getItemizedExpenses();
        if (items != null && !items.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Double> e : items.entrySet()) {
                sb.append(e.getKey())
                        .append(": Rs.")
                        .append(e.getValue())
                        .append("\n");
            }
            h.subtitle.setText(sb.toString().trim());
        } else {
            h.subtitle.setText("No line-items");
        }

        // card‐style margins/padding (same as you already have)
        int m = dpToPx(h.itemView.getContext(), 8);
        ViewGroup.MarginLayoutParams lp =
                (ViewGroup.MarginLayoutParams) h.itemView.getLayoutParams();
        lp.setMargins(m, m, m, 0);
        h.itemView.setLayoutParams(lp);
        h.itemView.setPadding(m, m, m, m);
        h.itemView.setBackgroundResource(R.drawable.card_background);

        // **NEW**: any tap on this card will trigger the edit callback
        if (editListener != null) {
            h.itemView.setOnClickListener(__ -> editListener.onEdit(b));
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

    private int dpToPx(Context c, int dp) {
        float d = c.getResources().getDisplayMetrics().density;
        return Math.round(dp * d);
    }
}
