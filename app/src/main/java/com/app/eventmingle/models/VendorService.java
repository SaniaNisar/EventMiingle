package com.app.eventmingle.models;

public class VendorService {
    private String vendorId;
    private String address;
    private String contactNumber;
    private String description;
    // no-arg ctor required by Firebase
    public VendorService() {}

    public String getVendorId()       { return vendorId; }
    public void   setVendorId(String id) { this.vendorId = id; }

    public String getAddress()        { return address; }
    public void   setAddress(String a){ this.address = a; }

    public String getContactNumber()  { return contactNumber; }
    public void   setContactNumber(String c){ this.contactNumber = c; }

    public String getDescription()    { return description; }
    public void   setDescription(String d){ this.description = d; }
}
