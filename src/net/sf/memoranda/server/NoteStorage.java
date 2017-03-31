package net.sf.memoranda.server;

import java.io.Serializable;

public class NoteStorage {
	private String title;
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String serialize()
	{	
		// save using Storage class
		Storage st = new Storage();
		st.saveEntry("title", this.title);
		st.saveEntry("content", this.content);
		
		return st.save();
	}
	
	public void deserialize(String str)
	{
		// load key-value map
		Storage st = new Storage();
		st.load(str);
		
		// set instance variables
		this.setTitle(st.readEntry("title"));
		this.setContent(st.readEntry("content"));
	}
}
