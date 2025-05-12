package com.app.eventmingle.models;

import java.util.Map;

public class Event {

    private String eventId;

    private String eventName;
    private String description;
    private String dateTime; // e.g. "2025-05-15 18:00"
    private String venue;
    private String hostId;
    private String hostName; // You can fetch this using hostId from user data
    private String theme;
    private String category;
    private double budget;
    private long createdAt;

    private Map<String, Boolean> guestList;
    private Map<String, Boolean> vendorList;


    public Event() {
    }

    public Event(String eventId, String eventName, String eventDescription, String eventDateTime,
                 String eventHostId, String eventHostName, String eventLocation) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = eventDescription;
        this.dateTime = eventDateTime;
        this.hostId = eventHostId;
        this.hostName = eventHostName;
        this.venue = eventLocation;
    }

    // Getters and setters

    public void setEventId(String eventId)
    {
        this.eventId = eventId;
    }

    public String getEventId()
    {
        return this.eventId;
    }
    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getVenue() {
        return venue;
    }

    public Map<String, Boolean> getVendorList() {
        return vendorList;
    }

    public void setVendorList(Map<String, Boolean> vendorList) {
        this.vendorList = vendorList;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, Boolean> getGuestList() {
        return guestList;
    }

    public void setGuestList(Map<String, Boolean> guestList) {
        this.guestList = guestList;
    }
}
