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
    private LocalDate classDate;
    private String startTime;
    private String endTime;
    private String courseDesc;
    private String profId;
    

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

    public Course(String courseName, String courseId, String courseNumber, String classRoom, LocalDate classDate, String startTime, String endTime, String courseDesc, String profId) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseNumber = courseNumber;
        this.classRoom = classRoom;
        this.classDate = classDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDesc = courseDesc;
        this.profId = profId;
    }

    public LocalDate getClassDate() {
        return classDate;
    }

    public void setClassDate(LocalDate classDate) {
        this.classDate = classDate;
    }

    public String getProfId() {
        return profId;
    }

    public void setProfId(String profId) {
        this.profId = profId;
    }



         
public ArrayList<Course> getCoursesForStudent(ArrayList<Student> studentsRegistered,Student thisStudent)
        
        {
        ArrayList<Course> thisStudentsCourses = null;
           for(int x = 0;x<studentsRegistered.size();x++)
           {
            if (studentsRegistered.get(x).equals(thisStudent))
            {
               for (int i = 0;i<thisStudent.getCourseList().size();i++)
             {
                thisStudentsCourses.add(thisStudent.getCourseList().get(i));
             }
            }   
           }
        return thisStudentsCourses;
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
    
    
}
