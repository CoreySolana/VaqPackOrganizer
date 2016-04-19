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
    
    private String courseName;
    private String courseId;
    private String courseNumber;
    private String classRoom;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startTime;
    private String endTime;
    private String courseDesc;
    private String profId;

    public Course(String courseName, String courseId, String courseNumber, String classRoom, LocalDate startDate, LocalDate endDate, String startTime, String endTime, String courseDesc, String profId) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseNumber = courseNumber;
        this.classRoom = classRoom;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDesc = courseDesc;
        this.profId = profId;
    }
    

    
/* Will be the button for opening the syllabus
  button.setOnAction(new EventHandler<ActionEvent>() {
   @Override
   public void handle(ActionEvent arg0) {
    // TODO Auto-generated method stub
    File pdfFile = new File("C:\\hai\\123.csv");
    if (pdfFile.exists())
    {
     if (Desktop.isDesktopSupported())
     {
      try
      {
       Desktop.getDesktop().open(pdfFile);
      }
      catch (IOException e)
      {
      
       e.printStackTrace();
      }
     }
     else
      {
       System.out.println("Awt Desktop is not supported!");
      }
    }
    
    else
    {
     System.out.println("File is not exists!");
    }
  
   }
  });
  
 }
}


*/    

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public String getProfId() {
        return profId;
    }

    public void setProfId(String profId) {
        this.profId = profId;
    }
    
    
}
