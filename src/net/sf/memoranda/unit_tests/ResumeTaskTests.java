package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;
import net.sf.memoranda.*;
import net.sf.memoranda.date.CalendarDate;

import org.junit.Test;

public class ResumeTaskTests {

	@Test
	public void testResumeTasks() 
	{
		CalendarDate date = new CalendarDate(3, 3, 2017);

		Task task = TaskGenerator.generate(date, date, "0", "0", "123", "3");
		task.setProgress(100);
		
		assertEquals(task.getStatus(date), Task.COMPLETED);
		
		task.setProgress(0);
		assertEquals(task.getStatus(date), Task.DEADLINE);
	}

}
