package net.sf.memoranda.unit_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ResumeTaskTests.class, ShortcutTests.class,
		StickyNotesTests.class, TaskCompletionTests.class,
		TaskDueDateTests.class, PreferencesTest.class, AppFrameTest.class })
public class AllTests {

}
