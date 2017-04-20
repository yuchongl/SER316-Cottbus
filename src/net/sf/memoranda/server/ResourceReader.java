package net.sf.memoranda.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ResourceReader
{
	public static String read(String file)
	{
		String result = "";
		
		InputStream is = RequestHandler.class.getClassLoader().getResourceAsStream("net/sf/memoranda/server/" + file);

        BufferedReader br=new BufferedReader(new InputStreamReader(is));  
        String s = "";  
        try
		{
			while((s = br.readLine()) != null)  
			{
			    result += s;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
        
        return result;
	}
	
}
