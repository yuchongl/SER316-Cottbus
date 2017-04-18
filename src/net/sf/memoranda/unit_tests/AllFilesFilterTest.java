package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import net.sf.memoranda.ui.AllFilesFilter;

public class AllFilesFilterTest {

	@Test
	public void acceptTest() {
		AllFilesFilter aff = new AllFilesFilter(AllFilesFilter.RTF);
		assertFalse(aff.accept(new File("r.html")));
		assertFalse(aff.accept(new File("r.htm")));
		assertFalse(aff.accept(new File("r.xhtml")));
		assertFalse(aff.accept(new File("r.zip")));
		assertFalse(aff.accept(new File("r.exe")));
		assertFalse(aff.accept(new File("r.jar")));
		assertFalse(aff.accept(new File("r.ico")));
		assertFalse(aff.accept(new File("r.wav")));
		assertFalse(aff.accept(new File("r.txt")));
		assertFalse(aff.accept(new File("r.asdf")));
		assertTrue(aff.accept(new File("r.rtf")));

		aff = new AllFilesFilter(AllFilesFilter.HTML);
		assertTrue(aff.accept(new File("r.html")));
		assertTrue(aff.accept(new File("r.htm")));
		assertFalse(aff.accept(new File("r.xhtml")));
		assertFalse(aff.accept(new File("r.zip")));
		assertFalse(aff.accept(new File("r.exe")));
		assertFalse(aff.accept(new File("r.jar")));
		assertFalse(aff.accept(new File("r.ico")));
		assertFalse(aff.accept(new File("r.wav")));
		assertFalse(aff.accept(new File("r.txt")));
		assertFalse(aff.accept(new File("r.asdf")));
		assertFalse(aff.accept(new File("r.rtf")));
		
		aff = new AllFilesFilter(AllFilesFilter.WAV);
		assertFalse(aff.accept(new File("r.html")));
		assertFalse(aff.accept(new File("r.htm")));
		assertFalse(aff.accept(new File("r.xhtml")));
		assertFalse(aff.accept(new File("r.zip")));
		assertFalse(aff.accept(new File("r.exe")));
		assertFalse(aff.accept(new File("r.jar")));
		assertFalse(aff.accept(new File("r.ico")));
		assertTrue(aff.accept(new File("r.wav")));
		assertFalse(aff.accept(new File("r.txt")));
		assertFalse(aff.accept(new File("r.asdf")));
		assertFalse(aff.accept(new File("r.rtf")));
	}

}
