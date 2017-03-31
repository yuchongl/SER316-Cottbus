package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.History;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

/*$Id: TaskPanel.java,v 1.27 2007/01/17 20:49:12 killerjoe Exp $*/
public class TaskPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout1 = new BorderLayout();
	JButton historyBackB = new JButton();
	JToolBar tasksToolBar = new JToolBar();
	JButton historyForwardB = new JButton();
	JButton newTaskB = new JButton();
	JButton subTaskB = new JButton();
	JButton editTaskB = new JButton();
	JButton removeTaskB = new JButton();
	JButton completeTaskB = new JButton();

	JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();

	JScrollPane scrollPane = new JScrollPane();
	TaskTable taskTable = new TaskTable();
	JMenuItem ppEditTask = new JMenuItem();
	JPopupMenu taskPpMenu = new JPopupMenu();
	JMenuItem ppRemoveTask = new JMenuItem();
	JMenuItem ppNewTask = new JMenuItem();
	JMenuItem ppCompleteTask = new JMenuItem();

	JMenuItem ppResumeTask = new JMenuItem();

	// JMenuItem ppSubTasks = new JMenuItem();
	// JMenuItem ppParentTask = new JMenuItem();
	JMenuItem ppAddSubTask = new JMenuItem();
	JMenuItem ppCalcTask = new JMenuItem();
	DailyItemsPanel parentPanel = null;

	public TaskPanel(DailyItemsPanel parentPanel) {
		try {
			this.parentPanel = parentPanel;
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		tasksToolBar.setFloatable(false);

		historyBackB.setAction(History.historyBackAction);
		historyBackB.setFocusable(false);
		historyBackB.setBorderPainted(false);
		historyBackB.setToolTipText(Local.getString("History back"));
		historyBackB.setRequestFocusEnabled(false);
		historyBackB.setPreferredSize(new Dimension(24, 24));
		historyBackB.setMinimumSize(new Dimension(24, 24));
		historyBackB.setMaximumSize(new Dimension(24, 24));
		historyBackB.setText("");

		historyForwardB.setAction(History.historyForwardAction);
		historyForwardB.setBorderPainted(false);
		historyForwardB.setFocusable(false);
		historyForwardB.setPreferredSize(new Dimension(24, 24));
		historyForwardB.setRequestFocusEnabled(false);
		historyForwardB.setToolTipText(Local.getString("History forward"));
		historyForwardB.setMinimumSize(new Dimension(24, 24));
		historyForwardB.setMaximumSize(new Dimension(24, 24));
		historyForwardB.setText("");

		newTaskB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new.png")));
		newTaskB.setEnabled(true);
		newTaskB.setMaximumSize(new Dimension(24, 24));
		newTaskB.setMinimumSize(new Dimension(24, 24));
		newTaskB.setToolTipText(Local.getString("Create new task"));
		newTaskB.setRequestFocusEnabled(false);
		newTaskB.setPreferredSize(new Dimension(24, 24));
		newTaskB.setFocusable(false);
		newTaskB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				newTaskB_actionPerformed(ev);
			}
		});
		newTaskB.setBorderPainted(false);

		subTaskB.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new_sub.png")));
		subTaskB.setEnabled(true);
		subTaskB.setMaximumSize(new Dimension(24, 24));
		subTaskB.setMinimumSize(new Dimension(24, 24));
		subTaskB.setToolTipText(Local.getString("Add subtask"));
		subTaskB.setRequestFocusEnabled(false);
		subTaskB.setPreferredSize(new Dimension(24, 24));
		subTaskB.setFocusable(false);
		subTaskB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				addSubTask_actionPerformed(ev);
			}
		});
		subTaskB.setBorderPainted(false);

		editTaskB.setBorderPainted(false);
		editTaskB.setFocusable(false);
		editTaskB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				editTaskB_actionPerformed(ev);
			}
		});
		editTaskB.setPreferredSize(new Dimension(24, 24));
		editTaskB.setRequestFocusEnabled(false);
		editTaskB.setToolTipText(Local.getString("Edit task"));
		editTaskB.setMinimumSize(new Dimension(24, 24));
		editTaskB.setMaximumSize(new Dimension(24, 24));
		// editTaskB.setEnabled(true);
		editTaskB.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_edit.png")));

		removeTaskB.setBorderPainted(false);
		removeTaskB.setFocusable(false);
		removeTaskB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				removeTaskB_actionPerformed(ev);
			}
		});
		removeTaskB.setPreferredSize(new Dimension(24, 24));
		removeTaskB.setRequestFocusEnabled(false);
		removeTaskB.setToolTipText(Local.getString("Remove task"));
		removeTaskB.setMinimumSize(new Dimension(24, 24));
		removeTaskB.setMaximumSize(new Dimension(24, 24));
		removeTaskB.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_remove.png")));

		completeTaskB.setBorderPainted(false);
		completeTaskB.setFocusable(false);
		completeTaskB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ppCompleteTask_actionPerformed(ev);
			}
		});
		completeTaskB.setPreferredSize(new Dimension(24, 24));
		completeTaskB.setRequestFocusEnabled(false);
		completeTaskB.setToolTipText(Local.getString("Complete task"));
		completeTaskB.setMinimumSize(new Dimension(24, 24));
		completeTaskB.setMaximumSize(new Dimension(24, 24));
		completeTaskB.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_complete.png")));

		ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		ppShowActiveOnlyChB.setText(Local.getString("Show Active only"));
		ppShowActiveOnlyChB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				toggleShowActiveOnly_actionPerformed(ev);
			}
		});
		boolean isShao = (Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
				&& (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
		ppShowActiveOnlyChB.setSelected(isShao);
		toggleShowActiveOnly_actionPerformed(null);

		this.setLayout(borderLayout1);
		scrollPane.getViewport().setBackground(Color.gray);
		/*
		 * taskTable.setMaximumSize(new Dimension(32767, 32767));
		 * taskTable.setRowHeight(24);
		 */
		ppEditTask.setFont(new java.awt.Font("Dialog", 1, 11));
		ppEditTask.setText(Local.getString("Edit task") + "...");
		ppEditTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ppEditTask_actionPerformed(ev);
			}
		});
		ppEditTask.setEnabled(false);
		ppEditTask.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_edit.png")));
		taskPpMenu.setFont(new java.awt.Font("Dialog", 1, 10));
		ppRemoveTask.setFont(new java.awt.Font("Dialog", 1, 11));
		ppRemoveTask.setText(Local.getString("Remove task"));
		ppRemoveTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ppRemoveTask_actionPerformed(ev);
			}
		});
		ppRemoveTask.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_remove.png")));
		ppRemoveTask.setEnabled(false);
		ppNewTask.setFont(new java.awt.Font("Dialog", 1, 11));
		ppNewTask.setText(Local.getString("New task") + "...");
		ppNewTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ppNewTask_actionPerformed(ev);
			}
		});
		ppNewTask
				.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new.png")));

		ppAddSubTask.setFont(new java.awt.Font("Dialog", 1, 11));
		ppAddSubTask.setText(Local.getString("Add subtask"));
		ppAddSubTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ppAddSubTask_actionPerformed(ev);
			}
		});
		ppAddSubTask.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new_sub.png")));

		ppResumeTask.setFont(new java.awt.Font("Dialog", 1, 11));
		ppResumeTask.setText(Local.getString("Resume task"));

		ppResumeTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("resume task");
				ppResumeTask_actionPerformed(ev);
			}
		});

		ppCompleteTask.setFont(new java.awt.Font("Dialog", 1, 11));
		ppCompleteTask.setText(Local.getString("Complete task"));
		ppCompleteTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ppCompleteTask_actionPerformed(ev);
			}
		});
		ppCompleteTask.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_complete.png")));
		ppCompleteTask.setEnabled(false);

		ppCalcTask.setFont(new java.awt.Font("Dialog", 1, 11));
		ppCalcTask.setText(Local.getString("Calculate task data"));
		ppCalcTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ppCalcTask_actionPerformed(ev);
			}
		});
		ppCalcTask.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_complete.png")));
		ppCalcTask.setEnabled(false);

		scrollPane.getViewport().add(taskTable, null);
		this.add(scrollPane, BorderLayout.CENTER);
		tasksToolBar.add(historyBackB, null);
		tasksToolBar.add(historyForwardB, null);
		tasksToolBar.addSeparator(new Dimension(8, 24));

		tasksToolBar.add(newTaskB, null);
		tasksToolBar.add(subTaskB, null);
		tasksToolBar.add(removeTaskB, null);
		tasksToolBar.addSeparator(new Dimension(8, 24));
		tasksToolBar.add(editTaskB, null);
		tasksToolBar.add(completeTaskB, null);

		this.add(tasksToolBar, BorderLayout.NORTH);

		PopupListener ppListener = new PopupListener();
		scrollPane.addMouseListener(ppListener);
		taskTable.addMouseListener(ppListener);

		CurrentDate.addDateListener(new DateListener() {
			public void dateChange(CalendarDate date) {
				newTaskB.setEnabled(
						date.inPeriod(CurrentProject.get().getStartDate(), CurrentProject.get().getEndDate()));
			}
		});
		CurrentProject.addProjectListener(new ProjectListener() {
			public void projectChange(Project pr, NoteList nl, TaskList tl, ResourcesList rl) {
				newTaskB.setEnabled(CurrentDate.get().inPeriod(pr.getStartDate(), pr.getEndDate()));
			}

			public void projectWasChanged() {
				// taskTable.setCurrentRootTask(null);
			}
		});
		taskTable.tableChanged();
		taskTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent ev) {
				// task 97
				subTaskB.setEnabled(true);
				editTaskB.setEnabled(true);
				ppEditTask.setEnabled(true);
				removeTaskB.setEnabled(true);
				ppRemoveTask.setEnabled(true);

				ppCompleteTask.setEnabled(true);
				completeTaskB.setEnabled(true);
				ppAddSubTask.setEnabled(true);
				// ppSubTasks.setEnabled(enbl); // default value to be
				// over-written later depending on
				// whether it has sub tasks
				ppCalcTask.setEnabled(true); // default value to be over-written
												// later depending on whether
												// it has sub tasks
				//task 89/82
				if (taskTable.getSelectedRow() != -1) {
					if (taskTable.getRowCount() > 0) {
						String thisTaskId = taskTable.getModel()
								.getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();

						boolean hasSubTasks = CurrentProject.getTaskList().hasSubTasks(thisTaskId);
						// ppSubTasks.setEnabled(hasSubTasks);
						ppCalcTask.setEnabled(hasSubTasks);
						Task task = CurrentProject.getTaskList().getTask(thisTaskId);
						parentPanel.calendar.jnCalendar.renderer.setTask(task);
						parentPanel.calendar.jnCalendar.updateUI();
					} else {
						parentPanel.calendar.jnCalendar.renderer.setTask(null);
						parentPanel.calendar.jnCalendar.updateUI();
					}
				}
			}
		});

		// task 84 top task selected by default
		// only matters when program first starts
		// decides what should be enabled based on if there are any tasks
		// selected
		if (taskTable.getSelectedRow() <= -1) {
			// task 97
			subTaskB.setEnabled(false);
			editTaskB.setEnabled(false);
			removeTaskB.setEnabled(false);
			completeTaskB.setEnabled(false);
			ppAddSubTask.setEnabled(false);
		} else {
			// task 97
			subTaskB.setEnabled(true);
			editTaskB.setEnabled(true);
			ppEditTask.setEnabled(true);
			removeTaskB.setEnabled(true);
			ppRemoveTask.setEnabled(true);

			ppCompleteTask.setEnabled(true);
			completeTaskB.setEnabled(true);
			ppAddSubTask.setEnabled(true);
		}
		// ppSubTasks.setEnabled(false);
		// ppParentTask.setEnabled(false);
		taskPpMenu.add(ppEditTask);

		taskPpMenu.add(ppResumeTask);

		taskPpMenu.addSeparator();
		taskPpMenu.add(ppNewTask);
		taskPpMenu.add(ppAddSubTask);
		taskPpMenu.add(ppRemoveTask);

		taskPpMenu.addSeparator();
		taskPpMenu.add(ppCompleteTask);
		taskPpMenu.add(ppCalcTask);

		// taskPPMenu.addSeparator();

		// taskPPMenu.add(ppSubTasks);

		// taskPPMenu.addSeparator();
		// taskPPMenu.add(ppParentTask);

		taskPpMenu.addSeparator();
		taskPpMenu.add(ppShowActiveOnlyChB);

		// define key actions in TaskPanel:
		// - KEY:DELETE => delete tasks (recursivly).
		// - KEY:INTERT => insert new Subtask if another is selected.
		// - KEY:INSERT => insert new Task if nothing is selected.
		// - KEY:SPACE => finish Task.
		taskTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ev) {
				if (taskTable.getSelectedRows().length > 0 && ev.getKeyCode() == KeyEvent.VK_DELETE) {
					ppRemoveTask_actionPerformed(null);
				} else if (ev.getKeyCode() == KeyEvent.VK_INSERT) {
					if (taskTable.getSelectedRows().length > 0) {
						ppAddSubTask_actionPerformed(null);
					} else {
						ppNewTask_actionPerformed(null);
					}
				} else if (ev.getKeyCode() == KeyEvent.VK_SPACE && taskTable.getSelectedRows().length > 0) {
					ppCompleteTask_actionPerformed(null);
				}
			}

			public void keyReleased(KeyEvent ev) {
			}

			public void keyTyped(KeyEvent ev) {
			}
		});

	}

	void editTaskB_actionPerformed(ActionEvent ev) {
		Task task = CurrentProject.getTaskList()
				.getTask(taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString());
		TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("Edit task"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
				(frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.todoField.setText(task.getText());
		dlg.descriptionField.setText(task.getDescription());
		dlg.startDate.getModel().setValue(task.getStartDate().getDate());
		dlg.endDate.getModel().setValue(task.getEndDate().getDate());
		dlg.priorityCB.setSelectedIndex(task.getPriority());
		dlg.effortField.setText(Util.getHoursFromMillis(task.getEffort()));
		dlg.chkEndDate.setSelected(true);
		dlg.progress.setValue(new Integer(task.getProgress()));
		dlg.chkEndDate_actionPerformed(null);
		dlg.setVisible(true);
		if (dlg.CANCELLED) {
			return;
		}
		
		//TASK 88
		CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
		if (task.getParentTask() != null){
			if (sd.getDate().before(task.getParentTask().getStartDate().getDate())){
				sd = new CalendarDate(task.getParentTask().getStartDate().getDate());
				JOptionPane.showMessageDialog(this, "Start date can't be before parent task's start date", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//TASK 88
		CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
		if (dlg.chkEndDate.isSelected()) {
			// task 73
			if (sd.getDate().after(ed.getDate())) {
				ed = new CalendarDate(sd.getDate());
				JOptionPane.showMessageDialog(this, "Due date can't be before Start Date", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			if (task.getParentTask() != null){
				if (ed.getDate().after(task.getParentTask().getEndDate().getDate())){
					ed = new CalendarDate(task.getParentTask().getEndDate().getDate());
					JOptionPane.showMessageDialog(this, "Due date can't be after parent task's due date", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			if (sd.getDate().after(ed.getDate())) {
				sd = new CalendarDate(task.getParentTask().getStartDate().getDate());
			}
		} else {
			ed = null;
		}
		task.setStartDate(sd);
		task.setEndDate(ed);
		task.setText(dlg.todoField.getText());
		task.setDescription(dlg.descriptionField.getText());
		task.setPriority(dlg.priorityCB.getSelectedIndex());
		task.setEffort(Util.getMillisFromHours(dlg.effortField.getText()));
		task.setProgress(((Integer) dlg.progress.getValue()).intValue());
		
		for (int i = 0; i < task.getSubTasks().size(); i++){
			if (((Task)task.getSubTasks().toArray()[i]).getEndDate().after(task.getEndDate())){
				((Task)task.getSubTasks().toArray()[i]).setEndDate(task.getEndDate()); 
			}
			if (((Task)task.getSubTasks().toArray()[i]).getStartDate().before(task.getStartDate())){
				((Task)task.getSubTasks().toArray()[i]).setStartDate(task.getStartDate()); 
			}
		}

		// CurrentProject.getTaskList().adjustParentTasks(t);

		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		taskTable.tableChanged();
		parentPanel.updateIndicators();
		// taskTable.updateUI();
	}

	void newTaskB_actionPerformed(ActionEvent ev) {
		TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("New task"));

		// String parentTaskId = taskTable.getCurrentRootTask();

		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dlg.startDate.getModel().setValue(CurrentDate.get().getDate());
		dlg.endDate.getModel().setValue(CurrentDate.get().getDate());
		dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
				(frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		if (dlg.CANCELLED) {
			return;
		}
		
		//TASK 88
		CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
		CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());;
		if (dlg.chkEndDate.isSelected()) {
			// task 73
			if (sd.getDate().after(ed.getDate())) {
				ed = new CalendarDate(sd.getDate());
				JOptionPane.showMessageDialog(this, "Due date can't be before Start Date", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			ed = null;
		}
		long effort = Util.getMillisFromHours(dlg.effortField.getText());
		Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(),
				dlg.priorityCB.getSelectedIndex(), effort, dlg.descriptionField.getText(), null);
		newTask.setProgress(((Integer) dlg.progress.getValue()).intValue());
		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		taskTable.tableChanged();
		parentPanel.updateIndicators();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// task 90 highlight created task
				for (int i = 0; i < taskTable.getRowCount(); i++) {
					if (taskTable.getValueAt(i, 1).equals(newTask)) {
						taskTable.setRowSelectionInterval(i, i);
					}
				}
			}
		});
		
	}

	void addSubTask_actionPerformed(ActionEvent ev) {
		TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("New Task"));
		String parentTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();

		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		Task parent = CurrentProject.getTaskList().getTask(parentTaskId);
		CalendarDate todayD = CurrentDate.get();
		if (todayD.after(parent.getStartDate())) {
			dlg.setStartDate(todayD);
		} else {
			dlg.setStartDate(parent.getStartDate());
		}
		if (parent.getEndDate() != null) {
			dlg.setEndDate(parent.getEndDate());
		} else {
			dlg.setEndDate(CurrentProject.get().getEndDate());
		}
		dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
				(frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		if (dlg.CANCELLED) {
			return;
		}
		
		//TASK 88
		CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
		Task parentTask = (Task) taskTable.getModel().getValueAt(taskTable.getSelectedRow(), 1);
		if (sd.getDate().before(parentTask.getStartDate().getDate())){
			sd = new CalendarDate(parentTask.getStartDate().getDate());
			JOptionPane.showMessageDialog(this, "Start date can't be before parent task's start date", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
		//TASK 88
		CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
		if (dlg.chkEndDate.isSelected()) {
			// task 73
			if (sd.getDate().after(ed.getDate())) {
				ed = new CalendarDate(sd.getDate());
				JOptionPane.showMessageDialog(this, "Due date can't be before Start Date", "Error",
						JOptionPane.ERROR_MESSAGE);
			} 
			//Task parentTask = (Task) taskTable.getModel().getValueAt(taskTable.getSelectedRow(), 1);
			if (ed.getDate().after(parentTask.getEndDate().getDate())){
				ed = new CalendarDate(parentTask.getEndDate().getDate());
				JOptionPane.showMessageDialog(this, "Due date can't be after parent task's due date", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			if (sd.getDate().after(ed.getDate())) {
				sd = new CalendarDate(parentTask.getStartDate().getDate());
			}
		} else {
			ed = null;
		}
		long effort = Util.getMillisFromHours(dlg.effortField.getText());
		Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(),
				dlg.priorityCB.getSelectedIndex(), effort, dlg.descriptionField.getText(), parentTaskId);
		newTask.setProgress(((Integer) dlg.progress.getValue()).intValue());

		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		taskTable.tableChanged();
		parentPanel.updateIndicators();

	}

	void calcTask_actionPerformed(ActionEvent ev) {
		TaskCalcDialog dlg = new TaskCalcDialog(App.getFrame());
		dlg.pack();
		Task task = CurrentProject.getTaskList()
				.getTask(taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString());

		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();

		dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
				(frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		if (dlg.CANCELLED) {
			return;
		}

		TaskList tl = CurrentProject.getTaskList();
		if (dlg.calcEffortChB.isSelected()) {
			task.setEffort(tl.calculateTotalEffortFromSubTasks(task));
		}

		if (dlg.compactDatesChB.isSelected()) {
			task.setStartDate(tl.getEarliestStartDateFromSubTasks(task));
			task.setEndDate(tl.getLatestEndDateFromSubTasks(task));
		}

		if (dlg.calcCompletionChB.isSelected()) {
			long[] res = tl.calculateCompletionFromSubTasks(task);
			int thisProgress = (int) Math.round((((double) res[0] / (double) res[1]) * 100));
			task.setProgress(thisProgress);
		}

		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		taskTable.tableChanged();
	}


  void listSubTasks_actionPerformed(ActionEvent ev) {
		// taskTable.setCurrentRootTask(parentTaskId);
		taskTable.tableChanged();
	}

	void removeTaskB_actionPerformed(ActionEvent ev) {
		String msg;
		String thisTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();

		if (taskTable.getSelectedRows().length > 1) {
			msg = Local.getString("Remove") + " " + taskTable.getSelectedRows().length + " " + Local.getString("tasks")
					+ "?" + "\n" + Local.getString("Are you sure?");
		} else {
			Task task = CurrentProject.getTaskList().getTask(thisTaskId);
			// check if there are subtasks
			if (CurrentProject.getTaskList().hasSubTasks(thisTaskId)) {
				msg = Local.getString("Remove task") + "\n'" + task.getText() + Local.getString("' and all subtasks")
						+ "\n" + Local.getString("Are you sure?");
			} else {
				msg = Local.getString("Remove task") + "\n'" + task.getText() + "'\n"
						+ Local.getString("Are you sure?");
			}
		}
		int pane = JOptionPane.showConfirmDialog(App.getFrame(), msg, Local.getString("Remove task"),
				JOptionPane.YES_NO_OPTION);
		if (pane != JOptionPane.YES_OPTION) {
			return;
		}
		Vector<Task> toremove = new Vector<Task>();
		for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
			Task task = CurrentProject.getTaskList().getTask(
					taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
			if (task != null) {
				toremove.add(task);
			}
		}
		for (int i = 0; i < toremove.size(); i++) {
			CurrentProject.getTaskList().removeTask((Task) toremove.get(i));
		}
		taskTable.tableChanged();
		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		parentPanel.updateIndicators();
		
		//task 89/82
		if (taskTable.getRowCount() <= 0){
			subTaskB.setEnabled(false);
			editTaskB.setEnabled(false);
			ppEditTask.setEnabled(false);
			removeTaskB.setEnabled(false);
			ppRemoveTask.setEnabled(false);

			ppCompleteTask.setEnabled(false);
			completeTaskB.setEnabled(false);
			ppAddSubTask.setEnabled(false);
		}

	}

	void ppResumeTask_actionPerformed(ActionEvent ev) {
		Vector<Task> tocomplete = new Vector<Task>();
		for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
			Task task = CurrentProject.getTaskList().getTask(
					taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
			if (task != null) {
				tocomplete.add(task);
			}
		}
		for (int i = 0; i < tocomplete.size(); i++) {
			Task tsk = (Task) tocomplete.get(i);
			tsk.setProgress(0);
		}
		taskTable.tableChanged();
		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		parentPanel.updateIndicators();
	}

	void ppCompleteTask_actionPerformed(ActionEvent ev) {
		Vector<Task> tocomplete = new Vector<Task>();
		for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
			Task task = CurrentProject.getTaskList().getTask(
					taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
			if (task != null) {
				tocomplete.add(task);
			}
		}
		for (int i = 0; i < tocomplete.size(); i++) {
			Task tsk = (Task) tocomplete.get(i);
			tsk.setProgress(100);
		}
		taskTable.tableChanged();
		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		parentPanel.updateIndicators();
	}

	// toggle "show active only"
	void toggleShowActiveOnly_actionPerformed(ActionEvent ev) {
		Context.put("SHOW_ACTIVE_TASKS_ONLY", new Boolean(ppShowActiveOnlyChB.isSelected()));
		taskTable.tableChanged();
	}

	class PopupListener extends MouseAdapter {

		public void mouseClicked(MouseEvent ev) {
			int r = taskTable.rowAtPoint(ev.getPoint());
			if (r > -1 && r < taskTable.getRowCount()) {
				taskTable.setRowSelectionInterval(r, r);
			}
			
			if (SwingUtilities.isLeftMouseButton(ev) && (ev.getClickCount() == 2)
					&& (taskTable.getSelectedRow() > -1)) {
				int r2 = taskTable.rowAtPoint(ev.getPoint());
				if (r2 > -1 && r2 < taskTable.getRowCount()) {
					editTaskB_actionPerformed(null);
				}
			}
		}

		public void mousePressed(MouseEvent ev) {
			maybeShowPopup(ev);
		}

		public void mouseReleased(MouseEvent ev) {
			maybeShowPopup(ev);
		}

		private void maybeShowPopup(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				// task 83. Selects the task that is right clicked on
				int r = taskTable.rowAtPoint(ev.getPoint());
				if (r > -1 && r < taskTable.getRowCount()) {
					taskTable.setRowSelectionInterval(r, r);
					taskPpMenu.show(ev.getComponent(), ev.getX(), ev.getY());
				}
			}
		}

	}

	void ppEditTask_actionPerformed(ActionEvent ev) {
		editTaskB_actionPerformed(ev);
	}

	void ppRemoveTask_actionPerformed(ActionEvent ev) {
		removeTaskB_actionPerformed(ev);
	}

	void ppNewTask_actionPerformed(ActionEvent ev) {
		newTaskB_actionPerformed(ev);
	}

	void ppAddSubTask_actionPerformed(ActionEvent ev) {
		addSubTask_actionPerformed(ev);
	}

	void ppListSubTasks_actionPerformed(ActionEvent ev) {
		listSubTasks_actionPerformed(ev);
	}

	void ppCalcTask_actionPerformed(ActionEvent ev) {
		calcTask_actionPerformed(ev);
	}

}
