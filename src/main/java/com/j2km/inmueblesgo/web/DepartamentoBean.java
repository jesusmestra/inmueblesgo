package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.service.DepartamentoRepository;
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

@Named("departamentoBean")
@ViewScoped
public class DepartamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(DepartamentoBean.class.getName());
    
    private DepartamentoEntity departamento;
    
    @Inject
    private DepartamentoRepository departamentoService;
    
    
    public void prepareNewDepartamento() {
        reset();
        this.departamento = new DepartamentoEntity();
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (departamento.getId() != null) {
                departamento = departamentoService.save(departamento);
                message = "message_successfully_updated";
            } else {
                departamento = departamentoService.save(departamento);
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
            departamentoService.remove(departamento);
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
    
    public void onDialogOpen(DepartamentoEntity departamento) {
        reset();
        this.departamento = departamento;
    }
    
    public void reset() {
        departamento = null;

        //allTipoIdentificacionsList = null;
        
    }

    
    
    public DepartamentoEntity getDepartamento() {
        if (this.departamento == null) {
            prepareNewDepartamento();
        }
        return this.departamento;
    }
    
    public void setDepartamento(DepartamentoEntity departamento) {
        this.departamento = departamento;
    }
    
}
