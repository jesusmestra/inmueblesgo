package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.service.NegociacionService;
import com.j2km.inmueblesgo.service.OfertaService;
import com.j2km.inmueblesgo.service.TerceroService;
import com.j2km.inmueblesgo.web.generic.GenericLazyDataModel;
import com.j2km.inmueblesgo.web.util.MessageFactory;

import java.io.Serializable;
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

@Named("negociacionBean")
@ViewScoped
public class NegociacionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(NegociacionBean.class.getName());
    
    private GenericLazyDataModel<NegociacionEntity> lazyModel;
    
    private NegociacionEntity negociacion;
    
    @Inject
    private NegociacionService negociacionService;
    
    @Inject
    private TerceroService terceroService;
    
    private List<TerceroEntity> allTerceroList;
    
    private List<OfertaEntity> allOfertaList;
    
    @Inject
    private OfertaService ofertaService;
    
    private int cantidadCuotas;

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }
    
    

    public List<TerceroEntity> getAllTerceroList() {
        return allTerceroList;
    }

    public void setAllTerceroList(List<TerceroEntity> allTerceroList) {
        this.allTerceroList = allTerceroList;
    }

    public List<OfertaEntity> getAllOfertaList() {
        return allOfertaList;
    }

    public void setAllOfertaList(List<OfertaEntity> allOfertaList) {
        this.allOfertaList = allOfertaList;
    }
    
    
    
    public void prepareNewNegociacion() {
        reset();
        this.negociacion = new NegociacionEntity();
        this.allTerceroList =  terceroService.findAllTerceroEntities();
        this.allOfertaList =  ofertaService.findAllOfertaEntities();
        this.cantidadCuotas = 0;
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }

    public GenericLazyDataModel<NegociacionEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(negociacionService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (negociacion.getId() != null) {
                negociacion = negociacionService.update(negociacion);
                message = "message_successfully_updated";
            } else {
                negociacion = negociacionService.save(negociacion);
                message = "message_successfully_created";
            }
        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_optimistic_locking_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_save_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        
        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            negociacionService.delete(negociacion);
            message = "message_successfully_deleted";
            reset();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_delete_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));
        
        return null;
    }
    
    public void onDialogOpen(NegociacionEntity negociacion) {
        reset();
        this.negociacion = negociacion;
    }
    
    public void reset() {
        negociacion = null;

        //allTipoIdentificacionsList = null;
        
    }

    
    
    public NegociacionEntity getNegociacion() {
        if (this.negociacion == null) {
            prepareNewNegociacion();
        }
        return this.negociacion;
    }
    
    public void setNegociacion(NegociacionEntity negociacion) {
        this.negociacion = negociacion;
    }
    
    public void  cambioOfetra(){
        System.out.println("Cambiando oferta.....");
        System.out.println("Oferta sel..."+this.negociacion.getOferta().getNumeroCuotas());
        if(this.negociacion.getOferta() !=null)
            this.cantidadCuotas = this.negociacion.getOferta().getNumeroCuotas();;
        
        
        
    }
    
}
