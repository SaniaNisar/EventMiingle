package com.app.eventmingle.models;

public class BudgetItem {
    private String budgetItemId;
    private String title;         // e.g., Catering, Decoration
    private int amount;           // Amount in currency
    private String description;   // Optional
    private String vendorId;      // Optional: associated vendor

    public BudgetItem() {}

    public BudgetItem(String budgetItemId, String title, int amount, String description, String vendorId) {
        this.budgetItemId = budgetItemId;
        this.title = title;
        this.amount = amount;
        this.description = description;
        this.vendorId = vendorId;
    }

    // Getters and setters
    public String getBudgetItemId() { return budgetItemId; }
    public void setBudgetItemId(String budgetItemId) { this.budgetItemId = budgetItemId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }
}
