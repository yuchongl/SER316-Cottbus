package net.sf.memoranda.unit_tests;

import net.sf.memoranda.Task;
import net.sf.memoranda.TaskImpl;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Element;

public class TaskGenerator {
	public static Task generate(CalendarDate startDate, CalendarDate endDate, 
			String effort, String priority, String text, String description)
	{
		 Element el = new Element("task");
        el.addAttribute(new Attribute("startDate", startDate.toString()));
        el.addAttribute(new Attribute("endDate", endDate != null? endDate.toString():""));
		String id = Util.generateId();
        el.addAttribute(new Attribute("id", id));
        el.addAttribute(new Attribute("progress", "0"));
        el.addAttribute(new Attribute("effort", String.valueOf(effort)));
        el.addAttribute(new Attribute("priority", String.valueOf(priority)));
                
        Element txt = new Element("text");
        txt.appendChild(text);
        el.appendChild(txt);

        Element desc = new Element("description");
        desc.appendChild(description);
        el.appendChild(desc);

        
        return new TaskImpl(el, null);
	}
}
