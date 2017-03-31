package net.sf.memoranda.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

public class Storage implements Serializable
{		
	/**
	 * 
	 */
	private static final long serialVersionUID = 7222804314853404193L;
	HashMap<String, String> storageMap;
	
	int VERBOSE = 1;
	
	public Storage()
	{
		storageMap = new HashMap<String, String>();
	}
	
	public boolean saveEntry(String key, String value)
	{
		storageMap.put(key, value);
		return true;
	}
	
	public String readEntry(String key)
	{
		return storageMap.get(key);
	}
	
	public String save()
	{
		String result = "";
		
		result += storageMap.size() + ";";
		
		for(Entry<String, String> entry : storageMap.entrySet())
		{
			result += entry.getKey().length() + "," + entry.getValue().length() + ",";
			result += entry.getKey() + "," + entry.getValue() + ";";
		}
		
		return result;
	}
	
	public void load(String str)
	{	
		String[] items = str.split(";");
		
		// get element count
		int len = Integer.parseInt(items[0]);
		
		// stop if element count mismatches
		if(len != items.length - 1)
		{
			System.out.println("invalid element count");
			return;
		}
		
		// clear existing key-value pairs
		storageMap.clear();
		
		for(int i = 1; i < len + 1; i++)
		{
			// get entry description
			String[] entry = items[i].split(",");
			
			if(entry.length != 4)
			{
				// stop if length is wrong
				System.out.println("invalid entry description");
				return;
			}

			// parse key and value length
			int keyLength = Integer.parseInt(entry[0]);
			int valueLength = Integer.parseInt(entry[1]);
			
			// get key and value
			String key = entry[2];
			String value = entry[3];
			
			// stop if length is wrong
			if(key.length() != keyLength || value.length() != valueLength)
			{
				System.out.println("invalid key/value length");
				return;
			}
			
			storageMap.put(key, value);
			
			if(VERBOSE == 1)
			{
				System.out.println("key: " + key + ", value: " + value);
			}
		}
		
		if(VERBOSE == 1)
		{
			System.out.println("completed. elements: " + len);
		}
	}
}
