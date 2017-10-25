package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.Configuracion;
import com.j2km.inmueblesgo.service.ConfiguracionRepository;
import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ApplicationScoped
public class ApplicationBean implements Serializable {

    @Inject private HttpServletRequest request;
    @Inject private ConfiguracionRepository cr; 
    
    private Configuracion configuracion;

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }
    
    private static final long serialVersionUID = 1L;
    
    private String rutaFisicaArchivos;
    private String rutaWebArchivos;

    public String getRutaFisicaArchivos() {
        return rutaFisicaArchivos;
    }

    public void setRutaFisicaArchivos(String rutaFisicaArchivos) {
        this.rutaFisicaArchivos = rutaFisicaArchivos;
    }

    public String getRutaWebArchivos() {
        return rutaWebArchivos;
    }

    public void setRutaWebArchivos(String rutaWebArchivos) {
        this.rutaWebArchivos = rutaWebArchivos;
    }

    // Used on edit and create dialogs to check if a certain "add entity" dialog component exists on page
    public boolean componentExists(String id) {
        return FacesContext.getCurrentInstance().getViewRoot().findComponent(id) != null;
    }

    public String rutaCarpeta() {
        return "/home/files/inmueblesGo";
    }

    public String webCarpeta() {
        return "http://"+request.getServerName()+":"+request.getServerPort()+"/archivos/inmueblesGo/";// 
        
        //return "http://localhost:8080/archivos/inmueblesGo/";
    }
    
    @PostConstruct
    public void iniciar(){
        configuracion = cr.findBy(1L);
        rutaFisicaArchivos = "/home/files/inmueblesGo";
        rutaWebArchivos = "http://"+request.getServerName()+":"+request.getServerPort()+"/archivos/inmueblesGo";        
    }

}
