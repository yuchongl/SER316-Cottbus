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
		Storage st = new Storage();
		st.saveEntry("title", this.title);
		st.saveEntry("content", this.content);
		
		return st.save();
	}
	
	public void deserialize(String str)
	{
		
	}
}
