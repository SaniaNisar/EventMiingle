package com.app.eventmingle.models;

public class DashboardItem {
    private String title;
    private String subtitle;

    public DashboardItem() {}

    public DashboardItem(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }

    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
}
