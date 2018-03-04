/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class PromesaService implements Serializable {

    public void generar() {
        FileInputStream fis = null;
        try {
            File file = new File("/home/files/inmueblesGo/documentos/promesa.docx");
            fis = new FileInputStream(file);

            XWPFDocument document = new XWPFDocument(fis);

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                
                List<XWPFRun> runs = paragraph.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("[[empresa]]")) {
                            text = text.replace("[[empresa]]", "Gran alianza");
                            r.setText(text, 0);
                        }
                    }
                }
            }
            document.write(new FileOutputStream("/home/files/inmueblesGo/documentos/promesa2.docx"));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PromesaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PromesaService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(PromesaService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
