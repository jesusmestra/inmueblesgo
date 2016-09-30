package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.TipoInmuebleEntity;
import com.j2km.inmueblesgo.service.TipoInmuebleService;
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

@Named("tipoInmuebleBean")
@ViewScoped
public class TipoInmuebleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TipoInmuebleBean.class.getName());
    
    private GenericLazyDataModel<TipoInmuebleEntity> lazyModel;
    
    private TipoInmuebleEntity tipoInmueble;
    
    @Inject
    private TipoInmuebleService tipoInmuebleService;
    
    
    public void prepareNewTipoInmueble() {
        reset();
        this.tipoInmueble = new TipoInmuebleEntity();
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }

    public GenericLazyDataModel<TipoInmuebleEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(tipoInmuebleService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (tipoInmueble.getId() != null) {
                tipoInmueble = tipoInmuebleService.update(tipoInmueble);
                message = "message_successfully_updated";
            } else {
                tipoInmueble = tipoInmuebleService.save(tipoInmueble);
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
            tipoInmuebleService.delete(tipoInmueble);
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
    
    public void onDialogOpen(TipoInmuebleEntity tipoInmueble) {
        reset();
        this.tipoInmueble = tipoInmueble;
    }
    
    public void reset() {
        tipoInmueble = null;

        //allTipoIdentificacionsList = null;
        
    }

    
    
    public TipoInmuebleEntity getTipoInmueble() {
        if (this.tipoInmueble == null) {
            prepareNewTipoInmueble();
        }
        return this.tipoInmueble;
    }
    
    public void setTipoInmueble(TipoInmuebleEntity tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }
    
}
