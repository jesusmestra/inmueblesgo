package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.TorreEntity;
import com.j2km.inmueblesgo.service.TorreService;
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

@Named("torreBean")
@ViewScoped
public class TorreBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TorreBean.class.getName());
    
    private GenericLazyDataModel<TorreEntity> lazyModel;
    
    private TorreEntity torre;
    
    @Inject
    private TorreService torreService;
    
    
    public void prepareNewTorre() {
        reset();
        this.torre = new TorreEntity();
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }

    public GenericLazyDataModel<TorreEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(torreService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (torre.getId() != null) {
                torre = torreService.update(torre);
                message = "message_successfully_updated";
            } else {
                torre = torreService.save(torre);
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
            torreService.delete(torre);
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
    
    public void onDialogOpen(TorreEntity torre) {
        reset();
        this.torre = torre;
    }
    
    public void reset() {
        torre = null;
        //allTipoIdentificacionsList = null;        
    }

    
    
    public TorreEntity getTorre() {
        if (this.torre == null) {
            prepareNewTorre();
        }
        return this.torre;
    }
    
    public void setTorre(TorreEntity torre) {
        this.torre = torre;
    }
    
}
