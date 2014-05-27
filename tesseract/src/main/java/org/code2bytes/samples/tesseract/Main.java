package org.code2bytes.samples.tesseract;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Main {

	public static void main(String[] args) {
		try {
			Tesseract tesseract = Tesseract.getInstance();
			String result = tesseract.doOCR(new File("d:/temp/tess4j.png"));
			System.out.println(result);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
	}
}
