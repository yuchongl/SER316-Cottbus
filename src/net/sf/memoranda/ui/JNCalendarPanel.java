package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Local;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/* $Id: JNCalendarPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $ */
public class JNCalendarPanel extends JPanel {

	CalendarDate _date = CurrentDate.get();
	JToolBar navigationBar = new JToolBar();
	JPanel mntyPanel = new JPanel(new BorderLayout());
	JPanel navbPanel = new JPanel(new BorderLayout());
	JButton dayForwardB = new JButton();
	JPanel dayForwardBPanel = new JPanel();
	JButton todayB = new JButton();
	JPanel todayBPanel = new JPanel();
	JPanel dayBackBPanel = new JPanel();
	JButton dayBackB = new JButton();
	JComboBox monthsCB = new JComboBox(Local.getMonthNames());
	BorderLayout borderLayout4 = new BorderLayout();
	JNCalendar jnCalendar = new JNCalendar(CurrentDate.get());
	JPanel jnCalendarPanel = new JPanel();
	BorderLayout borderLayout5 = new BorderLayout();
	JSpinner yearSpin = new JSpinner(new SpinnerNumberModel(jnCalendar.get().getYear(), 1980, 2999, 1));
	JSpinner.NumberEditor yearSpinner = new JSpinner.NumberEditor(yearSpin, "####");
	JToolBar navigationBar2 = new JToolBar();
	JPanel navb2Panel = new JPanel(new BorderLayout());
	JButton monthForwardB = new JButton(); // Daniel Perez
	JPanel monthForwardBPanel = new JPanel();
	JPanel monthBackPanel = new JPanel(); // Daniel Perez
	JButton monthBackB = new JButton();
	BorderLayout borderLayout6 = new BorderLayout();
	boolean ignoreChange = false;

	private Vector selectionListeners = new Vector();

	Border border1;
	Border border2;

	public JNCalendarPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}
}

}
