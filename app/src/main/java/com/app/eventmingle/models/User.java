package com.app.eventmingle.models;

import java.util.Map;

public class User {

    private String name;

    private String email;

    private String role;

    private String profileImageUrl;

    private String phoneNumber;

    private Map<String, Boolean> createdEvents;

    private Map<String, Boolean> invitedEvents;

    private Map<String, Boolean> acceptedInvites;

    private Map<String, Boolean> managingEvents;

    private Map<String, VendorService> vendorServices;


    public User() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Boolean> getCreatedEvents() {
        return this.createdEvents;
    }

    public void setCreatedEvents(Map<String, Boolean> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public Map<String, Boolean> getInvitedEvents() {
        return this.invitedEvents;
    }

    public void setInvitedEvents(Map<String, Boolean> invitedEvents) {
        this.invitedEvents = invitedEvents;
    }

    public Map<String, Boolean> getAcceptedInvites() {
        return this.acceptedInvites;
    }

    public void setAcceptedInvites(Map<String, Boolean> acceptedInvites) {
        this.acceptedInvites = acceptedInvites;
    }

    public Map<String, Boolean> getManagingEvents() {
        return this.managingEvents;
    }

    public void setManagingEvents(Map<String, Boolean> managingEvents) {
        this.managingEvents = managingEvents;
    }

    public Map<String, VendorService> getVendorServices() {
        return this.vendorServices;
    }

    public void setVendorServices(Map<String, VendorService> vendorServices) {
        this.vendorServices = vendorServices;
    }
}