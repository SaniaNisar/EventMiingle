package com.app.eventmingle.models;

import java.util.Map;

public class Chat {

    private Map<String, Boolean> participants;

    private long lastUpdated;


    public Chat() {
    }

    public Map<String, Boolean> getParticipants() {
        return this.participants;
    }

    public void setParticipants(Map<String, Boolean> participants) {
        this.participants = participants;
    }

    public long getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
