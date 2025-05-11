package com.app.eventmingle.models;

public class Notification {

    private String type;

    private String eventId;

    private String fromUserId;

    private String message;

    private boolean isRead;

    private long timestamp;


    public Notification() {}

    public String getType() { return this.type; }
    public void setType(String type) { this.type = type; }
    public String getEventId() { return this.eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getFromUserId() { return this.fromUserId; }
    public void setFromUserId(String fromUserId) { this.fromUserId = fromUserId; }
    public String getMessage() { return this.message; }
    public void setMessage(String message) { this.message = message; }
    public boolean getIsRead() { return this.isRead; }
    public void setIsRead(boolean isRead) { this.isRead = isRead; }
    public long getTimestamp() { return this.timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
