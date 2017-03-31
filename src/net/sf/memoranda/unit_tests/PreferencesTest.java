package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;

import javax.swing.JFrame;
import org.junit.Test;

import net.sf.memoranda.ui.PreferencesDialog;
import net.sf.memoranda.util.Configuration;

public class PreferencesTest {

	PreferencesDialog pd;

	@Test
	public void testTabs() {
		pd = new PreferencesDialog(null, "Resource Types");
		assertEquals(pd.tabbedPanel.getTitleAt(0), "Resource Types");
		pd = new PreferencesDialog(null, "General");
		assertEquals(pd.tabbedPanel.getTitleAt(0), "General");
		pd = new PreferencesDialog(null, "Theme");
		assertEquals(pd.tabbedPanel.getTitleAt(0), "Theme");
		pd = new PreferencesDialog(null, "Sound");
		assertEquals(pd.tabbedPanel.getTitleAt(0), "Sound");
		pd = new PreferencesDialog(null, "Editor");
		assertEquals(pd.tabbedPanel.getTitleAt(0), "Editor");
	}
	
	@Test
	public void testTheme() {
		Configuration.put("THEME_SETTING", "DEFAULT");
		pd = new PreferencesDialog(new JFrame(), "Resource Types");
		assertTrue(pd.themeDefaultRB.isSelected());
		assertFalse(pd.themeOP1RB.isSelected());
		assertFalse(pd.themeOP2RB.isSelected());
		assertFalse(pd.classicThemeRB.isSelected());
		
		Configuration.put("THEME_SETTING", "METAL");
		pd = new PreferencesDialog(new JFrame(), "Resource Types");
		assertFalse(pd.themeDefaultRB.isSelected());
		assertFalse(pd.themeOP1RB.isSelected());
		assertTrue(pd.themeOP2RB.isSelected());
		assertFalse(pd.classicThemeRB.isSelected());
		
		Configuration.put("THEME_SETTING", "Nimbus");
		pd = new PreferencesDialog(new JFrame(), "Resource Types");
		assertFalse(pd.themeDefaultRB.isSelected());
		assertTrue(pd.themeOP1RB.isSelected());
		assertFalse(pd.themeOP2RB.isSelected());
		assertFalse(pd.classicThemeRB.isSelected());
		
		Configuration.put("THEME_SETTING", "CLASSIC");
		pd = new PreferencesDialog(new JFrame(), "Resource Types");
		assertFalse(pd.themeDefaultRB.isSelected());
		assertFalse(pd.themeOP1RB.isSelected());
		assertFalse(pd.themeOP2RB.isSelected());
		assertTrue(pd.classicThemeRB.isSelected());
	}

}
