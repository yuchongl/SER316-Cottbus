package net.sf.memoranda.ui;

import net.sf.memoranda.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Editor for task progress.
 */
public class TaskProgressEditor extends JPanel implements TableCellEditor {

	private static final long serialVersionUID = 1L;
	JTable table;
	Task current;
	boolean isSelected;
	int row;
	int column;

	java.util.List<CellEditorListener> listeners = new java.util.ArrayList<CellEditorListener>();

	JLabel label = new JLabel();

	public TaskProgressEditor() {
		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent ev) {
				if (ev instanceof MouseEvent) {
					MouseEvent me = (MouseEvent) ev;
					if (me.getButton() != MouseEvent.BUTTON1) {
						stopEditing();
						return;
					}
				}

				int wid = getWidth() / 2;
				if (ev.getX() > wid) {
					current.setProgress(current.getProgress() + 5);
				} else {
					current.setProgress(current.getProgress() - 5);
				}
				
				//task 85
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						// updates the table to reflect changes (task 50)
						table.updateUI();
					}
				});
			}
		});
		setLayout(new java.awt.BorderLayout());
		label.setOpaque(false);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rw, int clm) {
		current = (Task) value;
		this.table = table;
		this.isSelected = isSelected;
		row = rw;
		column = clm;
		return this;
	}

	public void paint(Graphics gr) {
		paintComponent(gr);
	}

	public void paintComponent(Graphics gr) {
		TableCellRenderer cr = table.getCellRenderer(row, column);
		((TaskProgressLabel) cr.getTableCellRendererComponent(table, current, isSelected, true, row, column))
				.paintComponent(gr);

		label.setSize(this.getSize());

		label.setText("-");
		label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		label.paint(gr);
		label.setText("+");
		label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		label.paint(gr);

	}

	private void stopEditing() {
		for (int i = 0; i < listeners.size(); i++) {
			CellEditorListener cel = listeners.get(i);
			cel.editingStopped(null);
		}
	}

	public void addCellEditorListener(CellEditorListener var0) {
		listeners.add(var0);
	}

	public void removeCellEditorListener(CellEditorListener var0) {
		listeners.remove(var0);
	}

	public void cancelCellEditing() {
	}

	public java.lang.Object getCellEditorValue() {
		return null; // just return null, because model will not use this
	}

	public boolean isCellEditable(java.util.EventObject ev) {
		if (ev instanceof MouseEvent) {
			MouseEvent me = (MouseEvent) ev;
			if (me.getButton() == MouseEvent.BUTTON1) {
				return true;
			}
		}
		return false;
	}

	public boolean stopCellEditing() {
		return true;
	}

	public boolean shouldSelectCell(java.util.EventObject var0) {
		return true;
	}

}
