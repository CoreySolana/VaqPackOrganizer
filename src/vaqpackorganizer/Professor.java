/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaqpackorganizer;

import java.util.ArrayList;

/**
 *
 * @author Corey
 */
public class Professor {
       
private String firstName;
private String middleName;    
private String LastName;
private String emailAddress;
private String phoneNumber;
private String officeNumber;
private String officeHoursStartTime;

    public Professor(String firstName, String middleName, String LastName, String emailAddress, String phoneNumber, String officeNumber, String officeHoursStartTime, String officeHoursEndTime) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.LastName = LastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.officeNumber = officeNumber;
        this.officeHoursStartTime = officeHoursStartTime;
        this.officeHoursEndTime = officeHoursEndTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getOfficeHoursStartTime() {
        return officeHoursStartTime;
    }

    public void setOfficeHoursStartTime(String officeHoursStartTime) {
        this.officeHoursStartTime = officeHoursStartTime;
    }

    public String getOfficeHoursEndTime() {
        return officeHoursEndTime;
    }

    public void setOfficeHoursEndTime(String officeHoursEndTime) {
        this.officeHoursEndTime = officeHoursEndTime;
    }
private String officeHoursEndTime;

//change here
}
