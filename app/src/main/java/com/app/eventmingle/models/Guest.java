package com.app.eventmingle.models;

public class Guest {
    private String userId;
    private String status; // invited, accepted, declined
    private String arrivalTime;

    public Guest() {}

    public Guest(String userId, String status, String arrivalTime) {
        this.userId = userId;
        this.status = status;
        this.arrivalTime = arrivalTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
