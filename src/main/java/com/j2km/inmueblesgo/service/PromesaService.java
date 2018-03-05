/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class PromesaService implements Serializable {
    
    @Inject private NegociacionRepository nr;
    @Inject private TerceroRepository terceroRepository;
    
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat formatea = new DecimalFormat("###,###.##");

    public byte[] generar(NegociacionEntity negociacion) {
        List<TerceroEntity> clientes = terceroRepository.findByNegociacion(negociacion);
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
                        //Colocar los datos de la empresa-----------------------------------------
                        if (text != null && text.contains("[[empresa]]")) {
                            text = text.replace("[[empresa]]", 
                                    negociacion.getInmueble().getProyecto().getEmpresa().getNombre());
                            r.setText(text, 0);
                        }
                        
                        if (text != null && text.contains("[[nit]]")) {
                            text = text.replace("[[nit]]", 
                                    negociacion.getInmueble().getProyecto().getEmpresa().getNit());
                            r.setText(text, 0);
                        }
                        //----------------------Apartamento ----------------------------------
                        if (text != null && text.contains("[[apartamento_numero]]")) {
                            text = text.replace("[[apartamento_numero]]", 
                                    negociacion.getInmueble().getNumero());
                            r.setText(text, 0);
                        }
                        
                        if (text != null && text.contains("[[apartamento_area]]")) {
                            text = text.replace("[[apartamento_area]]", 
                                    formatea.format(negociacion.getInmueble().getArea()));
                            r.setText(text, 0);
                        }
                        
                        // ------------------- COMPRADORES ------------------------------------
                        String strClientes = "";
                        for (TerceroEntity cliente : clientes) {
                            strClientes +=cliente.getNombreCompleto();
                            strClientes +=" con "+cliente.getTipoIdentificacion().getNombre().toLowerCase();
                            strClientes +=" número "+cliente.getIdentificacion();
                            strClientes +=" de "+cliente.getLugarExpedicion().getNombre()+ ", ";
                        }
                        
                        if (text != null && text.contains("[[compradores]]")) {
                            text = text.replace("[[compradores]]",strClientes);
                            r.setText(text, 0);
                        }
                        
                        /**
                         * --------------------------- 
                         * con cedula de ciudadanía número
                         * ------------------------------, ----------------, 
                         * mayor de edad y vecino(a) de esta ciudad
                         */
                    }
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.write(baos);
            return baos.toByteArray();
            //document.write(new FileOutputStream("/home/files/inmueblesGo/documentos/promesa2.docx"));

        } catch (FileNotFoundException ex ) {
            System.err.println(ex.getMessage());
            Logger.getLogger(PromesaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            Logger.getLogger(PromesaService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(PromesaService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
