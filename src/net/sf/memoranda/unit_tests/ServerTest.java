package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import net.sf.memoranda.server.NoteStorage;
import net.sf.memoranda.server.RequestHandler;
import net.sf.memoranda.server.ResourceReader;
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
	
	@Test
	public void testResources()
	{
		// make sure it reads the resource
		assertTrue(ResourceReader.read("server_index.html").length() > 0);
	}
	
	@Test
	public void testRequestHandler()
	{
		RequestHandler handler = new RequestHandler("server_tmp.per");
		
		// put some notes
		HashMap<String, String> args = new HashMap<String, String>();
		
		args.clear();
		args.put("title", "test_title");
		args.put("action", "save");
		args.put("content", "test_content");
		handler.op("notes_op", args);
		
		args.clear();
		args.put("title", "test_title2");
		args.put("action", "save");
		args.put("content", "test_content");
		handler.op("notes_op", args);
		
		// load the server from a temporary file
		RequestHandler handler2 = new RequestHandler("server_tmp.per");
		
		// should have 2 note
		assertTrue(handler2.notes.size() == 2);
		
		// delete the note
		args.clear();
		args.put("title", "test_title");
		handler2.op("delete", args);
		
		// now there should have 1 note
		assertTrue(handler2.notes.size() == 1);
		
		args.clear();
		args.put("title", "test_title2");
		handler2.op("delete", args);
		
		// now there should have zero note
		assertTrue(handler2.notes.size() == 0);
	}

}
