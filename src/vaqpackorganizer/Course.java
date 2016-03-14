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
public class Course {
    
    private String courseName;
    private String coursePrefix;
    private String courseNumber;
    private String classRoom;
    private String startTime;
    private String endTime;
    private String courseDesc;
    private String startIndex;
    private String endIndex;
    private Professor prof;
    private ArrayList<Student> studentsRegistered;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePrefix() {
        return coursePrefix;
    }

    public void setCoursePrefix(String coursePrefix) {
        this.coursePrefix = coursePrefix;
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

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }

    public ArrayList<Student> getStudentsRegistered() {
        return studentsRegistered;
    }

    public void setStudentsRegistered(ArrayList<Student> studentsRegistered) {
        this.studentsRegistered = studentsRegistered;
    }

    public Course(String courseName, String coursePrefix, String courseNumber, String classRoom, String startTime, String endTime, String courseDesc, String startIndex, String endIndex, Professor prof, ArrayList<Student> studentsRegistered) {
        this.courseName = courseName;
        this.coursePrefix = coursePrefix;
        this.courseNumber = courseNumber;
        this.classRoom = classRoom;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDesc = courseDesc;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.prof = prof;
        this.studentsRegistered = studentsRegistered;
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
