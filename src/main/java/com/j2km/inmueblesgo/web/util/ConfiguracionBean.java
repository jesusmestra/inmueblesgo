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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.primefaces.model.UploadedFile;

@Named(value = "configuracionBean")
@SessionScoped
public class ConfiguracionBean implements Serializable {

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
        System.out.println("Esta es la divipola" + divipola);

        if (divipola.getContents().length == 0) {
            FacesMessage facesMessage = MessageFactory.getMessage("message_archivo_vacio");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } else {
            configuracion.cargarDivipola(divipola);
        }

    }
    private UploadedFile archivo2;

    public UploadedFile getArchivo2() {
        return archivo2;
    }

    public void setArchivo2(UploadedFile archivo2) {
        this.archivo2 = archivo2;
    }

    public void grabarArchivo() throws IOException {
        
        //Properties p = getPropiedades();
        
        //System.err.println("Propieda"+p.getProperty("ruta"));
        
        configuracion.copiarArchivo(archivo2, "proyecto_9.jpg", "proyecto");
    }

    
    
    

}
