package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.TipoInmuebleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaDetalleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import com.j2km.inmueblesgo.service.InmuebleService;
import com.j2km.inmueblesgo.service.TipoInmuebleService;
import com.j2km.inmueblesgo.service.TipoPlantaService;
import com.j2km.inmueblesgo.service.TorreService;
import com.j2km.inmueblesgo.web.generic.GenericLazyDataModel;
import com.j2km.inmueblesgo.web.util.MessageFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

@Named("torreBusinessBean")
@ViewScoped
public class TorreBusinessBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TorreBusinessBean.class.getName()); 
    
    private TorreEntity torre;
    private TipoPlantaEntity tipoPlanta;
    private int pisoInicial;
    private int pisoFinal;
    
    private List<TipoInmuebleEntity> tipoInmuebleList;
    private List<TipoPlantaEntity> tipoPlantaList;
    
    @Inject
    private TorreService torreService;
    
    @Inject
    private InmuebleService inmuebleService;
    
    @Inject
    private TipoInmuebleService tipoInmuebleService;
    
    @Inject
    private TipoPlantaService tipoPlantaService;

    public TorreEntity getTorre() {
        return torre;
    }

    public void setTorre(TorreEntity torre) {
        this.torre = torre;
    }  

    public List<TipoInmuebleEntity> getTipoInmuebleList() {
        if(tipoInmuebleList == null){
            tipoInmuebleList = tipoInmuebleService.findAllTipoInmuebleEntities();
        }
        return tipoInmuebleList;
    }

    public void setTipoInmuebleList(List<TipoInmuebleEntity> tipoInmuebleList) {
        this.tipoInmuebleList = tipoInmuebleList;
    }      

    public List<TipoPlantaEntity> getTipoPlantaList() {
        if(tipoPlantaList == null){
            tipoPlantaList = tipoPlantaService.findAllTipoPlantaEntities();
        }
        return tipoPlantaList;
    }

    public void setTipoPlantaList(List<TipoPlantaEntity> tipoPlantaList) {
        this.tipoPlantaList = tipoPlantaList;
    }    

    public TipoPlantaEntity getTipoPlanta() {
        return tipoPlanta;
    }

    public void setTipoPlanta(TipoPlantaEntity tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
    }    

    public int getPisoInicial() {
        return pisoInicial;
    }

    public void setPisoInicial(int pisoInicial) {
        this.pisoInicial = pisoInicial;
    }

    public int getPisoFinal() {
        return pisoFinal;
    }

    public void setPisoFinal(int pisoFinal) {
        this.pisoFinal = pisoFinal;
    }
    
    public void generarMasivo(){
        
        InmuebleEntity inmueble;
        List <TipoPlantaDetalleEntity> tipoPlantaDetalle =  tipoPlantaService.findAllDetallesByTipoPlanta(tipoPlanta);
        
        for (int i = pisoInicial; i <= pisoFinal; i++){
            System.out.println(i);
            System.out.println(tipoPlanta);
            
            for(TipoPlantaDetalleEntity detalle: tipoPlantaDetalle){
                
                inmueble = new InmuebleEntity();
                inmueble.setArea(detalle.getTipoInmueble().getArea());
                inmueble.setValorMetroCuadrado(detalle.getTipoInmueble().getValorMetroCuadrado());
                inmueble.setProyecto(torre.getProyecto());
                inmueble.setValorSeparacion(detalle.getTipoInmueble().getValorSeparacion());
                inmueble.setNumero(i+" "+detalle.getNumero());
                inmuebleService.save(inmueble);
                
                
                System.out.println(detalle.getTipoInmueble());
            }
        }
    }
    
    public void onLoad(){
            
        
    }

    public void guardarDetalle(){
               
        
    }
}