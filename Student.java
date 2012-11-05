/**
 * Student.java
 * @author Jordan Leeper
 * @version 1.0
 * 
 */

public class Student
{
  int studentID;
  String firstName;
  String lastName;
  String phone;
  double balance;
  String enrollDate;  

  public Student(int sid, String fn, String ln, String p, double b, String d )
  {
    studentID = sid;
    firstName = fn;
    lastName = ln;
    phone = p;
    balance = b;
    enrollDate = d;
  }

  public Student()
  {
  }
  
/**
 * @return the id of a student
 */
  public int getStudentID()
  {
    return studentID;
  }
  
  /**
   * @return the first name of a student
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * @return the last name of a student.
   */
  public String getLastName()
  {
    return lastName;
  }
/**
 * @return the phone number of a student.
 */
  public String getPhone()
  {
    return phone;
  }

  
  /**
   * @return the student's account balance.
   */
  public double getBalance()
  {
    return balance;
  }

  /**
   * @return the enrollment date of the student.
   */
  public String getEnrollDate()
  {
    return enrollDate;
  }

  /**
   * @return all information concerning the student as a string.
   */
  public String toString()
  {
    return studentID + ", " + firstName + ", " + lastName + ", " + phone + " " +
      balance + " " + enrollDate;
  }
}
