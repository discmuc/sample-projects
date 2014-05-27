package org.code2bytes.samples.tesseract;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Main {

	public static void main(String[] args) {
		try {
			Tesseract tesseract = Tesseract.getInstance();
			String result = tesseract.doOCR(new File("f:/DE000010338920A1-gd 21.12.05.pdf"));
			System.out.println(result);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
	}
}
