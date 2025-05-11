package com.app.eventmingle.models;

import java.util.List;
public class User {
    private String userId;
    private String name;
    private String email;
    private String role; // "simple", "vendor"
    private List<String> createdEvents;
    private List<String> invitedEvents;
    private List<String> acceptedInvites;
    private List<String> managingEvents;

    public User() {}

    public User(String userId, String name, String email, String role,
                List<String> createdEvents, List<String> invitedEvents,
                List<String> acceptedInvites, List<String> managingEvents) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdEvents = createdEvents;
        this.invitedEvents = invitedEvents;
        this.acceptedInvites = acceptedInvites;
        this.managingEvents = managingEvents;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(List<String> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public List<String> getInvitedEvents() {
        return invitedEvents;
    }

    public void setInvitedEvents(List<String> invitedEvents) {
        this.invitedEvents = invitedEvents;
    }

    public List<String> getAcceptedInvites() {
        return acceptedInvites;
    }

    public void setAcceptedInvites(List<String> acceptedInvites) {
        this.acceptedInvites = acceptedInvites;
    }

    public List<String> getManagingEvents() {
        return managingEvents;
    }

    public void setManagingEvents(List<String> managingEvents) {
        this.managingEvents = managingEvents;
    }
}
