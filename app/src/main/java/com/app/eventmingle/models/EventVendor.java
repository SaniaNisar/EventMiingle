package com.app.eventmingle.models;
//linking class to link a vendor with an event
public class EventVendor {
    private String vendorId;
    private String service;
    private String status; // confirmed, pending

    public EventVendor() {}

    public EventVendor(String vendorId, String service, String status) {
        this.vendorId = vendorId;
        this.service = service;
        this.status = status;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

