/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.service.DepartamentoService;
import com.j2km.inmueblesgo.service.MunicipioService;
import com.j2km.inmueblesgo.service.PobladoService;
import com.j2km.inmueblesgo.service.TerceroService;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jkelsy
 */

@ViewScoped
@Named("buscarTerceroBean")
public class BuscarTerceroBean implements Serializable{
    
    private static final Logger logger = Logger.getLogger(BuscarTerceroBean.class.getName());
    
    @Inject 
    private TerceroService terceroService;  
    
    private TerceroEntity terceroEntity;    
    private List<TerceroEntity> allTerceros;  

    public TerceroEntity getTerceroEntity() {
        return terceroEntity;
    }

    public void setTerceroEntity(TerceroEntity terceroEntity) {
        this.terceroEntity = terceroEntity;
    }

    public List<TerceroEntity> getAllTerceros() {
        return allTerceros;
    }

    public void setAllTerceros(List<TerceroEntity> allTerceros) {
        this.allTerceros = allTerceros;
    }    

    public void buscarTercero() {
        System.out.println("Enter presionado");
        this.allTerceros = terceroService.buscarTercerosFiltro(terceroEntity);
    }

    public void selectTerceroFromDialog(TerceroEntity tercero) {
        RequestContext.getCurrentInstance().closeDialog(tercero);
    }

    public void resetBuscar() {
        terceroEntity = new TerceroEntity();        
        allTerceros = null;
    }

    public void chooseTercero() {

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", "80%");
        options.put("height", 400);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        RequestContext.getCurrentInstance().openDialog("/pages/tercero/terceroBuscarInclude", options, null);
        System.out.println("Terminado selec");
    }
    
}
