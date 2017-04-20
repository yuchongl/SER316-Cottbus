package net.sf.memoranda.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

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
			
			// save server object
			try
			{
				saveServer();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
		else 
		{
			response = notesOp(op, args);
		}
		
		return response;
	}
}
