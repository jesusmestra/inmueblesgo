package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.service.OfertaService;
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

@Named("ofertaBean")
@ViewScoped
public class OfertaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(OfertaBean.class.getName());
    
    private GenericLazyDataModel<OfertaEntity> lazyModel;
    
    private OfertaEntity oferta;
    
    @Inject
    private OfertaService ofertaService;
    
    
    public void prepareNewOferta() {
        reset();
        this.oferta = new OfertaEntity();
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }

    public GenericLazyDataModel<OfertaEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(ofertaService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (oferta.getId() != null) {
                oferta = ofertaService.update(oferta);
                message = "message_successfully_updated";
            } else {
                oferta = ofertaService.save(oferta);
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
            ofertaService.delete(oferta);
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
    
    public void onDialogOpen(OfertaEntity oferta) {
        reset();
        this.oferta = oferta;
    }
    
    public void reset() {
        oferta = null;

        //allTipoIdentificacionsList = null;
        
    }

    
    
    public OfertaEntity getOferta() {
        if (this.oferta == null) {
            prepareNewOferta();
        }
        return this.oferta;
    }
    
    public void setOferta(OfertaEntity oferta) {
        this.oferta = oferta;
    }
    
}
