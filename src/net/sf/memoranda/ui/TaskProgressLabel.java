/*
 * TaskProgressLabel.java         
 * -----------------------------------------------------------------------------
 * Project           memoranda
 * Package           net.sf.memoranda.ui
 * Created           Apr 5, 2007 12:51:26 PM by alex
 * Latest revision   $Revision: 1.1 $
 *                   $Date: 2007/04/05 08:28:14 $
 *                   $Author: alexeya $
 * Tag               $Name:  $
 *
 * @VERSION@ 
 *
 * @COPYRIGHT@
 * 
 * @LICENSE@ 
 *
 * Revisions:
 * $Log: TaskProgressLabel.java,v $
 * Revision 1.1  2007/04/05 08:28:14  alexeya
 * Fixed: Dates in TaskTable were not localized
 *
 * -----------------------------------------------------------------------------
 */

package net.sf.memoranda.ui;

import net.sf.memoranda.Task;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 * <h1>TaskProgressLabel</h1>
 * 
 * Component showing task progress as colorful bar>
 * 
 * @version $Name: $ $Revision: 1.1 $
 * @author Alex Alishevskikh, alexeya(at)gmail.com
 * 
 */

class TaskProgressLabel extends JLabel {
  
  private static final long serialVersionUID = 1L;
  TaskTable table;
  int column;
  Task task;

  TaskProgressLabel(TaskTable table) {
    this.table = table;
    setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
  }

  public void setTask(Task tsk) {
    task = tsk;
  }

  public void setColumn(int col) {
    column = col;
  }

  public void paintComponent(Graphics gr) {
    // forces an update to child task when parent is completed (task 47)
    task.setProgress(task.getProgress());

    int val = task.getProgress();
    int width = table.getColumnModel().getColumn(column).getWidth();
    int height = table.getRowHeight();
    int pr = width * val / 100;

    gr.setColor(Color.WHITE);
    gr.fillRect(0, 0, width, height);

    gr.setColor(TaskTreeTableCellRenderer.getColorForTaskStatus(task, true));
    gr.fillRect(1, 1, pr, height - 2);
    gr.setColor(Color.LIGHT_GRAY);
    gr.drawRect(1, 1, width, height - 2);

    setText(val + "%");
    setBounds(0, 0, width, height);

    super.paintComponent(gr);
  }
}
