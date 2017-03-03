package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;

import org.junit.Test;

public class TaskDueDateTests {

	@Test
	public void testDueDates() 
	{
		CalendarDate date = new CalendarDate(3, 3, 2017);

		Task task = TaskGenerator.generate(date, date, "0", "0", "123", "3");

		assertEquals(task.getEndDate().getDay(), 3);
		assertEquals(task.getEndDate().getMonth(), 3);
		assertEquals(task.getEndDate().getYear(), 2017);
	}

}
