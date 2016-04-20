package vaqpackorganizer;

import java.time.LocalDate;



public class Appointment {
 
 int appointmentId;
 int studentId;
 String apptDate;
 String apptStartTime;
 String apptEndTime;
 String apptLoc;
 String apptReason;

    public Appointment(int appointmentId, int studentId, String apptDate, String apptStartTime, String apptEndTime, String apptLoc, String apptReason) {
        this.appointmentId = appointmentId;
        this.studentId = studentId;
        this.apptDate = apptDate;
        this.apptStartTime = apptStartTime;
        this.apptEndTime = apptEndTime;
        this.apptLoc = apptLoc;
        this.apptReason = apptReason;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getApptDate() {
        return apptDate;
    }

    public void setApptDate(String apptDate) {
        this.apptDate = apptDate;
    }

    public String getApptStartTime() {
        return apptStartTime;
    }

    public void setApptStartTime(String apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    public String getApptEndTime() {
        return apptEndTime;
    }

    public void setApptEndTime(String apptEndTime) {
        this.apptEndTime = apptEndTime;
    }

    public String getApptLoc() {
        return apptLoc;
    }

    public void setApptLoc(String apptLoc) {
        this.apptLoc = apptLoc;
    }

    public String getApptReason() {
        return apptReason;
    }

    public void setApptReason(String apptReason) {
        this.apptReason = apptReason;
    }

  
   
}
