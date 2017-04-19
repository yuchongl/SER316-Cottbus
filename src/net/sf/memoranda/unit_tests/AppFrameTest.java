package net.sf.memoranda.unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.memoranda.ui.AppFrame;

public class AppFrameTest {

	@Test
	public void testSticker() {
		AppFrame appf = new AppFrame();
		assertEquals(appf.jStickerExportSticker.getText(), "Export sticker as .txt");
		assertEquals(appf.jStickerExportStickerHTML.getText(), "Export sticker as .html");
		assertEquals(appf.jStickerAddSticker.getText(), "New sticker");
		assertEquals(appf.jStickerImportSticker.getText(), "Import sticker");
	}

}
