package com.app.eventmingle.models;

public class Event {
    private String eventId;
    private String hostId;
    private String title;
    private String type; // birthday, wedding, etc.
    private String venue;
    private String date;
    private String time;
    private String theme;
    private int budget;
    private int budgetUsed;

    public Event() {}

    public Event(String eventId, String hostId, String title, String type,
                 String venue, String date, String time, String theme,
                 int budget, int budgetUsed) {
        this.eventId = eventId;
        this.hostId = hostId;
        this.title = title;
        this.type = type;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.theme = theme;
        this.budget = budget;
        this.budgetUsed = budgetUsed;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getBudgetUsed() {
        return budgetUsed;
    }

    public void setBudgetUsed(int budgetUsed) {
        this.budgetUsed = budgetUsed;
    }
}
