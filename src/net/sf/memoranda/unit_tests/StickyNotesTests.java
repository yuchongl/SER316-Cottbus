package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;

import org.junit.Test;

public class StickyNotesTests {
	
	@Test
	public void testStickyNotes() 
	{
		CalendarDate date = new CalendarDate(3, 3, 2017);

		Task task = TaskGenerator.generate(date, date, "0", "0", "123", "3");
		
		task.setPriority(Task.PRIORITY_LOW);
		
		assertEquals(task.getPriority(), Task.PRIORITY_LOW);
	}
}
