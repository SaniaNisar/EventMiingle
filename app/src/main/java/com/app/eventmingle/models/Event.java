package com.app.eventmingle.models;

import java.util.Map;

public class Event {

    private String title;

    private String description;

    private String date;

    private String time;

    private String venue;

    private String hostId;

    private String theme;

    private String type;

    private double budget;

    private double budgetUsed;

    private long createdAt;

    private Map<String, Boolean> guests;

    private Map<String, Boolean> management;

    private Map<String, Boolean> vendors;

    private Map<String, Announcement> announcements;

    private Map<String, Task> tasks;


    public Event() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return this.venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getHostId() {
        return this.hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBudget() {
        return this.budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getBudgetUsed() {
        return this.budgetUsed;
    }

    public void setBudgetUsed(double budgetUsed) {
        this.budgetUsed = budgetUsed;
    }

    public long getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, Boolean> getGuests() {
        return this.guests;
    }

    public void setGuests(Map<String, Boolean> guests) {
        this.guests = guests;
    }

    public Map<String, Boolean> getManagement() {
        return this.management;
    }

    public void setManagement(Map<String, Boolean> management) {
        this.management = management;
    }

    public Map<String, Boolean> getVendors() {
        return this.vendors;
    }

    public void setVendors(Map<String, Boolean> vendors) {
        this.vendors = vendors;
    }

    public Map<String, Announcement> getAnnouncements() {
        return this.announcements;
    }

    public void setAnnouncements(Map<String, Announcement> announcements) {
        this.announcements = announcements;
    }

    public Map<String, Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(Map<String, Task> tasks) {
        this.tasks = tasks;
    }
}