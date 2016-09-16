package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.service.EmpresaService;
import com.j2km.inmueblesgo.service.OfertaService;
import com.j2km.inmueblesgo.service.ProyectoService;
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

@Named("proyectoBean")
@ViewScoped
public class ProyectoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ProyectoBean.class.getName());
    
    private GenericLazyDataModel<ProyectoEntity> lazyModel;
    
    private ProyectoEntity proyecto;
    
    @Inject
    private ProyectoService proyectoService;
    
    @Inject
    private OfertaService ofertaService;
    
    private List<OfertaEntity> allOfertasList;
    
    
    @Inject
    private EmpresaService empresaService;
    
    private List<EmpresaEntity> allEmpresasList;    
    
    public void prepareNewProyecto() {
        reset();
        this.proyecto = new ProyectoEntity();
        // set any default values now, if you need
        // Example: this.proyecto.setAnything("test");
    }

    public GenericLazyDataModel<ProyectoEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(proyectoService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (proyecto.getId() != null) {
                proyecto = proyectoService.update(proyecto);
                message = "message_successfully_updated";
            } else {
                proyecto = proyectoService.save(proyecto);
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
            proyectoService.delete(proyecto);
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
    
    public void onDialogOpen(ProyectoEntity proyecto) {
        reset();
        this.proyecto = proyecto;
    }
    
    public void reset() {
        proyecto = null;

        allOfertasList = null;
         allEmpresasList = null;
        
    }

    // Get a List of all oferta
    public List<OfertaEntity> getOfertas() {
        if (this.allOfertasList == null) {
            this.allOfertasList = ofertaService.findAllOfertaEntities();
        }
        return this.allOfertasList;
    }
    
    // Update oferta of the current proyecto
    public void updateOferta(OfertaEntity oferta) {
        this.proyecto.setOferta(oferta);
        // Maybe we just created and assigned a new oferta. So reset the allOfertaList.
        allOfertasList = null;
    }
    
     // Get a List of all oferta
    public List<EmpresaEntity> getEmpresas() {
        if (this.allEmpresasList == null) {
            this.allEmpresasList = empresaService.findAllEmpresaEntities();
        }
        return this.allEmpresasList;
    }
    
    // Update empresa of the current proyecto
    public void updateempresa(EmpresaEntity empresa) {
        this.proyecto.setEmpresa(empresa);
        // Maybe we just created and assigned a new empresa. So reset the allEmpresaList.
        allEmpresasList = null;
    }
    
    
    
    
    public ProyectoEntity getProyecto() {
        if (this.proyecto == null) {
            prepareNewProyecto();
        }
        return this.proyecto;
    }
    
    public void setProyecto(ProyectoEntity proyecto) {
        this.proyecto = proyecto;
    }
    
}
