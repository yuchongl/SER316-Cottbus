package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;
import net.sf.memoranda.server.NoteStorage;
import net.sf.memoranda.server.Storage;

import org.junit.Test;

public class ServerTest {

	@Test
	public void testNoteStorage() {
		
		// test note storage by serializing a note and deserialize it.
		NoteStorage ns = new NoteStorage();
		ns.setTitle("test title");
		ns.setContent("test content");
		
		NoteStorage ns2 = new NoteStorage();
		ns2.deserialize(ns.serialize());

		// content and title should equal
		assertTrue(ns.getTitle().equals(ns2.getTitle()));
		assertTrue(ns.getContent().equals(ns2.getContent()));
	}
	
	@Test
	public void testStorage() {
		Storage s = new Storage();
		
		// save a key into the storage
		s.saveEntry("key1", "value1");
		s.saveEntry("key2", "value2");
		
		// test added key-value pairs
		assertTrue(s.readEntry("key1").equals("value1"));
		assertTrue(s.readEntry("key2").equals("value2"));
		
		// test a key that does not exist
		assertTrue(s.readEntry("key that does not exist") == null);
	}

}
