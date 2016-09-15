package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.service.MunicipioService;
import com.j2km.inmueblesgo.service.PobladoService;
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

@Named("pobladoBean")
@ViewScoped
public class PobladoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(PobladoBean.class.getName());
    
    private GenericLazyDataModel<PobladoEntity> lazyModel;
    
    private PobladoEntity poblado;
    
    @Inject
    private PobladoService pobladoService;
    
    @Inject
    private MunicipioService municipioService;
    
    private List<MunicipioEntity> allMunicipiosList;
    
    public void prepareNewPoblado() {
        reset();
        this.poblado = new PobladoEntity();
        // set any default values now, if you need
        // Example: this.poblado.setAnything("test");
    }

    public GenericLazyDataModel<PobladoEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(pobladoService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (poblado.getId() != null) {
                poblado = pobladoService.update(poblado);
                message = "message_successfully_updated";
            } else {
                poblado = pobladoService.save(poblado);
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
            pobladoService.delete(poblado);
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
    
    public void onDialogOpen(PobladoEntity poblado) {
        reset();
        this.poblado = poblado;
    }
    
    public void reset() {
        poblado = null;

        allMunicipiosList = null;
        
    }

    // Get a List of all municipio
    public List<MunicipioEntity> getMunicipios() {
        if (this.allMunicipiosList == null) {
            this.allMunicipiosList = municipioService.findAllMunicipioEntities();
        }
        return this.allMunicipiosList;
    }
    
    // Update municipio of the current poblado
    public void updateMunicipio(MunicipioEntity municipio) {
        this.poblado.setMunicipio(municipio);
        // Maybe we just created and assigned a new municipio. So reset the allMunicipioList.
        allMunicipiosList = null;
    }
    
    public PobladoEntity getPoblado() {
        if (this.poblado == null) {
            prepareNewPoblado();
        }
        return this.poblado;
    }
    
    public void setPoblado(PobladoEntity poblado) {
        this.poblado = poblado;
    }
    
}
