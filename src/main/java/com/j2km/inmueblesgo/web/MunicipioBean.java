package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.service.DepartamentoService;
import com.j2km.inmueblesgo.service.MunicipioService;
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

@Named("municipioBean")
@ViewScoped
public class MunicipioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(MunicipioBean.class.getName());
    
    private GenericLazyDataModel<MunicipioEntity> lazyModel;
    
    private MunicipioEntity municipio;
    
    @Inject
    private MunicipioService municipioService;
    
    @Inject
    private DepartamentoService departamentoService;
    
    private List<DepartamentoEntity> allDepartamentosList;
    
    public void prepareNewMunicipio() {
        reset();
        this.municipio = new MunicipioEntity();
        // set any default values now, if you need
        // Example: this.municipio.setAnything("test");
    }

    public GenericLazyDataModel<MunicipioEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(municipioService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (municipio.getId() != null) {
                municipio = municipioService.update(municipio);
                message = "message_successfully_updated";
            } else {
                municipio = municipioService.save(municipio);
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
            municipioService.delete(municipio);
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
    
    public void onDialogOpen(MunicipioEntity municipio) {
        reset();
        this.municipio = municipio;
    }
    
    public void reset() {
        municipio = null;

        allDepartamentosList = null;
        
    }

    // Get a List of all departamento
    public List<DepartamentoEntity> getDepartamentos() {
        if (this.allDepartamentosList == null) {
            this.allDepartamentosList = departamentoService.findAllDepartamentoEntities();
        }
        return this.allDepartamentosList;
    }
    
    // Update departamento of the current municipio
    public void updateDepartamento(DepartamentoEntity departamento) {
        this.municipio.setDepartamento(departamento);
        // Maybe we just created and assigned a new departamento. So reset the allDepartamentoList.
        allDepartamentosList = null;
    }
    
    public MunicipioEntity getMunicipio() {
        if (this.municipio == null) {
            prepareNewMunicipio();
        }
        return this.municipio;
    }
    
    public void setMunicipio(MunicipioEntity municipio) {
        this.municipio = municipio;
    }
    
}
