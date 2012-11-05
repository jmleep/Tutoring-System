/**
 * Database Class
 * @author Jordan Leeper
 * @version 1.0
 */

import java.sql.*;
import java.util.ArrayList;

// Be sure to change the dbURL/username/password to your own!
public class DB {
  private Connection connect = null;
  private String dbURL = "jdbc:mysql://localhost/leeperj28";
  private String username = "leeperj28";
  private String password = "leeperj28";

  public DB() {
    getConnection();
  }

  private void getConnection()
  {
    try
    {
      connect = DriverManager.getConnection(dbURL, username, password);
    }
    catch (SQLException e)
    {
      System.out.println("Exception thrown calling getConnection.\n" + e.getMessage());
    }
  }

  /**
   * Inserts a student instance into the student table
   * @param Student object
   * @return String result confirming student was added
   */
  public String addStudent(Student c)
  {
    String result = "";
    
    try
    {
      String q = "INSERT INTO STUDENT (STU_ID, STU_FNAME, STU_LNAME, STU_PHONE, STU_BALANCE, STU_ENROLLDATE) "
        + "values (null, ?, ?, ?, ?, ?)";
      PreparedStatement ps = connect.prepareStatement(q);
      ps.setString(1, c.getFirstName());
      ps.setString(2, c.getLastName());
      ps.setString(3, c.getPhone());
      ps.setDouble(4, c.getBalance());
      ps.setString(5, c.getEnrollDate()); 
      ps.executeUpdate();
      result = c.getFirstName() + " " + c.getLastName() + " has been added.";
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
    }
    return result;
  }

  /**
   * Returns an arraylist of all students
   * @param int order by  
   * @param int record to start at
   * @param int number of records to display
   * @return an ArrayList of Student objects
   */
  public ArrayList<Student> listStudents(int orderBy,
      int startRecord, int numberToDisplay)
  {
    ArrayList<Student> cList = new ArrayList<Student>();
    PreparedStatement ps = null;
    ResultSet rs = null; 
    try
    {
      String q = "";
      if (1==orderBy)
      {
        q = "SELECT STU_ID, STU_FNAME, STU_LNAME, STU_PHONE, STU_BALANCE, STU_ENROLLDATE FROM STUDENT "
          + "ORDER BY STU_ID LIMIT ?, ?";
      } 
      else
      {
        q = "SELECT STU_ID, STU_FNAME, STU_LNAME, STU_PHONE, STU_BALANCE, STU_ENROLLDATE FROM STUDENT "
          + "ORDER BY STU_LNAME, STU_FNAME LIMIT ?, ?";
      }
      ps = connect.prepareStatement(q);
      ps.setInt(1, startRecord-1);
      ps.setInt(2, numberToDisplay);
      rs = ps.executeQuery();

      while (rs.next())
      {
        cList.add( new Student(
              rs.getInt("STU_ID"), rs.getString("STU_FNAME"),
              rs.getString("STU_LNAME"), rs.getString("STU_PHONE"), rs.getDouble("STU_BALANCE"),
              rs.getString("STU_ENROLLDATE") ) );
      }
      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }

    return cList;
  }

  /**
   * Returns an arraylist of student objects searched for by name
   * @param String first name of student
   * @param String last name of student
   * @return an ArrayList of Student objects
   */
  public ArrayList<Student> findStudentByName(String first, String last)
  {
    ArrayList<Student> cList = new ArrayList<Student>();
    PreparedStatement ps = null;
    ResultSet rs = null; 
    try
    {
      String q = "select STU_ID, STU_FNAME, STU_LNAME, STU_PHONE, STU_BALANCE, STU_ENROLLDATE from STUDENT "
          + "where STU_FNAME like ? and STU_LNAME like ? order by STU_ID";
      ps = connect.prepareStatement(q);
      ps.setString(1, first + "%");
      ps.setString(2, last + "%");
      rs = ps.executeQuery();

      while (rs.next())
      {
        cList.add( new Student(
              rs.getInt("STU_ID"), rs.getString("STU_FNAME"),
              rs.getString("STU_LNAME"), rs.getString("STU_PHONE"), rs.getDouble("STU_BALANCE"),
              rs.getString("STU_ENROLLDATE") ) );
      }
      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }

    return cList;
  }
  
  /**
   * Returns a certain student from the database that matches a student ID
   * @param int ID to search for
   * @return A Student object corresponding to the ID
   */
  public Student findStudentByID(int stid)
  {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Student s = new Student();
    try
    {
      String q = "SELECT STU_ID, STU_FNAME, STU_LNAME, STU_PHONE, STU_BALANCE, STU_ENROLLDATE FROM STUDENT "
          + "WHERE STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, stid);
      rs = ps.executeQuery();
      rs.first();

            
      s = new Student(rs.getInt("STU_ID"), rs.getString("STU_FNAME"), rs.getString("STU_LNAME"),
        rs.getString("STU_PHONE"), rs.getDouble("STU_BALANCE"), rs.getString("STU_ENROLLDATE"));

      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
    }
    
    return s;
  }

  /**
   * Returns a student object that's first name has been modified
   * @param Student object being edited
   * @param String the new first name to enter
   * @return the Student object that has been edited
   */
  public Student editFirst(Student s, String f)
  {
    PreparedStatement ps = null;
    int studentID = s.getStudentID();
    Student s1 = new Student();

    try
    {
      String q = "update STUDENT set STU_FNAME = ? where STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setString(1, f);
      ps.setInt(2, studentID);
      ps.executeUpdate();
      
      s1 = findStudentByID(studentID);
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }

    return s1;
  }

  /**
   * Returns a student object that's last name has been modified
   * @param Student object being edited
   * @param String the new last name to enter
   * @return the Student object that has been edited
   */
  public Student editLast(Student s, String f)
  {
    PreparedStatement ps = null;
    int studentID = s.getStudentID();
    Student s1 = new Student();

    try
    {
      String q = "update STUDENT set STU_LNAME = ? where STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setString(1, f);
      ps.setInt(2, studentID);
      ps.executeUpdate();
      
      s1 = findStudentByID(studentID);
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }

    return s1;
  }

  /**
   * Returns a student object that's phone number has been modified
   * @param Student object being edited
   * @param String the new phone to enter
   * @return the Student object that has been edited
   */
  public Student editPhone(Student s, String f)
  {
    PreparedStatement ps = null;
    int studentID = s.getStudentID();
    Student s1 = new Student();

    try
    {
      String q = "update STUDENT set STU_PHONE = ? where STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setString(1, f);
      ps.setInt(2, studentID);
      ps.executeUpdate();
      
      s1 = findStudentByID(studentID);
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }

    return s1;
  }


  /**
   * Returns a student object that's balance has been modified
   * @param Student object being edited
   * @param String the new balance to enter
   * @return the Student object that has been edited
   */
  public Student editBalance(Student s, double f)
  {
    PreparedStatement ps = null;
    int studentID = s.getStudentID();
    Student s1 = new Student();

    try
    {
      String q = "update STUDENT set STU_BALANCE = ? where STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setDouble(1, f);
      ps.setInt(2, studentID);
      ps.executeUpdate();
      
      s1 = findStudentByID(studentID);
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }

    return s1;
  }
 
  /**
   * Returns a string confirming the deletion of a student from the database
   * @param int the ID of the student to delete
   * @return String confirming student's deletion
   */
  public String delStudent(int tid)
  {
    String result = "";
    Student t = findStudentByID(tid);
    String first = t.getFirstName();
    String last = t.getLastName();
    PreparedStatement ps = null;

    try
    {
      String q = "DELETE FROM STUDENT WHERE STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, tid);
      ps.executeUpdate();

      result = first + " " + last + " deleted.";

      ps.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
    return result;
  }

  /**
   * Returns a string confirming the addition of a teacher to the database
   * @param Teacher object
   * @return String confirming addition of teacher instance
   */
  public String addTeacher(Teacher t)
  {
    String result = "";
    
    try
    {
      String q = "INSERT INTO TEACHER (TEACH_ID, TEACH_FNAME, TEACH_LNAME, TEACH_PHONE) "
        + "values (null, ?, ?, ?)";
      PreparedStatement ps = connect.prepareStatement(q);
      ps.setString(1, t.getFirstName());
      ps.setString(2, t.getLastName());
      ps.setString(3, t.getPhone());
      ps.executeUpdate();
      result = t.getFirstName() + " " + t.getLastName() + " has been added.";
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
    }
    return result;
  }

  /**
   * Returns a Teacher object after finding the corresponding row from the database
   * @param int the Teacher's ID
   * @return Teacher object from the database
   */
  public Teacher findTeacherByID(int tid)
  {
    Teacher t = new Teacher();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      String q = "SELECT TEACH_ID, TEACH_FNAME, TEACH_LNAME, TEACH_PHONE FROM TEACHER WHERE "
        + "TEACH_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, tid);
      rs = ps.executeQuery();
      rs.first();

      t = new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));

      ps.close();
      rs.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }

    return t;
  }

  /**
   * Returns modified Teacher object after first name was replaced
   * @param int Teacher ID
   * @param String new first name of teacher
   * @return Teacher object that has been edited
   */
  public Teacher editTeacherFirst(int tid, String f)
  {
    PreparedStatement ps = null;
    Teacher t = new Teacher();

    try
    {
      String q = "UPDATE TEACHER SET TEACH_FNAME = ? WHERE TEACH_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setString(1, f);
      ps.setInt(2, tid);
      ps.executeUpdate();

      t = findTeacherByID(tid);
      ps.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
    return t;
  }

  /**
   * Returns modified Teacher object after last name was replaced
   * @param int Teacher ID
   * @param String new last name of teacher
   * @return Teacher object that has been edited
   */
  public Teacher editTeacherLast(int tid, String f)
  {
    PreparedStatement ps = null;
    Teacher t = new Teacher();

    try
    {
      String q = "UPDATE TEACHER SET TEACH_LNAME = ? WHERE TEACH_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setString(1, f);
      ps.setInt(2, tid);
      ps.executeUpdate();

      t = findTeacherByID(tid);
      ps.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
    return t;
  }

  /**
   * Returns modified Teacher object after phone number was replaced
   * @param int Teacher ID
   * @param String new phone number of teacher
   * @return Teacher object that has been edited
   */
  public Teacher editTeacherPhone(int tid, String f)
  {
    PreparedStatement ps = null;
    Teacher t = new Teacher();

    try
    {
      String q = "UPDATE TEACHER SET TEACH_PHONE = ? WHERE TEACH_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setString(1, f);
      ps.setInt(2, tid);
      ps.executeUpdate();

      t = findTeacherByID(tid);

      ps.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
    return t;
  }

  /**
   * Removes teacher from database
   * @param int Teacher ID
   * @return String confirming deletion of Teacher instance
   */
  public String delTeacher(int tid)
  {
    String result = "";
    Teacher t = findTeacherByID(tid);
    String first = t.getFirstName();
    String last = t.getLastName();
    PreparedStatement ps = null;

    try
    {
      String q = "DELETE FROM TEACHER WHERE TEACH_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, tid);
      ps.executeUpdate();

      result = first + " " + last + " deleted.";

      ps.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
    return result;
  }

  /**
   * Returns Tutorial object corresponding to database ID
   * @param int Tutorial ID
   * @return Tutorial object found using Tutorial ID
   */
  public Tutorial findTutorialByID(int t)
  {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Tutorial tut = new Tutorial();
    try
    {
      String q = "SELECT TUT_ID, TUT_DESCRIBE, TEACH_ID FROM TUTORIAL "
          + "WHERE TUT_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, t);
      rs = ps.executeQuery();
      rs.first();

            
      tut = new Tutorial(rs.getInt("TUT_ID"), rs.getString("TUT_DESCRIBE"), rs.getInt("TEACH_ID"));

      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
    }
    
    return tut;
  }


  /**
   * Returns a list of all Tutorial instances within the database
   * @return ArrayList of Tutorial objects
   */
  public ArrayList<Tutorial> listTutorials()
  {
    ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();
    PreparedStatement ps = null;
    ResultSet rs = null; 
    try
    {
       String q = "SELECT TUT_ID, TUT_DESCRIBE, TEACH_ID FROM TUTORIAL "
          + "ORDER BY TUT_ID";
      ps = connect.prepareStatement(q);
      rs = ps.executeQuery();
      
      while (rs.next())
      {   
        tutorials.add( new Tutorial(rs.getInt("TUT_ID"),rs.getString("TUT_DESCRIBE"), rs.getInt("TEACH_ID")));
      }
      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }

    return tutorials;
  }

  /**
   * Enrolls student within specified tutorial
   * @param Student object to be enrolled
   * @param int ID of tutorial to enroll student within
   */
  public void enrollStudent(Student s, int tid)
  {
    PreparedStatement ps = null;
    try
    {
      String q = "INSERT INTO ENROLL (TUT_ID, STU_ID, ENROLL_GRADE) " +
        "values (?, ?, ?)";
      ps = connect.prepareStatement(q);
      ps.setInt(1, tid);
      ps.setInt(2, s.getStudentID());
      ps.setString(3, "N"); 
      ps.executeUpdate();

      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQuery: " + ps.toString());
    }
  }

  /**
   * Returns an arraylist of Student objects within specified tutorial
   * @param int the Tutorial ID to search for students within
   * @return ArrayList of Student objects that are enrolled within specified tutorial
   */
  public ArrayList<Student> listStudentsInTutorial(int t)
  {
    ArrayList<Student> sa = new ArrayList<Student>();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try
    {
      String q = "SELECT STUDENT.STU_ID, STUDENT.STU_FNAME, STUDENT.STU_LNAME, STUDENT.STU_PHONE, "
        + "STUDENT.STU_BALANCE, STUDENT.STU_ENROLLDATE FROM STUDENT INNER JOIN ENROLL ON "
        + "STUDENT.STU_ID = ENROLL.STU_ID WHERE ENROLL.TUT_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, t);
      rs = ps.executeQuery();

      while(rs.next())
      {
        sa.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getDouble(5), rs.getString(6)));
      }
      ps.close();
      rs.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }

    return sa;
  }
  
  /**
   * Returns a String containing grade for student from certain tutorial
   * @param Student object 
   * @param int Tutorial ID
   * @return String the grade of the Student object for specified Tutorial
   */
  public String getEnrollGrade(Student s, int t)
  {
    int stid = s.getStudentID();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String result = "";
    try
    {
      String q = "SELECT ENROLL_GRADE FROM ENROLL WHERE TUT_ID = ? AND " +
        "STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, t);
      ps.setInt(2, stid);
      rs = ps.executeQuery();
      rs.first();
      result = rs.getString("ENROLL_GRADE");
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
    return result;
  }

  /**
   * Returns an ArrayList of Teacher objects within the tutoring system
   * @return ArrayList of Teacher objects
   */
  public ArrayList<Teacher> listTeachers()
  {
    ArrayList<Teacher> t = new ArrayList<Teacher>();
    PreparedStatement ps = null;
    ResultSet rs = null; 
    try
    {
       String q = "SELECT TEACH_ID, TEACH_FNAME, TEACH_LNAME, TEACH_PHONE FROM TEACHER "
          + "ORDER BY TEACH_ID";
      ps = connect.prepareStatement(q);
      rs = ps.executeQuery();
      
      while (rs.next())
      {
         
        t.add( new Teacher(rs.getInt("TEACH_ID"),rs.getString("TEACH_FNAME"),
               rs.getString("TEACH_LNAME"), rs.getString("TEACH_PHONE")));

      }
      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }

    return t;
  }

  /**
   * Assigns grade to student within certain tutorial
   * @param int Tutorial ID
   * @param int Student ID
   * @param String new grade
   */
  public void assignGrade(int tut, int stu, String g)
  {
    PreparedStatement ps = null;
    
    try
    {
      String q = "UPDATE ENROLL SET ENROLL_GRADE = ? WHERE TUT_ID = ? AND STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setString(1, g);
      ps.setInt(2, tut);
      ps.setInt(3, stu);
      ps.executeUpdate();

      ps.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Returns an ArrayList of Tutorial Objects that a specified Student is enrolled within
   * @param int Student ID
   * @return ArrayList of Tutorial Objects
   */
  public ArrayList<Tutorial> getTutorialsByStudentID(int stid)
  {
    ArrayList<Tutorial> tut = new ArrayList<Tutorial>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      String q = "SELECT TUTORIAL.TUT_ID, TUTORIAL.TUT_DESCRIBE, TUTORIAL.TEACH_ID FROM TUTORIAL INNER JOIN ENROLL "
        + " ON ENROLL.TUT_ID = TUTORIAL.TUT_ID WHERE ENROLL.STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, stid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        tut.add(new Tutorial(rs.getInt(1), rs.getString(2), rs.getInt(3)));
      }

      ps.close();
      rs.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }

    return tut;
  }

  /**
   * Creates a new Tutorial within the database
   * @param String name of tutorial/ tutorial description
   * @param int corresponding teacher of the new tutorial
   */
  public void createTut(String n, int teach)
  {
    PreparedStatement ps = null;
    String result = "";

    try
    {
      String q = "INSERT INTO TUTORIAL (TUT_ID, TUT_DESCRIBE, TEACH_ID) "
          + "VALUES (NULL, ?, ?)";
      ps = connect.prepareStatement(q);
      ps.setString(1, n);
      ps.setInt(2, teach);
      ps.executeUpdate();
      
      ps.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Processes payment of student balance
   * @param double amount of payment
   * @param int Student ID
   * @return String confirming or denying payment
   */
  public String payment(double a, int sID)
  {
    String result = "";
    Student s = findStudentByID(sID);
    double currBalance = s.getBalance();
    PreparedStatement ps = null;

    if(currBalance == 0.00)
    {
      result = "Unable to process payment. Student has $0.00 balance.";
    }
    else if(currBalance > a)
    {
      currBalance = currBalance - a;
      result = "Student balance is $" + currBalance;
      try
      {
        String q = "UPDATE STUDENT SET STU_BALANCE = ? WHERE STU_ID = ?";
        ps = connect.prepareStatement(q);
        ps.setDouble(1, currBalance);
        ps.setInt(2, sID);
        ps.executeUpdate();
        ps.close();
      }
      catch(SQLException e)
      {
        System.out.println(e.getMessage());
      }
    }
    else
    {
      result = "Payment greater than remaining balance.";
    }
    return result;
  }

  /**
   * Lists Tutorials that a Student is enrolled within
   * @param Student Object
   * @return ArrayList of String Objects containing tutorial name and student grade
   */
  public ArrayList<String> listTutorialsEnrolledIn(Student s)
  {
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList<String> x = new ArrayList<String>();

    try
    {
      String q = "SELECT TUTORIAL.TUT_DESCRIBE, ENROLL.ENROLL_GRADE FROM TUTORIAL INNER JOIN ENROLL "
        + "ON TUTORIAL.TUT_ID = ENROLL.TUT_ID WHERE ENROLL.STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, s.getStudentID());
      rs = ps.executeQuery();

      while(rs.next())
      {
        x.add(rs.getString(1) + " " + rs.getString(2));
      }
      ps.close();
      rs.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
    return x;
  }
  
  /**
   * Returns the average grade for a student from all tutorial grades
   * @param Student Object
   * @return String containing average grade
   */
  public String getAvgGrade(Student s)
  {
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList<String> x = new ArrayList<String>();
    int avg = 0;
    int count = 0;
    String avgGrade = "";
    try
    {
      String q = "SELECT ENROLL_GRADE FROM ENROLL WHERE STU_ID = ?";
      ps = connect.prepareStatement(q);
      ps.setInt(1, s.getStudentID());
      rs = ps.executeQuery();

      while(rs.next())
      {
        x.add(rs.getString(1));
      }

      ps.close();
      rs.close();
    }
    catch(SQLException e)
    {
      System.out.println(e.getMessage());
    }
    for(String g : x)
    {
      count++;
      if(g.equalsIgnoreCase("A"))
      {
        avg += 1;
      }
      else if(g.equalsIgnoreCase("B"))
      {
        avg += 2;
      }
      else if(g.equalsIgnoreCase("C"))
      {
        avg += 3;
      }
      else if(g.equalsIgnoreCase("D"))
      {
        avg+= 4;
      }
      else if(g.equalsIgnoreCase("F"))
      {
        avg+= 5;
      }
      else if(g.equalsIgnoreCase("N"))
      {
        avg += 0;
      }
      else{}
    }

    if (count != 0)
    {
      avg = avg/count;
    }
    else
    {
      return "No grades as of now.";
    }

    if(avg >= 1 && avg < 2)
    {
      avgGrade = "A";
    }
    else if(avg >=2 && avg < 3)
    {
      avgGrade = "B";
    }
    else if(avg >= 3 && avg <4)
    {
      avgGrade = "C";
    }
    else if(avg >= 4 && avg < 5)
    {
      avgGrade = "D";
    }
    else if(avg >= 5)
    {
      avgGrade = "F";
    }
    else
    {
      avgGrade = "No grades as of now.";
    }

    return avgGrade;
  }

}
