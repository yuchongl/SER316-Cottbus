package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;

import org.junit.Test;

public class TaskCompletionTests {
	
	@Test
	public void testCompletion() 
	{	
		CalendarDate date = new CalendarDate(3, 3, 2017);

		Task task = TaskGenerator.generate(date, date, "0", "0", "123", "3");
		task.setProgress(50);
		task.setProgress(100);
		
		// set progress to 100 should complete the task
		assertEquals(task.getStatus(date), Task.COMPLETED);
	}

}
