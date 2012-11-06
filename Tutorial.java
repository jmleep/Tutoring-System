/**
 * Tutorial.java
 * @author Jordan Leeper
 * @version 1.0
 */

public class Tutorial
{
  int tutID;
  String tut;
  int teachID;

  public Tutorial(int t, String tu, int teach)
  {
    tutID = t;
    tut = tu;
    teachID = teach;
  }
 
  public Tutorial()
  {
  }
 
  /**
   * Returns the tutorial ID
   * @return int Tutorial ID
   */
  public int getTutorialID()
  {
    return tutID;
  }

  /**
   * Returns the description of the tutorial
   * @return String tutorial description
   */
  public String getDescription()
  {
    return tut;
  }

  /**
   * Returns the ID of the Teacher of the tutorial
   * @return int Teacher ID
   */
  public int getTeacherID()
  {
    return teachID;
  }

  /**
   * Returns all Enrollment data
   * @return String all enrollment information
   */
  public String toString()
  {
    return tutID + " " + tut + " " + teachID;
  }
}

