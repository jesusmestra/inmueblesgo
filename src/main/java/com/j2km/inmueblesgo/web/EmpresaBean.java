package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.service.EmpresaService;
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

@Named("empresaBean")
@ViewScoped
public class EmpresaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(EmpresaBean.class.getName());
    
    private GenericLazyDataModel<EmpresaEntity> lazyModel;
    
    private EmpresaEntity empresa;
    
    @Inject
    private EmpresaService empresaService;
    
    @Inject
    private PobladoService pobladoService;
    
    private List<PobladoEntity> allPobladosList;
    
    public void prepareNewEmpresa() {
        reset();
        this.empresa = new EmpresaEntity();
        // set any default values now, if you need
        // Example: this.empresa.setAnything("test");
    }

    public GenericLazyDataModel<EmpresaEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(empresaService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (empresa.getId() != null) {
                empresa = empresaService.update(empresa);
                message = "message_successfully_updated";
            } else {
                empresa = empresaService.save(empresa);
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
            empresaService.delete(empresa);
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
    
    public void onDialogOpen(EmpresaEntity empresa) {
        reset();
        this.empresa = empresa;
    }
    
    public void reset() {
        empresa = null;

        allPobladosList = null;
        
    }

    // Get a List of all poblado
    public List<PobladoEntity> getPoblados() {
        if (this.allPobladosList == null) {
            this.allPobladosList = pobladoService.findAllPobladoEntities();
        }
        return this.allPobladosList;
    }
    
    // Update poblado of the current empresa
    public void updatePoblado(PobladoEntity poblado) {
        this.empresa.setPoblado(poblado);
        // Maybe we just created and assigned a new poblado. So reset the allPobladoList.
        allPobladosList = null;
    }
    
    public EmpresaEntity getEmpresa() {
        if (this.empresa == null) {
            prepareNewEmpresa();
        }
        return this.empresa;
    }
    
    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }
    
}
