import static org.junit.Assert.*;

import org.junit.Test;


public class StudentTest {
	
	@Test
	public void testGetStudentID() {
		Student s = new Student(1, "Jordan", "Leeper", "7171234567", 200.00, "2012-11-06 00:00:00");
		assertTrue(s.getStudentID() == 1);
	}
	
	@Test
	public void testGetFirstName() {
		Student s = new Student(1, "Jordan", "Leeper", "7171234567", 200.00, "2012-11-06 00:00:00");
		assertTrue(s.getFirstName(),s.getFirstName().equals("Jordan"));
	}
	
	@Test
	public void testGetLastName() {
		Student s = new Student(1, "Jordan", "Leeper", "7171234567", 200.00, "2012-11-06 00:00:00");
		assertEquals("Failed", s.getLastName(), "Leeper");
	}
	
	@Test
	public void testGetPhone() {
		Student s = new Student(1, "Jordan", "Leeper", "7171234567", 200.00, "2012-11-06 00:00:00");
		assertEquals("Failed", s.getPhone(), "7171234567");
	}
	
	@Test
	public void testGetBalance() {
		Student s = new Student(1, "Jordan", "Leeper", "7171234567", 200.00, "2012-11-06 00:00:00");
		assertEquals("Failed", s.getBalance(), 200.00, .001);
	}
	
	@Test
	public void testGetEnrollDate() {
		Student s = new Student(1, "Jordan", "Leeper", "7171234567", 200.00, "2012-11-06 00:00:00");
		assertEquals("Failed", s.getEnrollDate(), "2012-11-06 00:00:00");
	}

}
