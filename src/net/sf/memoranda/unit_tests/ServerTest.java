package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;
import net.sf.memoranda.server.NoteStorage;

import org.junit.Test;

public class ServerTest {

	@Test
	public void testStorage() {
		// test note storage by serializing a note and deserialize it.
		NoteStorage ns = new NoteStorage();
		ns.setTitle("test title");
		ns.setContent("test content");
		
		NoteStorage ns2 = new NoteStorage();
		ns2.deserialize(ns.serialize());

		assertTrue(ns.getTitle().equals(ns2.getTitle()));
		assertTrue(ns.getContent().equals(ns2.getContent()));
	}

}
