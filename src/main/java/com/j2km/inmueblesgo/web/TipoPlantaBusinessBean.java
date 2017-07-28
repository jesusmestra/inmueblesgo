package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.TipoInmuebleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaDetalleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaEntity;
import com.j2km.inmueblesgo.service.TipoInmuebleRepository;
import com.j2km.inmueblesgo.service.TipoInmuebleService;
import com.j2km.inmueblesgo.service.TipoPlantaDetalleRepository;
import com.j2km.inmueblesgo.service.TipoPlantaDetalleService;
import com.j2km.inmueblesgo.service.TipoPlantaRepository;
import com.j2km.inmueblesgo.service.TipoPlantaService;
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

@Named("tipoPlantaBusinessBean")
@ViewScoped
public class TipoPlantaBusinessBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TipoPlantaBusinessBean.class.getName()); 
    
    private Long tipoPlantaId;
    private TipoPlantaEntity tipoPlanta;    
    private List<TipoPlantaDetalleEntity> detalles;
    private List<TipoInmuebleEntity> tipoInmuebleList;
    
    @Inject
    private TipoPlantaRepository tipoPlantaService;
    
    @Inject
    private TipoPlantaDetalleRepository tipoPlantaDetalleService;
    
    @Inject
    private TipoInmuebleRepository tipoInmuebleService;

    public Long getTipoPlantaId() {
        return tipoPlantaId;
    }

    public void setTipoPlantaId(Long tipoPlantaId) {
        this.tipoPlantaId = tipoPlantaId;
    }
    
    public TipoPlantaEntity getTipoPlanta() {
        return tipoPlanta;
    }

    public void setTipoPlanta(TipoPlantaEntity tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
    }

    public List<TipoPlantaDetalleEntity> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<TipoPlantaDetalleEntity> detalles) {
        this.detalles = detalles;
    }   

    public List<TipoInmuebleEntity> getTipoInmuebleList() {
        if(tipoInmuebleList == null){
            tipoInmuebleList = tipoInmuebleService.findAll();
        }
        return tipoInmuebleList;
    }

    public void setTipoInmuebleList(List<TipoInmuebleEntity> tipoInmuebleList) {
        this.tipoInmuebleList = tipoInmuebleList;
    }    
    
    public void onLoad(){
        if(this.tipoPlantaId != null){
            tipoPlanta = tipoPlantaService.findBy(tipoPlantaId);
            detalles = tipoPlantaDetalleService.findByTipoPlanta(tipoPlanta);
            TipoPlantaDetalleEntity entity;
            
            if (detalles == null || detalles.isEmpty()){
                detalles = new ArrayList<>();
                for(int i = 0; i < tipoPlanta.getNumeroInmuebles(); i++ ){
                    entity =  new TipoPlantaDetalleEntity();
                    entity.setTipoPlanta(tipoPlanta);
                    entity.setNumero(i+1);
                    detalles.add(entity);
                }
            }
        }    
        
    }

    public void guardarDetalle(){
        for (TipoPlantaDetalleEntity detalle : detalles) {
            tipoPlantaDetalleService.save(detalle);            
        }
        
        detalles = tipoPlantaDetalleService.findByTipoPlanta(tipoPlanta);
        FacesMessage facesMsg = new FacesMessage(
                            FacesMessage.SEVERITY_INFO, 
                            "Detalle Actualizado", 
                            "Detalle Actualizado");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);        
        
    }
}