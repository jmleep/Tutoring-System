/**
 * Validator.java
 * @author Jordan Leeper and John Phillips
 * @version 2.0 
 */

// Validator.java prompts and validates console input.
// Based on code from Murach's Java with a few changes.
// Ranges are inclusive. Changes by Phillips on 9/3/12.

import java.util.Scanner;

public class Validator
{

  /**
   * Returns a string and stores string entered by user
   * @param Scanner object
   * @param String prompt
   * @return String response
   */
  public static String getString(Scanner sc, String prompt)
  {
    System.out.print(prompt);
    String s = sc.next();		// read the first string on the line
    sc.nextLine();				// discard any other data entered on the line
    return s;
  }
  
  /**
   * Returns a string object 
   * @param Scanner object
   * @param String prompt
   * @return String response
   */
  public static String getLine(Scanner sc, String prompt)
  {
    System.out.print(prompt);
    String s = sc.nextLine();
    return s;
  }

  /**
   * Returns a string object that must be a certain length
   * @param Scanner object
   * @param String prompt
   * @param int length of string
   * @return String response
   */  
  public static String getLine(Scanner sc, String prompt, int i)
  {
    boolean isValid = false;
    String input = "";
    while(!isValid)
    {
      System.out.println(prompt);
      input = sc.nextLine();
      if(input.length() == 10)
      {
        isValid = true;
      }
      else
      {
        System.out.println("Enter a 10 digit phone number.");
      }
    }
    return input;
  }

    
  /**
   * Returns an integer
   * @param Scanner object
   * @param String prompt
   * @return int value entered by user
   */  
  public static int getInt(Scanner sc, String prompt)
  {
    boolean isValid = false;
    int i = 0;
    while (isValid == false)
    {
      System.out.print(prompt);
      if (sc.hasNextInt())
      {
        i = sc.nextInt();
        isValid = true;
      }
      else
      {
        System.out.println("Error! Invalid integer value. Try again.");
      }
      sc.nextLine();
    }
    return i;
  }
  
  /**
   * Returns an int 
   * @param Scanner object
   * @param String prompt
   * @param int minimum number of user int
   * @param int maximum number of user int
   * @return int response
   */
  public static int getInt(Scanner sc, String prompt,
      int min, int max)
  {
    int i = 0;
    boolean isValid = false;
    while (isValid == false)
    {
      i = getInt(sc, prompt);
      if (i < min)
        System.out.println(
            "Error! Number must be greater than or equal to " + min);
      else if (i > max)
        System.out.println(
            "Error! Number must be less than or equal to " + max);
      else
        isValid = true;
    }
    return i;
  }

  /**
   * Returns a double 
   * @param Scanner object
   * @param String prompt
   * @return double response
   */
  public static double getDouble(Scanner sc, String prompt)
  {
    boolean isValid = false;
    double d = 0;
    while (isValid == false)
    {
      System.out.print(prompt);
      if (sc.hasNextDouble())
      {
        d = sc.nextDouble();
        isValid = true;
      }
      else
      {
        System.out.println("Error! Invalid decimal value. Try again.");
      }
      sc.nextLine();
    }
    return d;
  }

  /**
   * Returns a double 
   * @param Scanner object
   * @param String prompt
   * @param double minimum number of user double
   * @param double maximum number of user double
   * @return double response
   */
  public static double getDouble(Scanner sc, String prompt,
      double min, double max)
  {
    double d = 0;
    boolean isValid = false;
    while (isValid == false)
    {
      d = getDouble(sc, prompt);
      if (d < min)
        System.out.println(
            "Error! Number must be greater than or equal to " + min);
      else if (d > max)
        System.out.println(
            "Error! Number must be less than or equal to " + max);
      else
        isValid = true;
    }
    return d;
  } 
  
  /**
   * Returns a boolean value
   * @param Scanner object
   * @param String prompt
   * @return boolean value
   */
  public static boolean getBool(Scanner sc, String prompt)
  {
    System.out.println(prompt);
    String s = sc.nextLine();
    if(s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y"))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

}
