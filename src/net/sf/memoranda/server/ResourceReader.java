package net.sf.memoranda.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		} 
        catch (Exception e)
		{
			return "";
		}
        
        return result;
	}
}
