/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.web.util;

import com.j2km.inmueblesgo.service.ConfiguracionService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.poi.util.IOUtils;
import org.primefaces.model.UploadedFile;

@Named(value = "configuracionBean")
@SessionScoped
public class ConfiguracionBean implements Serializable{
     @Inject
     ConfiguracionService configuracion;

    private UploadedFile divipola;

    public ConfiguracionBean() {
    }

    public UploadedFile getDivipola() {
        return divipola;
    }

    public void setDivipola(UploadedFile divipola) {
        this.divipola = divipola;
    }

    public void cargarDivipola() {       
        System.out.println("Esta es la divipola"+divipola);
        
        if(divipola.getContents().length == 0) {
            FacesMessage facesMessage = MessageFactory.getMessage("message_archivo_vacio");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);         
        }else{
            configuracion.cargarDivipola(divipola);
        }
        
        
        
        
    }
    
    
    private UploadedFile archivo;

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }
    
    
    
    
    
    
//    public void saveFile(InputStream uploadedInputStream, String serverLocation) {
    public void saveFile() throws FileNotFoundException, IOException {
        
        
        String filePath ="/home/jdmp/programacion/Proyectos/inmueblesgo/src/main/webapp/resources/images/proyecto/"+File.separator;

    String filename = archivo.getFileName();
    InputStream input = archivo.getInputstream();
    OutputStream output = new FileOutputStream(new File(filePath, filename));

    try {
        IOUtils.copy(input, output);
    } finally {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }
        
        
        
        

}
    
    
    
}
