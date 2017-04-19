package net.sf.memoranda.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.HTMLEditor;

public class TXTFileImport {
	public TXTFileImport(File f, HTMLEditor editor) {
		String text = "";
		BufferedReader in;
		try {
			// in = new BufferedReader(new InputStreamReader(new
			// FileInputStream(f), "UTF-8"));
			in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = in.readLine();
			while (line != null) {
				text = text + line + "\n";
				line = in.readLine();
			}
			in.close();
		} catch (Exception e) {
			new ExceptionDialog(e, "Failed to import " + f.getPath(), "");
			return;
		}

		editor.insertHTML(text, editor.editor.getCaretPosition());

	}
}
