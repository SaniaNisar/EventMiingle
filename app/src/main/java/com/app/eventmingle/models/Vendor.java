package com.app.eventmingle.models;

public class Vendor {
    private String vendorId;
    private String name;
    private String email;
    private String serviceType; // e.g. catering, decoration
    private String phone;

    public Vendor() {}

    public Vendor(String vendorId, String name, String email, String serviceType, String phone) {
        this.vendorId = vendorId;
        this.name = name;
        this.email = email;
        this.serviceType = serviceType;
        this.phone = phone;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

