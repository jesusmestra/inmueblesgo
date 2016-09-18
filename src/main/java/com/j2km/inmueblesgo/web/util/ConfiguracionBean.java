/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.web.util;

import com.j2km.inmueblesgo.service.ConfiguracionService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;



@Named(value = "configuracionBean")
@SessionScoped
public class ConfiguracionBean implements Serializable{
     @Inject
     ConfiguracionService configuracion;

    private Part divipola;

    public ConfiguracionBean() {
    }

    public Part getDivipola() {
        return divipola;
    }

    public void setDivipola(Part divipola) {
        this.divipola = divipola;
    }

    public void cargarDivipola() {       
        System.out.println("aksjas"+divipola);
        configuracion.cargarDivipola(divipola);
    }
    
}
