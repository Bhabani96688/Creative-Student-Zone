package com.crtv.creativetechnocollege;

public class ContactInfo {
    String Name;
    String Designation;
    String PhoneNumber;
    ContactInfo(String Name,String Designation,String PhoneNumber)
    {
        this.Name=Name;
        this.Designation=Designation;
        this.PhoneNumber=PhoneNumber;
    }
     public String getName()
    {
        return Name;
    }
    public String getDesignation()
    {
        return Designation;
    }
    public String getContact()
    {
        return PhoneNumber;
    }
}
