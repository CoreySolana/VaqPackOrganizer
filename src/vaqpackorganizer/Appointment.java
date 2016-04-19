package vaqpackorganizer;

import java.time.LocalDate;
public class Appointment {
LocalDate appointmentDate;
 String appointmentStartTime;
 String appointmentEndTime;
 String appointmentLocation;
 String appointmentReason;

    public Appointment(LocalDate appointmentDate, String appointmentStartTime, String appointmentEndTime, String appointmentLocation, String appointmentReason) {
        this.appointmentDate = appointmentDate;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.appointmentLocation = appointmentLocation;
        this.appointmentReason = appointmentReason;
    }
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
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
