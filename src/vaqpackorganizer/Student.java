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
public class Student {
private String firstName;
private String middleName;    
private String LastName;
private String passWord;
private String userName;
private String studentId;
private String emailAddress;
private String phoneNumber;
private int privLvl;
private ArrayList<Course> courseList;
private ArrayList<Appointment> appointmentList;

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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public int getPrivLvl() {
        return privLvl;
    }

    public void setPrivLvl(int privLvl) {
        this.privLvl = privLvl;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public Student(String firstName, String middleName, String LastName, String passWord, String userName, String studentId, String emailAddress, String phoneNumber, int privLvl, ArrayList<Course> courseList,ArrayList<Appointment> appointmentList) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.LastName = LastName;
        this.passWord = passWord;
        this.userName = userName;
        this.studentId = studentId;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.privLvl = privLvl;
        this.courseList = courseList;
        this.appointmentList = appointmentList;
    }
    }
