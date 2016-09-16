package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.service.EstadoInmuebleService;
import com.j2km.inmueblesgo.web.generic.GenericLazyDataModel;
import com.j2km.inmueblesgo.web.util.MessageFactory;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

@Named("estadoInmuebleBean")
@ViewScoped
public class EstadoInmuebleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(EstadoInmuebleBean.class.getName());
    
    private GenericLazyDataModel<EstadoInmuebleEntity> lazyModel;
    
    private EstadoInmuebleEntity estadoInmueble;
    
    @Inject
    private EstadoInmuebleService estadoInmuebleService;
    
    
    public void prepareNewEstadoInmueble() {
        reset();
        this.estadoInmueble = new EstadoInmuebleEntity();
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }

    public GenericLazyDataModel<EstadoInmuebleEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(estadoInmuebleService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (estadoInmueble.getId() != null) {
                estadoInmueble = estadoInmuebleService.update(estadoInmueble);
                message = "message_successfully_updated";
            } else {
                estadoInmueble = estadoInmuebleService.save(estadoInmueble);
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
            estadoInmuebleService.delete(estadoInmueble);
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
    
    public void onDialogOpen(EstadoInmuebleEntity estadoInmueble) {
        reset();
        this.estadoInmueble = estadoInmueble;
    }
    
    public void reset() {
        estadoInmueble = null;

        //allTipoIdentificacionsList = null;
        
    }

    
    
    public EstadoInmuebleEntity getEstadoInmueble() {
        if (this.estadoInmueble == null) {
            prepareNewEstadoInmueble();
        }
        return this.estadoInmueble;
    }
    
    public void setEstadoInmueble(EstadoInmuebleEntity estadoInmueble) {
        this.estadoInmueble = estadoInmueble;
    }
    
}
