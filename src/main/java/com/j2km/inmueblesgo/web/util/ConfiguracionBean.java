package com.j2km.inmueblesgo.web.util;

import com.j2km.inmueblesgo.service.ConfiguracionService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
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
}
