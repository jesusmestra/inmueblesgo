package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.TipoPlantaEntity;
import com.j2km.inmueblesgo.service.TipoPlantaRepository;
import com.j2km.inmueblesgo.service.TipoPlantaService;
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

@Named("tipoPlantaBean")
@ViewScoped
public class TipoPlantaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TipoPlantaBean.class.getName());
    
    private TipoPlantaEntity tipoPlanta;
    private List<TipoPlantaEntity> tipoPlantaList;
    
    @Inject
    private TipoPlantaRepository tipoPlantaService;
    
    
    public void prepareNewTipoPlanta() {
        reset();
        this.tipoPlanta = new TipoPlantaEntity();
        
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }

    public List<TipoPlantaEntity> getTipoPlantaList() {
        tipoPlantaList = tipoPlantaService.findAll();
        return tipoPlantaList;
    }

    public void setTipoPlantaList(List<TipoPlantaEntity> tipoPlantaList) {
        this.tipoPlantaList = tipoPlantaList;
    }
    
    
    
    public String persist() {

        String message;
        
        try {
            
            if (tipoPlanta.getId() != null) {
                tipoPlanta = tipoPlantaService.save(tipoPlanta);
                message = "message_successfully_updated";
            } else {
                tipoPlanta = tipoPlantaService.save(tipoPlanta);
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
            tipoPlantaService.remove(tipoPlanta);
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
    
    public void onDialogOpen(TipoPlantaEntity tipoPlanta) {
        reset();
        this.tipoPlanta = tipoPlanta;
    }
    
    public void reset() {
        tipoPlanta = null;

        //allTipoIdentificacionsList = null;
        
    }
    
    public TipoPlantaEntity getTipoPlanta() {
        if (this.tipoPlanta == null) {
            prepareNewTipoPlanta();
        }
        return this.tipoPlanta;
    }
    
    public void setTipoPlanta(TipoPlantaEntity tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
    }
    
}
