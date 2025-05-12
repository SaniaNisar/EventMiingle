package com.app.eventmingle.models;

public class Guest {
    private String guestId;
    private String hostId;
    private String eventId;
    private String email;

    public Guest() {
        // Required empty constructor for Firebase
    }

    public Guest(String guestId, String hostId, String eventId, String email) {
        this.guestId = guestId;
        this.hostId = hostId;
        this.eventId = eventId;
        this.email = email;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
