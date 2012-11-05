/**
 * Enroll.java
 * @author Jordan Leeper
 * @version 1.0
 */

public class Enroll
{
  int tutID;
  int stID;
  String grade;

  public Enroll(int t, int s, String g)
  {
    tutID = t;
    stID = s;
    grade = g;
  }

  /**
   * Returns Tutorial ID
   * @return int Tutorial ID
   */
  public int getTutorialID()
  {
    return tutID;
  }

  /**
   * Returns Student ID
   * @return int Student ID
   */
  public int getStudentID()
  {
    return stID;
  }

  /**
   * Returns Students grade for tutorial
   * @return String student grade
   */
  public String getEnrollGrade()
  {
    return grade;
  }
}
