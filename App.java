/**
 * Tutoring Management System
 * @author Jordan Leeper
 * @version 1.0
 * 
 */


import java.sql.*;
import java.util.*;
import java.text.*;

public class App
{
  DB mydb;
  Scanner sc;
  SimpleDateFormat sdf;
  public App()
  {
    mydb = new DB();
    sc = new Scanner(System.in);
    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  }

  public void showMenu()
  {
     System.out.println();
     System.out.println("1 = Student Menu");
     System.out.println("2 = Teacher Menu");
     System.out.println("3 = Tutorial Menu");
     System.out.println("0 = Quit");
  }

  public void studentMenu()
  {
    System.out.println();
    System.out.println("1 = Add Student");
    System.out.println("2 = List Students");
    System.out.println("3 = Find Student");
    System.out.println("4 = Edit Student Information");
    System.out.println("5 = Delete Student");
    System.out.println("6 = Enroll Student in Tutorial");
    System.out.println("7 = List Students in a Tutorial");
    System.out.println("8 = Student Grades");
    System.out.println("9 = Assign Student Grade");
    System.out.println("10 = Make Payment on Student Account");
    System.out.println("0 = Quit");
  }

  public void teacherMenu()
  {
    System.out.println();
    System.out.println("1 = List Teachers");
    System.out.println("2 = Add Teacher");
    System.out.println("3 = Edit Teacher");
    System.out.println("4 = Delete Teacher");
    System.out.println("0 = Quit");
  }

  public void tutorialMenu()
  {
    System.out.println();
    System.out.println("1 = Enroll Student in Tutorial");
    System.out.println("2 = List Tutorials/Students enrolled within Tutorial");
    System.out.println("3 = Create New Tutorial");
    System.out.println("0 = Quit");
  }
  public void mainLoop() throws Exception
  {
    int choice = 999;
    while (choice != 0) {
      showMenu();
      choice = Validator.getInt(sc, "Enter choice: ", 0, 3);
      if (1 == choice) studentLoop();
      else if (2 == choice) teacherLoop();
      else if (3 == choice) tutorialLoop();
      else if (0 == choice);
      else System.out.println("\nInvalid choice. Please try again.\n");
    }
  }

  public void studentLoop() throws Exception
  {
    int choice = 999;
    studentMenu();
    choice = Validator.getInt(sc, "Enter choice: ", 0, 10);
    if (1 == choice) addStudent();
    else if (2 == choice) listStudents();
    else if (3 == choice) findStudent();
    else if (4 == choice) editStudent();
    else if (5 == choice) deleteStudent();
    else if (6 == choice) enrollStudent();
    else if (7 == choice) listStudentsInTutorial();
    else if (8 == choice) studentGrade();
    else if (9 == choice) assignGrade();
    else if (10 == choice) makePayment();
    else if (0 == choice) mainLoop();
    else System.out.println("Invalid choice. Please try again.\n");
  }

  public void teacherLoop() throws Exception
  {
    int choice = 999;
    teacherMenu();
    choice = Validator.getInt(sc, "Enter choice: ", 0, 4);
    if (1 == choice) listTeachers();
    else if (2 == choice) addTeacher();
    else if (3 == choice) editTeacher();
    else if (4 == choice) deleteTeacher();
    else if (0 == choice) mainLoop();
    else System.out.println("Invalid choice. Please try again.\n");
  }

  public void tutorialLoop() throws Exception
  {
    int choice = 999;
    tutorialMenu();
    choice = Validator.getInt(sc, "Enter choice: ", 0, 3);
    if (1 == choice) enrollStudent();
    else if (2 == choice) listStudentsInTutorial();
    else if (3 == choice) createTutorial();
    else if (0 == choice) mainLoop();
    else System.out.println("Invalid choice. Please try again.\n");
  }

//***************** Methods ***************************
  public void addStudent()
  {
    int studentID = 0;
    double balance = 0.00;
    java.util.Date d = new java.util.Date();
    String enrollDate = sdf.format(d);
    String first = Validator.getLine(sc, "Enter student first name: ");
    String last  = Validator.getLine(sc, "Enter student last name: ");
    String phone = Validator.getLine(sc, "Enter phone number (1112223333): ");
    boolean hasBalance = Validator.getBool(sc, "Does student have remaining balance: ");
    if (hasBalance)
    {
      balance = Validator.getDouble(sc, "Enter total remaining balance: ");
    }
    Student s = new Student(studentID, first, last, phone, balance, enrollDate);
    String result = mydb.addStudent(s);
    System.out.println(result);
  }

  public void listStudents()
  {
    int orderBy =
      Validator.getInt(sc, "1 = sort by student ID, 2 = sort by student name: ", 1, 2);
    int startRecord =
      Validator.getInt(sc, "Index of starting record: ", 1, 999999999);
    int numberToDisplay =
      Validator.getInt(sc, "How many records to display: ", 1, 999999999);

    ArrayList<Student> cList =
      mydb.listStudents(orderBy, startRecord, numberToDisplay);

    for (Student c : cList)
    {
      System.out.printf("%-10s %-20s %-20s %-10s %-8.2f %-15s\n",
          c.getStudentID(), c.getFirstName(), c.getLastName(), c.getPhone(), c.getBalance(),
            c.getEnrollDate());
    }
  }

  public void findStudent()
  {

    int question = Validator.getInt(sc, "1 = search by student name, 2 = search by student ID: ", 1, 2);

    if(question == 1)
    {
      String first = Validator.getLine(sc, "Enter student first name: ");
      String last  = Validator.getLine(sc, "Enter student last name: ");
      ArrayList<Student> cList = mydb.findStudentByName(first, last);
      for (Student c : cList)
      {
        System.out.printf("%-10s %-20s %-20s %-10s %-10.2f %-15s\n",
          c.getStudentID(), c.getFirstName(), c.getLastName(), c.getPhone(), c.getBalance(),
          c.getEnrollDate());
      }
    }
    else
    {
      int studentID = Validator.getInt(sc, "Enter student ID: ");
      Student c = mydb.findStudentByID(studentID);
      if(c.getFirstName() != null)
      {
        System.out.printf("%-10s %-20s %-20s %-10s %-10.2f %-15s\n",
          c.getStudentID(), c.getFirstName(), c.getLastName(), c.getPhone(), c.getBalance(),
          c.getEnrollDate());
      }
      else
      {
        System.out.println("Student not found.");
      }
    }
  }

 public void editStudent()
 {
   int studentID = Validator.getInt(sc, "Enter the student ID of the student to edit: ");
   Student c = mydb.findStudentByID(studentID);

   if(c.getFirstName() != null)
   {
      System.out.printf("%-10s %-20s %-20s %-10s %-10.2f %-15s\n",
        c.getStudentID(), c.getFirstName(), c.getLastName(), c.getPhone(), c.getBalance(),
        c.getEnrollDate());
   	boolean isStudent = Validator.getBool(sc, "Is this the desired student (y or n): ");

   if(isStudent)
   {
     int editWhat = Validator.getInt(sc, "Edit 1 = name, 2 = phone, 3 = balance: ", 1, 3);
     if(editWhat == 1)
     {
       int forl = Validator.getInt(sc, "Edit 1 = first or 2 = last: ", 1, 2);
       if(forl == 1)
       {
         String newFirst = Validator.getLine(sc, "Enter new first name: ");
         Student fn = mydb.editFirst(c, newFirst);
         System.out.println(fn);
       }
       else
       {
         String newLast = Validator.getLine(sc, "Enter new last name: ");
         Student ln = mydb.editLast(c, newLast);
         System.out.println(ln);
       }
     }
     else if(editWhat == 2)
     {
       String phone = Validator.getLine(sc, "Enter new phone: ", 10);
       Student p = mydb.editPhone(c, phone);
       System.out.println(p);
     }
     else if(editWhat == 3)
     {
       double newBalance = Validator.getDouble(sc, "Enter new balance: ");
       Student b = mydb.editBalance(c, newBalance);
       System.out.println(b);
     }
     else{}
   }
  }
  else
  {
    System.out.println("Student not found.");
  }

 }
  public void enrollStudent()
  {
    int select = Validator.getInt(sc, "Enter student ID to enroll: ");
    Student s = mydb.findStudentByID(select);
    if(s.getFirstName() != null)
    {
		System.out.println(s.toString());
		ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();

		boolean isStudent = Validator.getBool(sc, "Is this the desired student (y or n): ");
		if(isStudent)
		{
		  tutorials = mydb.listTutorials();
		  for(Tutorial t : tutorials)
		  {
			System.out.println(t.toString());
		  }

		  int t = Validator.getInt(sc, "Enter the ID of the desired tutorial: ");
		  Tutorial tut = mydb.findTutorialByID(t);
		  if(tut.getDescription() != null)
		  {
		  	mydb.enrollStudent(s, t);
		  	System.out.println(s.getFirstName() + " " + s.getLastName() + " enrolled in " + tut.getDescription());
		  }
		  else{
			  System.out.println("Tutorial not found.");
		  }

		}
    	else{}
	}
    else
    {
		System.out.println("Student not found.");
	}
  }

  public void deleteStudent()
  {
    String result = "";
    ArrayList<Student> stud = mydb.listStudents(1, 1, 999);
    for(Student t : stud)
    {
       System.out.println(t.toString());
    }

    int sID = Validator.getInt(sc, "Enter the ID of the student to delete: ");
    Student s = mydb.findStudentByID(sID);
    if(s.getFirstName() != null)
    {
		boolean sure = Validator.getBool(sc, "Are you sure you want to delete this student (y or n): ");

		if(sure)
		{
		  result = mydb.delStudent(sID);
		}
		else
		{
		  result = "Canceled.";
		}
		System.out.println(result);
	}
	else
	{
		System.out.println("Student not found.");
	}
  }

  public void listStudentsInTutorial()
  {
    ArrayList<Tutorial> tut = mydb.listTutorials();
    for(Tutorial t : tut)
    {
      System.out.println(t.toString());
    }
    int t = Validator.getInt(sc, "Enter ID of desired tutorial to display students enrolled: ");
	Tutorial tutorial = mydb.findTutorialByID(t);
	if(tutorial.getDescription() != null)
	{
		ArrayList<Student> st = mydb.listStudentsInTutorial(t);
    if(!st.isEmpty())
    {
		  for(Student s : st)
		  { 
		    System.out.println(s.toString()+ " Student Grade: " + mydb.getEnrollGrade(s,t));
		  }
    }
    else
    {
      System.out.println("No students enrolled.");
    }
	}
	else
	{
		System.out.println("Tutorial not found.");
	}

  }

  public void listTeachers()
  {
    ArrayList<Teacher> teach = mydb.listTeachers();
    for(Teacher t : teach)
    {
      System.out.println(t.toString());
    }
  }

  public void addTeacher()
  {
    int teachID = 0;
    String first = Validator.getLine(sc, "Enter teacher first name: ");
    String last  = Validator.getLine(sc, "Enter teacher last name: ");
    String phone = Validator.getLine(sc, "Enter phone number (1112223333): ");
    Teacher t = new Teacher(teachID, first, last, phone);
    String result = mydb.addTeacher(t);
    System.out.println(result);
  }

  public void editTeacher()
  {
    ArrayList<Teacher> teach = mydb.listTeachers();
    for(Teacher t : teach)
    {
      System.out.println(t.toString());
    }

   int teachID = Validator.getInt(sc, "Enter the teacher ID of the teacher to edit: ");
   Teacher teacher = mydb.findTeacherByID(teachID);
   if(teacher.getFirstName() != null)
   {
     int editWhat = Validator.getInt(sc, "Edit 1 = name, 2 = phone: ", 1, 2);
     if(editWhat == 1)
     {
       int forl = Validator.getInt(sc, "Edit 1 = first or 2 = last: ", 1, 2);
       if(forl == 1)
       {
         String newFirst = Validator.getLine(sc, "Enter new first name: ");
         Teacher fn = mydb.editTeacherFirst(teachID, newFirst);
         System.out.println(fn);
       }
       else
       {
         String newLast = Validator.getLine(sc, "Enter new last name: ");
         Teacher ln = mydb.editTeacherLast(teachID, newLast);
         System.out.println(ln);
       }
     }
     else if(editWhat == 2)
     {
       String phone = Validator.getLine(sc, "Enter new phone: ", 10);
       Teacher p = mydb.editTeacherPhone(teachID, phone);
       System.out.println(p);
     }
     else{}
 	}
 	else
 	{
		System.out.println("Teacher not found.");
	}
  }

  public void deleteTeacher()
  {
    String result = "";
    ArrayList<Teacher> teach = mydb.listTeachers();
    for(Teacher t : teach)
    {
       System.out.println(t.toString());
    }

    int tID = Validator.getInt(sc, "Enter the ID of the teacher to delete: ");
    Teacher teacher = mydb.findTeacherByID(tID);
    if(teacher.getFirstName() != null)
    {
		boolean sure = Validator.getBool(sc, "Are you sure you want to delete this teacher (y or n): ");

		if(sure)
		{
		  result = mydb.delTeacher(tID);
		}
		else
		{
		  result = "Canceled.";
		}
		System.out.println(result);
	}
	else
	{
		System.out.println("Teacher not found.");
	}
  }

  public void assignGrade()
  {
    int search = Validator.getInt(sc, "Assign student grade by 1 = search tutorial or 2 = search student: ", 1, 2);
    if(search == 1)
    {
      ArrayList<Tutorial> tut = mydb.listTutorials();
      for(Tutorial t : tut)
      {
        System.out.println(t.toString());
      }
      int tutSelect = Validator.getInt(sc, "Enter tutorial ID you would like to search: ");
      Tutorial tutorial = mydb.findTutorialByID(tutSelect);
      if(tutorial.getDescription() != null)
      {
		  ArrayList<Student> st = mydb.listStudentsInTutorial(tutSelect);
		  for(Student s : st)
		  {
			System.out.println(s.toString());
		  }

		  int stuSelect = Validator.getInt(sc, "Enter student ID to assign new grade: ");
		  Student student = mydb.findStudentByID(stuSelect);
		  if(student.getFirstName() != null)
		  {
			  String grade = Validator.getLine(sc, "Enter grade (A-F), (N = no grade): ");
			  mydb.assignGrade(tutSelect, stuSelect, grade);

			  System.out.println("Grade updated.");
		  }
		  else
		  {
			  System.out.println("Student not found.");
		  }
	  }
	  else
	  {
		  System.out.println("Tutorial not found.");
	  }
    }
    else
    {
      ArrayList<Student> st2 = mydb.listStudents(1, 1, 999);
      for(Student s : st2)
      {
        System.out.println(s.toString());
      }
      int stuSelect2 = Validator.getInt(sc, "Enter student ID to view tutorials enrolled in: ");
      Student student2 = mydb.findStudentByID(stuSelect2);
      if(student2.getFirstName() != null)
      {
		  ArrayList<Tutorial> tut = mydb.getTutorialsByStudentID(stuSelect2);
		  for(Tutorial t : tut)
		  {
			System.out.println(t.toString());
		  }
		  int tutSelect2 = Validator.getInt(sc, "Enter the tutorial ID to assign student's grade: ");
		  Tutorial tutorial2 = mydb.findTutorialByID(tutSelect2);
		  if(tutorial2.getDescription() != null)
		  {
			  String grade2 = Validator.getLine(sc, "Enter grade (A-F), (N = no grade): ");
			  mydb.assignGrade(tutSelect2, stuSelect2, grade2);

			  System.out.println("Grade updated.");
		  }
		  else
		  {
			  System.out.println("Tutorial not found.");
		  }
	  }
	  else
	  {
		  System.out.println("Student not found.");
	  }
    }
  }

  public void createTutorial()
  {
    String result = "New tutorial added";
    String name = Validator.getLine(sc, "Enter the name of the tutorial: ");
    ArrayList<Teacher> teachers = mydb.listTeachers();
    for(Teacher t : teachers)
    {
      System.out.println(t.toString());
    }
    int teachSelect = Validator.getInt(sc, "Enter the ID of the teacher of tutorial: ");
	Teacher teacher = mydb.findTeacherByID(teachSelect);
	if(teacher.getFirstName() != null)
	{
		mydb.createTut(name, teachSelect);

		System.out.println(result);
	}
	else
	{
		System.out.println("Teacher not found.");
	}

  }

  public void makePayment()
  {
    double amount = Validator.getDouble(sc, "Enter the amount of payment: ");
    ArrayList<Student> st = mydb.listStudents(1,1,999);
    for(Student s : st)
    {
      System.out.println();
      System.out.println(s.toString());
    }

    int sID = Validator.getInt(sc, "Enter the student ID to make payment for: ");
    Student student = mydb.findStudentByID(sID);
    if(student.getFirstName() != null)
    {
		String result = mydb.payment(amount, sID);

		System.out.println(result);
	}
	else
	{
		System.out.println("Student not found.");
	}
  }

  public void studentGrade()
  {
    ArrayList<Student> st = mydb.listStudents(1,1,999);
    for(Student s : st)
    {
      System.out.println();
      System.out.println(s.toString());
    }
    int sid = Validator.getInt(sc, "Enter the student ID to view grades: ");
    Student s = mydb.findStudentByID(sid);
	if(s.getFirstName() != null)
	{
		ArrayList<String> tutGrade = mydb.listTutorialsEnrolledIn(s);
		for(String g : tutGrade)
		{
		  System.out.println(g);
		}
		String avg = mydb.getAvgGrade(s);
		System.out.println();
		System.out.println(s.getFirstName() + " " + s.getLastName() + " has a grade average of: " + avg);
		System.out.println("Grades are rounded up.");
	}
	else
	{
		System.out.println("Student not found.");
	}
  }

  public static void main(String[] args) throws Exception
  {
    App tutoringSystem = new App();
    tutoringSystem.mainLoop();
  }






}
