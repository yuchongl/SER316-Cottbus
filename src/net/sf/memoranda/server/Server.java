package net.sf.memoranda.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Server {
	public static void main(String[] args)
	{
		System.out.println("server starting");

		NoteStorage ns = new NoteStorage();
		ns.setTitle("test title");
		ns.setContent("test content");
		
		System.out.println(ns.serialize());
		
		//Storage s = new Storage();
		//s.load("2;5,10,title,test title;7,12,content,test content;");
	}
}
