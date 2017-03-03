/**
 * DefaultTask.java
 * Created on 12.02.2003, 15:30:40 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.Calendar; 

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

public class TaskImpl implements Task, Comparable<Object> {

  private Element element = null;
  private TaskList tl = null;

  /**
   * Constructor for DefaultTask.
   */
  public TaskImpl(Element taskElement, TaskList tl) {
    element = taskElement;
    this.tl = tl;
  }

  public Element getContent() {
    return element;
  }

  public CalendarDate getStartDate() {
    return new CalendarDate(element.getAttribute("startDate").getValue());
  }

  public void setStartDate(CalendarDate date) {
    setAttr("startDate", date.toString());
  }

  public CalendarDate getEndDate() {
    String ed = element.getAttribute("endDate").getValue();
    if (ed != "") {
      return new CalendarDate(element.getAttribute("endDate").getValue());
    }
    Task parent = this.getParentTask();
    if (parent != null) {
      return parent.getEndDate();
    }
    Project pr = this.tl.getProject();
    if (pr.getEndDate() != null) {
      return pr.getEndDate();
    }
    return this.getStartDate(); 

  }

  public void setEndDate(CalendarDate date) {
    if (date == null) {
      setAttr("endDate", "");
    } else {
      setAttr("endDate", date.toString());
    }
  }

  public long getEffort() {
    Attribute attr = element.getAttribute("effort");
    if (attr == null) {
      return 0;
    } else {
      try {
        return Long.parseLong(attr.getValue());
      } catch (NumberFormatException e) {
        return 0;
      }
    }
  }

  public void setEffort(long effort) {
    setAttr("effort", String.valueOf(effort));
  }

  /*
   * @see net.sf.memoranda.Task#getParentTask()
   */
  public Task getParentTask() {
    Node parentNode = element.getParent();
    if (parentNode instanceof Element) {
      Element parent = (Element) parentNode;
      if (parent.getLocalName().equalsIgnoreCase("task")) {
        return new TaskImpl(parent, tl);
      }
    }
    return null;
  }

  public String getParentId() {
    Task parent = this.getParentTask();
    if (parent != null) {
      return parent.getID();
    }
    return null;
  }

  public String getDescription() {
    Element thisElement = element.getFirstChildElement("description");
    if (thisElement == null) {
      return null;
    } else {
      return thisElement.getValue();
    }
  }

  public void setDescription(String str) {
    Element desc = element.getFirstChildElement("description");
    if (desc == null) {
      desc = new Element("description");
      desc.appendChild(str);
      element.appendChild(desc);
    } else {
      desc.removeChildren();
      desc.appendChild(str);
    }
  }

  /**
   * s
   * 
   * @see net.sf.memoranda.Task#getStatus()
   */
  public int getStatus(CalendarDate date) {
    CalendarDate start = getStartDate();
    CalendarDate end = getEndDate();
    if (isFrozen()) {
      return Task.FROZEN;
    }
    if (isCompleted()) {
      return Task.COMPLETED;
    }

    if (date.inPeriod(start, end)) {
      if (date.equals(end)) {
        return Task.DEADLINE;
      } else {
        return Task.ACTIVE;
      }
    } else if (date.before(start)) {
      return Task.SCHEDULED;
    }

    if (start.after(end)) {
      return Task.ACTIVE;
    }

    return Task.FAILED;
  }

  /**
   * Method isDependsCompleted.
   * 
   * @return boolean
   */
  /*
   * private boolean isDependsCompleted() { Vector v = (Vector) getDependsFrom(); boolean check =
   * true; for (Enumeration en = v.elements(); en.hasMoreElements();) { Task t = (Task)
   * en.nextElement(); if (t.getStatus() != Task.COMPLETED) check = false; } return check; }
   */
  private boolean isFrozen() {
    return element.getAttribute("frozen") != null;
  }

  private boolean isCompleted() {
    return getProgress() == 100;
  }

  /**
   * @see net.sf.memoranda.Task#getID()
   */
  public String getID() {
    return element.getAttribute("id").getValue();
  }

  /**
   * @see net.sf.memoranda.Task#getText()
   */
  public String getText() {
    return element.getFirstChildElement("text").getValue();
  }

  public String toString() {
    return getText();
  }

  /**
   * @see net.sf.memoranda.Task#setText()
   */
  public void setText(String str) {
    element.getFirstChildElement("text").removeChildren();
    element.getFirstChildElement("text").appendChild(str);
  }

  /**
   * @see net.sf.memoranda.Task#freeze()
   */
  public void freeze() {
    setAttr("frozen", "yes");
  }

  /**
   * @see net.sf.memoranda.Task#unfreeze()
   */
  public void unfreeze() {
    if (this.isFrozen()) {
      element.removeAttribute(new Attribute("frozen", "yes"));
    }
  }

  /**
   * @see net.sf.memoranda.Task#getDependsFrom()
   */
  public Collection<Task> getDependsFrom() {
    Vector<Task> vec = new Vector<Task>();
    Elements deps = element.getChildElements("dependsFrom");
    for (int i = 0; i < deps.size(); i++) {
      String id = deps.get(i).getAttribute("idRef").getValue();
      Task tsk = tl.getTask(id);
      if (tsk != null) {
        vec.add(tsk);
      }
    }
    return vec;
  }

  /**
   * @see net.sf.memoranda.Task#addDependsFrom(net.sf.memoranda.Task)
   */
  public void addDependsFrom(Task task) {
    Element dep = new Element("dependsFrom");
    dep.addAttribute(new Attribute("idRef", task.getID()));
    element.appendChild(dep);
  }

  /**
   * @see net.sf.memoranda.Task#removeDependsFrom(net.sf.memoranda.Task)
   */
  public void removeDependsFrom(Task task) {
    Elements deps = element.getChildElements("dependsFrom");
    for (int i = 0; i < deps.size(); i++) {
      String id = deps.get(i).getAttribute("idRef").getValue();
      if (id.equals(task.getID())) {
        element.removeChild(deps.get(i));
        return;
      }
    }
  }

  /**
   * @see net.sf.memoranda.Task#getProgress()
   */
  public int getProgress() {
    return new Integer(element.getAttribute("progress").getValue()).intValue();
  }

  /**
   * @see net.sf.memoranda.Task#setProgress(int)
   */
  public void setProgress(int pr) {
    int prev = getProgress();

    // determines if all subtasks of the current task are completed
    boolean allSubTasksCompleted = true;
    Iterator<Task> it = getSubTasks().iterator();
    while (it.hasNext()) {
      Task tsk = it.next();
      if (((TaskImpl) tsk).getProgress() < 100) {
        allSubTasksCompleted = false;
      }
      System.out.println(tsk);
    }

    // sets the progress to p
    if ((pr >= 0) && (pr <= 100)) {
      setAttr("progress", new Integer(pr).toString());
    }

    if (getParentTask() != null && getParentTask().getProgress() >= 100) {
      if (pr < prev) {
        // progress bar set to 95 for parent if it is at 100 and the current task
        // has had its progress lowered (task 47)
        getParentTask().setProgress(95);
      } else {
        // Progress set to 100 if parent task is completed (Task 47)
        setAttr("progress", new Integer(100).toString());
      }
    }
    // sets progress to 100 if all subtasks are completed and the user hits +,
    // regardless of what progress the bar is currently at. Does not occur for leaf tasks (Task 49).
    if (allSubTasksCompleted && getSubTasks().size() > 0 && pr > prev) {
      setAttr("progress", new Integer(100).toString());
    }

  }

  /**
   * @see net.sf.memoranda.Task#getPriority()
   */
  public int getPriority() {
    Attribute pa = element.getAttribute("priority");
    if (pa == null) {
      return Task.PRIORITY_NORMAL;
    }
    return new Integer(pa.getValue()).intValue();
  }

  /**
   * @see net.sf.memoranda.Task#setPriority(int)
   */
  public void setPriority(int pr) {
    setAttr("priority", String.valueOf(pr));
  }

  private void setAttr(String att, String value) {
    Attribute attr = element.getAttribute(att);
    if (attr == null) {
      element.addAttribute(new Attribute(att, value));
    } else {
      attr.setValue(value);
    }
  }

  /**
   * A "Task rate" is an informal index of importance of the task considering priority, number of
   * days to deadline and current progress.
   * 
   * rate = (100-progress) / (numOfDays+1) * (priority+1)
   * 
   * @param CalendarDate
   * @return long
   */

  private long calcTaskRate(CalendarDate date) {
    Calendar endDateCal = getEndDate().getCalendar();
    Calendar dateCal = date.getCalendar();
    int numOfDays = (endDateCal.get(Calendar.YEAR) * 365 + endDateCal.get(Calendar.DAY_OF_YEAR))
        - (dateCal.get(Calendar.YEAR) * 365 + dateCal.get(Calendar.DAY_OF_YEAR));
    if (numOfDays < 0){
      return -1; // Something wrong ?
    }
    return (100 - getProgress()) / (numOfDays + 1) * (getPriority() + 1);
  }

  /**
   * @see net.sf.memoranda.Task#getRate()
   */

  public long getRate() {
    /*
     * Task t = (Task)task; switch (mode) { case BY_IMP_RATE: return -1*calcTaskRate(t, date); case
     * BY_END_DATE: return t.getEndDate().getDate().getTime(); case BY_PRIORITY: return
     * 5-t.getPriority(); case BY_COMPLETION: return 100-t.getProgress(); } return -1;
     */
    return -1 * calcTaskRate(CurrentDate.get());
  }

  /*
   * Comparable interface
   */

  public int compareTo(Object obj) {
    Task task = (Task) obj;
    if (getRate() > task.getRate()) {
      return 1;
    } else if (getRate() < task.getRate()) {
      return -1;
    } else {
      return 0;
    }
  }

  public boolean equals(Object obj) {
    return ((obj instanceof Task) && (((Task) obj).getID().equals(this.getID())));
  }

  /*
   * @see net.sf.memoranda.Task#getSubTasks()
   */
  public Collection<Task> getSubTasks() {
    Elements subTasks = element.getChildElements("task");
    return convertToTaskObjects(subTasks);
  }

  private Collection<Task> convertToTaskObjects(Elements tasks) {
    Vector<Task> vec = new Vector<Task>();
    for (int i = 0; i < tasks.size(); i++) {
      Task tsk = new TaskImpl(tasks.get(i), tl);
      vec.add(tsk);
    }
    return vec;
  }

  /*
   * @see net.sf.memoranda.Task#getSubTask(java.lang.String)
   */
  public Task getSubTask(String id) {
    Elements subTasks = element.getChildElements("task");
    for (int i = 0; i < subTasks.size(); i++) {
      if (subTasks.get(i).getAttribute("id").getValue().equals(id)) {
        return new TaskImpl(subTasks.get(i), tl);
      }
    }
    return null;
  }

  /*
   * @see net.sf.memoranda.Task#hasSubTasks()
   */
  public boolean hasSubTasks(String id) {
    Elements subTasks = element.getChildElements("task");
    for (int i = 0; i < subTasks.size(); i++) {
      if (subTasks.get(i).getAttribute("id").getValue().equals(id)) {
        return true;
      }
    }
    return false;
  }

}
