/**
 * Teacher.java
 * @author Jordan Leeper
 * @version 1.0
 */

public class Teacher
{
  int teacherID;
  String firstName;
  String lastName;
  String phone;

  public Teacher(int sid, String fn, String ln, String p)
  {
    teacherID = sid;
    firstName = fn;
    lastName = ln;
    phone = p;
  }

  public Teacher()
  {}

  /**
   * Returns Teacher ID
   * @return int Teacher ID
   */
  public int getTeacherID()
  {
    return teacherID;
  }

  /**
   * Returns Teacher's first name
   * @return String Teacher's first name
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * Returns Teacher's last name
   * @return String Teacher's last name
   */
  public String getLastName()
  {
    return lastName;
  }
  
  /**
   * Returns Teacher's phone number
   * @return String teacher's phone
   */
  public String getPhone()
  {
    return phone;
  }

  /**
   * Returns all teacher data
   * @return String of all teacher data
   */
  public String toString()
  {
    return teacherID + ", " + firstName + ", " + lastName + ", " + phone;
  }
}
