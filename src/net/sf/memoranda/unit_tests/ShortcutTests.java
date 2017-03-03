package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;

import java.awt.Toolkit;

import org.junit.Test;

public class ShortcutTests {

	@Test
	public void testShortCuts() 
	{
		if(System.getProperty("os.name").equals("Mac OS X"))
		{
			// shortcut key mask should equal to 0x04 in Mac
			assertEquals(0x04, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		}	
	}

}
