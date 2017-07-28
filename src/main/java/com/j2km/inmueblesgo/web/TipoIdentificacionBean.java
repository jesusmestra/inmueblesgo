package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import com.j2km.inmueblesgo.service.TipoIdentificacionRepository;
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

@Named("tipoIdentificacionBean")
@ViewScoped
public class TipoIdentificacionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TipoIdentificacionBean.class.getName());
    
    private TipoIdentificacionEntity tipoIdentificacion;
    
    private List<TipoIdentificacionEntity> allTipoIdentificacionsList;
    
    @Inject
    private TipoIdentificacionRepository tipoIdentificacionService;
    
    public void prepareNewTipoIdentificacion() {
        reset();
        this.tipoIdentificacion = new TipoIdentificacionEntity();
        allTipoIdentificacionsList = tipoIdentificacionService.findAll();
    }

    public List<TipoIdentificacionEntity> getAllTipoIdentificacionsList() {
        return allTipoIdentificacionsList;
    }

    public void setAllTipoIdentificacionsList(List<TipoIdentificacionEntity> allTipoIdentificacionsList) {
        this.allTipoIdentificacionsList = allTipoIdentificacionsList;
    }

    public String persist() {

        String message;
        
        try {
            
            if (tipoIdentificacion.getId() != null) {
                tipoIdentificacion = tipoIdentificacionService.save(tipoIdentificacion);
                message = "message_successfully_updated";
            } else {
                tipoIdentificacion = tipoIdentificacionService.save(tipoIdentificacion);
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
            tipoIdentificacionService.remove(tipoIdentificacion);
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
    
    public void onDialogOpen(TipoIdentificacionEntity tipoIdentificacion) {
        reset();
        this.tipoIdentificacion = tipoIdentificacion;
    }
    
    public void reset() {
        tipoIdentificacion = null;

    }

    public TipoIdentificacionEntity getTipoIdentificacion() {
        if (this.tipoIdentificacion == null) {
            prepareNewTipoIdentificacion();
        }
        return this.tipoIdentificacion;
    }
    
    public void setTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    
}
