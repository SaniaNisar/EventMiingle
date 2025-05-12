package com.app.eventmingle.models;

import java.util.HashMap;
import java.util.Map;

public class BudgetItem {

    private String budgetItemId;     // Unique ID for this budget item
    private String eventId;          // Event this budget is tied to
    private String hostId;           // Host who owns the event
    private double maxBudget;        // The total/max budget set
    private double remainingBudget;  // Remaining after adding expenses

    // Map of item name to price (e.g., "Stage" → 5000.0)
    private Map<String, Double> itemizedExpenses;

    public BudgetItem() {
        // Needed for Firebase
    }

    public BudgetItem(String budgetItemId, String eventId, String hostId, double maxBudget) {
        this.budgetItemId = budgetItemId;
        this.eventId = eventId;
        this.hostId = hostId;
        this.maxBudget = maxBudget;
        this.remainingBudget = maxBudget;
        this.itemizedExpenses = new HashMap<>();
    }

    public String getBudgetItemId() {
        return budgetItemId;
    }

    public void setBudgetItemId(String budgetItemId) {
        this.budgetItemId = budgetItemId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(double maxBudget) {
        this.maxBudget = maxBudget;
        updateRemainingBudget(); // recalculate if needed
    }

    public double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(double remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public Map<String, Double> getItemizedExpenses() {
        return itemizedExpenses;
    }

    public void setItemizedExpenses(Map<String, Double> itemizedExpenses) {
        this.itemizedExpenses = itemizedExpenses;
        updateRemainingBudget();
    }

    public void addExpense(String item, double price) {
        if (itemizedExpenses == null) {
            itemizedExpenses = new HashMap<>();
        }
        itemizedExpenses.put(item, price);
        updateRemainingBudget();
    }

    private void updateRemainingBudget() {
        double totalSpent = 0;
        if (itemizedExpenses != null) {
            for (double price : itemizedExpenses.values()) {
                totalSpent += price;
            }
        }
        this.remainingBudget = maxBudget - totalSpent;
    }
}
