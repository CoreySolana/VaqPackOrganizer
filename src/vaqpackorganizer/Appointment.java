/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaqpackorganizer;

/**
 *
 * @author Corey
 */
public class Appointment {
 String appointmentWeekDay;
 String appointmentMonth;
 String appointmentDate;
 String appointmentStartTime;
 String appointmentEndTime;
 String appointmentLocation;
 String appointmentReason;

    public Appointment(String appointmentWeekDay, String appointmentMonth, String appointmentDate, String appointmentStartTime, String appointmentEndTime, String appointmentLocation, String appointmentReason) {
        this.appointmentWeekDay = appointmentWeekDay;
        this.appointmentMonth = appointmentMonth;
        this.appointmentDate = appointmentDate;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.appointmentLocation = appointmentLocation;
        this.appointmentReason = appointmentReason;
    }
 
 
    public String getAppointmentWeekDay() {
        return appointmentWeekDay;
    }

    public void setAppointmentWeekDay(String appointmentWeekDay) {
        this.appointmentWeekDay = appointmentWeekDay;
    }

    public String getAppointmentMonth() {
        return appointmentMonth;
    }

    public void setAppointmentMonth(String appointmentMonth) {
        this.appointmentMonth = appointmentMonth;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(String appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(String appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentReason() {
        return appointmentReason;
    }

    public void setAppointmentReason(String appointmentReason) {
        this.appointmentReason = appointmentReason;
    }
 
}
