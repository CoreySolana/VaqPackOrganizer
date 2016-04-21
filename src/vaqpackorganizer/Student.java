package vaqpackorganizer;
import java.util.ArrayList;

public class Student {
private int studentId;
private String userName;
private String passWord;
private String firstName;
private String middleName;    
private String LastName;
private String emailAddress;
private String phoneNumber;
private int privLvl;

    public Student(int studentId, String userName, String passWord, String firstName, String middleName, String LastName, String emailAddress, String phoneNumber, int privLvl) {
        this.studentId = studentId;
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.middleName = middleName;
        this.LastName = LastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.privLvl = privLvl;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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

    public int getPrivLvl() {
        return privLvl;
    }

    public void setPrivLvl(int privLvl) {
        this.privLvl = privLvl;
    }








}