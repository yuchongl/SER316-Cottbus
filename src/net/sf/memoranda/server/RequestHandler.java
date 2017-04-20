package net.sf.memoranda.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

import org.omg.CORBA.portable.InputStream;

public class RequestHandler extends Observable implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, NoteStorage> notes = new HashMap<String, NoteStorage>();
	
	final static String serverPersistencyFile = "server.persistent";
	
	
	public RequestHandler()
	{		
		try
		{
			 ObjectInputStream oin = new ObjectInputStream(new FileInputStream(serverPersistencyFile));
		     Object notesList = oin.readObject();
		     notes = (HashMap<String, NoteStorage>) notesList;
		     oin.close();
		     
		     System.out.println(serverPersistencyFile + " loaded.");
		     
		} catch (Exception e)
		{
			System.out.println("error while loading server");
			e.printStackTrace(System.out);
		}
	}
	
	public void saveServer() throws FileNotFoundException, IOException
	{
		File file = new File(serverPersistencyFile);

        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file));
        oout.writeObject(notes);
        oout.close();
        
        System.out.println(serverPersistencyFile + " updated");
	}
	
	public String notesOp(String op, Map<String, String> args)
	{
		// return if nothing to do with notes
		if(!op.equals("notes_op"))
		{
			return "";
		}
		
		if(args.get("action").equals("save"))
		{
			String noteTitle = args.get("title");
			String noteContent = args.get("content");
			
			// create note
			NoteStorage ns = new NoteStorage();
			ns.setTitle(noteTitle);
			ns.setContent(noteContent);
		
			// save it to the map
			notes.put(noteTitle, ns);
			
			setChanged();
			notifyObservers(this);
			
			return "OK";
		}
		
		else if(args.get("action").equals("load"))
		{
			String noteTitle = args.get("title");
			
			NoteStorage note = notes.get(noteTitle);
			
			if(note == null)
			{
				return "no such note";
			}
			else
			{
				return note.serialize();
			}
		}
		else 
		{
			return "unknown action";
		}
	}
	
	String prepare()
	{
		String response = "";
		response += "<html>";
		response += "<h1>manage memoranda server</h1>";
		response += ResourceReader.read("manage_template.html");
		response += "<hr />";
		
		return response;
	}
	
	private String finish()
	{
		return "</html>";
	}
	
	public String op(String op, Map<String, String> args)
	{	
		String response = "";

		if(op.equals("list"))
		{	
			for (Entry<String, NoteStorage> note : notes.entrySet())
			{
				response += note.getKey() + ",";
			}
		}
		else if(op.equals("view"))
		{
			response = prepare();
			
			response += "<h3>Title: " + args.get("title") + "</h3>";
			response += "<p>";
			response += notes.get(args.get("title")).getContent();
			response += "</p>";
			
			response += finish();
		}
		else if(op.equals("delete"))
		{
			response = prepare();
			
			response += "delete: " + args.get("title");
			notes.remove(args.get("title"));
			
			response += finish();
		}
		else if(op.equals("manage"))
		{
			response = prepare();
			
			for (Entry<String, NoteStorage> note : notes.entrySet())
			{
				response += "<p>";
				response += "<h3>Title: " + note.getKey() + "</h3>";
				
				response += "<a href=\"/mem?op=delete&title=" + note.getKey() + "\">Delete</a>";
				response += "<a href=\"/mem?op=view&title=" + note.getKey() + "\">View</a>";
				response += "<br /><br />";
				response += "</p>";
			}
			
			response += finish();
		}
		else 
		{
			response = notesOp(op, args);
		}
		
		// save server object
		try
		{
			saveServer();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return response;
	}
}
