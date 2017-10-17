package com.bmwu.captcha;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;

import java.io.File;

/**
 * Created by michael on 9/30/17.
 */
public class Tess4J {

    @Test
    public void test() {


        ITesseract instance = new Tesseract();
        File imageFile = new File("/Users/michael/Downloads/yzm1.jpg");

        try {
            instance.setDatapath("/usr/local/share/");
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
