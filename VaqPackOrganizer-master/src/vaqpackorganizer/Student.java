package vaqpackorganizer;
import java.util.ArrayList;

public class Student {
private String firstName;
private String middleName;    
private String LastName;
private String passWord;
private String userName;
private int studentId;
private String emailAddress;
private String phoneNumber;
private int privLvl;

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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
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

    public Student(int studentId,String firstName, String middleName, String LastName, String userName, String emailAddress, String phoneNumber, int privLvl,String passWord) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.LastName = LastName;
        this.passWord = passWord;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.privLvl = privLvl;
       
    }
    }
