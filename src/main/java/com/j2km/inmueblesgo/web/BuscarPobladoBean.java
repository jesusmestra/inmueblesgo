/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.service.DepartamentoRepository;
import com.j2km.inmueblesgo.service.MunicipioRepository;
import com.j2km.inmueblesgo.service.MunicipioService;
import com.j2km.inmueblesgo.service.PobladoRepository;
import com.j2km.inmueblesgo.service.PobladoService;
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
@Named("buscarPobladoBean")
public class BuscarPobladoBean implements Serializable{
    
    private static final Logger logger = Logger.getLogger(BuscarPobladoBean.class.getName());
    
    @Inject 
    private PobladoRepository pobladoService;
    
    @Inject
    private DepartamentoRepository departamentoService;
    
    @Inject
    private MunicipioRepository municipioService;
    
    private PobladoEntity pobladoEntity;
    private List<PobladoEntity> allPoblados;
    
    private DepartamentoEntity departamentoEntity;    
    private List<DepartamentoEntity> allDepartamentos;
    
    private MunicipioEntity municipioEntity;    
    private List<MunicipioEntity> allMunicipios;

    public DepartamentoEntity getDepartamentoEntity() {
        return departamentoEntity;
    }

    public void setDepartamentoEntity(DepartamentoEntity departamentoEntity) {
        this.departamentoEntity = departamentoEntity;
    }

    public MunicipioEntity getMunicipioEntity() {
        return municipioEntity;
    }

    public void setMunicipioEntity(MunicipioEntity municipioEntity) {
        this.municipioEntity = municipioEntity;
    }

    public List<MunicipioEntity> getAllMunicipios() {        
        return allMunicipios;
    }

    public void setAllMunicipios(List<MunicipioEntity> allMunicipios) {
        this.allMunicipios = allMunicipios;
    }       

    public List<DepartamentoEntity> getAllDepartamentos() {
        if(this.allDepartamentos == null){
            this.allDepartamentos = departamentoService.findAll();
        }
        return allDepartamentos;
    }

    public void setAllDepartamentos(List<DepartamentoEntity> allDepartamentos) {
        this.allDepartamentos = allDepartamentos;
    }

    public PobladoEntity getPobladoEntity() {
        return pobladoEntity;
    }

    public void setPobladoEntity(PobladoEntity pobladoEntity) {
        this.pobladoEntity = pobladoEntity;
    }

    public List<PobladoEntity> getAllPoblados() {
        return allPoblados;
    }

    public void setAllPoblados(List<PobladoEntity> allPoblados) {
        this.allPoblados = allPoblados;
    }
    
    public void onSeleccionarDepartamento(){
        if(this.departamentoEntity != null){
            this.allMunicipios = municipioService.findByDepartamento(departamentoEntity);
            this.allPoblados = null;
        }
    }
    
    public void onSeleccionarMunicipio(){
        if(this.municipioEntity != null){
            this.allPoblados = pobladoService.findByMunicipio(municipioEntity);
        }
    }
    
    public void choosePoblado() {

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", "50%");
        options.put("height", 200);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        RequestContext.getCurrentInstance().openDialog("/pages/poblado/pobladoBuscarInclude", options, null);
        
    } 
    
    public void selectPobladoFromDialog() {
        RequestContext.getCurrentInstance().closeDialog(pobladoEntity);
    }
    
    public void resetBuscar() {
        pobladoEntity = new PobladoEntity();        
        allPoblados = null;
    }
    
}
