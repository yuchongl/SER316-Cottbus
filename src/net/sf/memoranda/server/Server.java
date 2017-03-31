package net.sf.memoranda.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server
{
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(7501), 0);
		server.createContext("/mem", new MemHttpHandler());
		server.createContext("/", new DefaultHandler());
		server.setExecutor(null);
		server.start();
		
		System.out.println("http server started");
	}
	
	static class DefaultHandler implements HttpHandler
	{
		@Override
		public void handle(HttpExchange t) throws IOException {
			String response = "<html>";
			
			response += "<style type=\"text/css\"><!--.tab { margin-left: 40px; }--></style>";
			
			response += "<h2>SER316 memoranda project HTTP server. Task #76</h2>";
			response += "<h3>Storage for memoranda notes</h3>";
			response += "<div class=\"tab\">";
			
			response += "<p>This server uses HTTP query as parameters. It currently supports two operations: save note and read note. ";
			response += "For notes operation, the format of the query is listed below:</p>";
			response += "<code class=\"tab\">/mem?op=[notes_op]&action=[save | load]&title=[...]&content=[...]</code>";
			response += "<p>Note that the title of the note is used to identify it.</p>";
			
			response += "<h3>Saving a note</h3>";
			response += "<code class=\"tab\">Example: GET http://104.236.193.225:7501/mem?op=notes_op&action=save&title=test&content=test_content</code>";
			response += "<p>responses \"OK\" on success.</p>";
			
			response += "<h3>Reading a note</h3>";
			
			response += "<code class=\"tab\">Example: GET http://104.236.193.225:7501/mem?op=notes_op&action=load&title=test</code>";
			response += "<p>responses \"no such note\" if the note with the specified title does not exist.</p>";
			response += "<p>responses the serialized content of the note if it is found.</p>";
			response += "<code class=\"tab\">Example response: 2;5,6,title,test23;7,12,content,test_content;</code>";
			response += "<p>To deserialize the note, use the following code: </p>";
			response += "<code>NoteStorage ns = new NoteStorage();<br />ns.deserialize(str);</code>";
			
			response += "</div>";
			response += "<hr /><small>Yuchong Li, Mar. 26, 2017</small>";
			response += "</html>";
			
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class MemHttpHandler implements HttpHandler
	{
		// storage for notes
		static HashMap<String, NoteStorage> notes = new HashMap<String, NoteStorage>();
		
		/**
		 * http://localhost:7501/mem?op=notes_op&action=save&title=test&content=test_content
		 * http://localhost:7501/mem?op=notes_op&action=load&title=test
		 * */
		public static String notesOp(String op, Map<String, String> args)
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
		
		public static String op(String op, Map<String, String> args)
		{	
			String response = notesOp(op, args);
			
			return response;
		}
		
		public static Map<String, String> parseQueryString(String query) {
			Map<String, String> result = new HashMap<>();
			if (query == null)
			{
				return result;
			}
			
			int last = 0, next, len = query.length();
			while (last < len)
			{
				next = query.indexOf('&', last);
				if (next == -1)
				{
					next = len;
				}
				
				if (next > last)
				{
					int pos = query.indexOf('=', last);
					try
					{
						if (pos < 0 || pos > next)
						{
							result.put(URLDecoder.decode(query.substring(last, next), "utf-8"), "");
						} else
						{
							result.put(URLDecoder.decode(query.substring(last, pos), "utf-8"), URLDecoder.decode(query.substring(pos + 1, next), "utf-8"));
						}
					} catch (UnsupportedEncodingException e)
					{
						throw new RuntimeException(e);
					}
				}
				last = next + 1;
			}
			return result;
		}

		@Override
		public void handle(HttpExchange t) throws IOException {
			
			// parse query
			String query = t.getRequestURI().getQuery();
			Map<String, String> q = parseQueryString(query);

			System.out.println("===============");
			System.out.println("query: " + query);
			for (Entry<String, String> entry : q.entrySet())
			{
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}

			// send response
			String response = op(q.get("op"), q);
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
