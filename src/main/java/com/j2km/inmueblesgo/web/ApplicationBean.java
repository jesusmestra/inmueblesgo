package com.j2km.inmueblesgo.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ApplicationBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Properties p;

    // Used on edit and create dialogs to check if a certain "add entity" dialog component exists on page
    public boolean componentExists(String id) {
        return FacesContext.getCurrentInstance().getViewRoot().findComponent(id) != null; 
    }
    
    public String rutaCarpeta() {
        return "/home/files/inmueblesGo";
    }
    
    public String webCarpeta() {
        return "http://localhost:8086/archivos/inmueblesGo/";
    }    
    
     
}
