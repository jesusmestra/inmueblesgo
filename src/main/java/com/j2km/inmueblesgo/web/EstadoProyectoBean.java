package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import com.j2km.inmueblesgo.service.EstadoProyectoRepository;
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

@Named("estadoProyectoBean")
@ViewScoped
public class EstadoProyectoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EstadoProyectoBean.class.getName());    
    private EstadoProyectoEntity estadoProyecto;
    
    @Inject
    private EstadoProyectoRepository estadoProyectoService;
    
    
    public void prepareNewEstadoProyecto() {
        reset();
        this.estadoProyecto = new EstadoProyectoEntity();
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (estadoProyecto.getId() != null) {
                estadoProyecto = estadoProyectoService.save(estadoProyecto);
                message = "message_successfully_updated";
            } else {
                estadoProyecto = estadoProyectoService.save(estadoProyecto);
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
            estadoProyectoService.remove(estadoProyecto);
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
    
    public void onDialogOpen(EstadoProyectoEntity estadoProyecto) {
        reset();
        this.estadoProyecto = estadoProyecto;
    }
    
    public void reset() {
        estadoProyecto = null;

        //allTipoIdentificacionsList = null;
        
    }

    
    
    public EstadoProyectoEntity getEstadoProyecto() {
        if (this.estadoProyecto == null) {
            prepareNewEstadoProyecto();
        }
        return this.estadoProyecto;
    }
    
    public void setEstadoProyecto(EstadoProyectoEntity estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }
    
}
