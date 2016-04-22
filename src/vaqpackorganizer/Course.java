/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaqpackorganizer;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Corey
 */
public class Course {
    private int courseId;
    private String courseName;
    private String classRoom;
    private String startDate;
    private String startTime;
    private String endTime;
    private String courseDesc;
    private int profId;

    public Course(int courseId, String courseName, String classRoom, String startDate, String startTime, String endTime, String courseDesc, int profId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.classRoom = classRoom;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDesc = courseDesc;
        this.profId = profId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public int getProfId() {
        return profId;
    }

    public void setProfId(int profId) {
        this.profId = profId;
    }
    
}
