package org.code2bytes.samples.tesseract;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Main {

	public static void main(String[] args) {
		try {
			Tesseract tesseract = Tesseract.getInstance();
			tesseract.setLanguage("deu");
			String fileName = System.getProperty("c2b.tess4j.file");
			if (fileName != null && !fileName.isEmpty()) {
				File file = new File(fileName);
				if (file.exists()) {
					String result = tesseract.doOCR(file);
					result = result.replaceAll("-\n", "");
					result = result.replaceAll("\n", " ");
					System.out.println(result);
				}
			}
		} catch (TesseractException e) {
			e.printStackTrace();
		}
	}
}
