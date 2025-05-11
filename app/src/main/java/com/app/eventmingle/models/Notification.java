package com.app.eventmingle.models;

import java.util.Date;

public class Notification {
    private String notifId;
    private String toUserId;
    private String fromUserId;
    private String eventId;
    private String message;
    private String type; // invite, management
    private boolean isRead;
    private Date timestamp;

    public Notification() {}

    public Notification(String notifId, String toUserId, String fromUserId,
                        String eventId, String message, String type,
                        boolean isRead, Date timestamp) {
        this.notifId = notifId;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.eventId = eventId;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.timestamp = timestamp;
    }

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

