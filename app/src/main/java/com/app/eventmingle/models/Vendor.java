package com.app.eventmingle.models;

import java.util.UUID;

public class Vendor {
    private String vendorId;
    private String name;
    private String description;
    private String contactNumber;
    private String address;
    private String email;
    private String serviceType;

    // Default constructor required for Firebase
    public Vendor() {
    }

    // Constructor with all fields (vendorId auto-generated)
    public Vendor(String name, String description, String contactNumber, String address, String email, String serviceType) {
        this.vendorId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.contactNumber = contactNumber;
        this.address = address;
        this.email = email;
        this.serviceType = serviceType;
    }

    // Getters and Setters
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
